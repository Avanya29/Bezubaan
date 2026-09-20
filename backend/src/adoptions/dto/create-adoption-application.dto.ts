import { IsString, IsNotEmpty, IsOptional } from 'class-validator';

export class CreateAdoptionApplicationDto {
  @IsString()
  @IsNotEmpty()
  animalId: string;

  @IsString()
  @IsNotEmpty()
  motivation: string;

  @IsString()
  @IsNotEmpty()
  housingType: string;

  @IsString()
  @IsNotEmpty()
  householdInformation: string;

  @IsString()
  @IsNotEmpty()
  previousPetExperience: string;

  @IsString()
  @IsNotEmpty()
  existingAnimals: string;

  @IsString()
  @IsOptional()
  additionalNotes?: string;
}
