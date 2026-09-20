import { Test, TestingModule } from '@nestjs/testing';
import { AdoptionsService } from './adoptions.service';
import { PrismaService } from '../database/prisma.service';
import { EventsService } from '../events/events.service';
import { ApplicationStatus, Role } from '@prisma/client';
import { BadRequestException, NotFoundException } from '@nestjs/common';

describe('AdoptionsService', () => {
  let service: AdoptionsService;
  let prisma: PrismaService;
  let events: EventsService;

  const mockPrismaService: any = {
    animal: {
      findUnique: jest.fn(),
    },
    adoptionApplication: {
      create: jest.fn(),
      findUnique: jest.fn(),
      update: jest.fn(),
      findMany: jest.fn(),
      updateMany: jest.fn(),
    },
    $transaction: jest.fn((callback) => callback(mockPrismaService)),
  };

  const mockEventsService = {
    emit: jest.fn(),
  };

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      providers: [
        AdoptionsService,
        { provide: PrismaService, useValue: mockPrismaService },
        { provide: EventsService, useValue: mockEventsService },
      ],
    }).compile();

    service = module.get<AdoptionsService>(AdoptionsService);
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
          motivation: 'love',
          housingType: 'house',
          householdInformation: 'none',
          previousPetExperience: 'none',
          existingAnimals: 'none',
        }),
      ).rejects.toThrow(NotFoundException);
    });

    it('should throw if animal is already adopted', async () => {
      mockPrismaService.animal.findUnique.mockResolvedValueOnce({
        id: 'animal-1',
        AdoptionApplication: [
          { id: 'app-1', status: ApplicationStatus.APPROVED },
        ],
      });
      await expect(
        service.apply('user-1', {
          animalId: 'animal-1',
          motivation: 'love',
          housingType: 'house',
          householdInformation: 'none',
          previousPetExperience: 'none',
          existingAnimals: 'none',
        }),
      ).rejects.toThrow(BadRequestException);
    });

    it('should create an adoption application', async () => {
      mockPrismaService.animal.findUnique.mockResolvedValueOnce({
        id: 'animal-1',
        AdoptionApplication: [],
      });
      mockPrismaService.adoptionApplication.create.mockResolvedValueOnce({
        id: 'app-new',
      });

      const result = await service.apply('user-1', {
        animalId: 'animal-1',
        motivation: 'love',
        housingType: 'house',
        householdInformation: 'none',
        previousPetExperience: 'none',
        existingAnimals: 'none',
      });

      expect(result).toEqual({ id: 'app-new' });
      expect(mockEventsService.emit).toHaveBeenCalledWith(
        'adoption.application.created',
        { applicationId: 'app-new' },
      );
    });
  });

  describe('approve', () => {
    it('should approve and reject other applications', async () => {
      mockPrismaService.adoptionApplication.findUnique.mockResolvedValueOnce({
        id: 'app-1',
        status: ApplicationStatus.PENDING,
        animalId: 'animal-1',
      });
      mockPrismaService.adoptionApplication.update.mockResolvedValueOnce({
        id: 'app-1',
        status: ApplicationStatus.APPROVED,
      });

      const result = await service.approve('app-1');
      expect(result).toEqual({
        id: 'app-1',
        status: ApplicationStatus.APPROVED,
      });
      expect(mockEventsService.emit).toHaveBeenCalledWith(
        'adoption.application.approved',
        { applicationId: 'app-1' },
      );
    });
  });

  describe('reject', () => {
    it('should reject a pending application', async () => {
      mockPrismaService.adoptionApplication.findUnique.mockResolvedValueOnce({
        id: 'app-1',
        status: ApplicationStatus.PENDING,
      });
      mockPrismaService.adoptionApplication.update.mockResolvedValueOnce({
        id: 'app-1',
        status: ApplicationStatus.REJECTED,
      });

      const result = await service.reject('app-1');
      expect(result).toEqual({
        id: 'app-1',
        status: ApplicationStatus.REJECTED,
      });
    });
  });

  describe('findAll', () => {
    it('should return all for admin', async () => {
      mockPrismaService.adoptionApplication.findMany.mockResolvedValueOnce([]);
      await service.findAll({ role: Role.ADMIN });
      expect(mockPrismaService.adoptionApplication.findMany).toHaveBeenCalled();
    });

    it('should return user applications for normal user', async () => {
      mockPrismaService.adoptionApplication.findMany.mockResolvedValueOnce([]);
      await service.findAll({ id: 'user-1', role: Role.USER });
      expect(
        mockPrismaService.adoptionApplication.findMany,
      ).toHaveBeenCalledWith({
        where: { applicantId: 'user-1' },
        include: { animal: true },
        orderBy: { createdAt: 'desc' },
      });
    });
  });
});
