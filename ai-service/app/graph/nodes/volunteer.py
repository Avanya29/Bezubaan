import logging
from typing import Dict, Any, List
from pydantic import BaseModel, Field
from langchain_google_genai import ChatGoogleGenerativeAI
from langchain_core.messages import HumanMessage
from app.graph.state import TriageGraphState

logger = logging.getLogger(__name__)

class VolunteerRecommendationSchema(BaseModel):
    volunteer_id: str = Field(description="The exact ID of the recommended volunteer.")
    name: str = Field(description="The name of the volunteer.")
    reason: str = Field(description="Brief reason for the recommendation (e.g., 'Closest distance', 'Has medical experience').")
    match_score: int = Field(description="Confidence score from 0-100.")

class VolunteerResponseSchema(BaseModel):
    recommended_volunteers: List[VolunteerRecommendationSchema]

async def volunteer_node(state: TriageGraphState) -> Dict[str, Any]:
    """
    Volunteer Agent: Recommends the best available volunteer based on severity and backend-provided context.
    Does NOT assign, only recommends.
    """
    logger.info("Executing volunteer_node for rescue_id=%s", state["rescue_id"])
    
    volunteers = state.get("volunteers", [])
    if not volunteers:
        return {"recommended_volunteers": []}
        
    try:
        llm = ChatGoogleGenerativeAI(model="gemini-2.5-flash", temperature=0)
        structured_llm = llm.with_structured_output(VolunteerResponseSchema)
        
        severity = state.get("severity_estimate", "UNKNOWN")
        assessment = state.get("preliminary_assessment", "")
        
        vol_details = "\n".join([f"ID: {v['id']}, Name: {v['name']}, Distance: {v.get('distance_meters', 'Unknown')}m, Experience: {v.get('experience_level', 'Unknown')}, Skills: {', '.join(v.get('capabilities', []))}" for v in volunteers])
        
        prompt = (
            "You are Jeev, an expert AI triage assistant for the Bezubaan animal rescue platform.\n"
            "Your task is to recommend the best volunteer(s) for a rescue case based on the provided list of available volunteers.\n"
            "IMPORTANT RULES:\n"
            "1. You may ONLY recommend volunteers from the provided list. Do not invent names or IDs.\n"
            "2. If severity is Critical/High, prioritize medical experience or closest distance.\n"
            "3. You are RECOMMENDING, not assigning.\n\n"
            f"Case Severity: {severity}\n"
            f"Case Assessment: {assessment}\n\n"
            f"Available Volunteers:\n{vol_details}\n"
        )
        
        message = HumanMessage(content=prompt)
        result = await structured_llm.ainvoke([message])
        
        recs = [r.dict() for r in result.recommended_volunteers]
        return {"recommended_volunteers": recs}
        
    except Exception as e:
        logger.error(f"Volunteer model failed: {e}")
        return {"recommended_volunteers": []}
