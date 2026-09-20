import { Test, TestingModule } from '@nestjs/testing';
import { LostFoundService } from './lost-found.service';
import { PrismaService } from '../database/prisma.service';
import {
  BadRequestException,
  ForbiddenException,
  NotFoundException,
} from '@nestjs/common';
import { LostFoundStatus } from '@prisma/client';

describe('LostFoundService', () => {
  let service: LostFoundService;
  let prisma: PrismaService;

  const mockPrismaService = {
    lostFoundIncident: {
      create: jest.fn(),
      findMany: jest.fn(),
      findUnique: jest.fn(),
      update: jest.fn(),
      delete: jest.fn(),
    },
  };

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      providers: [
        LostFoundService,
        { provide: PrismaService, useValue: mockPrismaService },
      ],
    }).compile();

    service = module.get<LostFoundService>(LostFoundService);
    prisma = module.get<PrismaService>(PrismaService);
  });

  afterEach(() => {
    jest.clearAllMocks();
  });

  it('should be defined', () => {
    expect(service).toBeDefined();
  });

  describe('create', () => {
    it('should create an incident with status LOST', async () => {
      const dto = { animalType: 'Dog', description: 'Lost dog' };
      const expectedResult = { id: '1', ...dto, status: LostFoundStatus.LOST };
      mockPrismaService.lostFoundIncident.create.mockResolvedValue(
        expectedResult,
      );

      const result = await service.create('user1', dto);
      expect(result).toEqual(expectedResult);
      expect(mockPrismaService.lostFoundIncident.create).toHaveBeenCalledWith({
        data: {
          reporterId: 'user1',
          animalType: 'Dog',
          description: 'Lost dog',
          lastSeenLocation: undefined,
          status: LostFoundStatus.LOST,
        },
      });
    });

    it('should throw BadRequestException if status is invalid', async () => {
      const dto = {
        animalType: 'Dog',
        description: 'Lost dog',
        status: LostFoundStatus.SEARCHING,
      };
      await expect(service.create('user1', dto)).rejects.toThrow(
        BadRequestException,
      );
    });
  });

  describe('updateStatus', () => {
    it('should update status if transition is valid and user is reporter', async () => {
      const existing = {
        id: '1',
        reporterId: 'user1',
        status: LostFoundStatus.LOST,
      };
      mockPrismaService.lostFoundIncident.findUnique.mockResolvedValue(
        existing,
      );
      mockPrismaService.lostFoundIncident.update.mockResolvedValue({
        ...existing,
        status: LostFoundStatus.SEARCHING,
      });

      const result = await service.updateStatus('1', 'user1', {
        status: LostFoundStatus.SEARCHING,
      });
      expect(result.status).toBe(LostFoundStatus.SEARCHING);
      expect(mockPrismaService.lostFoundIncident.update).toHaveBeenCalledWith({
        where: { id: '1' },
        data: { status: LostFoundStatus.SEARCHING },
      });
    });

    it('should throw ForbiddenException if user is not reporter', async () => {
      const existing = {
        id: '1',
        reporterId: 'user1',
        status: LostFoundStatus.LOST,
      };
      mockPrismaService.lostFoundIncident.findUnique.mockResolvedValue(
        existing,
      );

      await expect(
        service.updateStatus('1', 'user2', {
          status: LostFoundStatus.SEARCHING,
        }),
      ).rejects.toThrow(ForbiddenException);
    });

    it('should throw BadRequestException if transition is invalid', async () => {
      const existing = {
        id: '1',
        reporterId: 'user1',
        status: LostFoundStatus.LOST,
      };
      mockPrismaService.lostFoundIncident.findUnique.mockResolvedValue(
        existing,
      );

      await expect(
        service.updateStatus('1', 'user1', {
          status: LostFoundStatus.REUNITED,
        }),
      ).rejects.toThrow(BadRequestException);
    });
  });
});
