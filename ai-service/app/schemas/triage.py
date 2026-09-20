"""
Bezubaan AI Service — Pydantic Schemas for Triage Request/Response

These schemas define the strict contract between NestJS and the AI service.
Every field traces to a confirmed requirement:
  - FR-008: AI Triage Integration
  - FR-009: Human-in-the-Loop Verification (output must be clearly AI-generated)
  - BR-007: AI output ≠ verified truth
  - NFR-004: AI Safety & Guardrails
"""
from __future__ import annotations

from typing import Optional
from pydantic import BaseModel, Field


# ===== REQUEST =====

class AnimalInfo(BaseModel):
    """Optional animal context provided by the reporter."""
    species: Optional[str] = None
    breed: Optional[str] = None
    approximate_age: Optional[str] = None
    description: Optional[str] = None


class LocationInfo(BaseModel):
    """Location of the rescue incident."""
    latitude: float
    longitude: float
    address: Optional[str] = None


class TriageRequest(BaseModel):
    """
    Inbound request from NestJS to the AI service.

    Fields:
      rescue_id:      Reference back to the RescueCase (for correlation only; AI never modifies it directly)
      description:    Reporter's description of the incident (FR-005, FR-008)
      location:       Geographic coordinates (FR-007)
      image_url:      Optional media reference (P8 — no binary, only URL/reference)
      animal_info:    Optional structured animal data (P4)
      correlation_id: For distributed tracing / observability
    """
    rescue_id: str
    description: str
    location: LocationInfo
    image_url: Optional[str] = None
    animal_info: Optional[AnimalInfo] = None
    correlation_id: Optional[str] = None


# ===== RESPONSE =====

class ModelMetadata(BaseModel):
    """Metadata about the AI model that produced the result."""
    provider: str = Field(description="AI provider name (e.g. 'mock', 'openai')")
    model: str = Field(description="Model identifier")
    processing_time_ms: int = Field(description="Wall-clock processing time in milliseconds")


class TriageResponse(BaseModel):
    """
    Structured AI triage output.

    IMPORTANT (BR-007, FR-009):
      This is a PRELIMINARY AI ASSESSMENT, NOT a verified diagnosis.
      The NestJS backend stores this in RescueCase.aiTriageData.
      Human verification is tracked separately via humanVerifiedById / humanVerifiedAt.

    Fields:
      observations:            What the AI observes from the provided data
      preliminary_assessment:  AI's preliminary interpretation (NOT a diagnosis)
      severity_estimate:       Low / Medium / High / Critical — purely indicative
      recommended_actions:     Suggested next steps for human reviewers
      safety_warnings:         Any urgent safety concerns the AI detects
      confidence_note:         AI's own assessment of its confidence / uncertainty
      model_metadata:          Provenance and timing information
      correlation_id:          Echo of the request correlation ID
    """
    observations: list[str] = Field(default_factory=list)
    preliminary_assessment: str = ""
    severity_estimate: str = Field(
        default="UNKNOWN",
        description="Low | Medium | High | Critical | UNKNOWN"
    )
    recommended_actions: list[str] = Field(default_factory=list)
    safety_warnings: list[str] = Field(default_factory=list)
    confidence_note: str = Field(
        default="This is an AI-generated preliminary assessment. It is NOT a verified diagnosis.",
    )
    model_metadata: ModelMetadata
    correlation_id: Optional[str] = None
