import { Test, TestingModule } from '@nestjs/testing';
import { INestApplication } from '@nestjs/common';
import { AppModule } from '../src/app.module';
import { PrismaService } from '../src/database/prisma.service';
import { DonationsService } from '../src/donations/donations.service';
import {
  PaymentStatus,
  NotificationChannel,
  NotificationCategory,
} from '@prisma/client';
import { CommunityService } from '../src/community/community.service';
import { NotificationsService } from '../src/notifications/notifications.service';
import { LostFoundService } from '../src/lost-found/lost-found.service';

describe('Database Integration Tests (e2e)', () => {
  let app: INestApplication;
  let prisma: PrismaService;
  let donationsService: DonationsService;
  let communityService: CommunityService;
  let notificationsService: NotificationsService;
  let lostFoundService: LostFoundService;

  beforeAll(async () => {
    const moduleFixture: TestingModule = await Test.createTestingModule({
      imports: [AppModule],
    }).compile();

    app = moduleFixture.createNestApplication();
    await app.init();

    prisma = app.get<PrismaService>(PrismaService);
    donationsService = app.get<DonationsService>(DonationsService);
    communityService = app.get<CommunityService>(CommunityService);
    notificationsService = app.get<NotificationsService>(NotificationsService);
    lostFoundService = app.get<LostFoundService>(LostFoundService);
  });

  afterAll(async () => {
    await prisma.donation.deleteMany();
    await prisma.campaign.deleteMany();
    await prisma.notification.deleteMany();
    await prisma.communityPost.deleteMany();
    await prisma.lostFoundIncident.deleteMany();
    await prisma.user.deleteMany();
    await app.close();
  });

  it('should verify donation transaction, idempotency, and refund reversal in Postgres', async () => {
    const user = await prisma.user.create({
      data: {
        email: 'donor_e2e@example.com',
        passwordHash: 'hash',
        role: 'USER',
      },
    });

    const campaign = await prisma.campaign.create({
      data: {
        title: 'Test',
        description: 'Test',
        goalAmount: 50000,
        creatorId: user.id,
      },
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

    let updatedCampaign = await prisma.campaign.findUnique({
      where: { id: campaign.id },
    });
    expect(updatedCampaign?.raisedAmount).toBe(1500);

    // Idempotency
    await donationsService.processWebhook({
      providerOrderId: donation.providerOrderId as string,
      status: PaymentStatus.SUCCESS,
      providerPaymentId: 'pay_xyz',
    });

    updatedCampaign = await prisma.campaign.findUnique({
      where: { id: campaign.id },
    });
    expect(updatedCampaign?.raisedAmount).toBe(1500);

    // Refund
    await donationsService.processWebhook({
      providerOrderId: donation.providerOrderId as string,
      status: PaymentStatus.REFUNDED,
    });

    updatedCampaign = await prisma.campaign.findUnique({
      where: { id: campaign.id },
    });
    expect(updatedCampaign?.raisedAmount).toBe(0);
  });

  it('should verify community post persistence', async () => {
    const user = await prisma.user.findFirst();
    const post = await communityService.createPost(user!.id, {
      content: 'Hello World Integration Test',
      privacy: 'PUBLIC',
    } as any);

    const dbPost = await prisma.communityPost.findUnique({
      where: { id: post.id },
    });
    expect(dbPost?.content).toBe('Hello World Integration Test');
  });

  it('should verify notification persistence', async () => {
    const user = await prisma.user.findFirst();
    await notificationsService.dispatch(
      user!.id,
      NotificationCategory.COMMUNITY,
      NotificationChannel.IN_APP,
      'Test DB',
      'Persisted via Postgres',
    );

    const notifications = await prisma.notification.findMany({
      where: { userId: user!.id },
    });
    expect(notifications.length).toBeGreaterThan(0);
    expect(notifications[0].title).toBe('Test DB');
  });

  it('should verify lost/found persistence and transition', async () => {
    const user = await prisma.user.findFirst();
    const incident = await lostFoundService.create(user!.id, {
      animalType: 'DOG',
      description: 'Lost husky',
      lastSeenLocation: 'Park',
      lastSeenDate: new Date(),
    } as any);

    const updated = await lostFoundService.updateStatus(user!.id, incident.id, {
      status: 'FOUND',
    } as any);
    expect(updated.status).toBe('FOUND');

    const dbIncident = await prisma.lostFoundIncident.findUnique({
      where: { id: incident.id },
    });
    expect(dbIncident?.status).toBe('FOUND');
  });
});
