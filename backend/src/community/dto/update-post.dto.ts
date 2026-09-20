import { IsEnum, IsOptional, IsString, MaxLength } from 'class-validator';
import { PostPrivacy } from '@prisma/client';

export class UpdatePostDto {
  @IsEnum(PostPrivacy)
  @IsOptional()
  privacy?: PostPrivacy;

  @IsString()
  @IsOptional()
  @MaxLength(5000)
  content?: string;
}
