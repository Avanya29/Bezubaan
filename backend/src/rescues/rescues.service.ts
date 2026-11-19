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
import { Role, RescueStatus, AssignmentStatus, VolunteerApprovalStatus, VolunteerAvailabilityStatus } from '@prisma/client';

import { EventsGateway } from '../events/events.gateway';
import { FirebaseService } from '../firebase/firebase.service';
import { AiService } from '../ai/ai.service';

@Injectable()
export class RescuesService {
  constructor(
    private readonly prisma: PrismaService,
    private readonly eventsGateway: EventsGateway,
    private readonly firebaseService: FirebaseService,
    private readonly aiService: AiService,
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
      include: {
        reporter: {
          select: { id: true, email: true },
        },
        animal: true,
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
            availabilityStatus: 'AVAILABLE',
          },
        },
        select: { fcmToken: true },
      });

      const tokens = availableVolunteers
        .map((v) => v.fcmToken)
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

    const uid = user.userId || user.id;
    const isReporter = rescueCase.reporterId === uid;
    const isAdmin = user.role === Role.ADMIN || user.role === Role.NGO_ADMIN;

    let isAssignedVolunteer = false;
    let isInvolvedVet = false;

    if (rescueCase.assignments) {
      isAssignedVolunteer = rescueCase.assignments.some(
        (a: any) =>
          (a.volunteer?.userId === uid || a.volunteer?.user?.id === uid) &&
          (a.status === 'ACCEPTED' ||
            a.status === 'ON_THE_WAY' ||
            a.status === 'RESCUE_IN_PROGRESS' ||
            a.status === 'RESCUED' ||
            a.status === 'COMPLETED'),
      );
    }

    if (rescueCase.medicalRecords) {
      isInvolvedVet = rescueCase.medicalRecords.some(
        (m: any) => m.vetId === uid,
      );
    }

    const canViewExact =
      isReporter ||
      isAdmin ||
      isAssignedVolunteer ||
      isInvolvedVet ||
      rescueCase.status === 'REPORTED' ||
      rescueCase.status === 'VOLUNTEER_ASSIGNED' ||
      rescueCase.status === 'VOLUNTEER_ACCEPTED' ||
      rescueCase.status === 'ON_THE_WAY' ||
      rescueCase.status === 'RESCUE_IN_PROGRESS' ||
      rescueCase.status === 'RESCUED';

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
        animal: true,
        reporter: {
          select: { id: true, email: true },
        },
        assignments: {
          include: {
            volunteer: {
              include: { user: { select: { id: true, email: true } } },
            },
          },
        },
        medicalRecords: true,
      },
    });

    return cases.map((c: any) => {
      const primaryAssignment =
        c.assignments?.find((a: any) => a.isPrimary) || c.assignments?.[0];
      const volunteerLocation =
        primaryAssignment?.volunteer?.currentLatitude != null &&
        primaryAssignment?.volunteer?.currentLongitude != null
          ? {
              latitude: primaryAssignment.volunteer.currentLatitude,
              longitude: primaryAssignment.volunteer.currentLongitude,
            }
          : null;
      const volunteerName =
        primaryAssignment?.volunteer?.user?.email?.split('@')[0] ||
        (c.status === 'RESCUED' ? 'Lead Volunteer' : null);

      const formatted = {
        ...c,
        volunteerLocation,
        volunteerName,
      };

      return this.maskLocationIfNotAuthorized(formatted, user);
    });
  }

  async findOne(id: string, user: any) {
    const c = await this.prisma.rescueCase.findUnique({
      where: { id },
      include: {
        animal: true,
        reporter: {
          select: { id: true, email: true },
        },
        assignments: {
          include: {
            volunteer: {
              include: { user: { select: { id: true, email: true } } },
            },
          },
        },
        medicalRecords: true,
      },
    });

    if (!c) throw new NotFoundException('Rescue case not found');

    const cAny = c as any;
    const primaryAssignment =
      cAny.assignments?.find((a: any) => a.isPrimary) || cAny.assignments?.[0];
    const volunteerLocation =
      primaryAssignment?.volunteer?.currentLatitude != null &&
      primaryAssignment?.volunteer?.currentLongitude != null
        ? {
            latitude: primaryAssignment.volunteer.currentLatitude,
            longitude: primaryAssignment.volunteer.currentLongitude,
          }
        : null;
    const volunteerName =
      primaryAssignment?.volunteer?.user?.email?.split('@')[0] ||
      (c.status === 'RESCUED' ? 'Lead Volunteer' : null);

    const formatted = {
      ...c,
      volunteerLocation,
      volunteerName,
    };

    return this.maskLocationIfNotAuthorized(formatted, user);
  }

  async triggerAiTriage(rescueId: string) {
    const rescue = await this.prisma.rescueCase.findUnique({
      where: { id: rescueId },
      include: { animal: true, media: true },
    });

    if (!rescue) throw new NotFoundException('Rescue case not found');

    // Fetch available volunteers to pass to AI for recommendation
    const availableVolunteers = await this.prisma.volunteerProfile.findMany({
      where: { availabilityStatus: 'AVAILABLE' },
      include: { user: true },
    });

    const mappedVolunteers = availableVolunteers.map((v) => ({
      id: v.id,
      name: v.user.email, // using email as a fallback, should use user profile name if available
      experience_level: v.experience || 'Unknown',
      capabilities: v.capabilities,
      // We could calculate distance using Haversine formula here if needed
      // distance_meters: 1000
    }));

    // Trigger the AI service
    const aiResponse = await this.aiService.requestTriage({
      rescueId: rescue.id,
      description: rescue.description,
      latitude: rescue.latitude,
      longitude: rescue.longitude,
      address: rescue.address || undefined,
      imageUrl: rescue.media[0]?.url, // Pass first media URL if exists
      animalSpecies: rescue.animal?.species,
      volunteers: mappedVolunteers,
    });

    // Save AI output to aiTriageData JSON field
    await this.prisma.rescueCase.update({
      where: { id: rescueId },
      data: {
        aiTriageData: aiResponse as any,
        status: RescueStatus.PENDING_VERIFICATION, // Move status after analysis
      },
    });

    return aiResponse;
  }

  // Exact Graph Implementation
  private isValidTransition(
    from: RescueStatus,
    to: RescueStatus,
    role: Role,
  ): boolean {
    if (from === to) return true;
    const transitions: Record<
      RescueStatus,
      { to: RescueStatus[]; roles: Role[] }[]
    > = {
      [RescueStatus.REPORTED]: [
        {
          to: [RescueStatus.UNDER_REVIEW],
          roles: [Role.NGO_ADMIN, Role.ADMIN],
        },
        {
          to: [
            RescueStatus.VOLUNTEER_ASSIGNED,
            RescueStatus.VOLUNTEER_ACCEPTED,
            RescueStatus.ON_THE_WAY,
            RescueStatus.RESCUE_IN_PROGRESS,
            RescueStatus.RESCUED,
          ],
          roles: [Role.USER, Role.VOLUNTEER, Role.NGO_ADMIN, Role.ADMIN],
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
          to: [
            RescueStatus.VOLUNTEER_ASSIGNED,
            RescueStatus.VOLUNTEER_ACCEPTED,
            RescueStatus.ON_THE_WAY,
            RescueStatus.RESCUE_IN_PROGRESS,
            RescueStatus.RESCUED,
          ],
          roles: [Role.USER, Role.VOLUNTEER, Role.NGO_ADMIN, Role.ADMIN],
        },
        { to: [RescueStatus.CANCELLED], roles: [Role.NGO_ADMIN, Role.ADMIN] },
      ],
      [RescueStatus.VOLUNTEER_ASSIGNED]: [
        {
          to: [
            RescueStatus.VOLUNTEER_ACCEPTED,
            RescueStatus.ON_THE_WAY,
            RescueStatus.RESCUE_IN_PROGRESS,
            RescueStatus.RESCUED,
          ],
          roles: [Role.USER, Role.VOLUNTEER, Role.NGO_ADMIN, Role.ADMIN],
        },
        {
          to: [RescueStatus.VOLUNTEER_SEARCH],
          roles: [Role.USER, Role.VOLUNTEER, Role.NGO_ADMIN, Role.ADMIN],
        },
        { to: [RescueStatus.CANCELLED], roles: [Role.NGO_ADMIN, Role.ADMIN] },
      ],
      [RescueStatus.VOLUNTEER_ACCEPTED]: [
        { to: [RescueStatus.ON_THE_WAY, RescueStatus.RESCUE_IN_PROGRESS, RescueStatus.RESCUED], roles: [Role.USER, Role.VOLUNTEER, Role.NGO_ADMIN, Role.ADMIN] },
        {
          to: [RescueStatus.VOLUNTEER_SEARCH],
          roles: [Role.USER, Role.VOLUNTEER, Role.NGO_ADMIN, Role.ADMIN],
        },
        { to: [RescueStatus.CANCELLED], roles: [Role.NGO_ADMIN, Role.ADMIN] },
      ],
      [RescueStatus.ON_THE_WAY]: [
        { to: [RescueStatus.RESCUE_IN_PROGRESS, RescueStatus.RESCUED], roles: [Role.USER, Role.VOLUNTEER, Role.NGO_ADMIN, Role.ADMIN] },
        {
          to: [RescueStatus.VOLUNTEER_SEARCH],
          roles: [Role.USER, Role.VOLUNTEER, Role.NGO_ADMIN, Role.ADMIN],
        },
        { to: [RescueStatus.CANCELLED], roles: [Role.NGO_ADMIN, Role.ADMIN] },
      ],
      [RescueStatus.RESCUE_IN_PROGRESS]: [
        {
          to: [RescueStatus.RESCUED],
          roles: [Role.USER, Role.VOLUNTEER, Role.NGO_ADMIN, Role.ADMIN],
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

    const userId = user.userId || user.id;

    if (
      dto.status === RescueStatus.VOLUNTEER_ACCEPTED ||
      dto.status === RescueStatus.ON_THE_WAY ||
      dto.status === RescueStatus.RESCUE_IN_PROGRESS ||
      dto.status === RescueStatus.RESCUED
    ) {
      let volProfile = await this.prisma.volunteerProfile.findUnique({
        where: { userId },
      });
      if (!volProfile) {
        volProfile = await this.prisma.volunteerProfile.create({
          data: {
            userId,
            approvalStatus: VolunteerApprovalStatus.APPROVED,
            availabilityStatus: VolunteerAvailabilityStatus.AVAILABLE,
          },
        });
      }

      if (volProfile) {
        const existingAssignment = await this.prisma.rescueAssignment.findFirst({
          where: { rescueCaseId: id, volunteerId: volProfile.id },
        });
        const assignStatus = AssignmentStatus.ACCEPTED;
        if (!existingAssignment) {
          await this.prisma.rescueAssignment.create({
            data: {
              rescueCaseId: id,
              volunteerId: volProfile.id,
              assignedById: userId,
              status: assignStatus,
              isPrimary: true,
              respondedAt: new Date(),
            },
          });
        } else {
          await this.prisma.rescueAssignment.update({
            where: { id: existingAssignment.id },
            data: { status: assignStatus, respondedAt: new Date() },
          });
        }
      }
    }

    const updated = await this.prisma.rescueCase.update({
      where: { id },
      data: { status: dto.status },
      include: {
        animal: true,
        reporter: { select: { id: true, email: true } },
        assignments: {
          include: {
            volunteer: {
              include: {
                user: { select: { id: true, email: true } },
              },
            },
          },
        },
      },
    });

    const updatedAny = updated as any;
    const primaryAssignment =
      updatedAny.assignments?.find((a: any) => a.isPrimary) || updatedAny.assignments?.[0];
    const volunteerLocation =
      primaryAssignment?.volunteer?.currentLatitude != null &&
      primaryAssignment?.volunteer?.currentLongitude != null
        ? {
            latitude: primaryAssignment.volunteer.currentLatitude,
            longitude: primaryAssignment.volunteer.currentLongitude,
          }
        : null;
    const volunteerName =
      primaryAssignment?.volunteer?.user?.email?.split('@')[0] ||
      (dto.status === RescueStatus.RESCUED ? 'Lead Volunteer' : null);

    const result = {
      ...updated,
      volunteerLocation,
      volunteerName,
    };

    try {
      this.eventsGateway.server?.emit('rescue_status_updated', result);
    } catch (e) {
      // Gateway emit safe ignore
    }

    return result;
  }
}
