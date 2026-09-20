import { IsString, IsOptional, IsInt, Min } from 'class-validator';

export class CreateCampaignDto {
  @IsString()
  title: string;

  @IsString()
  description: string;

  @IsOptional()
  @IsInt()
  @Min(1)
  goalAmount?: number;

  @IsOptional()
  @IsString()
  rescueCaseId?: string;

  @IsOptional()
  @IsString()
  animalId?: string;
}
