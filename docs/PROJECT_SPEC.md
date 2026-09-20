# Project Specification: Bezubaan Helping Hands

> **Document Status**: DRAFT / INITIAL SPECIFICATION  
> **Last Updated**: 2026-09-19  
> **Agent Resumability Note**: This document reflects verified repository facts and initial project goals. Future AI agents or engineers must reference this specification before proposing architecture or writing implementation code.

---

## 1. Project Overview

- **Project Name**: Bezubaan Helping Hands
- **Repository URL**: [https://github.com/Avanya29/Bezubaan.git](https://github.com/Avanya29/Bezubaan.git)
- **Purpose**: AI-assisted animal rescue and welfare platform
- **Core Domain**: Animal rescue operations, welfare tracking, AI-assisted identification and triage

---

## 2. Repository Facts & Verified Current State

The following facts reflect the exact state of the repository as of 2026-09-19:

| Fact / Attribute | Verified Detail |
| :--- | :--- |
| **Remote URL** | `https://github.com/Avanya29/Bezubaan.git` |
| **Branch** | `main` (single branch) |
| **Commit History** | Single commit: `c538f54` (*"initialization of project"*) by `AyushGupta011` on 2026-09-03 |
| **Committed Files** | `.gitignore`, `README.md` |
| **Working Tree Status** | `.gitignore` has been deleted from working tree; `README.md` is a placeholder with no project description |
| **Application Code** | **ZERO application code** (no `package.json`, no runtime configuration, no database schemas, no tests) |
| **Execution State** | Green-field / Unstarted |

---

## 3. Technology Stack (Intended)

> [!IMPORTANT]
> The technologies listed below represent the **INTENDED** tech stack from the initial project brief. They are **NOT CONFIRMED** as needed or final. Each item requires formal team evaluation and validation against concrete product requirements before adoption.

| Component / Layer | Intended Technology | Status |
| :--- | :--- | :--- |
| **Primary Backend** | Node.js, TypeScript, NestJS | INTENDED — Not confirmed / Not initialized |
| **Database** | PostgreSQL | INTENDED — Not confirmed / Not provisioned |
| **ORM** | Prisma | INTENDED — Not confirmed / Not configured |
| **Caching / PubSub** | Redis | INTENDED — Not confirmed / Not provisioned |
| **Message Queue** | RabbitMQ | INTENDED — Not confirmed / Not provisioned |
| **AI / ML Services** | Python, FastAPI, LangChain, LangGraph, LLM, Vision Model, RAG | INTENDED — Not confirmed / Not initialized |
| **Push Notifications** | Firebase Cloud Messaging (FCM) | INTENDED — Not confirmed / Not configured |
| **Geolocation / Maps** | Google Maps / Places API | INTENDED — Not confirmed / Not configured |
| **Object / File Storage** | Object/File Storage (provider TBD) | INTENDED — Not confirmed / Provider TBD |
| **Containerization** | Docker | INTENDED — Not confirmed / No Dockerfiles exist |

---

## 4. Stakeholders and Target Users

- **Stakeholders**: `UNKNOWN — REQUIRES DECISION`
- **Target Users**: `UNKNOWN — likely rescuers, volunteers, veterinarians, animal welfare organizations, general public (TBD — REQUIRES TEAM DECISION)`

---

## 5. Features and Requirements

The formal requirements have been audited and split into dedicated specification documents. All current requirements are pending stakeholder approval.

- **[Product Requirements Document (PRD)](PRODUCT_REQUIREMENTS.md)**: Master list of functional and non-functional requirements.
- **[User Stories](USER_STORIES.md)**: Proposed agile user stories.
- **[Business Rules](BUSINESS_RULES.md)**: Operational and domain constraints.
- **[Domain Glossary](DOMAIN_GLOSSARY.md)**: Ubiquitous language definitions.
- **[Requirement Traceability](REQUIREMENT_TRACEABILITY.md)**: Matrix linking rules and stories.
- **[Product Decisions Required](PRODUCT_DECISIONS_REQUIRED.md)**: 🚨 **CRITICAL**: The list of decisions currently blocking implementation.

---

## 6. Non-Functional Requirements (NFRs)

`UNKNOWN — REQUIRES DECISION`

Specific NFRs requiring formal definition:
- Availability and uptime targets
- Request latency and AI inference throughput targets
- Data retention, security, and access control policies
- Scalability parameters and concurrency limits
- Internationalization, localization, and offline capabilities

---

## 7. Resumption Guide for Future Agents

When resuming development from this document:
1. Verify if a detailed Product Requirements Document (PRD) has been provided.
2. Confirm the tech stack choices with the team before creating boilerplate files.
3. Follow the specifications in [`ARCHITECTURE.md`](file:///c:/Users/AYUSH%20GUPTA/Desktop/Bezubaan/docs/ARCHITECTURE.md), [`DATABASE.md`](file:///c:/Users/AYUSH%20GUPTA/Desktop/Bezubaan/docs/DATABASE.md), and [`API_CONTRACT.md`](file:///c:/Users/AYUSH%20GUPTA/Desktop/Bezubaan/docs/API_CONTRACT.md).
