# Observability & Monitoring

> **Last Updated**: 2026-09-19  
> **Status**: NO IMPLEMENTATION EXISTS

---

## Current State

No observability exists. No application code exists. This document outlines what needs to be decided and implemented.

---

## Logging

| Aspect | Status |
|--------|--------|
| Log format | `UNKNOWN — REQUIRES DECISION` |
| Log levels | `UNKNOWN — REQUIRES DECISION` (likely: error, warn, info, debug, verbose) |
| Log aggregation | `UNKNOWN — REQUIRES DECISION` |
| Structured logging | `UNKNOWN — REQUIRES DECISION` (recommend JSON format) |
| Logger library | `UNKNOWN — REQUIRES DECISION` (NestJS built-in, Winston, Pino) |
| PII redaction | `UNKNOWN — REQUIRES DECISION` |

---

## Metrics

| Aspect | Status |
|--------|--------|
| Metrics collection | `UNKNOWN — REQUIRES DECISION` (Prometheus, StatsD, custom) |
| Dashboard | `UNKNOWN — REQUIRES DECISION` (Grafana, Datadog, CloudWatch) |
| Key metrics to track | `UNKNOWN — REQUIRES DECISION` |

### Potential Metrics (SPECULATIVE)
- Request rate / latency / error rate (RED metrics)
- Database query performance
- Queue depth (RabbitMQ)
- Cache hit/miss ratio (Redis)
- AI inference latency
- Active WebSocket connections

> The above metrics are SPECULATIVE and will depend on what features are implemented.

---

## Tracing

| Aspect | Status |
|--------|--------|
| Distributed tracing | `UNKNOWN — REQUIRES DECISION` (OpenTelemetry recommended) |
| Request correlation IDs | `UNKNOWN — REQUIRES DECISION` |
| Cross-service tracing (NestJS ↔ FastAPI) | `UNKNOWN — REQUIRES DECISION` |

---

## Health Checks

| Service | Status |
|---------|--------|
| API health endpoint (`/health`) | ❌ Not implemented |
| Database health | ❌ Not implemented |
| Redis health | ❌ Not implemented |
| RabbitMQ health | ❌ Not implemented |
| FastAPI AI service health | ❌ Not implemented |

> NestJS has `@nestjs/terminus` for health checks. FastAPI has built-in health check patterns.

---

## Alerting

| Aspect | Status |
|--------|--------|
| Alert rules | `UNKNOWN — REQUIRES DECISION` |
| Alert channels | `UNKNOWN — REQUIRES DECISION` (email, Slack, PagerDuty) |
| On-call rotation | `UNKNOWN — REQUIRES DECISION` |
| Incident response process | `UNKNOWN — REQUIRES DECISION` |

---

## Notes for Implementation

- NestJS supports `@nestjs/terminus` for health checks
- OpenTelemetry has SDKs for both Node.js and Python
- Consider correlation ID middleware from day one
- Structured JSON logging enables better log aggregation
