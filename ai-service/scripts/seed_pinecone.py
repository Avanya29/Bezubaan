import os
import sys
import pandas as pd
from dotenv import load_dotenv
from langchain_google_genai import GoogleGenerativeAIEmbeddings
from langchain_pinecone import PineconeVectorStore
from langchain_core.documents import Document

def main():
    # Load environment variables
    load_dotenv()
    
    api_key = os.getenv("PINECONE_API_KEY")
    index_name = os.getenv("PINECONE_INDEX_NAME", "bezubaan-rag")
    google_api_key = os.getenv("LLM_API_KEY")
    
    if not api_key:
        print("ERROR: PINECONE_API_KEY is not set in .env")
        sys.exit(1)
        
    if not google_api_key:
        print("ERROR: LLM_API_KEY is not set in .env")
        sys.exit(1)

    csv_path = input("Enter the path to the Kaggle dataset file (.csv, .json, or .xlsx): ").strip('\"').strip('\'')
    if not os.path.exists(csv_path):
        print(f"ERROR: File {csv_path} does not exist.")
        sys.exit(1)
        
    print(f"Loading {csv_path}...")
    try:
        if csv_path.endswith('.csv'):
            df = pd.read_csv(csv_path)
        elif csv_path.endswith('.json'):
            df = pd.read_json(csv_path)
        elif csv_path.endswith('.xlsx'):
            df = pd.read_excel(csv_path)
        else:
            print("ERROR: Unsupported file format. Please provide a .csv, .json, or .xlsx file.")
            sys.exit(1)
    except Exception as e:
        print(f"Failed to read file: {e}")
        sys.exit(1)
        
    # Ask the user which column contains the text to embed
    print(f"Columns found: {', '.join(df.columns)}")
    text_column = input("Enter the exact name of the column containing the rescue SOP / knowledge text: ")
    
    if text_column not in df.columns:
        print(f"ERROR: Column '{text_column}' not found.")
        sys.exit(1)
        
    print("Preparing documents... (Limited to 100 to respect Gemini Free Tier Quota)")
    docs = []
    # Limit to 100 to prevent free-tier RESOURCE_EXHAUSTED errors
    for idx, row in df.head(100).iterrows():
        text = str(row[text_column]).strip()
        if text and text.lower() != 'nan':
            # Store other columns as metadata
            metadata = {col: str(row[col]) for col in df.columns if col != text_column and pd.notna(row[col])}
            docs.append(Document(page_content=text, metadata=metadata))
            
    print(f"Prepared {len(docs)} documents.")
    
    print("Initializing embeddings (gemini-embedding-2)...")
    embeddings = GoogleGenerativeAIEmbeddings(model="gemini-embedding-2", google_api_key=google_api_key)
    
    print(f"Uploading vectors to Pinecone index '{index_name}'...")
    print("This will take several minutes and make thousands of API calls to Google. Please wait.")
    
    try:
        # PineconeVectorStore.from_documents handles batching automatically
        PineconeVectorStore.from_documents(
            docs, 
            embeddings, 
            index_name=index_name,
            pinecone_api_key=api_key
        )
        print("✅ Successfully seeded Pinecone with Kaggle data!")
    except Exception as e:
        print(f"❌ Failed to seed Pinecone: {e}")

if __name__ == "__main__":
    main()
