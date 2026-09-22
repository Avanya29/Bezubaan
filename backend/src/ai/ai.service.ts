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
      (this.configService.get<number>('AI_PROVIDER_TIMEOUT_SECONDS') || 30) *
      1000;
  }

  async analyzeImage(imageUrl: string) {
    try {
      // NOTE: Update endpoint to match FastAPI when you build it
      const response = await fetch(`${this.aiServiceUrl}/api/v1/analyze`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ image_url: imageUrl })
      });
      if (!response.ok) throw new Error('AI Service failed');
      return await response.json();
    } catch (error) {
      this.logger.error('Analyze failed', error);
      // Fallback response for Android to parse
      return {
        breed: "Unknown (Backend Fallback)",
        urgency: "Medium",
        firstAid: "Keep the animal warm. Analysis failed."
      };
    }
  }

  async sendChatMessage(text: string) {
    try {
      // NOTE: Update endpoint to match FastAPI when you build it
      const response = await fetch(`${this.aiServiceUrl}/api/v1/chat`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ message: text })
      });
      if (!response.ok) throw new Error('AI Service failed');
      const data = await response.json();
      return {
        id: Date.now().toString(),
        text: data.reply || "I didn't understand that.",
        isUser: false,
        timestamp: Date.now()
      };
    } catch (error) {
      this.logger.error('Chat failed', error);
      return {
        id: Date.now().toString(),
        text: "I am having trouble connecting to my brain. Please try again.",
        isUser: false,
        timestamp: Date.now()
      };
    }
  }

  /**
   * Request AI triage analysis for a rescue case.
  async requestTriage(params: {
    rescueId: string;
    description: string;
    latitude: number;
    longitude: number;
    address?: string;
    imageUrl?: string;
    animalSpecies?: string;
    correlationId?: string;
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
