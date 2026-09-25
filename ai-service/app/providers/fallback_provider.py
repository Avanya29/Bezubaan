"""
Bezubaan AI Service — Fallback Provider

Implements a provider that acts as a switch agent. It tries the primary provider
(e.g., Gemini) and if it fails (e.g., due to a 429 rate limit or 503 unavailability),
it automatically switches to a secondary provider (e.g., Groq).
"""
import logging

from app.providers.base import (
    AIProvider,
    ProviderError,
    ProviderTimeoutError,
    ProviderUnavailableError,
)
from app.schemas.triage import TriageRequest, TriageResponse

logger = logging.getLogger(__name__)


class FallbackProvider(AIProvider):
    """
    A provider switch agent that attempts to use a primary provider,
    and falls back to a secondary provider if the primary fails.
    """
    def __init__(self, primary: AIProvider, secondary: AIProvider):
        self.primary = primary
        self.secondary = secondary

    async def analyze_rescue(self, request: TriageRequest) -> TriageResponse:
        try:
            logger.info("Attempting primary provider...")
            return await self.primary.analyze_rescue(request)
        except (ProviderError, ProviderUnavailableError, ProviderTimeoutError) as e:
            logger.warning(f"Primary provider failed: {e}. Switching to secondary provider.")
            try:
                return await self.secondary.analyze_rescue(request)
            except Exception as e2:
                logger.error(f"Secondary provider also failed: {e2}")
                # Re-raise the secondary's error if both fail
                raise e2
