import {
  Controller,
  Post,
  Body,
  Patch,
  Param,
  Get,
  UseGuards,
} from '@nestjs/common';
import { AdoptionsService } from './adoptions.service';
import { CreateAdoptionApplicationDto } from './dto/create-adoption-application.dto';
import { JwtAuthGuard } from '../auth/guards/jwt-auth.guard';
import { RolesGuard } from '../auth/guards/roles.guard';
import { Roles } from '../auth/decorators/roles.decorator';
import { CurrentUser } from '../auth/decorators/current-user.decorator';
import { Role } from '@prisma/client';

@Controller('api/adoptions')
@UseGuards(JwtAuthGuard, RolesGuard)
export class AdoptionsController {
  constructor(private readonly adoptionsService: AdoptionsService) {}

  @Post('apply')
  async apply(
    @CurrentUser() user: any,
    @Body() dto: CreateAdoptionApplicationDto,
  ) {
    return this.adoptionsService.apply(user.id, dto);
  }

  @Patch(':id/approve')
  @Roles(Role.NGO_ADMIN, Role.ADMIN)
  async approve(@Param('id') id: string) {
    return this.adoptionsService.approve(id);
  }

  @Patch(':id/reject')
  @Roles(Role.NGO_ADMIN, Role.ADMIN)
  async reject(@Param('id') id: string) {
    return this.adoptionsService.reject(id);
  }

  @Get()
  async findAll(@CurrentUser() user: any) {
    return this.adoptionsService.findAll(user);
  }
}
