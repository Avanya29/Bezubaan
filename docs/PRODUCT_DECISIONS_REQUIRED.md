# Product Decisions Required

> **Document Status**: RESOLVED  
> **Last Updated**: 2026-09-19  

## ✅ Resolved Decisions (Phase 3 Core Domain)

- **P1 — Authentication**: Both email/password and Google OAuth are supported.
- **P2 — Roles**: USER, VOLUNTEER, VETERINARIAN, NGO_ADMIN, ADMIN.
- **P3 — Anonymous Reporting**: Allowed. Anonymous capabilities limited by business rules.
- **P4 — Animal**: `species` (req), `breed` (opt), `sex` (opt), `approximateAge` (opt), `color` (opt), `size` (opt), `identifyingMarks` (opt), `name` (opt), `description` (opt).
- **P5 — Rescue Lifecycle**: Extensive explicit states from `REPORTED` through `CLOSED`/`CANCELLED`.
- **P6 — Rescue Report**: Required: `description`, `location`. Media is optional.
- **P7 — Location**: Store `latitude`, `longitude`, `address`. Spatial queries must be supported.
- **P8 — Media**: Dedicated Media relation/table. Metadata stored separately from binaries.
- **P9 — AI + Human Verification**: AI and human verification are explicitly distinguishable. AI is not treated as truth.
- **P10 — PII**: Separate core `User` identity from `UserProfile`/PII.
- **P11 — Medical History**: Belongs to `Animal` lifetime, optionally references a `RescueCase`.
