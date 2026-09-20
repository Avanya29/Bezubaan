import { Injectable, ConflictException } from '@nestjs/common';
import { PrismaService } from '../database/prisma.service';
import { Prisma, User, Role } from '@prisma/client';

@Injectable()
export class UsersService {
  constructor(private prisma: PrismaService) {}

  async findByEmail(email: string): Promise<User | null> {
    return this.prisma.user.findUnique({ where: { email } });
  }

  async findByGoogleId(googleId: string): Promise<User | null> {
    return this.prisma.user.findUnique({ where: { googleId } });
  }

  async findById(id: string): Promise<User | null> {
    return this.prisma.user.findUnique({
      where: { id },
      include: { profile: true },
    });
  }

  async createLocalUser(data: {
    email: string;
    passwordHash: string;
    role?: Role;
  }): Promise<User> {
    const existing = await this.findByEmail(data.email);
    if (existing) {
      throw new ConflictException('User with this email already exists');
    }
    return this.prisma.user.create({
      data: {
        email: data.email,
        passwordHash: data.passwordHash,
        role: data.role || 'USER',
        profile: {
          create: {}, // Create an empty profile by default
        },
      },
    });
  }

  async createGoogleUser(data: {
    email: string;
    googleId: string;
  }): Promise<User> {
    return this.prisma.user.create({
      data: {
        email: data.email,
        googleId: data.googleId,
        role: 'USER',
        profile: {
          create: {},
        },
      },
    });
  }

  async updateProfile(
    userId: string,
    data: { phoneNumber?: string; address?: string },
  ) {
    return this.prisma.userProfile.update({
      where: { userId },
      data,
    });
  }
}
