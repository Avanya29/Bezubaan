import { Test, TestingModule } from '@nestjs/testing';
import { AssignmentsService } from './assignments.service';
import { PrismaService } from '../database/prisma.service';

const mockPrismaService = {
  rescueCase: {
    findUnique: jest.fn(),
    update: jest.fn(),
  },
  volunteerProfile: {
    findUnique: jest.fn(),
  },
  rescueAssignment: {
    findFirst: jest.fn(),
    create: jest.fn(),
    update: jest.fn(),
    findUnique: jest.fn(),
  },
  $transaction: jest.fn(async (cb) => {
    const mockTx = {
      rescueAssignment: {
        create: jest.fn().mockResolvedValue({}),
        update: jest.fn().mockResolvedValue({}),
      },
      rescueCase: {
        update: jest.fn().mockResolvedValue({}),
      },
    };
    return cb(mockTx);
  }),
};

describe('AssignmentsService', () => {
  let service: AssignmentsService;

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      providers: [
        AssignmentsService,
        { provide: PrismaService, useValue: mockPrismaService },
      ],
    }).compile();

    service = module.get<AssignmentsService>(AssignmentsService);
  });

  it('should be defined', () => {
    expect(service).toBeDefined();
  });
});
