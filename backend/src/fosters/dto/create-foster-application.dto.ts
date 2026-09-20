import { IsString, IsNotEmpty, IsOptional } from 'class-validator';

export class CreateFosterApplicationDto {
  @IsString()
  @IsNotEmpty()
  animalId: string;

  @IsString()
  @IsNotEmpty()
  reason: string;

  @IsString()
  @IsNotEmpty()
  housingInformation: string;

  @IsString()
  @IsNotEmpty()
  householdInformation: string;

  @IsString()
  @IsNotEmpty()
  existingAnimals: string;

  @IsString()
  @IsNotEmpty()
  priorAnimalCareExperience: string;

  @IsString()
  @IsNotEmpty()
  availability: string;

  @IsString()
  @IsOptional()
  additionalNotes?: string;
}
