import { Module, Logger } from '@nestjs/common';
import { NotificationsService } from './notifications.service';
import { NotificationsController } from './notifications.controller';
import { PushDispatcher } from './dispatchers/push.dispatcher';
import { EmailDispatcher } from './dispatchers/email.dispatcher';
import { DatabaseModule } from '../database/database.module';
import { ClientsModule, Transport } from '@nestjs/microservices';

const logger = new Logger('NotificationsModule');

// Only register RabbitMQ client if the URL is configured and reachable.
// This prevents the app from hanging on startup when RABBITMQ_URL is not set
// (e.g., on Render free tier without a RabbitMQ addon).
const rmqImports = process.env.RABBITMQ_URL
  ? [
      ClientsModule.register([
        {
          name: 'RABBITMQ_SERVICE',
          transport: Transport.RMQ,
          options: {
            urls: [process.env.RABBITMQ_URL],
            queue: 'notifications_queue',
            queueOptions: {
              durable: false,
            },
            // Don't block startup if RabbitMQ is temporarily unavailable
            noAck: true,
            socketOptions: {
              heartbeatIntervalInSeconds: 60,
              reconnectTimeInSeconds: 5,
            },
          },
        },
      ]),
    ]
  : (() => {
      logger.warn(
        'RABBITMQ_URL is not set — RabbitMQ client will NOT be registered. Notification queuing is disabled.',
      );
      return [];
    })();

@Module({
  imports: [DatabaseModule, ...rmqImports],
  controllers: [NotificationsController],
  providers: [NotificationsService, PushDispatcher, EmailDispatcher],
  exports: [NotificationsService],
})
export class NotificationsModule {}
