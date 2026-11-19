import {
  Injectable,
  NotFoundException,
  BadRequestException,
} from '@nestjs/common';
import { PrismaService } from '../database/prisma.service';
import {
  VolunteerApprovalStatus,
  Role,
  VolunteerAvailabilityStatus,
} from '@prisma/client';
import {
  ApplyVolunteerDto,
  UpdateAvailabilityDto,
  UpdateLocationDto,
} from './dto/volunteer.dto';

@Injectable()
export class VolunteersService {
  constructor(private readonly prisma: PrismaService) {}

  async apply(userId: string, dto: ApplyVolunteerDto) {
    const existing = await this.prisma.volunteerProfile.findUnique({
      where: { userId },
    });

    if (existing) {
      throw new BadRequestException(
        'You have already applied to be a volunteer.',
      );
    }

    return this.prisma.volunteerProfile.create({
      data: {
        userId,
        serviceArea: dto.serviceArea,
        capabilities: dto.capabilities,
        bio: dto.bio,
        experience: dto.experience,
        emergencyContact: dto.emergencyContact,
        preferredRescueTypes: dto.preferredRescueTypes,
        additionalNotes: dto.additionalNotes,
        approvalStatus: VolunteerApprovalStatus.PENDING,
      },
    });
  }

  async approve(volunteerId: string) {
    const profile = await this.prisma.volunteerProfile.findUnique({
      where: { id: volunteerId },
    });

    if (!profile) {
      throw new NotFoundException('Volunteer profile not found.');
    }

    // Update profile and grant the VOLUNTEER role
    const updatedProfile = await this.prisma.volunteerProfile.update({
      where: { id: volunteerId },
      data: { approvalStatus: VolunteerApprovalStatus.APPROVED },
    });

    await this.prisma.user.update({
      where: { id: profile.userId },
      data: { role: Role.VOLUNTEER },
    });

    return updatedProfile;
  }

  async updateAvailability(userId: string, dto: UpdateAvailabilityDto) {
    return this.prisma.volunteerProfile.upsert({
      where: { userId },
      create: {
        userId,
        approvalStatus: VolunteerApprovalStatus.APPROVED,
        availabilityStatus: dto.availabilityStatus,
        currentLatitude: dto.currentLatitude,
        currentLongitude: dto.currentLongitude,
      },
      update: {
        approvalStatus: VolunteerApprovalStatus.APPROVED,
        availabilityStatus: dto.availabilityStatus,
        ...(dto.currentLatitude != null ? { currentLatitude: dto.currentLatitude } : {}),
        ...(dto.currentLongitude != null ? { currentLongitude: dto.currentLongitude } : {}),
      },
    });
  }

  async updateLocation(userId: string, dto: UpdateLocationDto) {
    return this.prisma.volunteerProfile.upsert({
      where: { userId },
      create: {
        userId,
        approvalStatus: VolunteerApprovalStatus.APPROVED,
        availabilityStatus: VolunteerAvailabilityStatus.AVAILABLE,
        currentLatitude: dto.currentLatitude,
        currentLongitude: dto.currentLongitude,
      },
      update: {
        approvalStatus: VolunteerApprovalStatus.APPROVED,
        currentLatitude: dto.currentLatitude,
        currentLongitude: dto.currentLongitude,
      },
    });
  }

  async getCandidates() {
    // Return available and approved volunteers
    return this.prisma.volunteerProfile.findMany({
      where: {
        approvalStatus: VolunteerApprovalStatus.APPROVED,
        availabilityStatus: VolunteerAvailabilityStatus.AVAILABLE,
      },
      include: {
        user: {
          select: {
            id: true,
            email: true,
            profile: true,
          },
        },
        _count: {
          select: {
            assignments: {
              where: {
                status: {
                  in: ['PENDING', 'ACCEPTED'],
                },
              },
            },
          },
        },
      },
    });
  }
}
