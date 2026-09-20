import { Test, TestingModule } from '@nestjs/testing';
import { HealthController } from './health.controller';
import { PrismaService } from '../database/prisma.service';

describe('HealthController', () => {
  let controller: HealthController;

  const mockPrismaService = {
    $queryRaw: jest.fn().mockResolvedValue([{ '?column?': 1 }]),
  };

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      controllers: [HealthController],
      providers: [
        {
          provide: PrismaService,
          useValue: mockPrismaService,
        },
      ],
    }).compile();

    controller = module.get<HealthController>(HealthController);
  });

  describe('liveness', () => {
    it('should return ok status', () => {
      const result = controller.liveness();
      expect(result.status).toBe('ok');
      expect(result.service).toBe('bezubaan-api');
      expect(result.timestamp).toBeDefined();
    });
  });

  describe('readiness', () => {
    it('should return ok when database is healthy', async () => {
      const result = await controller.readiness();
      expect(result.status).toBe('ok');
      expect(result.checks.database.status).toBe('ok');
    });

    it('should return degraded when database is down', async () => {
      mockPrismaService.$queryRaw.mockRejectedValueOnce(
        new Error('Connection refused'),
      );
      const result = await controller.readiness();
      expect(result.status).toBe('degraded');
      expect(result.checks.database.status).toBe('error');
    });
  });
});
