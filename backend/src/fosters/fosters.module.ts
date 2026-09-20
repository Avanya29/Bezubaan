import { Module } from '@nestjs/common';
import { FostersService } from './fosters.service';
import { FostersController } from './fosters.controller';
import { DatabaseModule } from '../database/database.module';
import { EventsModule } from '../events/events.module';

@Module({
  imports: [DatabaseModule, EventsModule],
  controllers: [FostersController],
  providers: [FostersService],
})
export class FostersModule {}
