import {
  Injectable,
  BadRequestException,
  NotFoundException,
} from '@nestjs/common';
import { PrismaService } from '../database/prisma.service';
import { EventsService } from '../events/events.service';
import { CreateAdoptionApplicationDto } from './dto/create-adoption-application.dto';
import { ApplicationStatus, Role } from '@prisma/client';

@Injectable()
export class AdoptionsService {
  constructor(
    private readonly prisma: PrismaService,
    private readonly eventsService: EventsService,
  ) {}

  async apply(userId: string, dto: CreateAdoptionApplicationDto) {
    const animal = await this.prisma.animal.findUnique({
      where: { id: dto.animalId },
      include: {
        AdoptionApplication: {
          where: { status: ApplicationStatus.APPROVED },
        },
      },
    });

    if (!animal) {
      throw new NotFoundException('Animal not found');
    }

    if (animal.AdoptionApplication.length > 0) {
      throw new BadRequestException('Animal is already adopted');
    }

    const application = await this.prisma.adoptionApplication.create({
      data: {
        applicantId: userId,
        animalId: dto.animalId,
        motivation: dto.motivation,
        housingType: dto.housingType,
        householdInformation: dto.householdInformation,
        previousPetExperience: dto.previousPetExperience,
        existingAnimals: dto.existingAnimals,
        additionalNotes: dto.additionalNotes,
        status: ApplicationStatus.PENDING,
      },
    });

    this.eventsService.emit('adoption.application.created', {
      applicationId: application.id,
    });

    return application;
  }

  async approve(id: string) {
    const application = await this.prisma.adoptionApplication.findUnique({
      where: { id },
    });

    if (!application) {
      throw new NotFoundException('Application not found');
    }

    if (application.status !== ApplicationStatus.PENDING) {
      throw new BadRequestException(
        'Only pending applications can be approved',
      );
    }

    const updatedApplication = await this.prisma.$transaction(async (tx) => {
      // Approve the current application
      const approved = await tx.adoptionApplication.update({
        where: { id },
        data: { status: ApplicationStatus.APPROVED },
      });

      // Reject all other pending applications for the same animal
      await tx.adoptionApplication.updateMany({
        where: {
          animalId: application.animalId,
          id: { not: id },
          status: ApplicationStatus.PENDING,
        },
        data: { status: ApplicationStatus.REJECTED },
      });

      return approved;
    });

    this.eventsService.emit('adoption.application.approved', {
      applicationId: id,
    });

    return updatedApplication;
  }

  async reject(id: string) {
    const application = await this.prisma.adoptionApplication.findUnique({
      where: { id },
    });

    if (!application) {
      throw new NotFoundException('Application not found');
    }

    if (application.status !== ApplicationStatus.PENDING) {
      throw new BadRequestException(
        'Only pending applications can be rejected',
      );
    }

    return this.prisma.adoptionApplication.update({
      where: { id },
      data: { status: ApplicationStatus.REJECTED },
    });
  }

  async findAll(user: any) {
    if (user.role === Role.ADMIN || user.role === Role.NGO_ADMIN) {
      return this.prisma.adoptionApplication.findMany({
        include: {
          animal: true,
          applicant: {
            select: { id: true, email: true, profile: true },
          },
        },
        orderBy: { createdAt: 'desc' },
      });
    }

    return this.prisma.adoptionApplication.findMany({
      where: { applicantId: user.id },
      include: {
        animal: true,
      },
      orderBy: { createdAt: 'desc' },
    });
  }
}
