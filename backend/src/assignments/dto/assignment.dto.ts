import { IsBoolean, IsOptional, IsString } from 'class-validator';

export class CreateAssignmentDto {
  @IsString()
  rescueCaseId: string;

  @IsString()
  volunteerId: string;

  @IsBoolean()
  @IsOptional()
  overrideCapacityLimit?: boolean;
}

export class RejectAssignmentDto {
  @IsString()
  rejectionReason: string;
}
