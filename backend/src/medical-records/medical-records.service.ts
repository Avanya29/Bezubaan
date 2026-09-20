import {
  Injectable,
  NotFoundException,
  ForbiddenException,
} from '@nestjs/common';
import { PrismaService } from '../database/prisma.service';
import { EventsService } from '../events/events.service';
import { CreateMedicalRecordDto } from './dto/create-medical-record.dto';
import { Role } from '@prisma/client';

@Injectable()
export class MedicalRecordsService {
  constructor(
    private readonly prisma: PrismaService,
    private readonly eventsService: EventsService,
  ) {}

  async create(vetId: string, dto: CreateMedicalRecordDto) {
    const animal = await this.prisma.animal.findUnique({
      where: { id: dto.animalId },
    });

    if (!animal) {
      throw new NotFoundException('Animal not found');
    }

    const record = await this.prisma.medicalRecord.create({
      data: {
        notes: dto.notes,
        animalId: dto.animalId,
        rescueCaseId: dto.rescueCaseId,
        diagnosis: dto.diagnosis,
        medications: dto.medications,
        cost: dto.cost,
        vetId,
        attachments: dto.attachments
          ? {
              create: dto.attachments,
            }
          : undefined,
      },
      include: { attachments: true },
    });

    console.log(
      `[Event Triggered] medical.record.created for record ${record.id}`,
    );
    this.eventsService.emit('medical.record.created', record);

    return record;
  }

  async addAddendum(id: string, notes: string) {
    const record = await this.prisma.medicalRecord.findUnique({
      where: { id },
    });

    if (!record) {
      throw new NotFoundException('Medical record not found');
    }

    const updatedNotes = record.notes + '\n\n--- Addendum ---\n' + notes;

    return this.prisma.medicalRecord.update({
      where: { id },
      data: { notes: updatedNotes },
    });
  }

  async getByAnimal(animalId: string, user: any) {
    if (user.role === Role.VETERINARIAN) {
      const hasRecord = await this.prisma.medicalRecord.findFirst({
        where: { animalId, vetId: user.id },
      });

      if (!hasRecord) {
        throw new ForbiddenException(
          'You do not have permission to view records for this animal',
        );
      }
    } else if (user.role !== Role.NGO_ADMIN && user.role !== Role.ADMIN) {
      throw new ForbiddenException('Insufficient permissions');
    }

    return this.prisma.medicalRecord.findMany({
      where: { animalId },
      orderBy: { createdAt: 'desc' },
    });
  }
}
