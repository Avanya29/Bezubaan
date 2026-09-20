import {
  Injectable,
  BadRequestException,
  NotFoundException,
} from '@nestjs/common';
import { PrismaService } from '../database/prisma.service';
import { EventsService } from '../events/events.service';
import { CreateFosterApplicationDto } from './dto/create-foster-application.dto';
import { ExtendFosterAssignmentDto } from './dto/extend-foster-assignment.dto';
import {
  ApplicationStatus,
  FosterAssignmentStatus,
  Role,
} from '@prisma/client';

@Injectable()
export class FostersService {
  constructor(
    private readonly prisma: PrismaService,
    private readonly eventsService: EventsService,
  ) {}

  async apply(userId: string, dto: CreateFosterApplicationDto) {
    const animal = await this.prisma.animal.findUnique({
      where: { id: dto.animalId },
    });

    if (!animal) {
      throw new NotFoundException('Animal not found');
    }

    return this.prisma.fosterApplication.create({
      data: {
        applicantId: userId,
        animalId: dto.animalId,
        reason: dto.reason,
        housingInformation: dto.housingInformation,
        householdInformation: dto.householdInformation,
        existingAnimals: dto.existingAnimals,
        priorAnimalCareExperience: dto.priorAnimalCareExperience,
        availability: dto.availability,
        additionalNotes: dto.additionalNotes,
        status: ApplicationStatus.PENDING,
      },
    });
  }

  async approve(id: string) {
    const application = await this.prisma.fosterApplication.findUnique({
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

    const expectedEndDate = new Date();
    expectedEndDate.setDate(expectedEndDate.getDate() + 14);

    const result = await this.prisma.$transaction(async (tx) => {
      const approvedApp = await tx.fosterApplication.update({
        where: { id },
        data: { status: ApplicationStatus.APPROVED },
      });

      await tx.fosterAssignment.create({
        data: {
          fosterApplicationId: id,
          startDate: new Date(),
          expectedEndDate,
          status: FosterAssignmentStatus.ACTIVE,
        },
      });

      return approvedApp;
    });

    this.eventsService.emit('foster.application.approved', {
      applicationId: id,
    });

    return result;
  }

  async extend(assignmentId: string, dto: ExtendFosterAssignmentDto) {
    const assignment = await this.prisma.fosterAssignment.findUnique({
      where: { id: assignmentId },
    });

    if (!assignment) {
      throw new NotFoundException('Assignment not found');
    }

    if (
      assignment.status !== FosterAssignmentStatus.ACTIVE &&
      assignment.status !== FosterAssignmentStatus.EXTENDED
    ) {
      throw new BadRequestException(
        'Only active or extended assignments can be extended',
      );
    }

    return this.prisma.fosterAssignment.update({
      where: { id: assignmentId },
      data: {
        expectedEndDate: new Date(dto.expectedEndDate),
        status: FosterAssignmentStatus.EXTENDED,
      },
    });
  }

  async complete(assignmentId: string) {
    const assignment = await this.prisma.fosterAssignment.findUnique({
      where: { id: assignmentId },
    });

    if (!assignment) {
      throw new NotFoundException('Assignment not found');
    }

    if (
      assignment.status !== FosterAssignmentStatus.ACTIVE &&
      assignment.status !== FosterAssignmentStatus.EXTENDED
    ) {
      throw new BadRequestException(
        'Only active or extended assignments can be completed',
      );
    }

    return this.prisma.fosterAssignment.update({
      where: { id: assignmentId },
      data: {
        status: FosterAssignmentStatus.COMPLETED,
        actualEndDate: new Date(),
      },
    });
  }

  async findAll(user: any) {
    if (user.role === Role.ADMIN || user.role === Role.NGO_ADMIN) {
      return this.prisma.fosterApplication.findMany({
        include: {
          animal: true,
          applicant: { select: { id: true, email: true, profile: true } },
          assignments: true,
        },
        orderBy: { createdAt: 'desc' },
      });
    }

    return this.prisma.fosterApplication.findMany({
      where: { applicantId: user.id },
      include: { animal: true, assignments: true },
      orderBy: { createdAt: 'desc' },
    });
  }
}
