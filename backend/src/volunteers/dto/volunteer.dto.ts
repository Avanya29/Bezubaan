import {
  IsArray,
  IsNumber,
  IsOptional,
  IsString,
  ArrayMinSize,
  IsEnum,
} from 'class-validator';
import { VolunteerAvailabilityStatus } from '@prisma/client';

export class ApplyVolunteerDto {
  @IsString()
  serviceArea: string;

  @IsArray()
  @IsString({ each: true })
  @ArrayMinSize(1)
  capabilities: string[];

  @IsOptional()
  @IsString()
  bio?: string;

  @IsOptional()
  @IsString()
  experience?: string;

  @IsOptional()
  @IsString()
  emergencyContact?: string;

  @IsOptional()
  @IsString()
  preferredRescueTypes?: string;

  @IsOptional()
  @IsString()
  additionalNotes?: string;
}

export class UpdateAvailabilityDto {
  @IsEnum(VolunteerAvailabilityStatus)
  availabilityStatus: VolunteerAvailabilityStatus;

  @IsOptional()
  @IsNumber()
  currentLatitude?: number;

  @IsOptional()
  @IsNumber()
  currentLongitude?: number;
}

export class UpdateLocationDto {
  @IsNumber()
  currentLatitude: number;

  @IsNumber()
  currentLongitude: number;
}
