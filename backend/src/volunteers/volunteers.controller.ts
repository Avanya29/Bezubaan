import {
  Controller,
  Post,
  Body,
  UseGuards,
  Patch,
  Param,
  Get,
} from '@nestjs/common';
import { VolunteersService } from './volunteers.service';
import {
  ApplyVolunteerDto,
  UpdateAvailabilityDto,
  UpdateLocationDto,
} from './dto/volunteer.dto';
import { JwtAuthGuard } from '../auth/guards/jwt-auth.guard';
import { RolesGuard } from '../auth/guards/roles.guard';
import { Roles } from '../auth/decorators/roles.decorator';
import { CurrentUser } from '../auth/decorators/current-user.decorator';
import { Role } from '@prisma/client';

@Controller('api/volunteers')
@UseGuards(JwtAuthGuard, RolesGuard)
export class VolunteersController {
  constructor(private readonly volunteersService: VolunteersService) {}

  @Post('apply')
  apply(@CurrentUser() user: any, @Body() dto: ApplyVolunteerDto) {
    return this.volunteersService.apply(user.userId, dto);
  }

  @Patch(':id/approve')
  @Roles(Role.ADMIN, Role.NGO_ADMIN)
  approve(@Param('id') id: string) {
    return this.volunteersService.approve(id);
  }

  @Patch('me/availability')
  @Roles(Role.VOLUNTEER)
  updateAvailability(
    @CurrentUser() user: any,
    @Body() dto: UpdateAvailabilityDto,
  ) {
    return this.volunteersService.updateAvailability(user.userId, dto);
  }

  @Patch('me/location')
  @Roles(Role.VOLUNTEER)
  updateLocation(@CurrentUser() user: any, @Body() dto: UpdateLocationDto) {
    return this.volunteersService.updateLocation(user.userId, dto);
  }

  @Get('candidates')
  @Roles(Role.ADMIN, Role.NGO_ADMIN)
  getCandidates() {
    return this.volunteersService.getCandidates();
  }
}
