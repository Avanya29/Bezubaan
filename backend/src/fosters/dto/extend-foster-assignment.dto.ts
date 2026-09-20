import { IsDateString, IsNotEmpty } from 'class-validator';

export class ExtendFosterAssignmentDto {
  @IsDateString()
  @IsNotEmpty()
  expectedEndDate: string;
}
