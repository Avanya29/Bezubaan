import { Injectable, Logger } from '@nestjs/common';

@Injectable()
export class PushDispatcher {
  private readonly logger = new Logger(PushDispatcher.name);

  async dispatch(
    userId: string,
    title: string,
    message: string,
    payload?: any,
  ): Promise<boolean> {
    try {
      this.logger.log(`[PushDispatcher] Sending push to ${userId}: ${title}`);
      // Simulate external provider boundary (e.g. Firebase/APNs)
      return true;
    } catch (error: any) {
      this.logger.error(
        `[PushDispatcher] Failed to send push to ${userId}`,
        error.stack,
      );
      return false; // failures handled safely without throwing up to rabbitmq blindly unless retrying
    }
  }
}
