import { Test, TestingModule } from '@nestjs/testing';
import { LostFoundController } from './lost-found.controller';
import { LostFoundService } from './lost-found.service';

describe('LostFoundController', () => {
  let controller: LostFoundController;

  beforeEach(async () => {
    const mockService = {
      create: jest.fn(),
      findAll: jest.fn(),
      findOne: jest.fn(),
      updateStatus: jest.fn(),
      remove: jest.fn(),
    };

    const module: TestingModule = await Test.createTestingModule({
      controllers: [LostFoundController],
      providers: [{ provide: LostFoundService, useValue: mockService }],
    }).compile();

    controller = module.get<LostFoundController>(LostFoundController);
  });

  it('should be defined', () => {
    expect(controller).toBeDefined();
  });
});
