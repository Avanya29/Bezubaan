import { Test, TestingModule } from '@nestjs/testing';
import { VetsService } from './vets.service';
import { PrismaService } from '../database/prisma.service';

describe('VetsService', () => {
  let service: VetsService;

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      providers: [
        VetsService,
        {
          provide: PrismaService,
          useValue: {
            vetProfile: {
              findUnique: jest.fn(),
              create: jest.fn(),
              update: jest.fn(),
            },
            rescueCase: {
              findMany: jest.fn(),
            },
            $transaction: jest.fn(),
          },
        },
      ],
    }).compile();

    service = module.get<VetsService>(VetsService);
  });

  it('should be defined', () => {
    expect(service).toBeDefined();
  });
});
