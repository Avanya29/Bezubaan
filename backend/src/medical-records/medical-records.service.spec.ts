import { Test, TestingModule } from '@nestjs/testing';
import { MedicalRecordsService } from './medical-records.service';
import { PrismaService } from '../database/prisma.service';
import { EventsService } from '../events/events.service';

describe('MedicalRecordsService', () => {
  let service: MedicalRecordsService;

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      providers: [
        MedicalRecordsService,
        {
          provide: PrismaService,
          useValue: {
            animal: { findUnique: jest.fn() },
            medicalRecord: {
              create: jest.fn(),
              findUnique: jest.fn(),
              update: jest.fn(),
              findFirst: jest.fn(),
              findMany: jest.fn(),
            },
          },
        },
        {
          provide: EventsService,
          useValue: {
            emit: jest.fn(),
          },
        },
      ],
    }).compile();

    service = module.get<MedicalRecordsService>(MedicalRecordsService);
  });

  it('should be defined', () => {
    expect(service).toBeDefined();
  });
});
