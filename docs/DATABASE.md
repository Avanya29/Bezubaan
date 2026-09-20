# Database Architecture

> **Document Status**: DRAFT / SPECIFICATION  
> **Last Updated**: 2026-09-19  
> **Notice**: No database instance, schema, or configuration currently exists in the repository. All entity references below are speculative domain concepts that require formal product confirmation.

---

## 1. Current State

| Component | Status | Details |
| :--- | :--- | :--- |
| **Database Instance** | NONE | No active or provisioned database exists |
| **Database Engine** | UNCONFIGURED | PostgreSQL is the intended engine |
| **ORM / Query Tool** | UNINITIALIZED | Prisma is the intended ORM (no `schema.prisma` exists) |
| **Schema Definition** | NONE | No tables, views, types, or relations have been defined |
| **Migrations** | NONE | No migration files or migration history exist |
| **Seed Scripts** | NONE | No database seed scripts or mock datasets exist |
| **Connection Configuration** | NONE | No database connection strings or environment variables exist |

---

## 2. Intended Database & ORM Stack

- **Intended Database**: PostgreSQL
- **Intended ORM**: Prisma
- **Adoption Note**: These technologies are intended based on the initial project brief; they are not yet confirmed or configured.

---

## 3. Schema Status

`UNKNOWN — REQUIRES ENTITY DESIGN`

No database schema has been designed, validated, or implemented.

---

## 4. Potential Entities (Domain Speculation)

> [!CAUTION]
> The entities listed below are speculative concepts derived solely from the animal rescue domain. **DO NOT INVENT** fields, foreign keys, or database tables until formal product specifications are approved.

| Candidate Entity | Status | Context |
| :--- | :--- | :--- |
| **Users** | `SPECULATIVE — NOT CONFIRMED. Requires product requirements.` | Candidate entity for platform actors (volunteers, rescuers, vets, admins, reporters) |
| **Animals** | `SPECULATIVE — NOT CONFIRMED. Requires product requirements.` | Candidate entity for animals undergoing rescue, treatment, or shelter tracking |
| **Rescues** | `SPECULATIVE — NOT CONFIRMED. Requires product requirements.` | Candidate entity for rescue missions, operational status, and assignments |
| **Reports** | `SPECULATIVE — NOT CONFIRMED. Requires product requirements.` | Candidate entity for distress/incident reports submitted by public or team |
| **Locations** | `SPECULATIVE — NOT CONFIRMED. Requires product requirements.` | Candidate entity for incident coordinates, shelter sites, and geo-tracking |
| **Notifications** | `SPECULATIVE — NOT CONFIRMED. Requires product requirements.` | Candidate entity for alert logs and responder updates |

---

## 5. Migrations and Version Control

- **Current Migrations**: None exist.
- **Tooling**: `TBD — REQUIRES TEAM DECISION` (Intended: Prisma Migrate).
- **Execution Strategy**: `TBD — REQUIRES TEAM DECISION`.

---

## 6. Seeding and Test Fixtures

- **Current Seeding**: None exists.
- **Seed Data Strategy**: `TBD — REQUIRES TEAM DECISION`.

---

## 7. Connection Configuration

- **Current Status**: Not configured.
- **Environment Setup**: `UNKNOWN — REQUIRES DECISION` (Connection string conventions and credential management to be decided).

---

## 8. Resumption Guide for Future Agents

When resuming work on the database:
1. Do not create schema files or tables until a formal Product Requirements Document specifies domain models.
2. Confirm the ORM choice (Prisma) and database engine (PostgreSQL) with the team.
3. Once approved, initialize the Prisma schema and create initial baseline migrations.
