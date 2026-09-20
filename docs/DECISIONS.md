# Architectural Decisions Record

This log records significant architectural and design decisions, their context, options, and rationale.

## ADR-001: Documentation-First Approach
- **Date**: 2026-09-19
- **Status**: ACCEPTED
- **Context**: Project repository was empty. Multiple AI agents will work on this project over time.
- **Decision**: Create comprehensive documentation system before writing any application code.
- **Rationale**: Ensures continuity between agent sessions and prevents hallucinated implementation.

## ADR-002: Monorepo Structure (PROPOSED)
- **Date**: 2026-09-19
- **Status**: PROPOSED — REQUIRES TEAM APPROVAL
- **Context**: The project needs both a NestJS API and a Python AI service.
- **Options**:
  1. Monorepo with both services in one repository
  2. Separate repositories for each service
  3. Monorepo with workspace tooling (Nx, Turborepo)
- **Decision**: PENDING
- **Notes**: Monorepo simplifies development and deployment coordination but requires careful configuration.

## ADR-003: Database ORM Choice (PROPOSED)
- **Date**: 2026-09-19
- **Status**: PROPOSED — from project brief
- **Context**: Need an ORM for PostgreSQL.
- **Decision**: Prisma (from project requirements)
- **Rationale**: Type-safe, excellent TypeScript integration, schema-as-code, automatic migrations.
- **Risks**: Prisma has limitations with complex queries; may need raw SQL for some operations.

## ADR-004: Prisma Downgrade to 5.x
- **Date**: 2026-09-19
- **Status**: ACCEPTED
- **Context**: Initial installation installed Prisma 7.x. Prisma 7 dropped direct URL configuration support in `schema.prisma` (`datasource db { url = env(...) }`), leading to client generation failures in the standard NestJS project layout.
- **Decision**: Downgrade Prisma CLI and `@prisma/client` to the stable v5.x release line (`^5.22.0`).
- **Rationale**:
  1. Maintains complete compatibility and stability with the NestJS 10 ecosystem and established project patterns.
  2. Ensures standard `schema.prisma` datasource declaration works reliably with environment variables.
  3. Eliminates instability and breaking schema requirements during foundational setup.
- **Consequences**:
  - `package.json` pins Prisma to `~5.22.0`.
  - Upgrading to subsequent major releases (Prisma 6/7) can be re-evaluated after core services and migrations are established.

## ADR-005: Strict Product Requirement Segregation
- **Date**: 2026-09-19
- **Status**: ACCEPTED
- **Context**: To prevent AI agents from hallucinating database schemas based on vague domains, we require explicit product definitions.
- **Decision**: Establish 6 core product files (PRD, User Stories, Business Rules, Glossary, Traceability, Decisions Required) and halt all Prisma schema generation until stakeholders answer the exact requirements.
- **Rationale**: Ensures the database exactly matches verified requirements and avoids premature denormalization or speculative fields.

## ADR-006: Database Schema Domain Approval (P1-P11)
- **Date**: 2026-09-19
- **Status**: ACCEPTED
- **Context**: Core domain fields and lifecycles were pending for Phase 3.
- **Decision**: The 11 core product decisions regarding Auth, Roles, Animal properties, Rescue states, Locations, Media, Verification, PII, and Medical History have been formally approved.
- **Rationale**: Provides the verified requirements needed to execute the Phase 3 schema generation without hallucination.

## ADR-007: Provider-Agnostic AI Architecture & Mock Foundation
- **Date**: 2026-09-19
- **Status**: ACCEPTED (Phase 6)
- **Context**: Establishing foundational AI architecture and integration testing mechanisms before selecting specific paid AI models.
- **Decision**: The FastAPI AI service implements an abstract `AIProvider` base class and relies entirely on a deterministic `MockProvider` for initial integration. The exact LLM (Q14) and Vision models (Q15) remain `UNKNOWN`. NestJS validates all AI output and stores it strictly in `aiTriageData`.
- **Rationale**: Prevents vendor lock-in, unblocks end-to-end integration testing, guarantees 100% test reproducibility, and safely enforces the human-in-the-loop requirement (FR-009) before paid AI models are integrated.

## ADR-008: Phase 7 Product Rules (Volunteer & Rescue Coordination)
- **Date**: 2026-09-20
- **Status**: ACCEPTED (Phase 7)
- **Context**: 50 product decisions regarding Volunteer matching, assignments, exact Rescue State Lifecycle transitions, and Location Privacy needed resolution.
- **Decision**: The decisions have been explicitly defined and documented in `PHASE_7_PRODUCT_DECISIONS.md`. A hybrid matching model, specific state transition graph, and strict location privacy boundaries (hiding coordinates until assignment acceptance) were adopted.
- **Rationale**: Formally defines business behavior to prevent AI hallucination and unblocks the database schema for Volunteer Profiles and Assignments.
