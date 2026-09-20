import { IsEnum, IsNotEmpty } from 'class-validator';
import { LostFoundStatus } from '@prisma/client';

export class UpdateLostFoundStatusDto {
  @IsEnum(LostFoundStatus)
  @IsNotEmpty()
  status: LostFoundStatus;
}
