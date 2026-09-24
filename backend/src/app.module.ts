import { Module } from '@nestjs/common';
import { AppConfigModule } from './config';
import { DatabaseModule } from './database';
import { HealthModule } from './health';
import { AuthModule } from './auth/auth.module';
import { UsersModule } from './users/users.module';
import { AnimalsModule } from './animals/animals.module';
import { RescuesModule } from './rescues/rescues.module';
import { FirebaseModule } from './firebase/firebase.module';
import { AiModule } from './ai/ai.module';
import { VolunteersModule } from './volunteers/volunteers.module';
import { AssignmentsModule } from './assignments/assignments.module';
import { EventsModule } from './events/events.module';
import { AdoptionsModule } from './adoptions/adoptions.module';
import { FostersModule } from './fosters/fosters.module';
import { VetsModule } from './vets/vets.module';
import { MedicalRecordsModule } from './medical-records/medical-records.module';
import { CommunityModule } from './community/community.module';
import { LostFoundModule } from './lost-found/lost-found.module';
import { DonationsModule } from './donations/donations.module';
import { NotificationsModule } from './notifications/notifications.module';

import { ServeStaticModule } from '@nestjs/serve-static';
import { join } from 'path';

@Module({
  imports: [
    ServeStaticModule.forRoot({
      rootPath: join(__dirname, '..', 'public'),
    }),
    AppConfigModule,
    DatabaseModule,
    HealthModule,
    AuthModule,
    UsersModule,
    AnimalsModule,
    RescuesModule,
    FirebaseModule,
    AiModule,
    VolunteersModule,
    AssignmentsModule,
    EventsModule,
    AdoptionsModule,
    FostersModule,
    VetsModule,
    MedicalRecordsModule,
    CommunityModule,
    LostFoundModule,
    DonationsModule,
    NotificationsModule,
  ],
})
export class AppModule {}
