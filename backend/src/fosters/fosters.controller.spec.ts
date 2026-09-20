import { Test, TestingModule } from '@nestjs/testing';
import { FostersController } from './fosters.controller';
import { FostersService } from './fosters.service';

describe('FostersController', () => {
  let controller: FostersController;

  const mockFostersService = {
    apply: jest.fn(),
    approve: jest.fn(),
    extend: jest.fn(),
    complete: jest.fn(),
    findAll: jest.fn(),
  };

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      controllers: [FostersController],
      providers: [{ provide: FostersService, useValue: mockFostersService }],
    }).compile();

    controller = module.get<FostersController>(FostersController);
  });

  it('should be defined', () => {
    expect(controller).toBeDefined();
  });

  it('should apply', async () => {
    mockFostersService.apply.mockResolvedValueOnce({ id: '1' });
    const result = await controller.apply(
      { id: 'user-1' },
      {
        animalId: 'animal-1',
        reason: 'test',
        housingInformation: 'test',
        householdInformation: 'test',
        existingAnimals: 'test',
        priorAnimalCareExperience: 'test',
        availability: 'test',
      },
    );
    expect(result).toEqual({ id: '1' });
  });

  it('should approve', async () => {
    mockFostersService.approve.mockResolvedValueOnce({ id: '1' });
    const result = await controller.approve('1');
    expect(result).toEqual({ id: '1' });
  });

  it('should extend', async () => {
    mockFostersService.extend.mockResolvedValueOnce({ id: '1' });
    const result = await controller.extend('1', {
      expectedEndDate: new Date().toISOString(),
    });
    expect(result).toEqual({ id: '1' });
  });

  it('should complete', async () => {
    mockFostersService.complete.mockResolvedValueOnce({ id: '1' });
    const result = await controller.complete('1');
    expect(result).toEqual({ id: '1' });
  });

  it('should findAll', async () => {
    mockFostersService.findAll.mockResolvedValueOnce([]);
    const result = await controller.findAll({ id: 'user-1' });
    expect(result).toEqual([]);
  });
});
