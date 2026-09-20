import { Controller, Post, Body, UseGuards, Param } from '@nestjs/common';
import { AssignmentsService } from './assignments.service';
import { CreateAssignmentDto, RejectAssignmentDto } from './dto/assignment.dto';
import { JwtAuthGuard } from '../auth/guards/jwt-auth.guard';
import { RolesGuard } from '../auth/guards/roles.guard';
import { Roles } from '../auth/decorators/roles.decorator';
import { CurrentUser } from '../auth/decorators/current-user.decorator';
import { Role } from '@prisma/client';

@Controller('api/assignments')
@UseGuards(JwtAuthGuard, RolesGuard)
export class AssignmentsController {
  constructor(private readonly assignmentsService: AssignmentsService) {}

  @Post()
  @Roles(Role.ADMIN, Role.NGO_ADMIN)
  createAssignment(@CurrentUser() user: any, @Body() dto: CreateAssignmentDto) {
    return this.assignmentsService.createAssignment(user.userId, dto);
  }

  @Post(':id/accept')
  @Roles(Role.VOLUNTEER)
  acceptAssignment(@Param('id') id: string, @CurrentUser() user: any) {
    return this.assignmentsService.acceptAssignment(user.userId, id);
  }

  @Post(':id/reject')
  @Roles(Role.VOLUNTEER)
  rejectAssignment(
    @Param('id') id: string,
    @CurrentUser() user: any,
    @Body() dto: RejectAssignmentDto,
  ) {
    return this.assignmentsService.rejectAssignment(user.userId, id, dto);
  }
}
