import {
  Injectable,
  NotFoundException,
  BadRequestException,
} from '@nestjs/common';
import { PrismaService } from '../database/prisma.service';
import { CreateCampaignDto } from './dto/create-campaign.dto';
import { CreateDonationDto } from './dto/create-donation.dto';
import { PaymentWebhookDto } from './dto/payment-webhook.dto';
import { PaymentStatus } from '@prisma/client';

@Injectable()
export class DonationsService {
  constructor(private readonly prisma: PrismaService) {}

  async createCampaign(creatorId: string, dto: CreateCampaignDto) {
    if (dto.rescueCaseId) {
      const rescueCase = await this.prisma.rescueCase.findUnique({
        where: { id: dto.rescueCaseId },
      });
      if (!rescueCase) throw new NotFoundException('Rescue case not found');
    }
    if (dto.animalId) {
      const animal = await this.prisma.animal.findUnique({
        where: { id: dto.animalId },
      });
      if (!animal) throw new NotFoundException('Animal not found');
    }
    return this.prisma.campaign.create({ data: { ...dto, creatorId } });
  }

  async listCampaigns() {
    return this.prisma.campaign.findMany({ orderBy: { createdAt: 'desc' } });
  }

  async createDonation(donorId: string | null, dto: CreateDonationDto) {
    if (dto.campaignId) {
      const campaign = await this.prisma.campaign.findUnique({
        where: { id: dto.campaignId },
      });
      if (!campaign) throw new NotFoundException('Campaign not found');
    }
    if (dto.currency && dto.currency !== 'INR') {
      throw new BadRequestException('Currency must be INR');
    }
    const mockProviderOrderId = `rzp_order_mock_${Date.now()}_${Math.floor(Math.random() * 1000)}`;

    return this.prisma.donation.create({
      data: {
        donorId,
        campaignId: dto.campaignId,
        isAnonymous: dto.isAnonymous ?? false,
        amount: dto.amount,
        currency: 'INR',
        status: PaymentStatus.PENDING,
        providerOrderId: mockProviderOrderId,
      },
    });
  }

  async processWebhook(dto: PaymentWebhookDto) {
    const donation = await this.prisma.donation.findUnique({
      where: { providerOrderId: dto.providerOrderId },
    });
    if (!donation)
      throw new NotFoundException('Donation with given order ID not found');

    // Idempotency check
    if (donation.status === dto.status) return donation;

    // Validate transition
    if (
      donation.status === PaymentStatus.SUCCESS &&
      dto.status === PaymentStatus.REFUNDED
    ) {
      // Valid transition: SUCCESS -> REFUNDED
    } else if (
      donation.status === PaymentStatus.PENDING &&
      (dto.status === PaymentStatus.SUCCESS ||
        dto.status === PaymentStatus.FAILED ||
        dto.status === PaymentStatus.REFUNDED)
    ) {
      // Valid transition: PENDING -> SUCCESS | FAILED | REFUNDED
    } else {
      throw new BadRequestException(
        `Cannot transition from ${donation.status} to ${dto.status}`,
      );
    }

    return this.prisma.$transaction(async (tx) => {
      const updatedDonation = await tx.donation.update({
        where: { id: donation.id },
        data: { status: dto.status, providerPaymentId: dto.providerPaymentId },
      });

      if (donation.campaignId) {
        if (dto.status === PaymentStatus.SUCCESS) {
          await tx.campaign.update({
            where: { id: donation.campaignId },
            data: { raisedAmount: { increment: donation.amount } },
          });
        } else if (
          donation.status === PaymentStatus.SUCCESS &&
          dto.status === PaymentStatus.REFUNDED
        ) {
          // If refunding a previously successful donation, decrement the raised amount
          await tx.campaign.update({
            where: { id: donation.campaignId },
            data: { raisedAmount: { decrement: donation.amount } },
          });
        }
      }

      return updatedDonation;
    });
  }
}
