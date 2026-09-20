# Domain Glossary

> **Document Status**: DRAFT / INITIAL DISCOVERY  
> **Last Updated**: 2026-09-19  

To ensure a ubiquitous language across the development team and AI agents, the following terms are defined. All definitions are currently **CONFIRMED REQUIREMENT**.

### Entities
- **RescueCase**: A single instance of an animal distress report, tracking its entire lifecycle from initial report to resolution. (Status: CONFIRMED)
- **Animal**: The physical animal associated with one or more RescueCases. (Status: CONFIRMED)
- **Reporter**: The user (authenticated or anonymous) who submits a RescueCase. (Status: CONFIRMED)
- **Volunteer**: A vetted user authorized to accept dispatch requests and handle field rescues. (Status: CONFIRMED)
- **Veterinarian (Vet)**: A specialized user authorized to update medical records for an Animal. (Status: CONFIRMED)
- **NGO Admin**: A high-privilege user managing the rescue organization, approving AI triages, and managing roles. (Status: CONFIRMED)

### Concepts
- **AI Triage**: The process of using LLMs and Vision models to assess an incoming report for severity, species, and required equipment. (Status: CONFIRMED)
- **Dispatch**: The act of assigning a Volunteer to a RescueCase. (Status: CONFIRMED)
- **Foster**: Temporary custody of an Animal post-recovery. (Status: CONFIRMED)
- **Adoption**: Permanent transfer of custody of an Animal to a public user. (Status: CONFIRMED)
