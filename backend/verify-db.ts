import { NestFactory } from '@nestjs/core';
import { AppModule } from './src/app.module';
import { PrismaService } from './src/database/prisma.service';
import { DonationsService } from './src/donations/donations.service';
import { PaymentStatus, NotificationCategory, NotificationChannel } from '@prisma/client';
import { CommunityService } from './src/community/community.service';
import { NotificationsService } from './src/notifications/notifications.service';
import { LostFoundService } from './src/lost-found/lost-found.service';

async function bootstrap() {
  const app = await NestFactory.createApplicationContext(AppModule);
  
  const prisma = app.get(PrismaService);
  const donationsService = app.get(DonationsService);
  const communityService = app.get(CommunityService);
  const notificationsService = app.get(NotificationsService);
  const lostFoundService = app.get(LostFoundService);

  try {
    console.log('--- Cleaning DB ---');
    await prisma.donation.deleteMany();
    await prisma.campaign.deleteMany();
    await prisma.notification.deleteMany();
    await prisma.communityPost.deleteMany();
    await prisma.lostFoundIncident.deleteMany();
    await prisma.user.deleteMany();

    console.log('--- 1. Testing Donation Idempotency & Reversal ---');
    const user = await prisma.user.create({
      data: { email: 'donor_e2e@example.com', passwordHash: 'hash', role: 'USER' },
    });

    const campaign = await prisma.campaign.create({
      data: { title: 'Test', description: 'Test', goalAmount: 50000, creatorId: user.id },
    });

    const donation = await donationsService.createDonation(user.id, {
      amount: 1500,
      currency: 'INR',
      campaignId: campaign.id,
    });
    
    await donationsService.processWebhook({
      providerOrderId: donation.providerOrderId as string,
      status: PaymentStatus.SUCCESS,
      providerPaymentId: 'pay_xyz',
    });

    let updatedCampaign = await prisma.campaign.findUnique({ where: { id: campaign.id } });
    if (updatedCampaign?.raisedAmount !== 1500) throw new Error('Raised amount should be 1500');

    await donationsService.processWebhook({
      providerOrderId: donation.providerOrderId as string,
      status: PaymentStatus.SUCCESS,
      providerPaymentId: 'pay_xyz',
    });
    
    updatedCampaign = await prisma.campaign.findUnique({ where: { id: campaign.id } });
    if (updatedCampaign?.raisedAmount !== 1500) throw new Error('Idempotency failed, amount changed');

    await donationsService.processWebhook({
      providerOrderId: donation.providerOrderId as string,
      status: PaymentStatus.REFUNDED,
    });

    updatedCampaign = await prisma.campaign.findUnique({ where: { id: campaign.id } });
    if (updatedCampaign?.raisedAmount !== 0) throw new Error('Refund failed, amount not 0');
    console.log('✓ Donation tests passed');

    console.log('--- 2. Testing Community Persistence ---');
    const post = await communityService.createPost(user.id, {
      content: 'Hello World Integration Test',
      privacy: 'PUBLIC',
    } as any);

    const dbPost = await prisma.communityPost.findUnique({ where: { id: post.id } });
    if (dbPost?.content !== 'Hello World Integration Test') throw new Error('Post not persisted correctly');
    console.log('✓ Community tests passed');

    console.log('--- 3. Testing Notification Persistence ---');
    await notificationsService.dispatch(
      user.id,
      NotificationCategory.COMMUNITY,
      NotificationChannel.IN_APP,
      'Test DB',
      'Persisted via Postgres'
    );

    const notifications = await prisma.notification.findMany({ where: { userId: user.id } });
    if (notifications.length === 0 || notifications[0].title !== 'Test DB') throw new Error('Notification not persisted');
    console.log('✓ Notification tests passed');

    console.log('--- 4. Testing Lost/Found Transitions ---');
    const incident = await lostFoundService.create(user.id, {
      animalType: 'DOG',
      description: 'Lost husky',
      lastSeenLocation: 'Park',
      lastSeenDate: new Date(),
    } as any);

    const updated = await lostFoundService.updateStatus(user.id, incident.id, { status: 'FOUND' } as any);
    if (updated.status !== 'FOUND') throw new Error('Status not updated in service response');

    const dbIncident = await prisma.lostFoundIncident.findUnique({ where: { id: incident.id } });
    if (dbIncident?.status !== 'FOUND') throw new Error('Status not persisted in DB');
    console.log('✓ Lost/Found tests passed');

    console.log('SUCCESS: All Database Integrations Verified!');

  } catch (error) {
    console.error('FAILED:', error);
    process.exit(1);
  } finally {
    await app.close();
  }
}

bootstrap();
