"""
Bezubaan AI Service — Groq Provider

Implements the AIProvider interface using Groq.
"""
import json
import logging
import time

try:
    from groq import AsyncGroq
    import groq
except ImportError:
    AsyncGroq = None

from app.config import settings
from app.providers.base import (
    AIProvider,
    ProviderError,
    ProviderTimeoutError,
    ProviderUnavailableError,
)
from app.schemas.triage import TriageRequest, TriageResponse, ModelMetadata

logger = logging.getLogger(__name__)


class GroqProvider(AIProvider):
    """Groq implementation for AI triage."""

    def __init__(self):
        if not AsyncGroq:
            raise ImportError("groq package is not installed.")
        if not settings.groq_api_key:
            raise ValueError("GROQ_API_KEY must be set to use GroqProvider")
            
        self.client = AsyncGroq(api_key=settings.groq_api_key)
        # Using Llama 3 for fast json inference
        self.model_name = "llama3-8b-8192"

    async def analyze_rescue(self, request: TriageRequest) -> TriageResponse:
        start_time = time.time()
        
        prompt = self._build_prompt(request)
        
        try:
            response = await self.client.chat.completions.create(
                messages=[
                    {
                        "role": "system",
                        "content": "You are a helpful assistant that outputs JSON."
                    },
                    {
                        "role": "user",
                        "content": prompt,
                    }
                ],
                model=self.model_name,
                response_format={"type": "json_object"},
                temperature=0.2,
            )
            
            processing_time_ms = int((time.time() - start_time) * 1000)
            
            if not response.choices or not response.choices[0].message.content:
                raise ProviderError("Groq returned an empty response.")
                
            content = response.choices[0].message.content
            result_dict = json.loads(content)
            
            # Construct the response
            return TriageResponse(
                observations=result_dict.get("observations", []),
                preliminary_assessment=result_dict.get("preliminary_assessment", "No assessment provided."),
                severity_estimate=result_dict.get("severity_estimate", "UNKNOWN"),
                recommended_actions=result_dict.get("recommended_actions", []),
                safety_warnings=result_dict.get("safety_warnings", []),
                confidence_note=result_dict.get("confidence_note", "This is an AI-generated preliminary assessment. It is NOT a verified diagnosis."),
                model_metadata=ModelMetadata(
                    provider="groq",
                    model=self.model_name,
                    processing_time_ms=processing_time_ms,
                ),
                correlation_id=request.correlation_id,
            )
            
        except json.JSONDecodeError as e:
            logger.error(f"Failed to parse Groq JSON output: {e}.")
            raise ProviderError(f"Invalid JSON response from model: {e}")
        except Exception as e:
            error_msg = str(e).lower()
            if "timeout" in error_msg:
                raise ProviderTimeoutError(f"Groq request timed out: {e}")
            if "unavailable" in error_msg or "503" in error_msg or "rate limit" in error_msg or "429" in error_msg:
                raise ProviderUnavailableError(f"Groq service unavailable or rate limited: {e}")
            raise ProviderError(f"Groq request failed: {e}")

    def _build_prompt(self, request: TriageRequest) -> str:
        """Construct the prompt for the LLM based on the request."""
        animal_ctx = ""
        if request.animal_info:
            info = request.animal_info
            animal_ctx = f"""
Animal Info:
- Species: {info.species or 'Unknown'}
- Breed: {info.breed or 'Unknown'}
- Age: {info.approximate_age or 'Unknown'}
- Description: {info.description or 'None'}
"""
        
        location_ctx = f"Location: {request.location.latitude}, {request.location.longitude} ({request.location.address or 'No address'})"

        return f"""
You are Jeev, an expert animal rescue triage assistant for the Bezubaan platform.
Your task is to analyze the following incident report and provide a structured preliminary assessment.

IMPORTANT SAFETY RULES:
1. You are providing a PRELIMINARY assessment, NOT a verified veterinary diagnosis.
2. If symptoms suggest rabies, severe trauma, or aggressive behavior, emphasize human safety in warnings.

Incident Description:
"{request.description}"

{location_ctx}
{animal_ctx}

Output MUST be a valid JSON object with the following schema:
{{
    "observations": ["list of key facts observed from the description"],
    "preliminary_assessment": "A brief 2-3 sentence assessment of the situation",
    "severity_estimate": "Low" | "Medium" | "High" | "Critical" | "UNKNOWN",
    "recommended_actions": ["list of recommended immediate steps"],
    "safety_warnings": ["any safety risks to humans or the animal"],
    "confidence_note": "A brief note on your confidence level given the provided information"
}}
"""
