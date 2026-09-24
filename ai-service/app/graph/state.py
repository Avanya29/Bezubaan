import operator
from typing import Annotated, Any, TypedDict

class TriageGraphState(TypedDict):
    """LangGraph State for Triage Workflow."""
    # Input
    rescue_id: str
    description: str
    latitude: float
    longitude: float
    image_url: str | None
    animal_info: dict | None
    correlation_id: str

    # Interim steps
    media_quality: str # ACCEPTABLE, LIMITED, UNUSABLE, NOT_PROVIDED
    vision_findings: Annotated[list[str], operator.add]
    evidence: Annotated[list[dict], operator.add]
    
    # Flags for safety and human review
    requires_human_review: bool
    human_review_reason: str | None
    safety_violations: Annotated[list[str], operator.add]

    # RAG / Retrieval
    retrieved_context: Annotated[list[str], operator.add]

    # Output
    preliminary_assessment: str
    severity_estimate: str
    recommended_actions: Annotated[list[str], operator.add]
    safety_warnings: Annotated[list[str], operator.add]
    confidence_note: str
    
    # Model Metadata
    model_metadata: dict
    error: str | None
    
    # Location
    nearby_vets: dict | None
    
    # Volunteers
    volunteers: list
    recommended_volunteers: list
