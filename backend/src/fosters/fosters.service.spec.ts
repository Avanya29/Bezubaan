import { Test, TestingModule } from '@nestjs/testing';
import { FostersService } from './fosters.service';
import { PrismaService } from '../database/prisma.service';
import { EventsService } from '../events/events.service';
import {
  ApplicationStatus,
  FosterAssignmentStatus,
  Role,
} from '@prisma/client';
import { BadRequestException, NotFoundException } from '@nestjs/common';

describe('FostersService', () => {
  let service: FostersService;
  let prisma: PrismaService;
  let events: EventsService;

  const mockPrismaService: any = {
    animal: {
      findUnique: jest.fn(),
    },
    fosterApplication: {
      create: jest.fn(),
      findUnique: jest.fn(),
      update: jest.fn(),
      findMany: jest.fn(),
    },
    fosterAssignment: {
      create: jest.fn(),
      findUnique: jest.fn(),
      update: jest.fn(),
    },
    $transaction: jest.fn((callback) => callback(mockPrismaService)),
  };

  const mockEventsService = {
    emit: jest.fn(),
  };

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      providers: [
        FostersService,
        { provide: PrismaService, useValue: mockPrismaService },
        { provide: EventsService, useValue: mockEventsService },
      ],
    }).compile();

    service = module.get<FostersService>(FostersService);
    prisma = module.get<PrismaService>(PrismaService);
    events = module.get<EventsService>(EventsService);
  });

  afterEach(() => {
    jest.clearAllMocks();
  });

  it('should be defined', () => {
    expect(service).toBeDefined();
  });

  describe('apply', () => {
    it('should throw if animal is not found', async () => {
      mockPrismaService.animal.findUnique.mockResolvedValueOnce(null);
      await expect(
        service.apply('user-1', {
          animalId: 'animal-1',
          reason: 'test',
          housingInformation: 'test',
          householdInformation: 'test',
          existingAnimals: 'test',
          priorAnimalCareExperience: 'test',
          availability: 'test',
        }),
      ).rejects.toThrow(NotFoundException);
    });

    it('should create a foster application', async () => {
      mockPrismaService.animal.findUnique.mockResolvedValueOnce({
        id: 'animal-1',
      });
      mockPrismaService.fosterApplication.create.mockResolvedValueOnce({
        id: 'app-1',
      });

      const result = await service.apply('user-1', {
        animalId: 'animal-1',
        reason: 'test',
        housingInformation: 'test',
        householdInformation: 'test',
        existingAnimals: 'test',
        priorAnimalCareExperience: 'test',
        availability: 'test',
      });

      expect(result).toEqual({ id: 'app-1' });
    });
  });

  describe('approve', () => {
    it('should approve application and create assignment', async () => {
      mockPrismaService.fosterApplication.findUnique.mockResolvedValueOnce({
        id: 'app-1',
        status: ApplicationStatus.PENDING,
      });
      mockPrismaService.fosterApplication.update.mockResolvedValueOnce({
        id: 'app-1',
        status: ApplicationStatus.APPROVED,
      });

      const result = await service.approve('app-1');
      expect(result).toEqual({
        id: 'app-1',
        status: ApplicationStatus.APPROVED,
      });
      expect(mockEventsService.emit).toHaveBeenCalledWith(
        'foster.application.approved',
        { applicationId: 'app-1' },
      );
      expect(mockPrismaService.fosterAssignment.create).toHaveBeenCalled();
    });
  });

  describe('extend', () => {
    it('should extend an active assignment', async () => {
      mockPrismaService.fosterAssignment.findUnique.mockResolvedValueOnce({
        id: 'assign-1',
        status: FosterAssignmentStatus.ACTIVE,
      });
      mockPrismaService.fosterAssignment.update.mockResolvedValueOnce({
        id: 'assign-1',
        status: FosterAssignmentStatus.EXTENDED,
      });

      const result = await service.extend('assign-1', {
        expectedEndDate: new Date().toISOString(),
      });
      expect(result).toEqual({
        id: 'assign-1',
        status: FosterAssignmentStatus.EXTENDED,
      });
    });
  });

  describe('complete', () => {
    it('should complete an assignment', async () => {
      mockPrismaService.fosterAssignment.findUnique.mockResolvedValueOnce({
        id: 'assign-1',
        status: FosterAssignmentStatus.ACTIVE,
      });
      mockPrismaService.fosterAssignment.update.mockResolvedValueOnce({
        id: 'assign-1',
        status: FosterAssignmentStatus.COMPLETED,
      });

      const result = await service.complete('assign-1');
      expect(result).toEqual({
        id: 'assign-1',
        status: FosterAssignmentStatus.COMPLETED,
      });
    });
  });

  describe('findAll', () => {
    it('should return all for admin', async () => {
      mockPrismaService.fosterApplication.findMany.mockResolvedValueOnce([]);
      await service.findAll({ role: Role.ADMIN });
      expect(mockPrismaService.fosterApplication.findMany).toHaveBeenCalled();
    });

    it('should return user applications for normal user', async () => {
      mockPrismaService.fosterApplication.findMany.mockResolvedValueOnce([]);
      await service.findAll({ id: 'user-1', role: Role.USER });
      expect(mockPrismaService.fosterApplication.findMany).toHaveBeenCalledWith(
        {
          where: { applicantId: 'user-1' },
          include: { animal: true, assignments: true },
          orderBy: { createdAt: 'desc' },
        },
      );
    });
  });
});
