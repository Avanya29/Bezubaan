# AI Architecture & Safety Boundary

> **Document Status**: APPROVED  
> **Last Updated**: 2026-09-19

## Overview
The Bezubaan AI architecture is explicitly designed as an **assistive processing pipeline**, heavily constrained by safety guardrails and separated from the authoritative business state. 

**NestJS (Backend)** is the authoritative source of truth.
**FastAPI (AI Service)** is a stateless, pure functional processor.

## 1. Provider Abstraction (ADR-007)
The exact LLM and Vision models (Q14, Q15) are currently `UNKNOWN`. 
To prevent vendor lock-in and allow unblocked development, the AI Service uses a Provider Abstraction (`AIProvider` ABC).
Currently, the system is backed by a deterministic `MockProvider` which returns safe, structured JSON matching the expected real-world contract.

## 2. Safety & Verification Boundary (BR-007, FR-009)
The AI Service is **FORBIDDEN** from modifying database state. 
- It accepts input (description, location, animal specs) via `TriageRequest`.
- It returns structured output (`TriageResponse`) containing `observations`, `preliminary_assessment`, `severity_estimate`, `recommended_actions`, and `safety_warnings`.
- NestJS stores this output strictly in `RescueCase.aiTriageData`.
- The AI output explicitly notes it is unverified (`confidence_note`).
- **Human Dispatchers** must independently verify the incident, tracked separately in `RescueCase.humanVerifiedById`.

## 3. NestJS ↔ FastAPI Contract
All communication is HTTP POST to `/api/v1/triage`.
- **Timeouts**: NestJS enforces a strict 30s timeout (`AI_PROVIDER_TIMEOUT_SECONDS`).
- **Resilience**: If the AI Service is down, times out, or returns malformed JSON, NestJS catches the failure and injects a "Safe Error Response" into the triage flow. **AI failure never blocks a rescue report from being created.**
- **Correlation**: Every request includes a `correlation_id` to trace logs across both services via OpenTelemetry (future).

## 4. Multi-Agent & Guardrails Orchestration
The AI Service utilizes **LangGraph** to coordinate multiple specialized agents:
- **Vision Agent**: Evaluates image quality and extracts observable evidence.
- **RAG Agent**: Retrieves Standard Operating Procedures (SOPs) from an in-memory vector store to ground the triage response in official guidelines.
- **Triage Agent**: Analyzes extracted evidence and RAG context using `gemini-1.5-flash` to formulate a severity priority.
- **Location Agent**: Uses Google Places API (New) to securely fetch nearby veterinary clinics if the case is deemed urgent (High/Critical severity).
- **Volunteer Agent**: Analyzes backend-provided volunteer lists and recommends the best match based on proximity, experience, and case severity.
- **Safety Node**: Custom Bezubaan validation to detect unsafe definitive diagnoses and executes NeMo Guardrails checks for prompt injection.

It also integrates **NeMo Guardrails** for:
- **Input Rails**: Prompt injection and jailbreak defense executed natively within the LangGraph Safety Node.
- **Output Rails**: Safety bounds on model outputs.


## 5. Security & Privacy
- **Stateless**: The AI service stores no state, no PII, and no images.
- **Internal Only**: The FastAPI service (`:18000`) is NOT exposed to the public internet; it sits behind the NestJS VPC boundary.
