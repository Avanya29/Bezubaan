import { Injectable, Logger } from '@nestjs/common';

@Injectable()
export class EmailDispatcher {
  private readonly logger = new Logger(EmailDispatcher.name);

  async dispatch(
    userId: string,
    title: string,
    message: string,
    payload?: any,
  ): Promise<boolean> {
    try {
      this.logger.log(`[EmailDispatcher] Sending email to ${userId}: ${title}`);
      // Simulate external provider boundary (e.g. SendGrid/AWS SES)
      return true;
    } catch (error: any) {
      this.logger.error(
        `[EmailDispatcher] Failed to send email to ${userId}`,
        error.stack,
      );
      return false;
    }
  }
}
