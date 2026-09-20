import { Test, TestingModule } from '@nestjs/testing';
import { DonationsService } from './donations.service';
import { PrismaService } from '../database/prisma.service';
import { BadRequestException } from '@nestjs/common';
import { PaymentStatus } from '@prisma/client';

describe('DonationsService', () => {
  let service: DonationsService;
  let mockPrismaService: any;

  beforeEach(async () => {
    mockPrismaService = {
      campaign: {
        create: jest.fn(),
        findMany: jest.fn(),
        findUnique: jest.fn(),
        update: jest.fn(),
      },
      donation: { create: jest.fn(), findUnique: jest.fn(), update: jest.fn() },
      $transaction: jest.fn().mockImplementation((cb) => cb(mockPrismaService)),
    };

    const module: TestingModule = await Test.createTestingModule({
      providers: [
        DonationsService,
        { provide: PrismaService, useValue: mockPrismaService },
      ],
    }).compile();

    service = module.get<DonationsService>(DonationsService);
  });

  describe('Webhook Processing', () => {
    it('should correctly process SUCCESS transition', async () => {
      mockPrismaService.donation.findUnique.mockResolvedValue({
        id: 'd1',
        status: 'PENDING',
        campaignId: 'c1',
        amount: 1000,
      });
      mockPrismaService.donation.update.mockResolvedValue({
        status: 'SUCCESS',
      });

      await service.processWebhook({
        providerOrderId: 'mock',
        status: 'SUCCESS',
        providerPaymentId: 'pay1',
      } as any);

      expect(mockPrismaService.donation.update).toHaveBeenCalled();
      expect(mockPrismaService.campaign.update).toHaveBeenCalledWith({
        where: { id: 'c1' },
        data: { raisedAmount: { increment: 1000 } },
      });
    });

    it('should allow SUCCESS to REFUNDED and decrement', async () => {
      mockPrismaService.donation.findUnique.mockResolvedValue({
        id: 'd2',
        status: 'SUCCESS',
        campaignId: 'c1',
        amount: 500,
      });
      mockPrismaService.donation.update.mockResolvedValue({
        status: 'REFUNDED',
      });

      await service.processWebhook({
        providerOrderId: 'mock',
        status: 'REFUNDED',
      } as any);

      expect(mockPrismaService.campaign.update).toHaveBeenCalledWith({
        where: { id: 'c1' },
        data: { raisedAmount: { decrement: 500 } },
      });
    });

    it('should be idempotent', async () => {
      mockPrismaService.donation.findUnique.mockResolvedValue({
        id: 'd3',
        status: 'SUCCESS',
      });
      const res = await service.processWebhook({
        providerOrderId: 'mock',
        status: 'SUCCESS',
      } as any);
      expect(res.id).toBe('d3');
      expect(mockPrismaService.donation.update).not.toHaveBeenCalled();
    });

    it('should enforce INR', async () => {
      await expect(
        service.createDonation(null, { amount: 1000, currency: 'USD' } as any),
      ).rejects.toThrow(BadRequestException);
    });
  });
});
