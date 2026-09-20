import { Test, TestingModule } from '@nestjs/testing';
import { VolunteersController } from './volunteers.controller';
import { VolunteersService } from './volunteers.service';
import { PrismaService } from '../database/prisma.service';

const mockPrismaService = {};

describe('VolunteersController', () => {
  let controller: VolunteersController;

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      controllers: [VolunteersController],
      providers: [
        {
          provide: VolunteersService,
          useValue: {
            apply: jest.fn(),
            approve: jest.fn(),
            updateAvailability: jest.fn(),
            updateLocation: jest.fn(),
            getCandidates: jest.fn(),
          },
        },
        { provide: PrismaService, useValue: mockPrismaService },
      ],
    }).compile();

    controller = module.get<VolunteersController>(VolunteersController);
  });

  it('should be defined', () => {
    expect(controller).toBeDefined();
  });
});
