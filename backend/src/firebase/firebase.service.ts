import { Injectable, Logger, OnModuleInit } from '@nestjs/common';
import { ConfigService } from '@nestjs/config';
import { initializeApp, cert } from 'firebase-admin/app';
import { getMessaging } from 'firebase-admin/messaging';

@Injectable()
export class FirebaseService implements OnModuleInit {
  private readonly logger = new Logger(FirebaseService.name);
  private isInitialized = false;

  constructor(private readonly configService: ConfigService) {}

  onModuleInit() {
    this.initializeFirebase();
  }

  private initializeFirebase() {
    try {
      const projectId = this.configService.get<string>('FIREBASE_PROJECT_ID');
      const clientEmail = this.configService.get<string>('FIREBASE_CLIENT_EMAIL');
      const privateKey = this.configService.get<string>('FIREBASE_PRIVATE_KEY')?.replace(/\\n/g, '\n');

      if (!projectId || !clientEmail || !privateKey) {
        this.logger.warn('Firebase Admin SDK credentials not found in environment variables. Push notifications will be disabled.');
        return;
      }

      initializeApp({
        credential: cert({
          projectId,
          clientEmail,
          privateKey,
        }),
      });

      this.isInitialized = true;
      this.logger.log('Firebase Admin SDK successfully initialized.');
    } catch (error) {
      this.logger.error('Failed to initialize Firebase Admin SDK', error);
    }
  }

  async sendEmergencyPush(tokens: string[], rescueData: any) {
    if (!this.isInitialized || tokens.length === 0) {
      return;
    }

    try {
      const message = {
        tokens,
        notification: {
          title: '🚨 EMERGENCY DISPATCH',
          body: `P1 CRITICAL: ${rescueData.description?.substring(0, 40)}...`,
        },
        data: {
          // Payload data for the app to handle in the background and pop up the dialog
          type: 'EMERGENCY_RESCUE',
          rescueId: rescueData.id,
          payload: JSON.stringify(rescueData)
        },
        android: {
          priority: 'high' as const,
        }
      };

      const response = await getMessaging().sendEachForMulticast(message);
      this.logger.log(`Successfully sent ${response.successCount} emergency push notifications.`);
      if (response.failureCount > 0) {
        response.responses.forEach((res: any, idx: number) => {
          if (!res.success) {
            this.logger.warn(`Failed to send to token at index ${idx}: ${res.error?.message}`);
          }
        });
      }
    } catch (error) {
      this.logger.error('Error sending emergency push notifications', error);
    }
  }
}
