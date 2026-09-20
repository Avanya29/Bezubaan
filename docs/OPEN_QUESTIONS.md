# Open Questions

**Purpose**: Questions that MUST be answered before or during implementation. Nothing should be guessed or assumed without verification.

### Critical (Blocks Phase 0)
- **Q1**: What package manager should be used? (`npm` / `yarn` / `pnpm`) — `UNKNOWN — REQUIRES DECISION`
- **Q2**: What Node.js version should be targeted? (recommend 20 LTS) — `UNKNOWN — REQUIRES DECISION`
- **Q3**: Should this be a monorepo or separate repos for NestJS and FastAPI? — `UNKNOWN — REQUIRES DECISION`
- **Q4**: What is the project's minimum viable product (MVP) feature set? — `UNKNOWN — REQUIRES DECISION`

### Product & Domain Decisions
- ✅ **RESOLVED** (Q26): Community, Donations, and Notifications features have been officially confirmed by the Product Owner. See `PHASE_9_PRODUCT_DECISIONS.md`. Phase 9 Implementation is UNBLOCKED.
- ✅ **RESOLVED**: Core domain product decisions (P1-P11) have been approved and moved to `PRODUCT_DECISIONS_REQUIRED.md`. Phase 3 Database Schema generation is UNBLOCKED.
- ✅ **RESOLVED** (Q24): Volunteer matching, location privacy, and rescue state transitions have been officially confirmed. See `PHASE_7_PRODUCT_DECISIONS.md`. Phase 7 Implementation is UNBLOCKED.
- ✅ **RESOLVED** (Q25): Medical Records, Veterinary Workflow, Adoption, and Foster requirements are confirmed. See `PHASE_8_PRODUCT_DECISIONS.md`. Phase 8 Implementation is UNBLOCKED.

### Important (Blocks Phase 3)
- **Q14**: What LLM provider? (OpenAI, Google Gemini, Anthropic, self-hosted?) — `UNKNOWN — REQUIRES DECISION`
- **Q15**: What vision model? (GPT-4V, Gemini Vision, custom trained?) — `UNKNOWN — REQUIRES DECISION`
- **Q16**: What vector database for RAG? (Pinecone, pgvector, Weaviate, ChromaDB?) — `UNKNOWN — REQUIRES DECISION`
- **Q17**: What knowledge should the RAG system contain? — `UNKNOWN — REQUIRES DECISION`

### Operational
- **Q18**: What cloud provider for hosting? (AWS, GCP, Azure, self-hosted?) — `UNKNOWN — REQUIRES DECISION`
- **Q19**: What CI/CD platform? (GitHub Actions, other?) — `UNKNOWN — REQUIRES DECISION`
- **Q20**: What monitoring/observability stack? (Prometheus+Grafana, Datadog, etc.?) — `UNKNOWN — REQUIRES DECISION`
- **Q21**: Budget constraints for cloud services and API calls? — `UNKNOWN — REQUIRES DECISION`
- **Q22**: Expected user scale? (100, 1000, 10000+ concurrent users?) — `UNKNOWN — REQUIRES DECISION`
- **Q23**: Is there a mobile app? If so, React Native, Flutter, or native? — `UNKNOWN — REQUIRES DECISION`
