"""
Bezubaan AI Service — Gemini Provider

Implements the AIProvider interface using Google Gemini 2.5-flash.
"""
import json
import logging
import time
from typing import Dict, Any

import google.generativeai as genai
from google.generativeai.types import generation_types

from app.config import settings
from app.providers.base import (
    AIProvider,
    ProviderError,
    ProviderTimeoutError,
    ProviderUnavailableError,
)
from app.schemas.triage import TriageRequest, TriageResponse, ModelMetadata

logger = logging.getLogger(__name__)


class GeminiProvider(AIProvider):
    """Google Gemini implementation for AI triage."""

    def __init__(self):
        if not settings.llm_api_key:
            raise ValueError("LLM_API_KEY must be set to use GeminiProvider")
            
        genai.configure(api_key=settings.llm_api_key)
        self.model_name = "gemini-2.5-flash"
        self.model = genai.GenerativeModel(self.model_name)

    async def analyze_rescue(self, request: TriageRequest) -> TriageResponse:
        start_time = time.time()
        
        prompt = self._build_prompt(request)
        
        try:
            # We enforce JSON output structure to match TriageResponse
            # Using the latest Structured Outputs capability if available, or just JSON mode
            response = await self.model.generate_content_async(
                prompt,
                generation_config=genai.types.GenerationConfig(
                    response_mime_type="application/json",
                    temperature=0.2, # Low temperature for more deterministic triage
                ),
            )
            
            processing_time_ms = int((time.time() - start_time) * 1000)
            
            if not response.text:
                raise ProviderError("Gemini returned an empty response.")
                
            result_dict = json.loads(response.text)
            
            # Construct the response
            return TriageResponse(
                observations=result_dict.get("observations", []),
                preliminary_assessment=result_dict.get("preliminary_assessment", "No assessment provided."),
                severity_estimate=result_dict.get("severity_estimate", "UNKNOWN"),
                recommended_actions=result_dict.get("recommended_actions", []),
                safety_warnings=result_dict.get("safety_warnings", []),
                confidence_note=result_dict.get("confidence_note", "This is an AI-generated preliminary assessment. It is NOT a verified diagnosis."),
                model_metadata=ModelMetadata(
                    provider="gemini",
                    model=self.model_name,
                    processing_time_ms=processing_time_ms,
                ),
                correlation_id=request.correlation_id,
            )
            
        except generation_types.StopCandidateException as e:
            logger.error(f"Gemini generation stopped unexpectedly: {e}")
            raise ProviderError(f"Generation stopped unexpectedly: {e}")
        except json.JSONDecodeError as e:
            logger.error(f"Failed to parse Gemini JSON output: {e}. Output was: {response.text}")
            raise ProviderError(f"Invalid JSON response from model: {e}")
        except Exception as e:
            error_msg = str(e).lower()
            if "timeout" in error_msg:
                raise ProviderTimeoutError(f"Gemini request timed out: {e}")
            if "unavailable" in error_msg or "503" in error_msg:
                raise ProviderUnavailableError(f"Gemini service unavailable: {e}")
            raise ProviderError(f"Gemini request failed: {e}")

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
