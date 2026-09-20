import {
  Controller,
  Get,
  Post,
  Body,
  Patch,
  Param,
  UseGuards,
} from '@nestjs/common';
import { NotificationsService } from './notifications.service';
import { UpdatePreferenceDto } from './dto/update-preference.dto';
import { JwtAuthGuard } from '../auth/guards/jwt-auth.guard';
import { CurrentUser } from '../auth/decorators/current-user.decorator';

@UseGuards(JwtAuthGuard)
@Controller('notifications')
export class NotificationsController {
  constructor(private readonly notificationsService: NotificationsService) {}

  @Get()
  getUserNotifications(@CurrentUser() user: any) {
    return this.notificationsService.getUserNotifications(user.userId);
  }

  @Patch(':id/read')
  markAsRead(@CurrentUser() user: any, @Param('id') id: string) {
    return this.notificationsService.markAsRead(user.userId, id);
  }

  @Get('preferences')
  getUserPreferences(@CurrentUser() user: any) {
    return this.notificationsService.getUserPreferences(user.userId);
  }

  @Post('preferences')
  updatePreference(
    @CurrentUser() user: any,
    @Body() updatePreferenceDto: UpdatePreferenceDto,
  ) {
    return this.notificationsService.updatePreference(
      user.userId,
      updatePreferenceDto,
    );
  }
}
