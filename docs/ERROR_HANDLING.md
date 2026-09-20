# Error Handling Strategy

> **Last Updated**: 2026-09-19  
> **Status**: NO IMPLEMENTATION EXISTS

---

## Current State

No error handling exists. No application code exists. This document outlines decisions that need to be made when implementation begins.

---

## Decisions Required

### Global Exception Handling

**Status**: `UNKNOWN — REQUIRES DECISION`

NestJS provides built-in exception filters. The following decisions are needed:

- Custom global exception filter vs. default behavior
- Error response format (proposed below, but NOT confirmed):

```json
{
  "statusCode": 400,
  "message": "Validation failed",
  "error": "Bad Request",
  "timestamp": "2026-09-19T12:00:00.000Z",
  "path": "/api/v1/animals"
}
```

> The above format is a **PROPOSAL**, not a decision.

---

### Validation Errors

**Status**: `UNKNOWN — REQUIRES DECISION`

- NestJS `class-validator` + `class-transformer` for DTO validation
- Validation pipe configuration (whitelist, transform, forbidNonWhitelisted)
- Error message formatting for validation failures

---

### Database Errors

**Status**: `UNKNOWN — REQUIRES DECISION`

- Prisma error handling (unique constraint violations, not found, etc.)
- Transaction error handling
- Connection failure handling

---

### External Service Errors

**Status**: `UNKNOWN — REQUIRES DECISION`

- Google Maps API failure handling
- Firebase Cloud Messaging failure handling
- LLM/Vision API failure handling
- File storage failure handling
- Circuit breaker pattern consideration
- Retry strategies for transient failures

---

### AI Service Errors

**Status**: `UNKNOWN — REQUIRES DECISION`

- FastAPI ↔ NestJS communication error handling
- LLM timeout handling
- Model inference failure fallbacks
- Rate limiting from AI providers

---

### Error Logging

**Status**: `UNKNOWN — REQUIRES DECISION`

- Log level per error type
- Structured logging format
- PII redaction in error logs
- Error aggregation and alerting

---

### Client-Facing vs. Internal Errors

**Status**: `UNKNOWN — REQUIRES DECISION`

- Which error details to expose to clients
- Internal error codes system
- Error message localization (if needed)

---

## Notes for Implementation

- NestJS has built-in `HttpException`, `ExceptionFilter`, and `ValidationPipe`
- Prisma throws typed exceptions (`PrismaClientKnownRequestError`, etc.)
- FastAPI has `HTTPException` and exception handlers
- Consider using a shared error code enum between services
