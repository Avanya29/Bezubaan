import { Test, TestingModule } from '@nestjs/testing';
import { AdoptionsController } from './adoptions.controller';
import { AdoptionsService } from './adoptions.service';

describe('AdoptionsController', () => {
  let controller: AdoptionsController;

  const mockAdoptionsService = {
    apply: jest.fn(),
    approve: jest.fn(),
    reject: jest.fn(),
    findAll: jest.fn(),
  };

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      controllers: [AdoptionsController],
      providers: [
        { provide: AdoptionsService, useValue: mockAdoptionsService },
      ],
    }).compile();

    controller = module.get<AdoptionsController>(AdoptionsController);
  });

  it('should be defined', () => {
    expect(controller).toBeDefined();
  });

  it('should apply', async () => {
    mockAdoptionsService.apply.mockResolvedValueOnce({ id: '1' });
    const result = await controller.apply(
      { id: 'user-1' },
      {
        animalId: 'animal-1',
        motivation: 'test',
        housingType: 'test',
        householdInformation: 'test',
        previousPetExperience: 'test',
        existingAnimals: 'test',
      },
    );
    expect(result).toEqual({ id: '1' });
  });

  it('should approve', async () => {
    mockAdoptionsService.approve.mockResolvedValueOnce({ id: '1' });
    const result = await controller.approve('1');
    expect(result).toEqual({ id: '1' });
  });

  it('should reject', async () => {
    mockAdoptionsService.reject.mockResolvedValueOnce({ id: '1' });
    const result = await controller.reject('1');
    expect(result).toEqual({ id: '1' });
  });

  it('should findAll', async () => {
    mockAdoptionsService.findAll.mockResolvedValueOnce([]);
    const result = await controller.findAll({ id: 'user-1' });
    expect(result).toEqual([]);
  });
});
