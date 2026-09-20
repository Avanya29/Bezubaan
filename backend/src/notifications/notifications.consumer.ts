import { Controller, Logger } from '@nestjs/common';
import { EventPattern, Payload, Ctx, RmqContext } from '@nestjs/microservices';
import { PushDispatcher } from './dispatchers/push.dispatcher';
import { EmailDispatcher } from './dispatchers/email.dispatcher';

@Controller()
export class NotificationsConsumer {
  private readonly logger = new Logger(NotificationsConsumer.name);

  constructor(
    private readonly pushDispatcher: PushDispatcher,
    private readonly emailDispatcher: EmailDispatcher,
  ) {}

  @EventPattern('notification.dispatch')
  async handleNotificationEvent(
    @Payload() data: any,
    @Ctx() context: RmqContext,
  ) {
    const channel = context.getChannelRef();
    const originalMsg = context.getMessage();

    try {
      this.logger.log(`Received notification event: ${JSON.stringify(data)}`);

      if (data.channel === 'PUSH') {
        await this.pushDispatcher.dispatch(
          data.userId,
          data.title,
          data.message,
          data.payload,
        );
      } else if (data.channel === 'EMAIL') {
        await this.emailDispatcher.dispatch(
          data.userId,
          data.title,
          data.message,
          data.payload,
        );
      }

      channel.ack(originalMsg);
    } catch (error: any) {
      this.logger.error(`Failed to process notification: ${error.message}`);
      // Nack without requeue for unrecoverable errors to prevent infinite loops (dead letter)
      channel.nack(originalMsg, false, false);
    }
  }
}
