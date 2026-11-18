import os
from dotenv import load_dotenv
from langchain_google_genai import GoogleGenerativeAIEmbeddings

def test():
    load_dotenv()
    google_api_key = os.getenv("LLM_API_KEY")
    embeddings = GoogleGenerativeAIEmbeddings(model="models/gemini-embedding-001", google_api_key=google_api_key)
    res = embeddings.embed_query("test")
    print(f"Dimension of gemini-embedding-001: {len(res)}")

if __name__ == "__main__":
    test()
