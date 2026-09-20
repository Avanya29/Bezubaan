import { IsOptional, IsString } from 'class-validator';

export class ApplyVetDto {
  @IsOptional()
  @IsString()
  licenseNumber?: string;

  @IsOptional()
  @IsString()
  clinicName?: string;
}
