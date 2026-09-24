# API Contract

> **Document Status**: ACTIVE  
> **Last Updated**: 2026-09-19  

---

## 1. Current State

| Aspect | Current Status | Notes |
| :--- | :--- | :--- |
| **Active Endpoints** | AUTH & USERS | Phase 4 Auth + User endpoints implemented |
| **Controllers / Handlers** | IMPLEMENTED | `AuthController`, `UsersController` |
| **Routing Layer** | NESTJS | Standard NestJS controllers |
| **API Documentation** | SWAGGER | Swagger enabled at `/api` prefix |

---

## 2. API Design & Protocol Decisions

- **API Style**: REST via NestJS
- **Base URL**: `http://localhost:3000/api`
- **Versioning Strategy**: TBD (Currently unversioned `/api`)
- **Authentication**: JWT Bearer Token + Google OAuth2.0
- **Rate Limiting**: `UNKNOWN — REQUIRES DECISION`
- **Pagination**: `UNKNOWN — REQUIRES DECISION`
- **Error Format**: NestJS Global Exception Filter

---

## 3. Endpoint Specifications

### 3.1 Authentication & User Endpoints

#### `POST /api/auth/register`
- **Description**: Registers a new user with Email/Password.
- **Authentication**: None.
- **Request Body**: `RegisterDto` (email, password).
- **Success Response**: `201 Created` with User object (minus passwordHash).
- **Error Responses**: `400 Bad Request` (Validation), `409 Conflict` (Duplicate email).

#### `POST /api/auth/login`
- **Description**: Authenticates user via local strategy.
- **Authentication**: LocalAuthGuard (Email/Password).
- **Request Body**: `LoginDto` (email, password).
- **Success Response**: `200 OK` with `{ access_token: string }`.
- **Error Responses**: `401 Unauthorized` (Invalid credentials).

#### `GET /api/auth/google`
- **Description**: Initiates Google OAuth2.0 flow.
- **Authentication**: None.

#### `GET /api/auth/google/callback`
- **Description**: Google OAuth2.0 callback endpoint.
- **Authentication**: GoogleOauthGuard.
- **Success Response**: `200 OK` with `{ access_token: string }` (Returns JSON for API).

#### `GET /api/users/me`
- **Description**: Retrieves current authenticated user profile.
- **Authentication**: `JwtAuthGuard`.
- **Success Response**: `200 OK` with full User and UserProfile object (minus passwordHash).

#### `PATCH /api/users/me/profile`
- **Description**: Updates user's PII (phone number, address).
- **Authentication**: `JwtAuthGuard`.
- **Request Body**: `UpdateProfileDto` (phoneNumber, address).
- **Success Response**: `200 OK` with updated UserProfile.

### 3.2 Animal Rescue & Incident Reporting Endpoints

#### `POST /api/rescues`
- **Description**: Creates a new rescue report.
- **Authentication**: `OptionalJwtAuthGuard` (Anonymous reporting allowed).
- **Request Body**: `CreateRescueDto` (description, latitude, longitude, address, optional animal details).
- **Success Response**: `201 Created` with RescueCase object (status initialized to `REPORTED`).
- **Error Responses**: `400 Bad Request` (Validation).

#### `GET /api/rescues`
- **Description**: Lists rescue cases.
- **Authentication**: `JwtAuthGuard`, `RolesGuard(ADMIN)`.
- **Note**: Currently restricted to ADMIN ONLY due to UNRESOLVED location privacy business rules (BR-004).

### 3.3 Animal Tracking & Welfare Endpoints

#### `POST /api/animals`
- **Description**: Creates a standalone animal profile.
- **Authentication**: `JwtAuthGuard`, `RolesGuard(VOLUNTEER, VETERINARIAN, NGO_ADMIN, ADMIN)`.
- **Request Body**: `CreateAnimalDto` (species required).
- **Success Response**: `201 Created` with Animal object.

#### `GET /api/animals/:id`
- **Description**: Retrieves a specific animal profile.
- **Authentication**: None (Public access).
- **Success Response**: `200 OK` with Animal object.
- **Error Responses**: `404 Not Found`.

#### `PATCH /api/animals/:id`
- **Description**: Updates an animal profile.
- **Authentication**: `JwtAuthGuard`, `RolesGuard(VOLUNTEER, VETERINARIAN, NGO_ADMIN, ADMIN)`.
- **Request Body**: `UpdateAnimalDto`.
- **Success Response**: `200 OK`.

### 3.4 AI / ML Services (Internal Contract)

#### `POST /api/v1/triage` (FastAPI)
- **Description**: Internal-only endpoint that analyzes a rescue incident.
- **Authentication**: VPC Internal Only (Called by NestJS).
- **Request Body**: 
  - `rescue_id` (string)
  - `description` (string)
  - `location` (lat/lng object)
  - `image_url` (optional string)
  - `animal_info` (optional species object)
  - `correlation_id` (string)
- **Success Response**: `200 OK` with structured `TriageResponse` (observations, preliminary_assessment, severity_estimate, recommended_actions, safety_warnings, model_metadata, nearby_veterinary_help, recommended_volunteers).
- **Failure Handling**: NestJS intercepts all 4xx/5xx/Timeouts from this endpoint and injects a safe fallback response to prevent rescue workflow failure.

### 3.5 Notifications Endpoints
`NO ENDPOINTS EXIST YET`

### 3.6 Geolocation & Mapping Endpoints
`NO ENDPOINTS EXIST YET`

---

## 4. Standard Response Formats

NestJS defaults with custom Global Exception Filter for standardized error format:
```json
{
  "statusCode": 400,
  "timestamp": "2026-09-19T00:00:00.000Z",
  "path": "/api/endpoint",
  "method": "POST",
  "message": "Error details"
}
```
