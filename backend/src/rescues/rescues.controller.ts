import {
  Controller,
  Post,
  Body,
  UseGuards,
  Get,
  Patch,
  Param,
} from '@nestjs/common';
import { RescuesService } from './rescues.service';
import {
  CreateRescueDto,
  UpdateRescueStatusDto,
} from './dto/create-rescue.dto';
import { OptionalJwtAuthGuard } from '../auth/guards/optional-jwt-auth.guard';
import { CurrentUser } from '../auth/decorators/current-user.decorator';
import { JwtAuthGuard } from '../auth/guards/jwt-auth.guard';
import { RolesGuard } from '../auth/guards/roles.guard';

@Controller('api/rescues')
export class RescuesController {
  constructor(private readonly rescuesService: RescuesService) {}

  @UseGuards(OptionalJwtAuthGuard)
  @Post()
  async create(
    @Body() createRescueDto: CreateRescueDto,
    @CurrentUser() user: any,
  ) {
    const reporterId = user?.id || null;
    return this.rescuesService.create(createRescueDto, reporterId);
  }

  // Location masking enforced in Service based on user context
  @UseGuards(OptionalJwtAuthGuard)
  @Get()
  async findAll(@CurrentUser() user: any) {
    return this.rescuesService.findAll(user);
  }

  @UseGuards(OptionalJwtAuthGuard)
  @Get(':id')
  async findOne(@Param('id') id: string, @CurrentUser() user: any) {
    return this.rescuesService.findOne(id, user);
  }

  @UseGuards(JwtAuthGuard, RolesGuard)
  @Patch(':id/status')
  async updateStatus(
    @Param('id') id: string,
    @Body() dto: UpdateRescueStatusDto,
    @CurrentUser() user: any,
  ) {
    return this.rescuesService.updateStatus(id, user, dto);
  }

  // Temporary/Admin endpoint to test AI triggering manually
  @Post(':id/trigger-ai')
  async triggerAi(@Param('id') id: string) {
    return this.rescuesService.triggerAiTriage(id);
  }
}
