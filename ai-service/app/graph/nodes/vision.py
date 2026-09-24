import logging
from typing import Dict, Any, List
from pydantic import BaseModel, Field
from langchain_google_genai import ChatGoogleGenerativeAI
from langchain_core.messages import HumanMessage
from app.graph.state import TriageGraphState

logger = logging.getLogger(__name__)

class EvidenceItem(BaseModel):
    observation: str = Field(description="The specific visual observation.")
    certainty: str = Field(description="low, moderate, or high")
    limitation: str = Field(description="Any limitations in the image affecting this observation.")

class VisionExtraction(BaseModel):
    media_quality: str = Field(description="Must be exactly one of: ACCEPTABLE, LIMITED, UNUSABLE")
    vision_findings: List[str] = Field(description="List of factual observable findings (e.g., injuries, condition)")
    evidence: List[EvidenceItem] = Field(description="List of specific visual evidence items.")

async def vision_node(state: TriageGraphState) -> Dict[str, Any]:
    """
    Vision Agent: Evaluates image quality and extracts observable evidence using Gemini Vision.
    Does NOT diagnose definitively.
    """
    logger.info("Executing vision_node for rescue_id=%s", state["rescue_id"])
    image_url = state.get("image_url")
    
    if not image_url:
        return {
            "media_quality": "NOT_PROVIDED",
            "vision_findings": ["No image provided for visual assessment."],
            "evidence": [{
                "observation": "No image available",
                "source": "vision_agent",
                "certainty": "high",
                "limitation": "Cannot assess without visual evidence."
            }]
        }
    
    try:
        # Initialize the Gemini Vision model (requires GOOGLE_API_KEY env var)
        llm = ChatGoogleGenerativeAI(model="gemini-1.5-flash", temperature=0)
        structured_llm = llm.with_structured_output(VisionExtraction)
        
        prompt = (
            "You are Jeev, an AI assistant for the Bezubaan Helping Hands animal rescue triage system. "
            "Analyze the provided image of a rescued animal. "
            "Identify visual findings, describe the animal's physical condition, and note any obvious injuries. "
            "Be factual. Do NOT provide definitive medical diagnoses, only visual observations."
        )
        
        message = HumanMessage(
            content=[
                {"type": "text", "text": prompt},
                {"type": "image_url", "image_url": {"url": image_url}}
            ]
        )
        
        # Invoke the model to extract structured data from the image
        result = await structured_llm.ainvoke([message])
        
        # Map back to state dictionary format, adding the source tracking
        evidence_with_source = []
        for ev in result.evidence:
            ev_dict = ev.model_dump()
            ev_dict["source"] = "vision_agent"
            evidence_with_source.append(ev_dict)
            
        return {
            "media_quality": result.media_quality,
            "vision_findings": result.vision_findings,
            "evidence": evidence_with_source
        }
        
    except Exception as e:
        logger.error(f"Vision model extraction failed: {e}")
        return {
            "media_quality": "LIMITED",
            "vision_findings": [f"Image extraction failed or was rejected: {str(e)}"],
            "evidence": [{
                "observation": "Image processing error",
                "source": "vision_agent",
                "certainty": "low",
                "limitation": "Failed to process image through API."
            }]
        }
