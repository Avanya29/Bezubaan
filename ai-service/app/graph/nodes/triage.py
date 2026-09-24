import logging
from typing import Dict, Any, List
from pydantic import BaseModel, Field
from langchain_google_genai import ChatGoogleGenerativeAI
from langchain_core.messages import HumanMessage
from app.graph.state import TriageGraphState

logger = logging.getLogger(__name__)

class TriageOutput(BaseModel):
    preliminary_assessment: str = Field(description="A brief 2-3 sentence preliminary assessment of the situation based on evidence.")
    severity_estimate: str = Field(description="Must be exactly one of: Low, Medium, High, Critical, UNKNOWN")
    recommended_actions: List[str] = Field(description="List of recommended immediate steps for the human rescue team.")
    safety_warnings: List[str] = Field(description="Any safety risks to humans or the animal. Empty if none.")

async def triage_node(state: TriageGraphState) -> Dict[str, Any]:
    """
    Triage Agent: Analyzes extracted evidence and description to formulate priority using Gemini.
    """
    logger.info("Executing triage_node for rescue_id=%s", state["rescue_id"])
    
    description = state.get("description", "")
    vision_findings = state.get("vision_findings", [])
    
    # If no description and no vision findings, we cannot triage.
    if not description.strip() and not vision_findings:
        return {
            "preliminary_assessment": "Insufficient evidence provided. Please provide a description or a clear image.",
            "severity_estimate": "UNKNOWN",
            "recommended_actions": ["Request more information from reporter."],
            "safety_warnings": []
        }
        
    try:
        llm = ChatGoogleGenerativeAI(model="gemini-1.5-flash", temperature=0)
        structured_llm = llm.with_structured_output(TriageOutput)
        
        vision_context = "\n".join(vision_findings) if vision_findings else "None provided."
        
        retrieved_context = state.get("retrieved_context", [])
        rag_context = "\n".join(retrieved_context) if retrieved_context else "None available."
        
        prompt = (
            "You are Jeev, an expert AI triage assistant for the Bezubaan animal rescue platform.\n"
            "Analyze the following incident report and visual evidence to provide a structured preliminary assessment.\n"
            "IMPORTANT SAFETY RULES:\n"
            "1. You provide a PRELIMINARY assessment, NOT a verified veterinary diagnosis.\n"
            "2. DO NOT invent clinical thresholds or fake medical terms.\n"
            "3. If symptoms suggest severe trauma, emphasize human and animal safety in warnings.\n"
            "4. Base your recommendations strictly on the provided Rescue SOP context if applicable.\n\n"
            f"Rescue SOP Context:\n{rag_context}\n\n"
            f"Reporter Description:\n{description}\n\n"
            f"Visual Evidence Extracted from Image:\n{vision_context}\n"
        )
        
        message = HumanMessage(content=prompt)
        result = await structured_llm.ainvoke([message])
        
        return {
            "preliminary_assessment": result.preliminary_assessment,
            "severity_estimate": result.severity_estimate,
            "recommended_actions": result.recommended_actions,
            "safety_warnings": result.safety_warnings
        }
    except Exception as e:
        logger.error(f"Triage model failed: {e}")
        return {
            "preliminary_assessment": "AI triage processing failed. Manual review required.",
            "severity_estimate": "UNKNOWN",
            "recommended_actions": ["Manual review required due to AI service error."],
            "safety_warnings": ["Always prioritize human safety when approaching unknown animals."]
        }
