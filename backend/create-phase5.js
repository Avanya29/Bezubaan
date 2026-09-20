const fs = require('fs');
const path = require('path');

const files = {
  // ================= ANIMALS MODULE =================
  'src/animals/dto/create-animal.dto.ts': `
import { IsOptional, IsString } from 'class-validator';

export class CreateAnimalDto {
  @IsString()
  species: string;

  @IsOptional()
  @IsString()
  breed?: string;

  @IsOptional()
  @IsString()
  sex?: string;

  @IsOptional()
  @IsString()
  approximateAge?: string;

  @IsOptional()
  @IsString()
  color?: string;

  @IsOptional()
  @IsString()
  size?: string;

  @IsOptional()
  @IsString()
  identifyingMarks?: string;

  @IsOptional()
  @IsString()
  name?: string;

  @IsOptional()
  @IsString()
  description?: string;
}
  `,

  'src/animals/dto/update-animal.dto.ts': `
import { PartialType } from '@nestjs/mapped-types';
import { CreateAnimalDto } from './create-animal.dto';

export class UpdateAnimalDto extends PartialType(CreateAnimalDto) {}
  `,

  'src/animals/animals.service.ts': `
import { Injectable, NotFoundException } from '@nestjs/common';
import { PrismaService } from '../database/prisma.service';
import { CreateAnimalDto } from './dto/create-animal.dto';
import { UpdateAnimalDto } from './dto/update-animal.dto';

@Injectable()
export class AnimalsService {
  constructor(private prisma: PrismaService) {}

  async create(createAnimalDto: CreateAnimalDto) {
    return this.prisma.animal.create({
      data: createAnimalDto,
    });
  }

  async findOne(id: string) {
    const animal = await this.prisma.animal.findUnique({
      where: { id },
    });
    if (!animal) {
      throw new NotFoundException(\`Animal with ID \${id} not found\`);
    }
    return animal;
  }

  async update(id: string, updateAnimalDto: UpdateAnimalDto) {
    // Verify existence
    await this.findOne(id);
    return this.prisma.animal.update({
      where: { id },
      data: updateAnimalDto,
    });
  }
}
  `,

  'src/animals/animals.controller.ts': `
import { Controller, Post, Get, Patch, Param, Body, UseGuards } from '@nestjs/common';
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
  async update(@Param('id') id: string, @Body() updateAnimalDto: UpdateAnimalDto) {
    return this.animalsService.update(id, updateAnimalDto);
  }
}
  `,

  'src/animals/animals.module.ts': `
import { Module } from '@nestjs/common';
import { AnimalsService } from './animals.service';
import { AnimalsController } from './animals.controller';

@Module({
  controllers: [AnimalsController],
  providers: [AnimalsService],
  exports: [AnimalsService],
})
export class AnimalsModule {}
  `,

  // ================= RESCUES MODULE =================
  'src/rescues/dto/create-rescue.dto.ts': `
import { IsString, IsNumber, IsOptional, ValidateNested } from 'class-validator';
import { Type } from 'class-transformer';
import { CreateAnimalDto } from '../../animals/dto/create-animal.dto';

export class CreateRescueDto {
  @IsString()
  description: string;

  @IsNumber()
  latitude: number;

  @IsNumber()
  longitude: number;

  @IsOptional()
  @IsString()
  address?: string;

  @IsOptional()
  @ValidateNested()
  @Type(() => CreateAnimalDto)
  animal?: CreateAnimalDto;
}
  `,

  'src/auth/guards/optional-jwt-auth.guard.ts': `
import { Injectable, ExecutionContext } from '@nestjs/common';
import { AuthGuard } from '@nestjs/passport';

@Injectable()
export class OptionalJwtAuthGuard extends AuthGuard('jwt') {
  handleRequest(err: any, user: any, info: any, context: ExecutionContext) {
    // Unlike standard JwtAuthGuard, we do not throw an error if user is absent
    return user || null;
  }
}
  `,

  'src/rescues/rescues.service.ts': `
import { Injectable, BadRequestException } from '@nestjs/common';
import { PrismaService } from '../database/prisma.service';
import { CreateRescueDto } from './dto/create-rescue.dto';

@Injectable()
export class RescuesService {
  constructor(private prisma: PrismaService) {}

  async create(createRescueDto: CreateRescueDto, reporterId: string | null) {
    const { animal, ...rescueData } = createRescueDto;
    
    return this.prisma.rescueCase.create({
      data: {
        ...rescueData,
        reporterId, // Nullable for anonymous
        status: 'REPORTED', // Initial status enforced
        animal: animal ? { create: animal } : undefined,
      },
    });
  }

  // Retrieving rescue cases requires ADMIN due to location privacy (UNRESOLVED DECISION)
  async findAll() {
    return this.prisma.rescueCase.findMany();
  }
}
  `,

  'src/rescues/rescues.controller.ts': `
import { Controller, Post, Body, UseGuards, Get } from '@nestjs/common';
import { RescuesService } from './rescues.service';
import { CreateRescueDto } from './dto/create-rescue.dto';
import { OptionalJwtAuthGuard } from '../auth/guards/optional-jwt-auth.guard';
import { CurrentUser } from '../auth/decorators/current-user.decorator';
import { JwtAuthGuard } from '../auth/guards/jwt-auth.guard';
import { RolesGuard } from '../auth/guards/roles.guard';
import { Roles } from '../auth/decorators/roles.decorator';
import { Role } from '@prisma/client';

@Controller('rescues')
export class RescuesController {
  constructor(private readonly rescuesService: RescuesService) {}

  // Allow anonymous reporting. If token is present, we link reporterId.
  @UseGuards(OptionalJwtAuthGuard)
  @Post()
  async create(
    @Body() createRescueDto: CreateRescueDto,
    @CurrentUser() user: any,
  ) {
    const reporterId = user?.id || null;
    return this.rescuesService.create(createRescueDto, reporterId);
  }

  // Location privacy rules are currently UNKNOWN. 
  // Until resolved, ONLY admins can list rescue cases to prevent PII leakage.
  @UseGuards(JwtAuthGuard, RolesGuard)
  @Roles(Role.ADMIN)
  @Get()
  async findAll() {
    return this.rescuesService.findAll();
  }
}
  `,

  'src/rescues/rescues.module.ts': `
import { Module } from '@nestjs/common';
import { RescuesService } from './rescues.service';
import { RescuesController } from './rescues.controller';

@Module({
  controllers: [RescuesController],
  providers: [RescuesService],
  exports: [RescuesService],
})
export class RescuesModule {}
  `,

  // ================= MAPPED TYPES DEPS =================
  'package.json.update': 'Need to ensure @nestjs/mapped-types is installed.'
};

for (const [filepath, content] of Object.entries(files)) {
  if (filepath.endsWith('.update')) continue;
  const dir = path.dirname(filepath);
  if (!fs.existsSync(dir)) {
    fs.mkdirSync(dir, { recursive: true });
  }
  fs.writeFileSync(filepath, content.trim());
}

console.log('Successfully wrote animals and rescues modules');
