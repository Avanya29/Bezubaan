import { IsBoolean, IsEnum, IsNotEmpty } from 'class-validator';
import { NotificationCategory } from '@prisma/client';

export class UpdatePreferenceDto {
  @IsEnum(NotificationCategory)
  @IsNotEmpty()
  category: NotificationCategory;

  @IsBoolean()
  @IsNotEmpty()
  isEnabled: boolean;
}
