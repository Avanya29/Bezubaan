import { Test, TestingModule } from '@nestjs/testing';
import { CommunityService } from './community.service';
import { PrismaService } from '../database/prisma.service';
import { ForbiddenException, NotFoundException } from '@nestjs/common';

const mockPrismaService = {
  communityPost: {
    create: jest.fn(),
    findMany: jest.fn(),
    findUnique: jest.fn(),
    update: jest.fn(),
  },
  postLike: { upsert: jest.fn(), delete: jest.fn() },
  postComment: { create: jest.fn(), findUnique: jest.fn(), update: jest.fn() },
  postSave: { upsert: jest.fn(), delete: jest.fn() },
  userFollow: { upsert: jest.fn(), delete: jest.fn() },
  user: { findUnique: jest.fn() },
  moderationReport: { create: jest.fn(), update: jest.fn() },
};

describe('CommunityService', () => {
  let service: CommunityService;

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      providers: [
        CommunityService,
        { provide: PrismaService, useValue: mockPrismaService },
      ],
    }).compile();

    service = module.get<CommunityService>(CommunityService);
    jest.clearAllMocks();
  });

  describe('Posts', () => {
    it('should allow author to edit within 30 minutes', async () => {
      const recentDate = new Date(Date.now() - 10 * 60 * 1000); // 10 mins ago
      mockPrismaService.communityPost.findUnique.mockResolvedValue({
        id: 'post1',
        authorId: 'user1',
        createdAt: recentDate,
        deletedAt: null,
      });
      mockPrismaService.communityPost.update.mockResolvedValue({
        content: 'updated',
      });

      const result = await service.updatePost('user1', 'post1', {
        content: 'updated',
      } as any);
      expect(result.content).toBe('updated');
    });

    it('should throw ForbiddenException if edit window expired', async () => {
      const oldDate = new Date(Date.now() - 40 * 60 * 1000); // 40 mins ago
      mockPrismaService.communityPost.findUnique.mockResolvedValue({
        id: 'post2',
        authorId: 'user1',
        createdAt: oldDate,
        deletedAt: null,
      });

      await expect(
        service.updatePost('user1', 'post2', { content: 'updated' } as any),
      ).rejects.toThrow(ForbiddenException);
    });

    it('should prevent deleting others post', async () => {
      mockPrismaService.communityPost.findUnique.mockResolvedValue({
        id: 'post3',
        authorId: 'user1',
        deletedAt: null,
      });

      await expect(service.deletePost('other_user', 'post3')).rejects.toThrow(
        ForbiddenException,
      );
    });
  });

  describe('Interactions', () => {
    it('should upsert like', async () => {
      mockPrismaService.communityPost.findUnique.mockResolvedValue({
        id: 'post1',
      });
      mockPrismaService.postLike.upsert.mockResolvedValue({
        userId: 'u1',
        postId: 'post1',
      });
      const result = await service.likePost('u1', 'post1');
      expect(result).toBeDefined();
    });

    it('should generate share link', async () => {
      mockPrismaService.communityPost.findUnique.mockResolvedValue({
        id: 'post1',
      });
      const res = await service.generateShareLink('post1');
      expect(res.shareLink).toContain('post1');
    });
  });
});
