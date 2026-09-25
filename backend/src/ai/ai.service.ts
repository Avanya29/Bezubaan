/**
 * Bezubaan — NestJS AI Integration Service
 *
 * This service is the ONLY point of contact between NestJS and the AI service.
 * It validates requests, calls the AI service, validates the response, and
 * returns structured typed data.
 *
 * Safety boundary (BR-007, FR-009):
 *   - AI output is NEVER trusted as business truth
 *   - AI output is stored in RescueCase.aiTriageData only
 *   - Human verification is tracked separately
 *   - AI failures return safe fallback responses (never corrupt rescue data)
 */
import { Injectable, Logger } from '@nestjs/common';
import { ConfigService } from '@nestjs/config';
import { TriageResponseDto } from './dto/triage-response.dto';

@Injectable()
export class AiService {
  private readonly logger = new Logger(AiService.name);
  private readonly aiServiceUrl: string;
  private readonly timeoutMs: number;

  constructor(private configService: ConfigService) {
    this.aiServiceUrl =
      this.configService.get<string>('AI_SERVICE_URL') ||
      'http://localhost:18000';
    this.timeoutMs =
      (this.configService.get<number>('AI_PROVIDER_TIMEOUT_SECONDS') || 60) *
      1000;
  }

  async analyzeImage(imageUrl: string) {
    try {
      const rescueId = `img-${Date.now()}`;
      
      let finalImageUrl = imageUrl;
      if (imageUrl.startsWith('data:image')) {
        const fs = require('fs');
        const path = require('path');
        const base64Data = imageUrl.replace(/^data:image\/\w+;base64,/, "");
        const buffer = Buffer.from(base64Data, 'base64');
        const filename = `${rescueId}.jpg`;
        const publicDir = path.join(__dirname, '..', '..', '..', 'public');
        if (!fs.existsSync(publicDir)) {
          fs.mkdirSync(publicDir, { recursive: true });
        }
        fs.writeFileSync(path.join(publicDir, filename), buffer);
        
        // Since we are running on Render, the host is usually available via env or we can construct it
        // As a fallback for Render, the host is usually the app name
        const host = process.env.RENDER_EXTERNAL_HOSTNAME ? `https://${process.env.RENDER_EXTERNAL_HOSTNAME}` : 'https://bezubaan-api.onrender.com';
        finalImageUrl = `${host}/uploads/${filename}`;
      }

      const response = await fetch(`${this.aiServiceUrl}/api/v1/triage`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
          rescue_id: rescueId,
          description: 'User uploaded image for AI vet analysis.',
          location: { latitude: 12.9716, longitude: 77.5946 }, // Bangalore default
          image_url: finalImageUrl,
        }),
        signal: AbortSignal.timeout(this.timeoutMs),
      });
      if (!response.ok) throw new Error(`AI Service returned ${response.status}`);
      const data = await response.json();
      // Map triage response to a format the Android AI chat can display
      const lines: string[] = [];
      if (data.preliminary_assessment) lines.push(data.preliminary_assessment);
      if (data.observations?.length) lines.push('\n🔍 Observations:\n• ' + data.observations.join('\n• '));
      if (data.recommended_actions?.length) lines.push('\n✅ Recommended Actions:\n• ' + data.recommended_actions.join('\n• '));
      if (data.safety_warnings?.length) lines.push('\n⚠️ Safety Warnings:\n• ' + data.safety_warnings.join('\n• '));
      if (data.confidence_note) lines.push('\n📋 ' + data.confidence_note);
      return {
        id: Date.now().toString(),
        text: lines.join('\n') || 'Analysis complete. Please consult a vet.',
        isUser: false,
        timestamp: Date.now(),
        severity: data.severity_estimate || 'UNKNOWN',
      };
    } catch (error) {
      this.logger.error('Analyze failed', error);
      return {
        id: Date.now().toString(),
        text: 'I was unable to analyze the image right now. Please describe the animal\'s condition and I will help you.',
        isUser: false,
        timestamp: Date.now(),
      };
    }
  }

  async sendChatMessage(text: string) {
    try {
      const rescueId = `chat-${Date.now()}`;
      const response = await fetch(`${this.aiServiceUrl}/api/v1/triage`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
          rescue_id: rescueId,
          description: text,
          location: { latitude: 12.9716, longitude: 77.5946 }, // Bangalore default
        }),
        signal: AbortSignal.timeout(this.timeoutMs),
      });
      if (!response.ok) throw new Error(`AI Service returned ${response.status}`);
      const data = await response.json();
      // Build a readable reply from the triage response
      const lines: string[] = [];
      if (data.preliminary_assessment) lines.push(data.preliminary_assessment);
      if (data.observations?.length) lines.push('\n🔍 Observations:\n• ' + data.observations.join('\n• '));
      if (data.recommended_actions?.length) lines.push('\n✅ Recommended Actions:\n• ' + data.recommended_actions.join('\n• '));
      if (data.safety_warnings?.length) lines.push('\n⚠️ Safety Warnings:\n• ' + data.safety_warnings.join('\n• '));
      if (data.confidence_note) lines.push('\n📋 ' + data.confidence_note);
      return {
        id: Date.now().toString(),
        text: lines.join('\n') || "I've reviewed the situation. Please consult a vet for professional advice.",
        isUser: false,
        timestamp: Date.now(),
      };
    } catch (error) {
      this.logger.error('Chat failed', error);
      return {
        id: Date.now().toString(),
        text: 'I am having trouble connecting right now. If this is an emergency, please contact a local animal rescue organization immediately.',
        isUser: false,
        timestamp: Date.now(),
      };
    }
  }

  /**
   * Request AI triage analysis for a rescue case.
   */
  async requestTriage(params: {
    rescueId: string;
    description: string;
    latitude: number;
    longitude: number;
    address?: string;
    imageUrl?: string;
    animalSpecies?: string;
    correlationId?: string;
    volunteers?: Array<{
      id: string;
      name: string;
      distance_meters?: number;
      experience_level?: string;
      capabilities?: string[];
    }>;
  }): Promise<TriageResponseDto> {
    const correlationId =
      params.correlationId ||
      `nest-${Date.now()}-${Math.random().toString(36).slice(2, 8)}`;

    const requestBody = {
      rescue_id: params.rescueId,
      description: params.description,
      location: {
        latitude: params.latitude,
        longitude: params.longitude,
        address: params.address,
      },
      image_url: params.imageUrl,
      animal_info: params.animalSpecies
        ? { species: params.animalSpecies }
        : undefined,
      volunteers: params.volunteers,
      correlation_id: correlationId,
    };

    this.logger.log(
      `AI triage requested | rescue_id=${params.rescueId} correlation_id=${correlationId}`,
    );

    try {
      const controller = new AbortController();
      const timeoutId = setTimeout(() => controller.abort(), this.timeoutMs);

      const response = await fetch(`${this.aiServiceUrl}/api/v1/triage`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(requestBody),
        signal: controller.signal,
      });

      clearTimeout(timeoutId);

      if (!response.ok) {
        this.logger.error(
          `AI service returned ${response.status} | rescue_id=${params.rescueId}`,
        );
        return this.safeErrorResponse(
          `AI service returned HTTP ${response.status}`,
          correlationId,
        );
      }

      const data = await response.json();

      // Validate that the response has the expected structure
      if (!data.model_metadata || !Array.isArray(data.observations)) {
        this.logger.error(
          `Malformed AI response | rescue_id=${params.rescueId}`,
        );
        return this.safeErrorResponse(
          'AI service returned malformed response',
          correlationId,
        );
      }

      this.logger.log(
        `AI triage completed | rescue_id=${params.rescueId} severity=${data.severity_estimate} provider=${data.model_metadata.provider}`,
      );

      return data as TriageResponseDto;
    } catch (error: any) {
      if (error.name === 'AbortError') {
        this.logger.error(`AI service timeout | rescue_id=${params.rescueId}`);
        return this.safeErrorResponse('AI service timed out', correlationId);
      }

      this.logger.error(
        `AI service error | rescue_id=${params.rescueId} error=${error.message}`,
      );
      return this.safeErrorResponse(
        `AI service unavailable: ${error.message}`,
        correlationId,
      );
    }
  }

  /**
   * Return a safe, structured error response.
   * AI failure MUST NOT corrupt rescue data (BR-007).
   */
  private safeErrorResponse(
    message: string,
    correlationId: string,
  ): TriageResponseDto {
    return {
      observations: [],
      preliminary_assessment: message,
      severity_estimate: 'UNKNOWN',
      recommended_actions: ['Manual review required due to AI service error.'],
      safety_warnings: [],
      confidence_note: 'AI analysis failed. This is NOT a valid assessment.',
      model_metadata: {
        provider: 'error',
        model: 'none',
        processing_time_ms: 0,
      },
      correlation_id: correlationId,
    };
  }
}
