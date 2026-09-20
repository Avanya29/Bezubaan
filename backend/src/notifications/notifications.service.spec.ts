import { Test, TestingModule } from '@nestjs/testing';
import { NotificationsService } from './notifications.service';
import { PrismaService } from '../database/prisma.service';
import { ClientProxy } from '@nestjs/microservices';

describe('NotificationsService', () => {
  let service: NotificationsService;
  let mockPrisma: any;
  let mockRabbit: any;

  beforeEach(async () => {
    mockPrisma = {
      notification: {
        findMany: jest.fn(),
        findUnique: jest.fn(),
        update: jest.fn(),
        create: jest.fn(),
      },
      notificationPreference: {
        findMany: jest.fn(),
        findUnique: jest.fn(),
        upsert: jest.fn(),
      },
    };
    mockRabbit = { emit: jest.fn() };

    const module: TestingModule = await Test.createTestingModule({
      providers: [
        NotificationsService,
        { provide: PrismaService, useValue: mockPrisma },
        { provide: 'RABBITMQ_SERVICE', useValue: mockRabbit },
      ],
    }).compile();

    service = module.get<NotificationsService>(NotificationsService);
  });

  describe('Preferences', () => {
    it('should bypass opt-out for SECURITY notifications', async () => {
      await expect(
        service.updatePreference('user1', {
          category: 'SECURITY',
          isEnabled: false,
        } as any),
      ).rejects.toThrow('SECURITY notifications cannot be disabled');
    });

    it('should dispatch normally', async () => {
      mockPrisma.notificationPreference.findUnique.mockResolvedValue(null);
      await service.dispatch('user1', 'COMMUNITY', 'IN_APP', 'Test', 'Message');
      expect(mockPrisma.notification.create).toHaveBeenCalled();
      expect(mockRabbit.emit).toHaveBeenCalledWith(
        'notification.dispatch',
        expect.any(Object),
      );
    });
  });
});
