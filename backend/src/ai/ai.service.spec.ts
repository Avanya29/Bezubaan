import { Test, TestingModule } from '@nestjs/testing';
import { ConfigService } from '@nestjs/config';
import { AiService } from './ai.service';

// Mock global fetch
const mockFetch = jest.fn();
(global as any).fetch = mockFetch;

describe('AiService', () => {
  let service: AiService;

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      providers: [
        AiService,
        {
          provide: ConfigService,
          useValue: {
            get: jest.fn((key: string) => {
              if (key === 'AI_SERVICE_URL') return 'http://mock-ai:8000';
              if (key === 'AI_PROVIDER_TIMEOUT_SECONDS') return 5;
              return undefined;
            }),
          },
        },
      ],
    }).compile();

    service = module.get<AiService>(AiService);
    mockFetch.mockReset();
  });

  const baseParams = {
    rescueId: 'rescue-001',
    description: 'Injured dog on the highway',
    latitude: 28.6139,
    longitude: 77.209,
  };

  it('should be defined', () => {
    expect(service).toBeDefined();
  });

  describe('requestTriage', () => {
    it('should return structured response on successful AI call', async () => {
      const mockResponse = {
        observations: ['Dog appears injured'],
        preliminary_assessment: 'Needs attention',
        severity_estimate: 'High',
        recommended_actions: ['Send volunteer'],
        safety_warnings: ['Do not approach'],
        confidence_note: 'AI preliminary assessment',
        model_metadata: {
          provider: 'mock',
          model: 'v1',
          processing_time_ms: 50,
        },
        correlation_id: 'corr-1',
      };

      mockFetch.mockResolvedValue({
        ok: true,
        json: async () => mockResponse,
      });

      const result = await service.requestTriage(baseParams);

      expect(result.severity_estimate).toBe('High');
      expect(result.model_metadata.provider).toBe('mock');
      expect(result.observations).toContain('Dog appears injured');
    });

    it('should return safe error on HTTP error from AI service', async () => {
      mockFetch.mockResolvedValue({
        ok: false,
        status: 500,
      });

      const result = await service.requestTriage(baseParams);

      expect(result.severity_estimate).toBe('UNKNOWN');
      expect(result.model_metadata.provider).toBe('error');
      expect(result.preliminary_assessment).toContain('500');
    });

    it('should return safe error on malformed AI response', async () => {
      mockFetch.mockResolvedValue({
        ok: true,
        json: async () => ({ garbage: true }),
      });

      const result = await service.requestTriage(baseParams);

      expect(result.severity_estimate).toBe('UNKNOWN');
      expect(result.preliminary_assessment).toContain('malformed');
    });

    it('should return safe error on network failure', async () => {
      mockFetch.mockRejectedValue(new Error('ECONNREFUSED'));

      const result = await service.requestTriage(baseParams);

      expect(result.severity_estimate).toBe('UNKNOWN');
      expect(result.model_metadata.provider).toBe('error');
      expect(result.preliminary_assessment).toContain('unavailable');
    });

    it('should return safe error on timeout', async () => {
      const abortError = new Error('Aborted');
      abortError.name = 'AbortError';
      mockFetch.mockRejectedValue(abortError);

      const result = await service.requestTriage(baseParams);

      expect(result.severity_estimate).toBe('UNKNOWN');
      expect(result.preliminary_assessment).toContain('timed out');
    });

    it('should never expose password or sensitive data in response', async () => {
      const mockResponse = {
        observations: [],
        preliminary_assessment: 'test',
        severity_estimate: 'Low',
        recommended_actions: [],
        safety_warnings: [],
        confidence_note: 'test',
        model_metadata: {
          provider: 'mock',
          model: 'v1',
          processing_time_ms: 1,
        },
        correlation_id: null,
      };

      mockFetch.mockResolvedValue({
        ok: true,
        json: async () => mockResponse,
      });

      const result = await service.requestTriage(baseParams);

      const resultStr = JSON.stringify(result);
      expect(resultStr).not.toContain('password');
      expect(resultStr).not.toContain('passwordHash');
      expect(resultStr).not.toContain('apiKey');
    });
  });
});
