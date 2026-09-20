import {
  Controller,
  Get,
  Post,
  Body,
  Patch,
  Param,
  Delete,
  UseGuards,
  Query,
  ParseIntPipe,
  DefaultValuePipe,
  HttpCode,
  HttpStatus,
} from '@nestjs/common';
import { CommunityService } from './community.service';
import {
  CreatePostDto,
  UpdatePostDto,
  CreateCommentDto,
  UpdateCommentDto,
  CreateReportDto,
} from './dto';
import { JwtAuthGuard } from '../auth/guards/jwt-auth.guard';
import { CurrentUser } from '../auth/decorators/current-user.decorator';

@Controller('community')
@UseGuards(JwtAuthGuard)
export class CommunityController {
  constructor(private readonly communityService: CommunityService) {}

  // ===================== POSTS =====================

  @Post('posts')
  async createPost(@CurrentUser() user: any, @Body() dto: CreatePostDto) {
    return this.communityService.createPost(user.id, dto);
  }

  @Get('feed')
  async getFeed(
    @CurrentUser() user: any,
    @Query('skip', new DefaultValuePipe(0), ParseIntPipe) skip: number,
    @Query('take', new DefaultValuePipe(20), ParseIntPipe) take: number,
  ) {
    return this.communityService.getFeed(user.id, skip, take);
  }

  @Get('posts/:id')
  async getPostById(@CurrentUser() user: any, @Param('id') id: string) {
    return this.communityService.getPostById(id, user.id);
  }

  @Patch('posts/:id')
  async updatePost(
    @CurrentUser() user: any,
    @Param('id') id: string,
    @Body() dto: UpdatePostDto,
  ) {
    return this.communityService.updatePost(user.id, id, dto);
  }

  @Delete('posts/:id')
  @HttpCode(HttpStatus.NO_CONTENT)
  async deletePost(@CurrentUser() user: any, @Param('id') id: string) {
    await this.communityService.deletePost(user.id, id);
  }

  // ===================== INTERACTIONS =====================

  @Post('posts/:id/like')
  async likePost(@CurrentUser() user: any, @Param('id') id: string) {
    return this.communityService.likePost(user.id, id);
  }

  @Delete('posts/:id/like')
  @HttpCode(HttpStatus.NO_CONTENT)
  async unlikePost(@CurrentUser() user: any, @Param('id') id: string) {
    await this.communityService.unlikePost(user.id, id);
  }

  @Post('posts/:id/save')
  async savePost(@CurrentUser() user: any, @Param('id') id: string) {
    return this.communityService.savePost(user.id, id);
  }

  @Delete('posts/:id/save')
  @HttpCode(HttpStatus.NO_CONTENT)
  async unsavePost(@CurrentUser() user: any, @Param('id') id: string) {
    await this.communityService.unsavePost(user.id, id);
  }

  @Get('posts/:id/share')
  async sharePost(@Param('id') id: string) {
    return this.communityService.generateShareLink(id);
  }

  @Post('posts/:id/comments')
  async createComment(
    @CurrentUser() user: any,
    @Param('id') id: string,
    @Body() dto: CreateCommentDto,
  ) {
    return this.communityService.createComment(user.id, id, dto);
  }

  @Patch('comments/:id')
  async updateComment(
    @CurrentUser() user: any,
    @Param('id') id: string,
    @Body() dto: UpdateCommentDto,
  ) {
    return this.communityService.updateComment(user.id, id, dto);
  }

  @Delete('comments/:id')
  @HttpCode(HttpStatus.NO_CONTENT)
  async deleteComment(@CurrentUser() user: any, @Param('id') id: string) {
    await this.communityService.deleteComment(user.id, id);
  }

  // ===================== FOLLOWS =====================

  @Post('users/:id/follow')
  async followUser(@CurrentUser() user: any, @Param('id') followingId: string) {
    return this.communityService.followUser(user.id, followingId);
  }

  @Delete('users/:id/follow')
  @HttpCode(HttpStatus.NO_CONTENT)
  async unfollowUser(
    @CurrentUser() user: any,
    @Param('id') followingId: string,
  ) {
    await this.communityService.unfollowUser(user.id, followingId);
  }

  // ===================== MODERATION =====================

  @Post('report')
  async reportContent(@CurrentUser() user: any, @Body() dto: CreateReportDto) {
    return this.communityService.reportContent(user.id, dto);
  }

  // ADMIN ENDPOINTS
  @Patch('moderation/reports/:id/status')
  async updateReportStatus(
    @Param('id') id: string,
    @Body('status') status: 'REVIEWED' | 'RESOLVED' | 'DISMISSED',
  ) {
    return this.communityService.updateReportStatus(id, status);
  }

  @Post('moderation/hide')
  async hideContent(
    @Body('targetType') targetType: string,
    @Body('targetId') targetId: string,
  ) {
    return this.communityService.hideContent(targetType, targetId);
  }
}
