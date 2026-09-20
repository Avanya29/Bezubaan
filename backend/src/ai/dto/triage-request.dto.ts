import {
  IsString,
  IsNumber,
  IsOptional,
  ValidateNested,
} from 'class-validator';
import { Type } from 'class-transformer';

class LocationDto {
  @IsNumber()
  latitude: number;

  @IsNumber()
  longitude: number;

  @IsOptional()
  @IsString()
  address?: string;
}

class AnimalInfoDto {
  @IsOptional()
  @IsString()
  species?: string;

  @IsOptional()
  @IsString()
  breed?: string;

  @IsOptional()
  @IsString()
  approximate_age?: string;

  @IsOptional()
  @IsString()
  description?: string;
}

export class TriageRequestDto {
  @IsString()
  rescue_id: string;

  @IsString()
  description: string;

  @ValidateNested()
  @Type(() => LocationDto)
  location: LocationDto;

  @IsOptional()
  @IsString()
  image_url?: string;

  @IsOptional()
  @ValidateNested()
  @Type(() => AnimalInfoDto)
  animal_info?: AnimalInfoDto;

  @IsOptional()
  @IsString()
  correlation_id?: string;
}
