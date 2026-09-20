# Product Requirements Document (PRD)

> **Document Status**: DRAFT / INITIAL DISCOVERY  
> **Last Updated**: 2026-09-19  
> **Constraint Notice**: In accordance with strict anti-hallucination rules, all requirements below that are not explicitly provided by the stakeholders are marked as `UNKNOWN — REQUIRES DECISION` or `PROPOSED / DISCUSSION`.

## 1. Product Purpose
- **FR-001**: **Core Platform Purpose**  
  *Description*: The system shall act as an AI-assisted animal rescue and welfare platform.  
  *Status*: **CONFIRMED REQUIREMENT**

## 2. Functional Requirements (FR)

### 2.1 Users, Profiles, and Authentication
- **FR-002**: **Authentication Strategy**  
  *Description*: The system shall support user authentication via Email/Password and Google OAuth.  
  *Status*: **CONFIRMED REQUIREMENT**
- **FR-003**: **User Roles**  
  *Description*: The system shall support distinct actor roles: USER, VOLUNTEER, VETERINARIAN, NGO_ADMIN, ADMIN.  
  *Status*: **CONFIRMED REQUIREMENT**
- **FR-004**: **User Profiles**  
  *Description*: The system shall store profile data isolated from the core identity model.  
  *Status*: **CONFIRMED REQUIREMENT**

### 2.2 Animal Reporting & Rescue
- **FR-005**: **Animal Reporting Flow**  
  *Description*: Users (and anonymous reporters) can report animals in distress. Location and Description are mandatory.  
  *Status*: **CONFIRMED REQUIREMENT**
- **FR-006**: **Rescue Case Lifecycle**  
  *Description*: Rescue reports shall transition through strict state machine statuses (e.g. REPORTED -> AT_VET -> CLOSED).  
  *Status*: **CONFIRMED REQUIREMENT**
- **FR-007**: **Location Tracking**  
  *Description*: Rescue cases shall store geographic location data via Latitude, Longitude, and Address strings, indexed for spatial query support.  
  *Status*: **CONFIRMED REQUIREMENT**

### 2.3 AI Assistance & Human Verification
- **FR-008**: **AI Triage Integration**  
  *Description*: The system shall analyze incoming rescue data. AI output is stored distinctly from verified truth.  
  *Status*: **CONFIRMED REQUIREMENT**
- **FR-009**: **Human-in-the-Loop Verification**  
  *Description*: AI triage decisions must be explicitly verified by a human administrator, logged distinctly in the system.  
  *Status*: **CONFIRMED REQUIREMENT**

### 2.4 Volunteers & Vets
- **FR-010**: **Volunteer Matching & Coordination**  
  *Description*: The system shall match and dispatch volunteers based on location and capability.  
  *Status*: **UNKNOWN — REQUIRES DECISION**
- **FR-011**: **Veterinary Integration**  
  *Description*: Vets shall have a dedicated portal/role to update medical statuses.  
  *Status*: **UNKNOWN — REQUIRES DECISION**

### 2.5 Animal Profiles, Medical, Adoption, and Foster
- **FR-012**: **Animal Entity Tracking**  
  *Description*: The system shall track individual animal profiles (species required).  
  *Status*: **CONFIRMED REQUIREMENT**
- **FR-013**: **Medical Records**  
  *Description*: The system shall log medical treatments tied to the Animal's lifetime, optionally referencing a Rescue Case.  
  *Status*: **CONFIRMED REQUIREMENT**
- **FR-014**: **Adoption & Foster Workflow**  
  *Description*: Rescued animals can be listed for adoption or fostering.  
  *Status*: **UNKNOWN — REQUIRES DECISION**

### 2.6 Community, Lost/Found, Donations, and Campaigns
- **FR-015**: **Lost/Found Portal**  
  *Description*: Public users can list lost or found pets.  
  *Status*: **UNKNOWN — REQUIRES DECISION**
- **FR-016**: **Community Features**  
  *Description*: Social feeds, commenting, or community interaction features.  
  *Status*: **UNKNOWN — REQUIRES DECISION**
- **FR-017**: **Donations & Payment Integration**  
  *Description*: The system shall accept and track monetary donations.  
  *Status*: **UNKNOWN — REQUIRES DECISION**
- **FR-018**: **Fundraising Campaigns**  
  *Description*: NGOs/Admins can run specific campaigns for cases.  
  *Status*: **UNKNOWN — REQUIRES DECISION**

### 2.7 Operations: Notifications, NGO/Admin, Moderation
- **FR-019**: **Notification System**  
  *Description*: The system shall dispatch push/email notifications for specific events.  
  *Status*: **UNKNOWN — REQUIRES DECISION**
- **FR-020**: **NGO / Admin Dashboard**  
  *Description*: Central dashboard for administrative oversight and reporting.  
  *Status*: **UNKNOWN — REQUIRES DECISION**
- **FR-021**: **Moderation Capabilities**  
  *Description*: Tools to flag, review, and remove inappropriate content or spam.  
  *Status*: **UNKNOWN — REQUIRES DECISION**

## 3. Non-Functional Requirements (NFR)

### 3.1 Privacy & Security
- **NFR-001**: **Data Privacy (PII)**  
  *Description*: How user locations and personal identities are protected.  
  *Status*: **UNKNOWN — REQUIRES DECISION**
- **NFR-002**: **Security & RBAC**  
  *Description*: Strict endpoint protection based on user roles.  
  *Status*: **PROPOSED / DISCUSSION**

### 3.2 Auditability & AI Safety
- **NFR-003**: **System Auditability**  
  *Description*: All state transitions on a Rescue Case must log an audit trail (who, what, when).  
  *Status*: **PROPOSED / DISCUSSION**
- **NFR-004**: **AI Safety & Hallucination Guardrails**  
  *Description*: AI responses must be bounded, and confidence scores explicitly recorded.  
  *Status*: **PROPOSED / DISCUSSION**
