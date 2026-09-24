import {
  Injectable,
  ForbiddenException,
  NotFoundException,
  BadRequestException,
} from '@nestjs/common';
import { PrismaService } from '../database/prisma.service';
import {
  CreatePostDto,
  UpdatePostDto,
  CreateCommentDto,
  UpdateCommentDto,
  CreateReportDto,
} from './dto';

@Injectable()
export class CommunityService {
  constructor(private readonly prisma: PrismaService) {}

  // ===================== POSTS =====================

  async createPost(userId: string, dto: CreatePostDto) {
    return this.prisma.communityPost.create({
      data: {
        authorId: userId,
        content: dto.content,
        type: dto.type,
        privacy: dto.privacy,
      },
      include: {
        author: { select: { id: true, profile: true } },
      },
    });
  }

  async getFeed(userId: string, skip = 0, take = 20) {
    // Only get PUBLIC posts and posts not deleted
    return this.prisma.communityPost.findMany({
      where: {
        deletedAt: null,
        OR: [{ privacy: 'PUBLIC' }, { authorId: userId }],
      },
      orderBy: { createdAt: 'desc' },
      skip,
      take,
      include: {
        author: { select: { id: true, profile: true } },
        likes: { where: { userId }, take: 1 },
        _count: { select: { likes: true, comments: true } },
      },
    });
  }

  async getPostById(postId: string, userId: string) {
    const post = await this.prisma.communityPost.findUnique({
      where: { id: postId },
      include: {
        author: { select: { id: true, profile: true } },
        likes: { where: { userId }, take: 1 },
        saves: { where: { userId }, take: 1 },
        comments: { include: { author: { select: { id: true, profile: true } } }, orderBy: { createdAt: 'desc' } },
        _count: { select: { likes: true, comments: true, saves: true } },
      },
    });

    if (!post || post.deletedAt) {
      throw new NotFoundException('Post not found');
    }

    if (post.privacy === 'PRIVATE' && post.authorId !== userId) {
      // Check if user is admin - omitting for MVP since we don't have roles passed
      throw new ForbiddenException('You do not have access to this post');
    }

    return post;
  }

  async updatePost(userId: string, postId: string, dto: UpdatePostDto) {
    const post = await this.prisma.communityPost.findUnique({
      where: { id: postId },
    });

    if (!post || post.deletedAt) throw new NotFoundException('Post not found');
    if (post.authorId !== userId)
      throw new ForbiddenException('You can only edit your own posts');

    const thirtyMinsAgo = new Date(Date.now() - 30 * 60 * 1000);
    if (post.createdAt < thirtyMinsAgo) {
      throw new ForbiddenException('Edit window (30 minutes) has expired');
    }

    return this.prisma.communityPost.update({
      where: { id: postId },
      data: {
        content: dto.content,
        privacy: dto.privacy,
      },
    });
  }

  async deletePost(userId: string, postId: string) {
    const post = await this.prisma.communityPost.findUnique({
      where: { id: postId },
    });
    if (!post || post.deletedAt) throw new NotFoundException('Post not found');
    if (post.authorId !== userId)
      throw new ForbiddenException('You can only delete your own posts');

    return this.prisma.communityPost.update({
      where: { id: postId },
      data: { deletedAt: new Date() },
    });
  }

  // ===================== INTERACTIONS =====================

  async likePost(userId: string, postId: string) {
    const post = await this.prisma.communityPost.findUnique({
      where: { id: postId },
    });
    if (!post || post.deletedAt) throw new NotFoundException('Post not found');

    return this.prisma.postLike.upsert({
      where: { userId_postId: { userId, postId } },
      create: { userId, postId },
      update: {},
    });
  }

  async unlikePost(userId: string, postId: string) {
    return this.prisma.postLike
      .delete({
        where: { userId_postId: { userId, postId } },
      })
      .catch(() => {
        throw new NotFoundException('Like not found');
      });
  }

