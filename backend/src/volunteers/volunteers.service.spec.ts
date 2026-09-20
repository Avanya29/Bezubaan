import { Test, TestingModule } from '@nestjs/testing';
import { VolunteersService } from './volunteers.service';
import { PrismaService } from '../database/prisma.service';

const mockPrismaService = {
  volunteerProfile: {
    findUnique: jest.fn(),
    create: jest.fn(),
    update: jest.fn(),
    findMany: jest.fn(),
  },
  user: {
    update: jest.fn(),
  },
};

describe('VolunteersService', () => {
  let service: VolunteersService;

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      providers: [
        VolunteersService,
        { provide: PrismaService, useValue: mockPrismaService },
      ],
    }).compile();

    service = module.get<VolunteersService>(VolunteersService);
  });

  it('should be defined', () => {
    expect(service).toBeDefined();
  });
});
