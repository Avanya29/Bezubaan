import pytest
from unittest.mock import AsyncMock

from app.schemas.triage import TriageRequest, TriageResponse, ModelMetadata
from app.providers.base import AIProvider, ProviderUnavailableError
from app.providers.fallback_provider import FallbackProvider

class MockPrimaryFailingProvider(AIProvider):
    """A mock primary provider that always fails with a rate limit error."""
    async def analyze_rescue(self, request: TriageRequest) -> TriageResponse:
        raise ProviderUnavailableError("Gemini service unavailable or rate limited: 429 Too Many Requests")

class MockSecondarySuccessProvider(AIProvider):
    """A mock secondary provider that always succeeds."""
    async def analyze_rescue(self, request: TriageRequest) -> TriageResponse:
        return TriageResponse(
            observations=["Groq handled this request!"],
            preliminary_assessment="Secondary provider assessment.",
            severity_estimate="Medium",
            recommended_actions=[],
            safety_warnings=[],
            confidence_note="Handled by fallback",
            model_metadata=ModelMetadata(
                provider="groq",
                model="llama3-8b",
                processing_time_ms=100
            ),
            correlation_id=request.correlation_id,
        )

@pytest.mark.asyncio
async def test_fallback_provider_switches_on_failure():
    """Test that the FallbackProvider switches to the secondary provider when the primary fails."""
    
    primary = MockPrimaryFailingProvider()
    secondary = MockSecondarySuccessProvider()
    
    fallback_agent = FallbackProvider(primary, secondary)
    
    request = TriageRequest(
        rescue_id="test-rescue-1",
        description="Dog injured on the street",
        location={"latitude": 12.9716, "longitude": 77.5946}
    )
    
    # This should internally fail on primary and return secondary's response
    response = await fallback_agent.analyze_rescue(request)
    
    # Verify that the response came from the secondary provider
    assert response.model_metadata.provider == "groq"
    assert "Groq handled this request!" in response.observations
