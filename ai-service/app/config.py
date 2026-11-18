"""
Bezubaan AI Service — Configuration
"""
from pydantic_settings import BaseSettings


class Settings(BaseSettings):
    ai_service_port: int = 8000
    ai_service_host: str = "0.0.0.0"
    ai_provider: str = "mock"  # mock | openai | gemini | anthropic (future)
    llm_api_key: str = ""
    groq_api_key: str = ""
    google_maps_api_key: str = ""
    pinecone_api_key: str = ""
    pinecone_index_name: str = "bezubaan-rag"
    ai_provider_timeout_seconds: int = 30
    log_level: str = "INFO"

    class Config:
        env_file = ".env"
        env_file_encoding = "utf-8"


settings = Settings()
