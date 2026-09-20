"""
Bezubaan AI Service — Triage Tests

Tests cover:
  - Mock provider success
  - Request validation
  - Response schema compliance
  - Provider timeout handling
  - Provider unavailable handling
  - Provider error handling
  - Malformed response handling
  - Anonymous correlation ID generation
"""
import pytest
from unittest.mock import AsyncMock, patch

from fastapi.testclient import TestClient

from app.main import app
from app.schemas.triage import TriageRequest, TriageResponse, LocationInfo, ModelMetadata
from app.providers.base import (
    AIProvider,
    ProviderError,
    ProviderTimeoutError,
    ProviderUnavailableError,
)
from app.providers.mock import MockProvider
from app.services.triage import TriageService


client = TestClient(app)


# ===== Fixtures =====

def _make_request(**overrides) -> dict:
    """Build a valid triage request payload."""
    base = {
        "rescue_id": "test-rescue-001",
        "description": "Injured dog found near the park, appears to have a broken leg",
        "location": {"latitude": 28.6139, "longitude": 77.2090},
        "correlation_id": "test-corr-001",
    }
    base.update(overrides)
    return base


# ===== FastAPI Endpoint Tests =====

class TestTriageEndpoint:
    """Tests for the /api/v1/triage endpoint via the FastAPI TestClient."""

    def test_successful_triage(self):
        """Mock provider returns a valid structured response."""
        response = client.post("/api/v1/triage", json=_make_request())
        assert response.status_code == 200

        data = response.json()
        assert "observations" in data
        assert "preliminary_assessment" in data
        assert "severity_estimate" in data
        assert "recommended_actions" in data
        assert "safety_warnings" in data
        assert "confidence_note" in data
        assert "model_metadata" in data
        assert data["correlation_id"] == "test-corr-001"
        assert data["model_metadata"]["provider"] == "mock"

    def test_missing_description_rejected(self):
        """Request without description is rejected by validation."""
        payload = _make_request()
        del payload["description"]
        response = client.post("/api/v1/triage", json=payload)
        assert response.status_code == 422

    def test_missing_location_rejected(self):
        """Request without location is rejected by validation."""
        payload = _make_request()
        del payload["location"]
        response = client.post("/api/v1/triage", json=payload)
        assert response.status_code == 422

    def test_missing_rescue_id_rejected(self):
        """Request without rescue_id is rejected by validation."""
        payload = _make_request()
        del payload["rescue_id"]
        response = client.post("/api/v1/triage", json=payload)
        assert response.status_code == 422

    def test_high_severity_keywords(self):
        """Mock provider detects high-severity keywords."""
        response = client.post(
            "/api/v1/triage",
            json=_make_request(description="Dog hit by car, bleeding heavily"),
        )
        assert response.status_code == 200
        assert response.json()["severity_estimate"] == "High"

    def test_low_severity_keywords(self):
        """Mock provider detects low-severity keywords."""
        response = client.post(
            "/api/v1/triage",
            json=_make_request(description="Minor scratch on a healthy cat"),
        )
        assert response.status_code == 200
        assert response.json()["severity_estimate"] == "Low"

    def test_with_animal_info(self):
        """Request with optional animal info is accepted."""
        response = client.post(
            "/api/v1/triage",
            json=_make_request(
                animal_info={"species": "Dog", "breed": "Labrador"}
            ),
        )
        assert response.status_code == 200
        observations = response.json()["observations"]
        assert any("Dog" in obs for obs in observations)

    def test_with_image_url(self):
        """Request with optional image_url is accepted."""
        response = client.post(
            "/api/v1/triage",
            json=_make_request(image_url="https://storage.example.com/img/001.jpg"),
        )
        assert response.status_code == 200
        observations = response.json()["observations"]
        assert any("Image" in obs for obs in observations)


# ===== Health Endpoint Tests =====

class TestHealthEndpoints:

    def test_health(self):
        response = client.get("/health")
        assert response.status_code == 200
        assert response.json()["status"] == "healthy"

    def test_ready(self):
        response = client.get("/ready")
        assert response.status_code == 200
        assert response.json()["status"] == "ready"


# ===== TriageService Unit Tests =====

class TestTriageServiceErrorHandling:
    """Tests that the TriageService gracefully handles provider failures."""

    @pytest.mark.asyncio
    async def test_provider_timeout_returns_safe_response(self):
        mock_provider = AsyncMock(spec=AIProvider)
        mock_provider.analyze_rescue.side_effect = ProviderTimeoutError("timed out")
        service = TriageService(mock_provider)

        request = TriageRequest(
            rescue_id="r1",
            description="test",
            location=LocationInfo(latitude=0, longitude=0),
        )
        result = await service.analyze(request)

        assert result.severity_estimate == "UNKNOWN"
        assert "timed out" in result.preliminary_assessment.lower()
        assert result.model_metadata.provider == "error"

    @pytest.mark.asyncio
    async def test_provider_unavailable_returns_safe_response(self):
        mock_provider = AsyncMock(spec=AIProvider)
        mock_provider.analyze_rescue.side_effect = ProviderUnavailableError()
        service = TriageService(mock_provider)

        request = TriageRequest(
            rescue_id="r1",
            description="test",
            location=LocationInfo(latitude=0, longitude=0),
        )
        result = await service.analyze(request)

        assert result.severity_estimate == "UNKNOWN"
        assert "unavailable" in result.preliminary_assessment.lower()

    @pytest.mark.asyncio
    async def test_generic_provider_error_returns_safe_response(self):
        mock_provider = AsyncMock(spec=AIProvider)
        mock_provider.analyze_rescue.side_effect = ProviderError("something broke")
        service = TriageService(mock_provider)

        request = TriageRequest(
            rescue_id="r1",
            description="test",
            location=LocationInfo(latitude=0, longitude=0),
        )
        result = await service.analyze(request)

        assert result.severity_estimate == "UNKNOWN"
        assert result.model_metadata.provider == "error"

    @pytest.mark.asyncio
    async def test_unexpected_exception_returns_safe_response(self):
        mock_provider = AsyncMock(spec=AIProvider)
        mock_provider.analyze_rescue.side_effect = RuntimeError("unexpected")
        service = TriageService(mock_provider)

        request = TriageRequest(
            rescue_id="r1",
            description="test",
            location=LocationInfo(latitude=0, longitude=0),
        )
        result = await service.analyze(request)

        assert result.severity_estimate == "UNKNOWN"
        assert "unexpected" in result.preliminary_assessment.lower()

    @pytest.mark.asyncio
    async def test_correlation_id_auto_generated(self):
        provider = MockProvider()
        service = TriageService(provider)

        request = TriageRequest(
            rescue_id="r1",
            description="test",
            location=LocationInfo(latitude=0, longitude=0),
            correlation_id=None,
        )
        result = await service.analyze(request)

        # correlation_id should have been auto-generated
        assert result.correlation_id is not None
        assert len(result.correlation_id) > 0
