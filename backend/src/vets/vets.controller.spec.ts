import { Test, TestingModule } from '@nestjs/testing';
import { VetsController } from './vets.controller';
import { VetsService } from './vets.service';

describe('VetsController', () => {
  let controller: VetsController;

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      controllers: [VetsController],
      providers: [
        {
          provide: VetsService,
          useValue: {},
        },
      ],
    }).compile();

    controller = module.get<VetsController>(VetsController);
  });

  it('should be defined', () => {
    expect(controller).toBeDefined();
  });
});
