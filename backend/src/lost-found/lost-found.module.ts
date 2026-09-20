import { Module } from '@nestjs/common';
import { LostFoundController } from './lost-found.controller';
import { LostFoundService } from './lost-found.service';
import { DatabaseModule as PrismaModule } from '../database/database.module';

@Module({
  imports: [PrismaModule],
  controllers: [LostFoundController],
  providers: [LostFoundService],
})
export class LostFoundModule {}
