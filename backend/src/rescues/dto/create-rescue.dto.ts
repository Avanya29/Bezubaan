import {
  IsString,
  IsNumber,
  IsOptional,
  ValidateNested,
  IsObject,
  IsEnum,
} from 'class-validator';
import { Type } from 'class-transformer';
import { RescueStatus } from '@prisma/client';

export class AnimalInputDto {
  @IsString()
  species: string;

  @IsString()
  @IsOptional()
  breed?: string;

  @IsString()
  @IsOptional()
  sex?: string;

  @IsString()
  @IsOptional()
  approximateAge?: string;

  @IsString()
  @IsOptional()
  color?: string;

  @IsString()
  @IsOptional()
  size?: string;

  @IsString()
  @IsOptional()
  identifyingMarks?: string;

  @IsString()
  @IsOptional()
  name?: string;

  @IsString()
  @IsOptional()
  description?: string;
}

export class CreateRescueDto {
  @IsString()
  description: string;

  @IsNumber()
  latitude: number;

  @IsNumber()
  longitude: number;

  @IsString()
  @IsOptional()
  address?: string;

  @IsOptional()
  @ValidateNested()
  @Type(() => AnimalInputDto)
  animal?: AnimalInputDto;
}

export class UpdateRescueStatusDto {
  @IsEnum(RescueStatus)
  status: RescueStatus;
}