  async createComment(userId: string, postId: string, dto: CreateCommentDto) {
    const post = await this.prisma.communityPost.findUnique({
      where: { id: postId },
    });
    if (!post || post.deletedAt) throw new NotFoundException('Post not found');

    return this.prisma.postComment.create({
      data: {
        postId,
        authorId: userId,
        content: dto.content,
      },
    });
  }

  async updateComment(
    userId: string,
    commentId: string,
    dto: UpdateCommentDto,
  ) {
    const comment = await this.prisma.postComment.findUnique({
      where: { id: commentId },
    });
    if (!comment || comment.deletedAt)
      throw new NotFoundException('Comment not found');
    if (comment.authorId !== userId)
      throw new ForbiddenException('You can only edit your own comments');

    const thirtyMinsAgo = new Date(Date.now() - 30 * 60 * 1000);
    if (comment.createdAt < thirtyMinsAgo) {
      throw new ForbiddenException('Edit window (30 minutes) has expired');
    }

    return this.prisma.postComment.update({
      where: { id: commentId },
      data: { content: dto.content },
    });
  }

  async deleteComment(userId: string, commentId: string) {
    const comment = await this.prisma.postComment.findUnique({
      where: { id: commentId },
    });
    if (!comment || comment.deletedAt)
      throw new NotFoundException('Comment not found');
    if (comment.authorId !== userId)
      throw new ForbiddenException('You can only delete your own comments');

    return this.prisma.postComment.update({
      where: { id: commentId },
      data: { deletedAt: new Date() },
    });
  }

  async savePost(userId: string, postId: string) {
    return this.prisma.postSave.upsert({
      where: { userId_postId: { userId, postId } },
      create: { userId, postId },
      update: {},
    });
  }

  async unsavePost(userId: string, postId: string) {
    return this.prisma.postSave
      .delete({
        where: { userId_postId: { userId, postId } },
      })
      .catch(() => {
        throw new NotFoundException('Save not found');
      });
  }

  async generateShareLink(postId: string) {
    const post = await this.prisma.communityPost.findUnique({
      where: { id: postId },
    });
    if (!post || post.deletedAt) throw new NotFoundException('Post not found');

    // In a real application, this might generate a dynamic branch.io link or signed URL
    return { shareLink: `https://bezubaan.org/community/posts/${postId}` };
  }

  // ===================== FOLLOWS =====================

  async followUser(followerId: string, followingId: string) {
    if (followerId === followingId)
      throw new BadRequestException('Cannot follow yourself');

    const userToFollow = await this.prisma.user.findUnique({
      where: { id: followingId },
    });
    if (!userToFollow) throw new NotFoundException('User not found');

    return this.prisma.userFollow.upsert({
      where: { followerId_followingId: { followerId, followingId } },
      create: { followerId, followingId },
      update: {},
    });
  }

  async unfollowUser(followerId: string, followingId: string) {
    return this.prisma.userFollow
      .delete({
        where: { followerId_followingId: { followerId, followingId } },
      })
      .catch(() => {
        throw new NotFoundException('Follow not found');
      });
  }

  // ===================== MODERATION =====================

  async reportContent(userId: string, dto: CreateReportDto) {
    return this.prisma.moderationReport.create({
      data: {
        reporterId: userId,
        targetType: dto.targetType,
        targetId: dto.targetId,
        reason: dto.reason,
        description: dto.description,
      },
    });
  }

  async updateReportStatus(
    reportId: string,
    status: 'REVIEWED' | 'RESOLVED' | 'DISMISSED',
  ) {
    return this.prisma.moderationReport.update({
      where: { id: reportId },
      data: { status },
    });
  }

  async hideContent(targetType: string, targetId: string) {
    if (targetType === 'POST') {
      return this.prisma.communityPost.update({
        where: { id: targetId },
        data: { deletedAt: new Date() }, // soft delete/hide
      });
    }
    if (targetType === 'COMMENT') {
      return this.prisma.postComment.update({
        where: { id: targetId },
        data: { deletedAt: new Date() }, // soft delete/hide
      });
    }
    throw new BadRequestException('Unknown target type');
  }
}
