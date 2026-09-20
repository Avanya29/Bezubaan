import { Module } from '@nestjs/common';
import { VetsController } from './vets.controller';
import { VetsService } from './vets.service';
import { DatabaseModule } from '../database/database.module';

@Module({
  imports: [DatabaseModule],
  controllers: [VetsController],
  providers: [VetsService],
})
export class VetsModule {}
