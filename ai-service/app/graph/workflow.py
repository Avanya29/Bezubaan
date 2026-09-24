import logging
from langgraph.graph import StateGraph, END
from app.graph.state import TriageGraphState
from app.graph.nodes.vision import vision_node
from app.graph.nodes.triage import triage_node
from app.graph.nodes.rag import rag_node
from app.graph.nodes.location import location_node
from app.graph.nodes.safety import safety_node

logger = logging.getLogger(__name__)

def build_triage_graph():
    """Builds the LangGraph for the AI Triage process."""
    
    workflow = StateGraph(TriageGraphState)
    
    # Add nodes
    workflow.add_node("vision", vision_node)
    workflow.add_node("triage", triage_node)
    workflow.add_node("rag", rag_node)
    workflow.add_node("location", location_node)
    from app.graph.nodes.volunteer import volunteer_node
    workflow.add_node("volunteer", volunteer_node)
    workflow.add_node("safety", safety_node)
    
    # Set entry point
    workflow.set_entry_point("vision")
    
    # Edges
    workflow.add_edge("vision", "rag")
    workflow.add_edge("rag", "triage")
    workflow.add_edge("triage", "location")
    workflow.add_edge("location", "volunteer")
    workflow.add_edge("volunteer", "safety")
    workflow.add_edge("safety", END)
    
    return workflow.compile()

# Compile the graph
triage_graph = build_triage_graph()
