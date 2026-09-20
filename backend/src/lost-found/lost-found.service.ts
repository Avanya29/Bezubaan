import {
  Injectable,
  BadRequestException,
  NotFoundException,
  ForbiddenException,
} from '@nestjs/common';
import { PrismaService } from '../database/prisma.service';
import { CreateLostFoundDto } from './dto/create-lost-found.dto';
import { UpdateLostFoundStatusDto } from './dto/update-lost-found-status.dto';
import { LostFoundStatus, LostFoundIncident } from '@prisma/client';

@Injectable()
export class LostFoundService {
  constructor(private readonly prisma: PrismaService) {}

  private isValidTransition(
    currentStatus: LostFoundStatus,
    nextStatus: LostFoundStatus,
  ): boolean {
    const validTransitions: Record<LostFoundStatus, LostFoundStatus[]> = {
      [LostFoundStatus.LOST]: [LostFoundStatus.SEARCHING],
      [LostFoundStatus.SEARCHING]: [
        LostFoundStatus.FOUND,
        LostFoundStatus.CLOSED,
      ],
      [LostFoundStatus.FOUND]: [LostFoundStatus.REUNITED],
      [LostFoundStatus.REUNITED]: [LostFoundStatus.CLOSED],
      [LostFoundStatus.CLOSED]: [],
    };

    return validTransitions[currentStatus]?.includes(nextStatus) ?? false;
  }

  async create(
    reporterId: string,
    dto: CreateLostFoundDto,
  ): Promise<LostFoundIncident> {
    const status = dto.status ?? LostFoundStatus.LOST;

    if (status !== LostFoundStatus.LOST && status !== LostFoundStatus.FOUND) {
      throw new BadRequestException('Initial status must be LOST or FOUND');
    }

    return this.prisma.lostFoundIncident.create({
      data: {
        reporterId,
        animalType: dto.animalType,
        description: dto.description,
        lastSeenLocation: dto.lastSeenLocation,
        status: status,
      },
    });
  }

  async findAll(): Promise<LostFoundIncident[]> {
    return this.prisma.lostFoundIncident.findMany({
      orderBy: { createdAt: 'desc' },
    });
  }

  async findOne(id: string): Promise<LostFoundIncident> {
    const incident = await this.prisma.lostFoundIncident.findUnique({
      where: { id },
    });

    if (!incident) {
      throw new NotFoundException(`Incident with ID ${id} not found`);
    }

    return incident;
  }

  async updateStatus(
    id: string,
    reporterId: string,
    dto: UpdateLostFoundStatusDto,
  ): Promise<LostFoundIncident> {
    const incident = await this.findOne(id);

    if (incident.reporterId !== reporterId) {
      throw new ForbiddenException(
        'You can only update your own reported incidents',
      );
    }

    if (!this.isValidTransition(incident.status, dto.status)) {
      throw new BadRequestException(
        `Invalid state transition from ${incident.status} to ${dto.status}`,
      );
    }

    return this.prisma.lostFoundIncident.update({
      where: { id },
      data: { status: dto.status },
    });
  }

  async remove(id: string, reporterId: string): Promise<void> {
    const incident = await this.findOne(id);

    if (incident.reporterId !== reporterId) {
      throw new ForbiddenException(
        'You can only delete your own reported incidents',
      );
    }

    await this.prisma.lostFoundIncident.delete({
      where: { id },
    });
  }
}
