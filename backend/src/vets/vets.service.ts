import {
  Injectable,
  NotFoundException,
  ConflictException,
} from '@nestjs/common';
import { PrismaService } from '../database/prisma.service';
import { ApplyVetDto } from './dto/apply-vet.dto';
import { Role, VetApprovalStatus } from '@prisma/client';

@Injectable()
export class VetsService {
  constructor(private readonly prisma: PrismaService) {}

  async apply(userId: string, applyVetDto: ApplyVetDto) {
    const existing = await this.prisma.vetProfile.findUnique({
      where: { userId },
    });

    if (existing) {
      throw new ConflictException('Application already exists');
    }

    return this.prisma.vetProfile.create({
      data: {
        userId,
        licenseNumber: applyVetDto.licenseNumber,
        clinicName: applyVetDto.clinicName,
        approvalStatus: VetApprovalStatus.PENDING,
      },
    });
  }

  async approve(id: string) {
    const profile = await this.prisma.vetProfile.findUnique({
      where: { id },
      include: { user: true },
    });

    if (!profile) {
      throw new NotFoundException('Vet profile not found');
    }

    if (profile.approvalStatus === VetApprovalStatus.APPROVED) {
      throw new ConflictException('Profile already approved');
    }

    return this.prisma.$transaction(async (tx) => {
      const updatedProfile = await tx.vetProfile.update({
        where: { id },
        data: { approvalStatus: VetApprovalStatus.APPROVED },
      });

      await tx.user.update({
        where: { id: profile.userId },
        data: { role: Role.VETERINARIAN },
      });

      return updatedProfile;
    });
  }

  async getCasesForVet(vetUserId: string) {
    return this.prisma.rescueCase.findMany({
      where: {
        medicalRecords: {
          some: {
            vetId: vetUserId,
          },
        },
      },
      distinct: ['id'],
    });
  }
}
