"""
Bezubaan AI Service — Abstract AI Provider Interface

Provider abstraction layer. All AI providers must implement this interface.
This ensures the application is decoupled from any specific LLM vendor.

When Q14/Q15 are resolved, concrete implementations (e.g. OpenAIProvider,
GeminiProvider) will be added alongside this base class.
"""
from abc import ABC, abstractmethod

from app.schemas.triage import TriageRequest, TriageResponse


class AIProvider(ABC):
    """Abstract base class for AI triage providers."""

    @abstractmethod
    async def analyze_rescue(self, request: TriageRequest) -> TriageResponse:
        """
        Analyze a rescue case and return a structured triage response.

        The provider MUST:
          - Return a valid TriageResponse
          - Populate model_metadata with provider/model info
          - Never claim to be a verified diagnosis
          - Handle its own internal errors and raise appropriate exceptions

        Raises:
            ProviderTimeoutError: If the provider exceeds the configured timeout.
            ProviderUnavailableError: If the provider cannot be reached.
            ProviderError: For any other provider-specific failures.
        """
        ...


class ProviderError(Exception):
    """Base exception for AI provider failures."""
    pass


class ProviderTimeoutError(ProviderError):
    """Raised when the AI provider exceeds configured timeout."""
    pass


class ProviderUnavailableError(ProviderError):
    """Raised when the AI provider cannot be reached."""
    pass
