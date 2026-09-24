import logging
from typing import Dict, Any
from app.graph.state import TriageGraphState

logger = logging.getLogger(__name__)

async def safety_node(state: TriageGraphState) -> Dict[str, Any]:
    """
    Safety Node: Validates that the model is not providing definitive diagnoses.
    """
    logger.info("Executing safety_node for rescue_id=%s", state["rescue_id"])
    
    assessment = state.get("preliminary_assessment", "").lower()
    safety_violations = []
    requires_human_review = state.get("requires_human_review", False)
    reason = state.get("human_review_reason")
    
    # Custom Bezubaan Safety Engine - Detect definitive diagnosis
    definitive_words = ["diagnosed with", "confirmed", "definitely", "treatment is"]
    for word in definitive_words:
        if word in assessment:
            safety_violations.append(f"Detected definitive diagnostic language: '{word}'")
            requires_human_review = True
            reason = reason or "AI attempted to provide a definitive diagnosis."
            
    # NeMo Guardrails Check on description
    description = state.get("description", "")
    if description:
        from app.guardrails.service import get_rails
        rails = get_rails()
        if rails:
            try:
                # generate_async takes an array of messages
                rails_res = await rails.generate_async(messages=[{"role": "user", "content": description}])
                # If NeMo intervened, the response content will typically be the bot refuse message
                if rails_res and "I cannot ignore my instructions" in rails_res.get("content", ""):
                    safety_violations.append("Prompt Injection / Jailbreak attempt detected.")
                    requires_human_review = True
                    reason = reason or "Security policy violation detected in input."
            except Exception as e:
                logger.error(f"NeMo Guardrails execution failed: {e}")
            
    if state.get("media_quality") == "UNUSABLE":
        requires_human_review = True
        reason = reason or "Image quality unusable, requires human review."

    return {
        "safety_violations": safety_violations,
        "requires_human_review": requires_human_review,
        "human_review_reason": reason,
        "confidence_note": "This is an AI-generated preliminary assessment. It is NOT a verified diagnosis." if not safety_violations else "WARNING: Potential unsafe AI assessment detected. Human review required."
    }
