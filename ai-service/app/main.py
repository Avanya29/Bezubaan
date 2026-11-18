"""
Bezubaan AI Service — FastAPI Application

This is the AI processing service for the Bezubaan Helping Hands platform.

Architecture:
  NestJS Backend → (HTTP) → This FastAPI Service → AI Provider → Structured Result → NestJS Backend

Safety (BR-007, FR-009):
  This service ONLY produces preliminary AI assessments.
  It NEVER modifies rescue case status, user data, or any business state.
  All AI output must be verified by a human before becoming business truth.
"""
import logging

from fastapi import FastAPI, HTTPException
from fastapi.responses import JSONResponse

from app.config import settings
from app.schemas.triage import TriageRequest, TriageResponse
from app.providers.mock import MockProvider
from app.providers.gemini import GeminiProvider
from app.providers.base import AIProvider
from app.services.triage import TriageService

# Configure logging
logging.basicConfig(
    level=getattr(logging, settings.log_level.upper(), logging.INFO),
    format="%(asctime)s [%(levelname)s] %(name)s: %(message)s",
)
logger = logging.getLogger(__name__)

# --- Provider factory ---
def _create_provider() -> AIProvider:
    """
    Instantiate the configured AI provider.

    Currently supports 'mock', 'gemini', 'groq', and 'fallback'.
    """
    provider_name = settings.ai_provider.lower()
    if provider_name == "mock":
        logger.info("Using MockProvider for AI triage")
        return MockProvider()
    elif provider_name == "gemini":
        logger.info("Using GeminiProvider (gemini-2.5-flash) for AI triage")
        return GeminiProvider()
    elif provider_name == "groq":
        logger.info("Using GroqProvider (llama3-8b) for AI triage")
        from app.providers.groq_provider import GroqProvider
        return GroqProvider()
    elif provider_name == "fallback":
        logger.info("Using FallbackProvider (Gemini -> Groq) for AI triage")
        from app.providers.groq_provider import GroqProvider
        from app.providers.fallback_provider import FallbackProvider
        return FallbackProvider(GeminiProvider(), GroqProvider())
    else:
        raise ValueError(
            f"Unknown AI_PROVIDER: '{settings.ai_provider}'. "
            f"Currently 'mock', 'gemini', 'groq', and 'fallback' are supported."
        )


# --- Application setup ---
app = FastAPI(
    title="Bezubaan AI Service",
    description="AI triage processing service for animal rescue cases",
    version="0.1.0",
)

provider = _create_provider()
triage_service = TriageService(provider)


# --- Health endpoints ---
@app.get("/health")
async def health():
    """Basic liveness check."""
    return {"status": "healthy", "service": "bezubaan-ai-service"}


@app.get("/ready")
async def ready():
    """Readiness check — confirms the provider is initialized."""
    return {
        "status": "ready",
        "provider": settings.ai_provider,
    }


# --- Triage endpoint ---
@app.post("/api/v1/triage", response_model=TriageResponse)
async def triage(request: TriageRequest):
    """
    Analyze a rescue case and return a structured AI triage assessment.

    This endpoint is called by the NestJS backend (not directly by clients).
    The response is stored in RescueCase.aiTriageData and must be
    verified by a human before becoming business truth.
    """
    logger.info(
        "Triage request received | rescue_id=%s correlation_id=%s",
        request.rescue_id,
        request.correlation_id,
    )

    result = await triage_service.analyze(request)
    return result
