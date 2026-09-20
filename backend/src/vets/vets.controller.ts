import {
  Controller,
  Post,
  Body,
  Patch,
  Param,
  Get,
  UseGuards,
} from '@nestjs/common';
import { VetsService } from './vets.service';
import { ApplyVetDto } from './dto/apply-vet.dto';
import { JwtAuthGuard } from '../auth/guards/jwt-auth.guard';
import { RolesGuard } from '../auth/guards/roles.guard';
import { Roles } from '../auth/decorators/roles.decorator';
import { CurrentUser } from '../auth/decorators/current-user.decorator';
import { Role } from '@prisma/client';

@UseGuards(JwtAuthGuard, RolesGuard)
@Controller('api/vets')
export class VetsController {
  constructor(private readonly vetsService: VetsService) {}

  @Post('apply')
  apply(@CurrentUser() user: any, @Body() applyVetDto: ApplyVetDto) {
    return this.vetsService.apply(user.id, applyVetDto);
  }

  @Roles(Role.NGO_ADMIN, Role.ADMIN)
  @Patch(':id/approve')
  approve(@Param('id') id: string) {
    return this.vetsService.approve(id);
  }

  @Roles(Role.VETERINARIAN)
  @Get('cases')
  getCases(@CurrentUser() user: any) {
    return this.vetsService.getCasesForVet(user.id);
  }
}
