# Phase 7 Product Decisions Required

> **Document Status**: RESOLVED / CONFIRMED  
> **Last Updated**: 2026-09-20  

This document serves as the formal decision gate for Phase 7 (Volunteer + Rescue Coordination). All requirements below have been confirmed by the Product Owner.

---

### A. Volunteer Profile
- **Status**: **CONFIRMED**
- **Required Fields**: `userId`, `approvalStatus`, `availabilityStatus`, `serviceArea/city`, at least one `capability/skill`.
- **Optional Fields**: `bio`, `experience`, `profile image`, `emergency contact`, `preferred rescue types`, `current latitude/longitude`, `additional notes`.

### B. Volunteer Eligibility
- **Status**: **CONFIRMED**
- **Rules**: Any authenticated USER can apply. NGO_ADMIN or ADMIN must approve. After approval, the user receives the VOLUNTEER role.

### C. Volunteer Availability
- **Status**: **CONFIRMED**
- **Rules**: Volunteers use an explicit AVAILABLE / UNAVAILABLE toggle. No schedule-based time windows for initial implementation.

### D. Volunteer Location
- **Status**: **CONFIRMED**
- **Rules**: Volunteers provide current location. NO continuous/background GPS tracking. Location is collected when toggling AVAILABLE, accepting an assignment, or explicitly refreshing.

### E. Volunteer Skills/Capabilities
- **Status**: **CONFIRMED**
- **Rules**: Required. Initial skills: Animal Handling, Basic First Aid, Dog Rescue, Cat Rescue, Bird Rescue, Large Animal Rescue, Wildlife Rescue, Transport, Foster Support. Minimum 1 capability required.

### F. Volunteer Capacity
- **Status**: **CONFIRMED**
- **Rules**: Maximum 2 simultaneous active assignments per volunteer by default. NGO_ADMIN/ADMIN can explicitly override this limit (must be audited).

### G. Rescue Matching
- **Status**: **CONFIRMED**
- **Rules**: HYBRID model. System ranks eligible candidates based on: Volunteer eligibility/approval, Availability, Distance, Skill match, and Current active workload. NGO_ADMIN/ADMIN makes the final assignment. No AI assignment allowed.

### H. Assignment
- **Status**: **CONFIRMED**
- **Rules**: NGO_ADMIN/ADMIN assigns. Multiple volunteers may support the same rescue, but only ONE active PRIMARY volunteer exists at a time.

### I. Acceptance/Rejection
- **Status**: **CONFIRMED**
- **Rules**: Volunteers can explicitly ACCEPT or REJECT. Rejection requires a reason. Acceptance timeout is 15 minutes, after which it expires and returns to VOLUNTEER_SEARCH.

### J. Reassignment
- **Status**: **CONFIRMED**
- **Rules**: NGO_ADMIN/ADMIN reassigns. Allowed on rejection, timeout, volunteer unavailability, drop-off, or operational reasons. Previous assignment is closed (REJECTED, EXPIRED, CANCELLED, REASSIGNED) and preserved for audit.

### K. Cancellation
- **Status**: **CONFIRMED**
- **Rules**: NGO_ADMIN/ADMIN can cancel. Allowed from REPORTED up to RESCUE_IN_PROGRESS. Cannot be cancelled after RESCUED. Cancellation reason required.

### L. Escalation
- **Status**: **CONFIRMED**
- **Rules**: Unaccepted assignments escalate after 15 minutes to NGO_ADMIN/ADMIN operational queue and return to VOLUNTEER_SEARCH.

### M. Rescue Lifecycle Transitions
- **Status**: **CONFIRMED**
- **Exact Graph**:
  - `REPORTED` → `UNDER_REVIEW` (Admin) / `CANCELLED`
  - `UNDER_REVIEW` → `AI_ANALYZING` (System) / `PENDING_VERIFICATION` / `CANCELLED`
  - `AI_ANALYZING` → `PENDING_VERIFICATION` (System) / `CANCELLED`
  - `PENDING_VERIFICATION` → `VERIFIED` (Admin/Vet) / `REJECTED` (Admin/Vet) / `CANCELLED`
  - `VERIFIED` → `VOLUNTEER_SEARCH` (Admin) / `CANCELLED`
  - `REJECTED` → `CLOSED`
  - `VOLUNTEER_SEARCH` → `VOLUNTEER_ASSIGNED` (Admin) / `CANCELLED`
  - `VOLUNTEER_ASSIGNED` → `VOLUNTEER_ACCEPTED` (Volunteer) / `VOLUNTEER_SEARCH` (Volunteer/Admin) / `CANCELLED`
  - `VOLUNTEER_ACCEPTED` → `ON_THE_WAY` (Volunteer) / `VOLUNTEER_SEARCH` / `CANCELLED`
  - `ON_THE_WAY` → `RESCUE_IN_PROGRESS` (Volunteer) / `VOLUNTEER_SEARCH` / `CANCELLED`
  - `RESCUE_IN_PROGRESS` → `RESCUED` (Volunteer/Admin) / `CANCELLED`
  - `RESCUED` → `AT_VET` (Admin/Vet) / `READY_FOR_ADOPTION` / `RELEASED`
  - `AT_VET` → `UNDER_TREATMENT` (Vet) / `RECOVERING` (Vet)
  - `UNDER_TREATMENT` → `RECOVERING` (Vet)
  - `RECOVERING` → `READY_FOR_ADOPTION` (Vet/Admin) / `RELEASED` (Vet/Admin)
  - `READY_FOR_ADOPTION` → `ADOPTED` (Admin) / `FOSTERED` (Admin) / `RELEASED` (Admin)
  - `FOSTERED` → `ADOPTED` (Admin) / `RELEASED` (Admin)
  - `ADOPTED` → `CLOSED` (Admin)
  - `RELEASED` → `CLOSED` (Admin)
- **Automatic Transitions**: `UNDER_REVIEW` → `AI_ANALYZING`, `AI_ANALYZING` → `PENDING_VERIFICATION`, and Assignment Timeout (15m).

### N. Location Visibility & Privacy
- **Status**: **CONFIRMED**
- **Exact Coordinates**: Reporter, Assigned Volunteer (only AFTER accepting assignment), NGO_ADMIN, ADMIN, VETERINARIAN (when directly involved).
- **Approximate Location**: Public users, unauthenticated users, unassigned volunteers, volunteers prior to acceptance.

### O. Notifications
- **Status**: **CONFIRMED**
- **Mandatory Triggers**: New assignment, acceptance/rejection, expiry, escalation, verification/rejection, reassignment, cancellation, major status changes, medical updates.
- **Channels**: Push, In-app, Email (for major operational events).

### P. Event/Workflow Triggers
- **Status**: **CONFIRMED**
- **RabbitMQ Integration**: Required for async processing of timeouts, escalations, and notifications. Core rescue state remains synchronous in NestJS. Event payloads are standardized with `eventId`, `eventType`, `correlationId`, etc.
