# Business Rules

> **Document Status**: DRAFT / INITIAL DISCOVERY  
> **Last Updated**: 2026-09-19  

This document defines the strict business logic rules that must be enforced by the backend systems.

## Operational Rules (Unverified)

- **BR-001**: **Rescue State Transitions**  
  *Description*: A rescue case cannot be marked as "Completed" unless a verified volunteer or vet has signed off.  
  *Status*: **PROPOSED / DISCUSSION**
- **BR-002**: **AI Autonomy Limits**  
  *Description*: The AI service may suggest triage priorities, but cannot automatically dispatch volunteers without human approval or predefined fallback thresholds.  
  *Status*: **PROPOSED / DISCUSSION**
- **BR-003**: **Role Isolation**  
  *Description*: A standard user cannot transition their own account to a "Veterinarian" or "NGO Admin" role without manual verification from an existing Admin.  
  *Status*: **UNKNOWN — REQUIRES DECISION**
- **BR-004**: **Location Privacy**  
  *Description*: Exact GPS coordinates of a reporter are only visible to assigned volunteers and admins, not to the general public.  
  *Status*: **UNKNOWN — REQUIRES DECISION**
- **BR-005**: **Donation Escrow**  
  *Description*: Donations tied to a specific campaign must be tracked independently from general NGO funds.  
  *Status*: **UNKNOWN — REQUIRES DECISION**
- **BR-006**: **State Transition Validity**  
  *Description*: Rescue workflows must define state-transition logic before implementation. Not all transitions are valid.  
  *Status*: **CONFIRMED REQUIREMENT**
- **BR-007**: **AI Output Boundaries**  
  *Description*: AI triage output must never be treated as verified truth.  
  *Status*: **CONFIRMED REQUIREMENT**
- **BR-008**: **Anonymous Capabilities Limits**  
  *Description*: Anonymous reporting is allowed, but capabilities must be limited by strict business rules.  
  *Status*: **CONFIRMED REQUIREMENT**
