import { Test, TestingModule } from '@nestjs/testing';
import { RescuesService } from './rescues.service';
import { PrismaService } from '../database/prisma.service';

const mockPrismaService = {
  rescueCase: {
    findMany: jest.fn(),
    findUnique: jest.fn(),
    create: jest.fn(),
    update: jest.fn(),
  },
};

describe('RescuesService', () => {
  let service: RescuesService;

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      providers: [
        RescuesService,
        { provide: PrismaService, useValue: mockPrismaService },
      ],
    }).compile();

    service = module.get<RescuesService>(RescuesService);
  });

  it('should be defined', () => {
    expect(service).toBeDefined();
  });
});
