import {
  Controller,
  Post,
  Get,
  Patch,
  Param,
  Body,
  UseGuards,
} from '@nestjs/common';
import { AnimalsService } from './animals.service';
import { CreateAnimalDto } from './dto/create-animal.dto';
import { UpdateAnimalDto } from './dto/update-animal.dto';
import { JwtAuthGuard } from '../auth/guards/jwt-auth.guard';
import { RolesGuard } from '../auth/guards/roles.guard';
import { Roles } from '../auth/decorators/roles.decorator';
import { Role } from '@prisma/client';

@Controller('animals')
export class AnimalsController {
  constructor(private readonly animalsService: AnimalsService) {}

  // Anyone can retrieve basic animal profiles
  @Get(':id')
  async findOne(@Param('id') id: string) {
    return this.animalsService.findOne(id);
  }

  // Only authorized roles can create standalone animals (rescues can also create them as part of report)
  @UseGuards(JwtAuthGuard, RolesGuard)
  @Roles(Role.VOLUNTEER, Role.VETERINARIAN, Role.NGO_ADMIN, Role.ADMIN)
  @Post()
  async create(@Body() createAnimalDto: CreateAnimalDto) {
    return this.animalsService.create(createAnimalDto);
  }

  @UseGuards(JwtAuthGuard, RolesGuard)
  @Roles(Role.VOLUNTEER, Role.VETERINARIAN, Role.NGO_ADMIN, Role.ADMIN)
  @Patch(':id')
  async update(
    @Param('id') id: string,
    @Body() updateAnimalDto: UpdateAnimalDto,
  ) {
    return this.animalsService.update(id, updateAnimalDto);
  }
}
