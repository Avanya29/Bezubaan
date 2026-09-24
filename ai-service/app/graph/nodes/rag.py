import logging
from typing import Dict, Any
from app.graph.state import TriageGraphState
from langchain_google_genai import GoogleGenerativeAIEmbeddings
from langchain_core.vectorstores import InMemoryVectorStore
from langchain_core.documents import Document

logger = logging.getLogger(__name__)

# Initialize an in-memory vector store with standard rescue SOPs
_vector_store = None

def get_vector_store():
    global _vector_store
    if _vector_store is None:
        try:
            embeddings = GoogleGenerativeAIEmbeddings(model="models/embedding-001")
            
            # Dummy SOP documents for RAG
            docs = [
                Document(page_content="Standard animal rescue protocol: ensure scene safety before approaching. Do not startle injured animals."),
                Document(page_content="For a severe wound or heavy bleeding, immediate veterinary attention and transport are required. Apply gentle pressure if safe."),
                Document(page_content="If a spinal injury is suspected (e.g., hit by car, unable to move hind legs), do not attempt to move the animal without a rigid stretcher."),
                Document(page_content="When dealing with a rabid or highly aggressive dog, wait for animal control. Do not attempt bare-handed capture."),
                Document(page_content="For minor scratches or ticks, clean the area and recommend a routine vet checkup.")
            ]
            _vector_store = InMemoryVectorStore.from_documents(docs, embeddings)
            logger.info("RAG VectorStore initialized with SOPs.")
        except Exception as e:
            logger.error(f"Failed to initialize RAG VectorStore: {e}")
    return _vector_store

async def rag_node(state: TriageGraphState) -> Dict[str, Any]:
    """
    RAG Agent: Retrieves context to ground the triage response.
    """
    logger.info("Executing rag_node for rescue_id=%s", state["rescue_id"])
    
    description = state.get("description", "")
    vision_findings = state.get("vision_findings", [])
    
    query = description + " " + " ".join(vision_findings)
    if not query.strip():
        return {"retrieved_context": []}
        
    try:
        vs = get_vector_store()
        if vs:
            # Perform similarity search
            results = await vs.asimilarity_search(query, k=2)
            retrieved_context = [doc.page_content for doc in results]
        else:
            retrieved_context = ["Vector store unavailable. Fallback to basic safety rules."]
    except Exception as e:
        logger.error(f"RAG retrieval failed: {e}")
        retrieved_context = ["Retrieval failed. Fallback to basic safety rules."]
        
    return {
        "retrieved_context": retrieved_context
    }
