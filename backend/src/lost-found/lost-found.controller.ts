import {
  Controller,
  Get,
  Post,
  Body,
  Patch,
  Param,
  Delete,
  UseGuards,
} from '@nestjs/common';
import { LostFoundService } from './lost-found.service';
import { CreateLostFoundDto } from './dto/create-lost-found.dto';
import { UpdateLostFoundStatusDto } from './dto/update-lost-found-status.dto';
import { JwtAuthGuard } from '../auth/guards/jwt-auth.guard';
import { CurrentUser } from '../auth/decorators/current-user.decorator';

@Controller('lost-found')
export class LostFoundController {
  constructor(private readonly lostFoundService: LostFoundService) {}

  @Post()
  @UseGuards(JwtAuthGuard)
  create(
    @CurrentUser() user: any,
    @Body() createLostFoundDto: CreateLostFoundDto,
  ) {
    return this.lostFoundService.create(user.id, createLostFoundDto);
  }

  @Get()
  findAll() {
    return this.lostFoundService.findAll();
  }

  @Get(':id')
  findOne(@Param('id') id: string) {
    return this.lostFoundService.findOne(id);
  }

  @Patch(':id/status')
  @UseGuards(JwtAuthGuard)
  updateStatus(
    @Param('id') id: string,
    @CurrentUser() user: any,
    @Body() updateStatusDto: UpdateLostFoundStatusDto,
  ) {
    return this.lostFoundService.updateStatus(id, user.id, updateStatusDto);
  }

  @Delete(':id')
  @UseGuards(JwtAuthGuard)
  remove(@Param('id') id: string, @CurrentUser() user: any) {
    return this.lostFoundService.remove(id, user.id);
  }
}
