import { IsEnum, IsNotEmpty, IsOptional, IsString } from 'class-validator';
import { LostFoundStatus } from '@prisma/client';

export class CreateLostFoundDto {
  @IsEnum(LostFoundStatus)
  @IsOptional()
  status?: LostFoundStatus;

  @IsString()
  @IsNotEmpty()
  animalType: string;

  @IsString()
  @IsNotEmpty()
  description: string;

  @IsString()
  @IsOptional()
  lastSeenLocation?: string;
}
