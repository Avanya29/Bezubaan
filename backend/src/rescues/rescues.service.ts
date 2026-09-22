import {
  Injectable,
  BadRequestException,
  ForbiddenException,
  NotFoundException,
} from '@nestjs/common';
import { PrismaService } from '../database/prisma.service';
import {
  CreateRescueDto,
  UpdateRescueStatusDto,
} from './dto/create-rescue.dto';
import { Role, RescueStatus } from '@prisma/client';

import { EventsGateway } from '../events/events.gateway';
import { FirebaseService } from '../firebase/firebase.service';

@Injectable()
export class RescuesService {
  constructor(
    private readonly prisma: PrismaService,
    private readonly eventsGateway: EventsGateway,
    private readonly firebaseService: FirebaseService,
  ) {}

  async create(createRescueDto: CreateRescueDto, reporterId: string | null) {
    const { animal, ...rescueData } = createRescueDto;

    const newRescue = await this.prisma.rescueCase.create({
      data: {
        ...rescueData,
        reporter: reporterId ? { connect: { id: reporterId } } : undefined,
        status: RescueStatus.REPORTED,
        animal: animal ? { create: animal as any } : undefined,
      },
    });

    // 1) WebSocket Broadcast (for users with the app OPEN)
    this.eventsGateway.broadcastEmergency(newRescue);

    // 2) FCM Push Notification (for users with the app CLOSED)
    try {
      // Find all volunteers that are available and have an fcmToken
      const availableVolunteers = await this.prisma.user.findMany({
        where: {
          role: 'VOLUNTEER',
          fcmToken: { not: null },
          volunteerProfile: {
            availabilityStatus: 'AVAILABLE'
          }
        },
        select: { fcmToken: true }
      });

      const tokens = availableVolunteers
        .map(v => v.fcmToken)
        .filter((t): t is string => t !== null);

      if (tokens.length > 0) {
        await this.firebaseService.sendEmergencyPush(tokens, newRescue);
      }
    } catch (fcmError) {
      console.error('Failed to send FCM push for new rescue', fcmError);
    }

    return newRescue;
  }

  // Applies confirmed Phase 7 Privacy Masking rules
  private maskLocationIfNotAuthorized(rescueCase: any, user: any) {
    if (!user) {
      // Anonymous user
      rescueCase.latitude = null;
      rescueCase.longitude = null;
      rescueCase.address = null;
      return rescueCase;
    }

    const isReporter = rescueCase.reporterId === user.userId;
    const isAdmin = user.role === Role.ADMIN || user.role === Role.NGO_ADMIN;

    let isAssignedVolunteer = false;
    let isInvolvedVet = false;

    if (rescueCase.assignments) {
      isAssignedVolunteer = rescueCase.assignments.some(
        (a: any) =>
          a.volunteer?.userId === user.userId &&
          (a.status === 'ACCEPTED' ||
            a.status === 'ON_THE_WAY' ||
            a.status === 'RESCUE_IN_PROGRESS' ||
            a.status === 'RESCUED'),
      );
    }

    if (rescueCase.medicalRecords) {
      isInvolvedVet = rescueCase.medicalRecords.some(
        (m: any) => m.vetId === user.userId,
      );
    }

    const canViewExact =
      isReporter || isAdmin || isAssignedVolunteer || isInvolvedVet;

    if (!canViewExact) {
      rescueCase.latitude = null;
      rescueCase.longitude = null;
      rescueCase.address = null;
    }

    return rescueCase;
  }

  async findAll(user: any) {
    const cases = await this.prisma.rescueCase.findMany({
      include: {
        assignments: {
          include: { volunteer: true },
        },
        medicalRecords: true,
      },
    });

    return cases.map((c: any) => this.maskLocationIfNotAuthorized(c, user));
  }

  async findOne(id: string, user: any) {
    const c = await this.prisma.rescueCase.findUnique({
      where: { id },
      include: {
        assignments: {
          include: { volunteer: true },
        },
        medicalRecords: true,
      },
    });

    if (!c) throw new NotFoundException('Rescue case not found');

    return this.maskLocationIfNotAuthorized(c, user);
  }

