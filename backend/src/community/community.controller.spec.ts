import { Test, TestingModule } from '@nestjs/testing';
import { CommunityController } from './community.controller';
import { CommunityService } from './community.service';

describe('CommunityController', () => {
  let controller: CommunityController;
  let mockService: any;

  beforeEach(async () => {
    mockService = {
      createPost: jest.fn(),
      getFeed: jest.fn(),
      getPostById: jest.fn(),
      updatePost: jest.fn(),
      deletePost: jest.fn(),
      likePost: jest.fn(),
      unlikePost: jest.fn(),
      generateShareLink: jest
        .fn()
        .mockResolvedValue({ shareLink: 'mock-link' }),
    };

    const module: TestingModule = await Test.createTestingModule({
      controllers: [CommunityController],
      providers: [{ provide: CommunityService, useValue: mockService }],
    }).compile();

    controller = module.get<CommunityController>(CommunityController);
  });

  it('should be defined', () => {
    expect(controller).toBeDefined();
  });

  it('should call share generation', async () => {
    const res = await controller.sharePost('1');
    expect(res.shareLink).toBe('mock-link');
    expect(mockService.generateShareLink).toHaveBeenCalledWith('1');
  });
});
