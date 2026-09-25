import logging
from typing import Dict, Any
from app.graph.state import TriageGraphState
from langchain_google_genai import GoogleGenerativeAIEmbeddings
from langchain_core.vectorstores import InMemoryVectorStore
from langchain_core.documents import Document

logger = logging.getLogger(__name__)

from langchain_pinecone import PineconeVectorStore
from app.config import settings

# Initialize vector store connection
_vector_store = None

def get_vector_store():
    global _vector_store
    if _vector_store is None:
        try:
            embeddings = GoogleGenerativeAIEmbeddings(model="models/gemini-embedding-2")
            
            if settings.pinecone_api_key:
                _vector_store = PineconeVectorStore(
                    index_name=settings.pinecone_index_name,
                    embedding=embeddings,
                    pinecone_api_key=settings.pinecone_api_key
                )
                logger.info("RAG VectorStore connected to Pinecone.")
            else:
                logger.warning("No PINECONE_API_KEY found, RAG will be disabled.")
                _vector_store = None
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
