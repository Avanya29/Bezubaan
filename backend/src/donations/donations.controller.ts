import {
  Controller,
  Post,
  Get,
  Body,
  UseGuards,
  HttpCode,
  HttpStatus,
} from '@nestjs/common';
import { DonationsService } from './donations.service';
import { CreateCampaignDto } from './dto/create-campaign.dto';
import { CreateDonationDto } from './dto/create-donation.dto';
import { PaymentWebhookDto } from './dto/payment-webhook.dto';
import { JwtAuthGuard } from '../auth/guards/jwt-auth.guard';
import { OptionalJwtAuthGuard } from '../auth/guards/optional-jwt-auth.guard';
import { CurrentUser } from '../auth/decorators/current-user.decorator';
import { RazorpayWebhookGuard } from './guards/razorpay-webhook.guard';

@Controller('donations')
export class DonationsController {
  constructor(private readonly donationsService: DonationsService) {}

  @Post('campaigns')
  @UseGuards(JwtAuthGuard)
  async createCampaign(
    @CurrentUser() user: any,
    @Body() dto: CreateCampaignDto,
  ) {
    return this.donationsService.createCampaign(user.id, dto);
  }

  @Get('campaigns')
  async listCampaigns() {
    return this.donationsService.listCampaigns();
  }

  @Post()
  @UseGuards(OptionalJwtAuthGuard)
  async createDonation(
    @CurrentUser() user: any,
    @Body() dto: CreateDonationDto,
  ) {
    const donorId = user?.id || null;
    return this.donationsService.createDonation(donorId, dto);
  }

  @Post('webhook')
  @UseGuards(RazorpayWebhookGuard)
  @HttpCode(HttpStatus.OK)
  async handleWebhook(@Body() dto: PaymentWebhookDto) {
    return this.donationsService.processWebhook(dto);
  }
}
