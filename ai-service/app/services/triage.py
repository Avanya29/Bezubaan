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
        Run AI triage analysis on a rescue case.

        On success: returns structured TriageResponse.
        On failure: returns a safe error TriageResponse (never raises to corrupt business data).
        """
        # Ensure correlation ID
        if not request.correlation_id:
            request.correlation_id = str(uuid.uuid4())

        correlation = request.correlation_id
        logger.info(
            "Triage analysis started | rescue_id=%s correlation_id=%s",
            request.rescue_id,
            correlation,
        )

        try:
            response = await self._provider.analyze_rescue(request)

            logger.info(
                "Triage analysis completed | rescue_id=%s correlation_id=%s severity=%s provider=%s time_ms=%d",
                request.rescue_id,
                correlation,
                response.severity_estimate,
                response.model_metadata.provider,
                response.model_metadata.processing_time_ms,
            )
            return response

        except ProviderTimeoutError:
            logger.error(
                "AI provider timeout | rescue_id=%s correlation_id=%s",
                request.rescue_id,
                correlation,
            )
            return self._error_response(
                "AI provider timed out. The rescue case remains unaffected.",
                correlation,
            )

        except ProviderUnavailableError:
            logger.error(
                "AI provider unavailable | rescue_id=%s correlation_id=%s",
                request.rescue_id,
                correlation,
            )
            return self._error_response(
                "AI provider is currently unavailable. The rescue case remains unaffected.",
                correlation,
            )

        except ProviderError as e:
            logger.error(
                "AI provider error | rescue_id=%s correlation_id=%s error=%s",
                request.rescue_id,
                correlation,
                str(e),
            )
            return self._error_response(
                f"AI provider encountered an error: {type(e).__name__}. The rescue case remains unaffected.",
                correlation,
            )

        except Exception as e:
            logger.exception(
                "Unexpected error during triage | rescue_id=%s correlation_id=%s",
                request.rescue_id,
                correlation,
            )
            return self._error_response(
                "An unexpected error occurred during AI analysis. The rescue case remains unaffected.",
                correlation,
            )

    @staticmethod
    def _error_response(message: str, correlation_id: str | None) -> TriageResponse:
        """Return a safe, structured error response that won't corrupt business data."""
        return TriageResponse(
            observations=[],
            preliminary_assessment=message,
            severity_estimate="UNKNOWN",
            recommended_actions=["Manual review required due to AI service error."],
            safety_warnings=[],
            confidence_note="AI analysis failed. This is NOT a valid assessment.",
            model_metadata=ModelMetadata(
                provider="error",
                model="none",
                processing_time_ms=0,
            ),
            correlation_id=correlation_id,
        )
