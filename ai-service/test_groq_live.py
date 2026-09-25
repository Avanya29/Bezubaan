import asyncio
import os
from dotenv import load_dotenv

# Load environment variables
load_dotenv()

from app.schemas.triage import TriageRequest, AnimalInfo
from app.providers.groq_provider import GroqProvider

async def main():
    print("Testing GroqProvider directly...")
    
    # Check if key is available
    if not os.getenv("GROQ_API_KEY"):
        print("ERROR: GROQ_API_KEY is not set in the environment or .env file.")
        return

    try:
        provider = GroqProvider()
        print(f"Initialized GroqProvider with model: {provider.model_name}")
        
        request = TriageRequest(
            rescue_id="test-live-1",
            description="Found a stray cat limping on the right hind leg.",
            location={"latitude": 12.9716, "longitude": 77.5946, "address": "Bangalore"},
            animal_info=AnimalInfo(species="Cat", approximate_age="Adult")
        )
        
        print("Sending request to Groq API...")
        response = await provider.analyze_rescue(request)
        
        print("\n✅ SUCCESS! Received TriageResponse:")
        print(f"Severity: {response.severity_estimate}")
        print(f"Assessment: {response.preliminary_assessment}")
        print(f"Observations: {response.observations}")
        print(f"Model Metadata: {response.model_metadata.model_dump()}")
        
    except Exception as e:
        print(f"\n❌ FAILED with error: {type(e).__name__}")
        print(f"Error details: {e}")

if __name__ == "__main__":
    asyncio.run(main())
