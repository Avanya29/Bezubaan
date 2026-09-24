"""
Bezubaan AI Service — Triage Orchestrator

Validates input → calls the configured AI provider → validates output.
Handles provider errors gracefully so that AI failures never corrupt rescue data.

Safety boundary (BR-007, FR-009):
  This service ONLY produces structured AI assessments.
  It NEVER modifies rescue case status or any business state.
"""
import logging
import uuid

from app.schemas.triage import TriageRequest, TriageResponse, ModelMetadata
from app.providers.base import (
    AIProvider,
    ProviderError,
    ProviderTimeoutError,
    ProviderUnavailableError,
)

logger = logging.getLogger(__name__)


class TriageService:
    """Orchestrates rescue triage analysis through the configured AI provider."""

    def __init__(self, provider: AIProvider):
        self._provider = provider

    async def analyze(self, request: TriageRequest) -> TriageResponse:
        """
        Run AI triage analysis on a rescue case using LangGraph.
        """
        if not request.correlation_id:
            request.correlation_id = str(uuid.uuid4())
        correlation = request.correlation_id

        logger.info(
            "Triage analysis started via LangGraph | rescue_id=%s correlation_id=%s",
            request.rescue_id,
            correlation,
        )

        try:
            from app.graph.workflow import triage_graph
            
            # Initial state
            initial_state = {
                "rescue_id": request.rescue_id,
                "description": request.description,
                "latitude": request.location.latitude,
                "longitude": request.location.longitude,
                "image_url": request.image_url,
                "animal_info": request.animal_info.model_dump() if request.animal_info else None,
                "volunteers": [v.model_dump() for v in request.volunteers] if request.volunteers else [],
                "correlation_id": correlation,
            }

            # Run LangGraph (stub for now, normally use aconfig or invoke)
            final_state = await triage_graph.ainvoke(initial_state)

            # Map back to TriageResponse
            response = TriageResponse(
                observations=final_state.get("vision_findings", []),
                preliminary_assessment=final_state.get("preliminary_assessment", ""),
                severity_estimate=final_state.get("severity_estimate", "UNKNOWN"),
                recommended_actions=final_state.get("recommended_actions", []),
                safety_warnings=final_state.get("safety_warnings", []),
                confidence_note=final_state.get("confidence_note", "Jeev AI-generated preliminary assessment."),
                model_metadata=ModelMetadata(
                    provider="langgraph_orchestrated",
                    model="mixed",
                    processing_time_ms=0,
                ),
                correlation_id=correlation,
                nearby_veterinary_help=final_state.get("nearby_vets"),
                recommended_volunteers=final_state.get("recommended_volunteers", [])
            )

            logger.info("Triage analysis completed | rescue_id=%s severity=%s", request.rescue_id, response.severity_estimate)
            return response

        except ProviderTimeoutError:
            logger.error("AI provider timeout | rescue_id=%s", request.rescue_id)
            return self._error_response("AI provider timed out.", correlation)
        except ProviderUnavailableError:
            logger.error("AI provider unavailable | rescue_id=%s", request.rescue_id)
            return self._error_response("AI provider is unavailable.", correlation)
        except Exception as e:
            logger.exception("Unexpected error | rescue_id=%s", request.rescue_id)
            return self._error_response("An unexpected error occurred.", correlation)

    @staticmethod
    def _error_response(message: str, correlation_id: str | None) -> TriageResponse:
        return TriageResponse(
            observations=[],
            preliminary_assessment=message,
            severity_estimate="UNKNOWN",
            recommended_actions=["Manual review required due to AI service error."],
            safety_warnings=[],
            confidence_note="Jeev AI analysis failed. This is NOT a valid assessment.",
            model_metadata=ModelMetadata(
                provider="error",
                model="none",
                processing_time_ms=0,
            ),
            correlation_id=correlation_id,
        )
