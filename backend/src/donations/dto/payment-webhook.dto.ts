import { IsString, IsEnum, IsOptional } from 'class-validator';
import { PaymentStatus } from '@prisma/client';

export class PaymentWebhookDto {
  @IsString()
  providerOrderId: string;

  @IsOptional()
  @IsString()
  providerPaymentId?: string;

  @IsEnum(PaymentStatus)
  status: PaymentStatus;
}
