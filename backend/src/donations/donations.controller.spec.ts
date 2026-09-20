import { Test, TestingModule } from '@nestjs/testing';
import { DonationsController } from './donations.controller';
import { DonationsService } from './donations.service';

describe('DonationsController', () => {
  let controller: DonationsController;

  beforeEach(async () => {
    const mockService = {
      createCampaign: jest.fn(),
      listCampaigns: jest.fn(),
      createDonation: jest.fn(),
      processWebhook: jest.fn().mockResolvedValue({ id: 'mock' }),
    };

    const module: TestingModule = await Test.createTestingModule({
      controllers: [DonationsController],
      providers: [{ provide: DonationsService, useValue: mockService }],
    }).compile();

    controller = module.get<DonationsController>(DonationsController);
  });

  it('should be defined', () => {
    expect(controller).toBeDefined();
  });
});
