import { IsString, IsOptional, IsInt, Min, IsBoolean } from 'class-validator';

export class CreateDonationDto {
  @IsOptional()
  @IsString()
  campaignId?: string;

  @IsOptional()
  @IsBoolean()
  isAnonymous?: boolean;

  @IsInt()
  @Min(1000) // Minimum INR 10 in paise
  amount: number;

  @IsOptional()
  @IsString()
  currency?: string;
}