  // Exact Graph Implementation
  private isValidTransition(
    from: RescueStatus,
    to: RescueStatus,
    role: Role,
  ): boolean {
    const transitions: Record<
      RescueStatus,
      { to: RescueStatus[]; roles: Role[] }[]
    > = {
      [RescueStatus.REPORTED]: [
        {
          to: [RescueStatus.UNDER_REVIEW],
          roles: [Role.NGO_ADMIN, Role.ADMIN],
        },
        { to: [RescueStatus.CANCELLED], roles: [Role.NGO_ADMIN, Role.ADMIN] },
      ],
      [RescueStatus.UNDER_REVIEW]: [
        {
          to: [
            RescueStatus.AI_ANALYZING,
            RescueStatus.PENDING_VERIFICATION,
            RescueStatus.CANCELLED,
          ],
          roles: [Role.NGO_ADMIN, Role.ADMIN],
        },
      ],
      [RescueStatus.AI_ANALYZING]: [
        {
          to: [RescueStatus.PENDING_VERIFICATION, RescueStatus.CANCELLED],
          roles: [Role.NGO_ADMIN, Role.ADMIN],
        },
      ],
      [RescueStatus.PENDING_VERIFICATION]: [
        {
          to: [RescueStatus.VERIFIED, RescueStatus.REJECTED],
          roles: [Role.NGO_ADMIN, Role.ADMIN, Role.VETERINARIAN],
        },
        { to: [RescueStatus.CANCELLED], roles: [Role.NGO_ADMIN, Role.ADMIN] },
      ],
      [RescueStatus.VERIFIED]: [
        {
          to: [RescueStatus.VOLUNTEER_SEARCH, RescueStatus.CANCELLED],
          roles: [Role.NGO_ADMIN, Role.ADMIN],
        },
      ],
      [RescueStatus.REJECTED]: [
        { to: [RescueStatus.CLOSED], roles: [Role.NGO_ADMIN, Role.ADMIN] },
      ],
      [RescueStatus.VOLUNTEER_SEARCH]: [
        {
          to: [RescueStatus.VOLUNTEER_ASSIGNED, RescueStatus.CANCELLED],
          roles: [Role.NGO_ADMIN, Role.ADMIN],
        },
      ],
      [RescueStatus.VOLUNTEER_ASSIGNED]: [
        { to: [RescueStatus.VOLUNTEER_ACCEPTED], roles: [Role.VOLUNTEER] },
        {
          to: [RescueStatus.VOLUNTEER_SEARCH],
          roles: [Role.VOLUNTEER, Role.NGO_ADMIN, Role.ADMIN],
        },
        { to: [RescueStatus.CANCELLED], roles: [Role.NGO_ADMIN, Role.ADMIN] },
      ],
      [RescueStatus.VOLUNTEER_ACCEPTED]: [
        { to: [RescueStatus.ON_THE_WAY], roles: [Role.VOLUNTEER] },
        {
          to: [RescueStatus.VOLUNTEER_SEARCH],
          roles: [Role.VOLUNTEER, Role.NGO_ADMIN, Role.ADMIN],
        },
        { to: [RescueStatus.CANCELLED], roles: [Role.NGO_ADMIN, Role.ADMIN] },
      ],
      [RescueStatus.ON_THE_WAY]: [
        { to: [RescueStatus.RESCUE_IN_PROGRESS], roles: [Role.VOLUNTEER] },
        {
          to: [RescueStatus.VOLUNTEER_SEARCH],
          roles: [Role.VOLUNTEER, Role.NGO_ADMIN, Role.ADMIN],
        },
        { to: [RescueStatus.CANCELLED], roles: [Role.NGO_ADMIN, Role.ADMIN] },
      ],
      [RescueStatus.RESCUE_IN_PROGRESS]: [
        {
          to: [RescueStatus.RESCUED],
          roles: [Role.VOLUNTEER, Role.NGO_ADMIN, Role.ADMIN],
        },
        { to: [RescueStatus.CANCELLED], roles: [Role.NGO_ADMIN, Role.ADMIN] },
      ],
      [RescueStatus.RESCUED]: [
        {
          to: [RescueStatus.AT_VET],
          roles: [Role.NGO_ADMIN, Role.ADMIN, Role.VETERINARIAN],
        },
        {
          to: [RescueStatus.READY_FOR_ADOPTION, RescueStatus.RELEASED],
          roles: [Role.NGO_ADMIN, Role.ADMIN],
        },
      ],
      [RescueStatus.AT_VET]: [
        {
          to: [RescueStatus.UNDER_TREATMENT, RescueStatus.RECOVERING],
          roles: [Role.VETERINARIAN, Role.NGO_ADMIN, Role.ADMIN],
        },
      ],
      [RescueStatus.UNDER_TREATMENT]: [
        {
          to: [RescueStatus.RECOVERING],
          roles: [Role.VETERINARIAN, Role.NGO_ADMIN, Role.ADMIN],
        },
      ],
      [RescueStatus.RECOVERING]: [
        {
          to: [RescueStatus.READY_FOR_ADOPTION, RescueStatus.RELEASED],
          roles: [Role.NGO_ADMIN, Role.ADMIN, Role.VETERINARIAN],
        },
      ],
      [RescueStatus.READY_FOR_ADOPTION]: [
        {
          to: [
            RescueStatus.ADOPTED,
            RescueStatus.FOSTERED,
            RescueStatus.RELEASED,
          ],
          roles: [Role.NGO_ADMIN, Role.ADMIN],
        },
      ],
      [RescueStatus.FOSTERED]: [
        {
          to: [RescueStatus.ADOPTED, RescueStatus.RELEASED],
          roles: [Role.NGO_ADMIN, Role.ADMIN],
        },
      ],
      [RescueStatus.ADOPTED]: [
        { to: [RescueStatus.CLOSED], roles: [Role.NGO_ADMIN, Role.ADMIN] },
      ],
      [RescueStatus.RELEASED]: [
        { to: [RescueStatus.CLOSED], roles: [Role.NGO_ADMIN, Role.ADMIN] },
      ],
      [RescueStatus.CLOSED]: [],
      [RescueStatus.CANCELLED]: [],
    };

    const possible = transitions[from];
    if (!possible) return false;

    for (const p of possible) {
      if (p.to.includes(to) && p.roles.includes(role)) return true;
    }
    return false;
  }

  async updateStatus(id: string, user: any, dto: UpdateRescueStatusDto) {
    const rescue = await this.prisma.rescueCase.findUnique({ where: { id } });
    if (!rescue) throw new NotFoundException('Rescue not found');

    const authorized = this.isValidTransition(
      rescue.status,
      dto.status,
      user.role,
    );
    if (!authorized) {
      throw new ForbiddenException(
        `Invalid or unauthorized state transition from ${rescue.status} to ${dto.status} for role ${user.role}`,
      );
    }

    return this.prisma.rescueCase.update({
      where: { id },
      data: { status: dto.status },
    });
  }
}
