import { Controller, Post, Body, UseGuards } from '@nestjs/common';
import { AiService } from './ai.service';
import { JwtAuthGuard } from '../auth/guards/jwt-auth.guard';

@Controller('ai')
@UseGuards(JwtAuthGuard)
export class AiController {
  constructor(private readonly aiService: AiService) {}

  @Post('analyze')
  async analyzeImage(
    @Body()
    body: {
      imageUrl: string;
      latitude?: number;
      longitude?: number;
      address?: string;
    },
  ) {
    return this.aiService.analyzeImage(
      body.imageUrl,
      body.latitude,
      body.longitude,
      body.address,
    );
  }

  @Post('chat')
  async sendChatMessage(
    @Body()
    body: {
      text: string;
      latitude?: number;
      longitude?: number;
      address?: string;
    },
  ) {
    return this.aiService.sendChatMessage(
      body.text,
      body.latitude,
      body.longitude,
      body.address,
    );
  }
}
