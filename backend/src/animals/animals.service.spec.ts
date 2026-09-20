import { Test, TestingModule } from '@nestjs/testing';
import { AnimalsService } from './animals.service';
import { PrismaService } from '../database/prisma.service';
import { NotFoundException } from '@nestjs/common';

describe('AnimalsService', () => {
  let service: AnimalsService;
  let prismaService: PrismaService;

  beforeEach(async () => {
    const mockPrismaService = {
      animal: {
        create: jest.fn(),
        findUnique: jest.fn(),
        update: jest.fn(),
      },
    };

    const module: TestingModule = await Test.createTestingModule({
      providers: [
        AnimalsService,
        { provide: PrismaService, useValue: mockPrismaService },
      ],
    }).compile();

    service = module.get<AnimalsService>(AnimalsService);
    prismaService = module.get<PrismaService>(PrismaService);
  });

  it('should be defined', () => {
    expect(service).toBeDefined();
  });

  describe('create', () => {
    it('should create an animal successfully', async () => {
      const createDto = { species: 'Dog', breed: 'Labrador' };
      const expectedResult = { id: '1', ...createDto };
      (prismaService.animal.create as jest.Mock).mockResolvedValue(
        expectedResult,
      );

      const result = await service.create(createDto);
      expect(result).toEqual(expectedResult);
      expect(prismaService.animal.create).toHaveBeenCalledWith({
        data: createDto,
      });
    });
  });

  describe('findOne', () => {
    it('should return animal if found', async () => {
      const animal = { id: '1', species: 'Cat' };
      (prismaService.animal.findUnique as jest.Mock).mockResolvedValue(animal);

      const result = await service.findOne('1');
      expect(result).toEqual(animal);
    });

    it('should throw NotFoundException if not found', async () => {
      (prismaService.animal.findUnique as jest.Mock).mockResolvedValue(null);

      await expect(service.findOne('1')).rejects.toThrow(NotFoundException);
    });
  });
});
