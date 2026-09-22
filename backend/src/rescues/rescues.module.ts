import { Module } from '@nestjs/common';
import { RescuesService } from './rescues.service';
import { RescuesController } from './rescues.controller';
import { DatabaseModule } from '../database/database.module';
import { AiModule } from '../ai/ai.module';
import { EventsModule } from '../events/events.module';
import { FirebaseModule } from '../firebase/firebase.module';

@Module({
  imports: [DatabaseModule, AiModule, EventsModule, FirebaseModule],
  controllers: [RescuesController],
  providers: [RescuesService],
  exports: [RescuesService],
})
export class RescuesModule {}
