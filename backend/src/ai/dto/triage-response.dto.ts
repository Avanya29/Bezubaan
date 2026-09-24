/**
 * Triage Response DTO — typed representation of the AI service response.
 *
 * IMPORTANT (BR-007, FR-009):
 *   This is a PRELIMINARY AI ASSESSMENT, NOT a verified diagnosis.
 *   Stored in RescueCase.aiTriageData as JSON.
 *   Human verification is tracked separately via humanVerifiedById / humanVerifiedAt.
 */

export interface ModelMetadata {
  provider: string;
  model: string;
  processing_time_ms: number;
}

export interface TriageResponseDto {
  observations: string[];
  preliminary_assessment: string;
  severity_estimate: string;
  recommended_actions: string[];
  safety_warnings: string[];
  confidence_note: string;
  model_metadata: ModelMetadata;
  correlation_id: string | null;
  nearby_veterinary_help?: any;
  recommended_volunteers?: Array<{
    volunteer_id: string;
    name: string;
    reason: string;
    match_score: number;
  }>;
}
