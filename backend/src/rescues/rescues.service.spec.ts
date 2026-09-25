import { Test, TestingModule } from '@nestjs/testing';
import { RescuesService } from './rescues.service';
import { PrismaService } from '../database/prisma.service';
import { EventsGateway } from '../events/events.gateway';
import { FirebaseService } from '../firebase/firebase.service';
import { AiService } from '../ai/ai.service';

const mockPrismaService = {
  rescueCase: {
    findMany: jest.fn(),
    findUnique: jest.fn(),
    create: jest.fn(),
    update: jest.fn(),
  },
};

describe('RescuesService', () => {
  let service: RescuesService;

  beforeEach(async () => {
    const mockEventsGateway = { broadcastEmergency: jest.fn() };
    const mockFirebaseService = { sendEmergencyPush: jest.fn() };
    const mockAiService = { requestTriage: jest.fn() };

    const module: TestingModule = await Test.createTestingModule({
      providers: [
        RescuesService,
        { provide: PrismaService, useValue: mockPrismaService },
        { provide: EventsGateway, useValue: mockEventsGateway },
        { provide: FirebaseService, useValue: mockFirebaseService },
        { provide: AiService, useValue: mockAiService },
      ],
    }).compile();

    service = module.get<RescuesService>(RescuesService);
  });

  it('should be defined', () => {
    expect(service).toBeDefined();
  });
});
