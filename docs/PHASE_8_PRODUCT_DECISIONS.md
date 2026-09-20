# Phase 8 Product Decisions
# Medical + Adoption + Foster

> **Status**: CONFIRMED

## A. MEDICAL RECORDS
1. **Required Fields**: `animalId`, `treatmentDate`, `notes`. `vetId` is required if created by a veterinarian. `rescueCaseId`, `diagnosis`, `medications/treatment details`, and `cost` are optional.
2. **Attachments**: YES. Supported examples include X-rays, lab reports, prescriptions. Max size: 10 MB per file. Multiple attachments allowed. Reuses existing `Media` abstraction.
3. **Historical Records**: APPEND-ONLY for normal users and Veterinarians. Vets cannot delete or modify historical records; corrections require a new addendum. ADMIN may perform auditable administrative corrections.
4. **Access Control**: Public, normal USER, reporting user, and unauthorized VOLUNTEER have NO access to medical records. Assigned VETERINARIAN, NGO_ADMIN, and ADMIN have access. Other unassigned vets have NO access.

## B. VETERINARY WORKFLOW
5. **Veterinarian Eligibility**: Existing `VETERINARIAN` role is retained. Users must apply for access. NGO_ADMIN/ADMIN approval is required. Optional credential/license info may be stored.
6. **Authorization**: VETERINARIAN may update/view records ONLY if associated with the relevant Animal's medical workflow (via RescueCase or explicit assignment).
7. **Recovery Confirmation**: VETERINARIAN, NGO_ADMIN, and ADMIN can confirm recovery state (`RECOVERING`, `READY_FOR_ADOPTION`, `RELEASED`). AI cannot perform these transitions.

## C. MEDICAL STATUS
8. **Valid Transitions**: 
   - `AT_VET` -> `UNDER_TREATMENT` / `RECOVERING` (Vet, NGO_Admin, Admin)
   - `UNDER_TREATMENT` -> `RECOVERING` (Vet, NGO_Admin, Admin)
   - `RECOVERING` -> `READY_FOR_ADOPTION` / `RELEASED` (Vet, NGO_Admin, Admin)
   - `READY_FOR_ADOPTION` -> `ADOPTED` / `FOSTERED` / `RELEASED` (NGO_Admin, Admin)
   - `FOSTERED` -> `ADOPTED` / `RELEASED` (NGO_Admin, Admin)

## D. ADOPTION
9. **Eligibility**: Any authenticated USER/VOLUNTEER may apply. No separate ADOPTER role. Animal must be eligible, and if already ADOPTED, cannot accept new applications.
10. **Application Process**: Required: `applicantId`, `animalId`, `motivation`, `housingType`, `householdInformation`, `previousPetExperience`, `existingAnimals`, `contactInformation`, `applicationStatus`. Optional: notes, media.
11. **Approval Workflow**: NGO_ADMIN or ADMIN reviews and approves. Decisions are auditable.
12. **Status Changes**: Multiple applications can pend. When one is APPROVED, the animal is reserved, and all other pending applications for that animal are auto-REJECTED transactionally.

## E. FOSTER
13. **Foster Eligibility**: NO separate FOSTER role. Available to authenticated USERs/VOLUNTEERs.
14. **Application**: Required: `applicantId`, `animalId`, `reason`, `housingInformation`, `householdInformation`, `existingAnimals`, `priorAnimalCareExperience`, `availability`, `applicationStatus`. Optional: notes, media.
15. **Duration**: Time-bound. Requires `startDate` and `expectedEndDate`. Default is 14 days. NGO_ADMIN/ADMIN can approve different dates or extend. Expiry creates a follow-up, never auto-adoption.

## F. NOTIFICATIONS & EVENTS
16. **Medical Events**: Sent on `medical.record.created`, `medical.status.changed`, `medical.followup.required`, `animal.recovery.confirmed`, `animal.ready_for_adoption`. Sent to Assigned Vet, NGO_Admin/Admin, and assigned volunteer (if necessary). Channels: In-app, Push, Email.
17. **Adoption/Foster Events**: Sent on app creation, approval, rejection, cancellation, completion, extension, expiring. Sent to Applicant/Fosterer, NGO_Admin/Admin. Channels: In-app, Push, Email (for approvals/lifecycle).
