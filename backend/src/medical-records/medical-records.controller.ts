import { Controller, Post, Body, Param, Get, UseGuards } from '@nestjs/common';
import { MedicalRecordsService } from './medical-records.service';
import { CreateMedicalRecordDto } from './dto/create-medical-record.dto';
import { AddendumDto } from './dto/addendum.dto';
import { JwtAuthGuard } from '../auth/guards/jwt-auth.guard';
import { RolesGuard } from '../auth/guards/roles.guard';
import { Roles } from '../auth/decorators/roles.decorator';
import { CurrentUser } from '../auth/decorators/current-user.decorator';
import { Role } from '@prisma/client';

@UseGuards(JwtAuthGuard, RolesGuard)
@Controller('api/medical-records')
export class MedicalRecordsController {
  constructor(private readonly medicalRecordsService: MedicalRecordsService) {}

  @Roles(Role.VETERINARIAN, Role.NGO_ADMIN, Role.ADMIN)
  @Post()
  create(@CurrentUser() user: any, @Body() dto: CreateMedicalRecordDto) {
    return this.medicalRecordsService.create(user.id, dto);
  }

  @Roles(Role.VETERINARIAN, Role.NGO_ADMIN, Role.ADMIN)
  @Post(':id/addendum')
  addAddendum(@Param('id') id: string, @Body() dto: AddendumDto) {
    return this.medicalRecordsService.addAddendum(id, dto.notes);
  }

  @Roles(Role.VETERINARIAN, Role.NGO_ADMIN, Role.ADMIN)
  @Get('animal/:animalId')
  getByAnimal(@Param('animalId') animalId: string, @CurrentUser() user: any) {
    return this.medicalRecordsService.getByAnimal(animalId, user);
  }
}
