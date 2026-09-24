import os
import logging
from nemoguardrails import LLMRails, RailsConfig

logger = logging.getLogger(__name__)

_rails = None

def get_rails() -> LLMRails | None:
    global _rails
    if _rails is None:
        try:
            config_path = os.path.join(os.path.dirname(__file__), "config")
            config = RailsConfig.from_path(config_path)
            _rails = LLMRails(config)
            logger.info("NeMo Guardrails initialized successfully.")
        except Exception as e:
            logger.error(f"Failed to initialize NeMo Guardrails: {e}")
            return None
    return _rails
