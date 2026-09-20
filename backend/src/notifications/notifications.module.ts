import { Module } from '@nestjs/common';
import { NotificationsService } from './notifications.service';
import { NotificationsController } from './notifications.controller';
import { NotificationsConsumer } from './notifications.consumer';
import { PushDispatcher } from './dispatchers/push.dispatcher';
import { EmailDispatcher } from './dispatchers/email.dispatcher';
import { DatabaseModule } from '../database/database.module';
import { ClientsModule, Transport } from '@nestjs/microservices';

@Module({
  imports: [
    DatabaseModule,
    ClientsModule.register([
      {
        name: 'RABBITMQ_SERVICE',
        transport: Transport.RMQ,
        options: {
          urls: [process.env.RABBITMQ_URL || 'amqp://localhost:5672'],
          queue: 'notifications_queue',
          queueOptions: {
            durable: false, // in prod: true
          },
        },
      },
    ]),
  ],
  controllers: [NotificationsController, NotificationsConsumer],
  providers: [NotificationsService, PushDispatcher, EmailDispatcher],
  exports: [NotificationsService],
})
export class NotificationsModule {}
