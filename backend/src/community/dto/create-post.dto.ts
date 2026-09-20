import {
  IsEnum,
  IsNotEmpty,
  IsOptional,
  IsString,
  MaxLength,
} from 'class-validator';
import { PostType, PostPrivacy } from '@prisma/client';

export class CreatePostDto {
  @IsEnum(PostType)
  @IsOptional()
  type?: PostType;

  @IsEnum(PostPrivacy)
  @IsOptional()
  privacy?: PostPrivacy;

  @IsString()
  @IsNotEmpty()
  @MaxLength(5000)
  content: string;
}
