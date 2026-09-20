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
      throw new NotFoundException(`Animal with ID ${id} not found`);
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
