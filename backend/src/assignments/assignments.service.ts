import {
  Injectable,
  NotFoundException,
  BadRequestException,
  ForbiddenException,
} from '@nestjs/common';
import { PrismaService } from '../database/prisma.service';
import { CreateAssignmentDto, RejectAssignmentDto } from './dto/assignment.dto';
import { AssignmentStatus, RescueStatus } from '@prisma/client';

@Injectable()
export class AssignmentsService {
  constructor(private readonly prisma: PrismaService) {}

  async createAssignment(adminId: string, dto: CreateAssignmentDto) {
    const rescueCase = await this.prisma.rescueCase.findUnique({
      where: { id: dto.rescueCaseId },
    });

    if (!rescueCase) {
      throw new NotFoundException('Rescue Case not found.');
    }

    const volunteer = await this.prisma.volunteerProfile.findUnique({
      where: { id: dto.volunteerId },
      include: {
        _count: {
          select: {
            assignments: {
              where: { status: { in: ['PENDING', 'ACCEPTED', 'REASSIGNED'] } },
            },
          },
        },
      },
    });

    if (!volunteer) {
      throw new NotFoundException('Volunteer Profile not found.');
    }

    // Capacity Logic: Max 2 active assignments
    const activeCount = volunteer._count.assignments;
    if (activeCount >= 2 && !dto.overrideCapacityLimit) {
      throw new BadRequestException(
        'Volunteer is at maximum capacity (2). Use override flag if necessary.',
      );
    }

    // Determine if Primary (first active assignment for this case)
    const existingPrimary = await this.prisma.rescueAssignment.findFirst({
      where: {
        rescueCaseId: dto.rescueCaseId,
        status: { in: ['PENDING', 'ACCEPTED'] },
        isPrimary: true,
      },
    });

    // We do this in a transaction to safely assign and transition rescue status
    return this.prisma.$transaction(async (tx: any) => {
      const assignment = await tx.rescueAssignment.create({
        data: {
          rescueCaseId: dto.rescueCaseId,
          volunteerId: dto.volunteerId,
          assignedById: adminId,
          status: AssignmentStatus.PENDING,
          isPrimary: !existingPrimary,
        },
      });

      // Update Rescue status if needed
      if (rescueCase.status === RescueStatus.VOLUNTEER_SEARCH) {
        await tx.rescueCase.update({
          where: { id: rescueCase.id },
          data: { status: RescueStatus.VOLUNTEER_ASSIGNED },
        });
      }

      // TODO: Dispatch Event for Timeout Worker (15m expiry) and Notifications
      return assignment;
    });
  }

  async acceptAssignment(userId: string, assignmentId: string) {
    const assignment = await this.prisma.rescueAssignment.findUnique({
      where: { id: assignmentId },
      include: { volunteer: true, rescueCase: true },
    });

    if (!assignment) throw new NotFoundException('Assignment not found.');
    if (assignment.volunteer.userId !== userId)
      throw new ForbiddenException('Not your assignment.');
    if (assignment.status !== AssignmentStatus.PENDING)
      throw new BadRequestException(
        `Cannot accept assignment in status ${assignment.status}`,
      );

    const now = new Date();
    // 15m timeout check
    const diffMins = (now.getTime() - assignment.assignedAt.getTime()) / 60000;
    if (diffMins > 15) {
      // It should ideally be expired by a background worker, but we catch it here just in case.
      await this.prisma.rescueAssignment.update({
        where: { id: assignmentId },
        data: { status: AssignmentStatus.EXPIRED, respondedAt: now },
      });
      throw new BadRequestException(
        'Assignment has expired (timeout after 15m).',
      );
    }

    return this.prisma.$transaction(async (tx: any) => {
      const updated = await tx.rescueAssignment.update({
        where: { id: assignmentId },
        data: { status: AssignmentStatus.ACCEPTED, respondedAt: now },
      });

      if (assignment.rescueCase.status === RescueStatus.VOLUNTEER_ASSIGNED) {
        await tx.rescueCase.update({
          where: { id: assignment.rescueCaseId },
          data: { status: RescueStatus.VOLUNTEER_ACCEPTED },
        });
      }

      return updated;
    });
  }

  async rejectAssignment(
    userId: string,
    assignmentId: string,
    dto: RejectAssignmentDto,
  ) {
    const assignment = await this.prisma.rescueAssignment.findUnique({
      where: { id: assignmentId },
      include: { volunteer: true, rescueCase: true },
    });

    if (!assignment) throw new NotFoundException('Assignment not found.');
    if (assignment.volunteer.userId !== userId)
      throw new ForbiddenException('Not your assignment.');
    if (
      assignment.status !== AssignmentStatus.PENDING &&
      assignment.status !== AssignmentStatus.ACCEPTED
    ) {
      throw new BadRequestException(
        `Cannot reject assignment in status ${assignment.status}`,
      );
    }

    return this.prisma.$transaction(async (tx: any) => {
      const updated = await tx.rescueAssignment.update({
        where: { id: assignmentId },
        data: {
          status: AssignmentStatus.REJECTED,
          rejectionReason: dto.rejectionReason,
          respondedAt: new Date(),
        },
      });

      // If they were primary, rescue returns to search
      if (
        (assignment.isPrimary &&
          assignment.rescueCase.status === RescueStatus.VOLUNTEER_ASSIGNED) ||
        assignment.rescueCase.status === RescueStatus.VOLUNTEER_ACCEPTED
      ) {
        await tx.rescueCase.update({
          where: { id: assignment.rescueCaseId },
          data: { status: RescueStatus.VOLUNTEER_SEARCH },
        });
      }

      return updated;
    });
  }
}
