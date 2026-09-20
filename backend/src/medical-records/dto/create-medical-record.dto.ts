import {
  IsString,
  IsNotEmpty,
  IsOptional,
  IsNumber,
  IsUUID,
  IsArray,
  ValidateNested,
  Max,
} from 'class-validator';
import { Type } from 'class-transformer';

export class CreateMedicalRecordDto {
  @IsUUID()
  @IsNotEmpty()
  animalId: string;

  @IsOptional()
  @IsUUID()
  rescueCaseId?: string;

  @IsString()
  @IsNotEmpty()
  notes: string;

  @IsOptional()
  @IsString()
  diagnosis?: string;

  @IsOptional()
  @IsString()
  medications?: string;

  @IsOptional()
  @IsNumber()
  cost?: number;

  @IsOptional()
  @IsArray()
  @ValidateNested({ each: true })
  @Type(() => AttachmentDto)
  attachments?: AttachmentDto[];
}

export class AttachmentDto {
  @IsString()
  url: string;

  @IsString()
  mimeType: string;

  @IsNumber()
  @Max(10 * 1024 * 1024, { message: 'File size must not exceed 10 MB' })
  sizeBytes: number;
}
