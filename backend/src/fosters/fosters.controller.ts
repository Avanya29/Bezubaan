import {
  Controller,
  Post,
  Body,
  Patch,
  Param,
  Get,
  UseGuards,
} from '@nestjs/common';
import { FostersService } from './fosters.service';
import { CreateFosterApplicationDto } from './dto/create-foster-application.dto';
import { ExtendFosterAssignmentDto } from './dto/extend-foster-assignment.dto';
import { JwtAuthGuard } from '../auth/guards/jwt-auth.guard';
import { RolesGuard } from '../auth/guards/roles.guard';
import { Roles } from '../auth/decorators/roles.decorator';
import { CurrentUser } from '../auth/decorators/current-user.decorator';
import { Role } from '@prisma/client';

@Controller('api/fosters')
@UseGuards(JwtAuthGuard, RolesGuard)
export class FostersController {
  constructor(private readonly fostersService: FostersService) {}

  @Post('apply')
  async apply(
    @CurrentUser() user: any,
    @Body() dto: CreateFosterApplicationDto,
  ) {
    return this.fostersService.apply(user.id, dto);
  }

  @Patch(':id/approve')
  @Roles(Role.NGO_ADMIN, Role.ADMIN)
  async approve(@Param('id') id: string) {
    return this.fostersService.approve(id);
  }

  @Patch('assignments/:id/extend')
  @Roles(Role.NGO_ADMIN, Role.ADMIN)
  async extend(
    @Param('id') id: string,
    @Body() dto: ExtendFosterAssignmentDto,
  ) {
    return this.fostersService.extend(id, dto);
  }

  @Patch('assignments/:id/complete')
  @Roles(Role.NGO_ADMIN, Role.ADMIN)
  async complete(@Param('id') id: string) {
    return this.fostersService.complete(id);
  }

  @Get()
  async findAll(@CurrentUser() user: any) {
    return this.fostersService.findAll(user);
  }
}
