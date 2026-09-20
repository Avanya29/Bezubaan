# Testing Strategy

## Overview

This document outlines the testing strategy for the **Bezubaan Helping Hands** project (an AI-assisted animal rescue and welfare platform).

---

## Current State

- **Existing Tests:** ZERO tests exist.
- **Test Framework:** No test framework configured.
- **Test Runner:** No test runner configured.
- **Coverage Tool:** No coverage tool configured.
- **Test Fixtures / Seed Data:** None exist.

---

## Proposed Testing Approach (DRAFT — Requires Team Decision)

The following test categories are proposed based on the intended tech stack (NestJS backend and Python FastAPI AI service). All items remain drafts pending architecture and team sign-off.

| Test Level / Type | Proposed Tooling | Scope & Target | Status |
| :--- | :--- | :--- | :--- |
| **Unit Tests (Backend)** | Jest | NestJS services, controllers, utilities, business logic | PROPOSED (DRAFT) |
| **Unit Tests (AI Service)** | pytest | FastAPI endpoints, prompt templates, helper modules | PROPOSED (DRAFT) |
| **Integration Tests** | Supertest + test database | API endpoints, Prisma ORM queries, Redis cache, RabbitMQ event dispatching | PROPOSED (DRAFT) |
| **End-to-End (E2E) Tests** | `UNKNOWN — REQUIRES DECISION` | User flows across backend, AI service, and database | `UNKNOWN — REQUIRES DECISION` |
| **AI Model Tests** | `UNKNOWN — REQUIRES DECISION` | LLM prompt evaluation, vision inference verification, hallucination checks | `UNKNOWN — REQUIRES DECISION` |
| **Load / Performance Tests** | `UNKNOWN — REQUIRES DECISION` | Concurrency testing under emergency rescue surge conditions (e.g., k6, Locust) | `UNKNOWN — REQUIRES DECISION` |

---

## Test Infrastructure & Architecture Decisions

The following key technical decisions must be finalized prior to implementing the test suite:

### 1. Test Database Strategy
- **Status:** `UNKNOWN — REQUIRES DECISION`
- **Options Under Consideration:**
  - Dedicated separate PostgreSQL instance (e.g., spun up via Docker container in local dev and CI).
  - In-memory SQLite or mocked database adapter (limited fidelity with PostgreSQL-specific features like PostGIS or JSONB).

### 2. Mocking Strategy
- **Status:** `UNKNOWN — REQUIRES DECISION`
- **Scope Requiring Strategy:**
  - External Third-Party APIs (Google Maps API, Firebase Cloud Messaging).
  - External AI Providers (LLM completions, Vision model API endpoints).
  - Asynchronous Message Broker (RabbitMQ) and Cache (Redis).

### 3. Coverage Requirements
- **Status:** `UNKNOWN — REQUIRES DECISION`
- **Thresholds:** No code coverage targets (e.g., line, branch, function percentages) have been set.

### 4. CI/CD Test Pipeline
- **Status:** Not configured.
- No automated pipeline runs tests on pull requests or commits.

---

## Agent-Resumable Next Steps for Test Implementation

When test infrastructure setup begins:
1. Establish unit test configurations (`jest.config.js` / `pytest.ini`) alongside application initialization.
2. Formulate decision on test database (spin up containerized PostgreSQL for integration tests).
3. Establish external API mock wrappers (specifically for Maps, FCM, and AI providers) to prevent tests from consuming external API credits or failing on network isolation.
4. Establish threshold gates once team approves coverage targets.
