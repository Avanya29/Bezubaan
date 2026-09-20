# Security Architecture

# Security Architecture

> [!NOTE]
> **Repository State Notice**: Auth (Phase 4) has been implemented. Security controls like JWT verification, password hashing, and role-based guards are now active.

---

## 1. Current State

| Security Area | Current Status |
| :--- | :--- |
| **Security Controls** | Helmet, CORS, and Class-Validator active. |
| **Authentication / Authorization Code** | JWT Bearer, Google OAuth, Local (Bcrypt) implemented. |
| **Secrets / Credential Configuration** | .env isolated. Secrets managed via ConfigModule. |
| **Security Testing & Scans** | Unit tests cover auth validation. No automated DAST yet. |

---

## 2. Sensitive Data Categories

| Data Category | Description & Context | Risk Profile |
| :--- | :--- | :--- |
| **User PII** | Phone numbers, email addresses. | Segregated into `UserProfile` and not exposed in public DTOs. |
| **Password Hashes** | User passwords. | Bcrypt hashed (Cost: 10), never logged, stripped from response payloads. |

---

## 3. Implemented Security Decisions

- **Authentication Strategy**: Local (Email/Bcrypt) + Google OAuth2.0
- **Token Strategy**: JWT (JSON Web Token) via Bearer Authorization Header.
- **Authorization Model**: Role-Based Access Control (RBAC) using NestJS `RolesGuard`.
- **Secret Management**: Environment variables `.env` parsed by `@nestjs/config`.
- **Input Validation**: `@nestjs/common` `ValidationPipe` with `class-validator` (whitelist: true).
- **HTTP Security**: `helmet` is active.

---

## 4. Agent Resumption Guide

For future development agents implementing business logic:
1. Use `@UseGuards(JwtAuthGuard)` to protect endpoints.
2. Use `@Roles('ADMIN', 'VOLUNTEER')` alongside `RolesGuard` to restrict access.
3. Use `@CurrentUser()` to extract the validated token payload.
4. Never expose `passwordHash` or `googleId` in API responses.
