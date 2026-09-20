import { IsString, IsNotEmpty } from 'class-validator';

export class AddendumDto {
  @IsString()
  @IsNotEmpty()
  notes: string;
}
