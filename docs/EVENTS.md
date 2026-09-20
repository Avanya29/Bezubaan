# Event System Architecture

> [!NOTE]
> **Repository State Notice**: As of current verification, the Bezubaan repository is empty (containing only a placeholder `README.md`). There is currently no event-driven infrastructure, broker setup, messaging client, queue, or event definition in place.

---

## 1. Current State

| Dimension | Current Verification Status |
| :--- | :--- |
| **Message Broker Infrastructure** | None exists. No broker instances, connection pools, or container configurations exist. |
| **Event Schemas** | `NONE DEFINED`. No message contracts, event payloads, or serialization formats exist. |
| **Queue Names** | `NONE DEFINED`. No queues, exchanges, or routing topics have been provisioned or registered. |
| **Producers & Consumers** | None implemented. |
| **Repository Baseline** | Empty repository with zero application code. |

---

## 2. Intended Technologies

> [!IMPORTANT]
> The messaging technologies listed below originate from the initial project brief. They are **INTENDED** and **NOT confirmed as needed**. They must be validated against concrete system throughput and reliability requirements before adoption.

| Technology | Intended Role | Confirmation Status |
| :--- | :--- | :--- |
| **RabbitMQ** | Message queuing for reliable asynchronous task processing and inter-service messaging | `INTENDED — NOT CONFIRMED AS NEEDED` |
| **Redis** | In-memory pub/sub for real-time broadcast and ephemeral messaging | `INTENDED — NOT CONFIRMED AS NEEDED` |

---

## 3. Event System Decisions & Unknowns

All architectural design patterns, routing topologies, and resilience policies require explicit team decisions:

| Architecture Area | Status | Context & Potential Options |
| :--- | :--- | :--- |
| **Event Architecture Pattern** | `UNKNOWN — REQUIRES DECISION` | Options: Event-Driven Architecture (EDA), Command Query Responsibility Segregation (CQRS), Saga Pattern (orchestration vs. choreography) |
| **Queue Topology & Naming Conventions** | `NONE DEFINED` | Topic exchanges, direct queues, fanout exchanges, naming conventions (e.g., `domain.entity.event_type.version`) |
| **Event Schema Specification** | `NONE DEFINED` | Schema format (JSON Schema, Protocol Buffers, Avro), schema registry, event versioning strategy |
| **Dead Letter Queue (DLQ) Strategy** | `UNKNOWN — REQUIRES DECISION` | Failure thresholds, dead-letter routing keys, inspection tooling, manual replay procedures |
| **Retry & Backoff Policy** | `UNKNOWN — REQUIRES DECISION` | Retry count limits, exponential backoff with jitter, non-retryable error classifications, consumer idempotency mechanisms |
| **Message Delivery Semantics** | `UNKNOWN — REQUIRES DECISION` | At-least-once vs. at-most-once processing, deduplication storage (e.g., Redis idempotency keys) |

---

## 4. Agent Resumption Guide

For future development agents establishing the event system:

1. **Wait for Broker Confirmation**: Do not build producers or consumers until RabbitMQ or Redis pub/sub selection is formally confirmed.
2. **Define Contracts Before Code**: Define strict event schemas and payload types before writing NestJS microservice controllers or publishers.
3. **Establish Reliability First**: Plan Dead Letter Exchanges (DLX) and retry backoff policies concurrently with queue declarations.
4. **Ensure Idempotency**: Design consumer handlers to be idempotent to safeguard against duplicate message processing.
