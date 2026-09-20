import { Injectable, Inject, NotFoundException } from '@nestjs/common';
import { PrismaService } from '../database/prisma.service';
import { UpdatePreferenceDto } from './dto/update-preference.dto';
import { ClientProxy } from '@nestjs/microservices';
import { NotificationCategory, NotificationChannel } from '@prisma/client';

@Injectable()
export class NotificationsService {
  constructor(
    private prisma: PrismaService,
    @Inject('RABBITMQ_SERVICE') private rabbitClient: ClientProxy,
  ) {}

  async getUserNotifications(userId: string) {
    return this.prisma.notification.findMany({
      where: { userId },
      orderBy: { createdAt: 'desc' },
      take: 50,
    });
  }

  async markAsRead(userId: string, notificationId: string) {
    const notification = await this.prisma.notification.findUnique({
      where: { id: notificationId },
    });

    if (!notification || notification.userId !== userId) {
      throw new NotFoundException('Notification not found');
    }

    return this.prisma.notification.update({
      where: { id: notificationId },
      data: { isRead: true },
    });
  }

  async getUserPreferences(userId: string) {
    return this.prisma.notificationPreference.findMany({
      where: { userId },
    });
  }

  async updatePreference(userId: string, dto: UpdatePreferenceDto) {
    // SECURITY cannot be disabled according to rules
    if (dto.category === 'SECURITY' && dto.isEnabled === false) {
      throw new Error('SECURITY notifications cannot be disabled');
    }

    return this.prisma.notificationPreference.upsert({
      where: {
        userId_category: {
          userId,
          category: dto.category,
        },
      },
      update: { isEnabled: dto.isEnabled },
      create: {
        userId,
        category: dto.category,
        isEnabled: dto.isEnabled,
      },
    });
  }

  // Abstraction for dispatching notifications from business logic
  async dispatch(
    userId: string,
    category: NotificationCategory,
    channel: NotificationChannel,
    title: string,
    message: string,
    payload?: any,
  ) {
    // Check preference
    const pref = await this.prisma.notificationPreference.findUnique({
      where: { userId_category: { userId, category } },
    });

    if (pref && !pref.isEnabled && category !== 'SECURITY') {
      // User opted out
      return;
    }

    // Persist in-app if it's IN_APP
    if (channel === 'IN_APP') {
      await this.prisma.notification.create({
        data: {
          userId,
          category,
          channel,
          title,
          message,
          payload: payload || {},
        },
      });
    }

    // Dispatch to RabbitMQ for async processing (email/push)
    this.rabbitClient.emit('notification.dispatch', {
      userId,
      category,
      channel,
      title,
      message,
      payload,
    });
  }
}
