# Risk Register

## Technical Risks

| ID | Risk | Likelihood | Impact | Mitigation | Status |
|----|------|-----------|--------|------------|--------|
| R1 | No product requirements document exists | HIGH | HIGH | Create OPEN_QUESTIONS.md, get answers before Phase 1 | OPEN |
| R2 | Multiple AI agents may make conflicting decisions | MEDIUM | HIGH | Documentation system, DECISIONS.md, AGENT_HANDOFF.md | MITIGATED |
| R3 | .gitignore deleted from working tree | LOW | LOW | Restore in Phase 0 setup | OPEN |
| R4 | Technology stack may be over-engineered for MVP | MEDIUM | MEDIUM | Validate each technology before adding as dependency | OPEN |
| R5 | AI/ML service costs may exceed budget | MEDIUM | HIGH | Unknown until LLM/Vision provider chosen | OPEN |
| R6 | No team member expertise information available | MEDIUM | MEDIUM | Document team capabilities | OPEN |
| R7 | Scope creep from broad technology list | HIGH | HIGH | Strict MVP definition needed | OPEN |

## Security Risks

| ID | Risk | Likelihood | Impact | Mitigation | Status |
|----|------|-----------|--------|------------|--------|
| S1 | No security architecture defined | HIGH | HIGH | Define in Phase 0/1 | OPEN |
| S2 | Sensitive animal location data exposure | MEDIUM | HIGH | Implement access controls, data classification | OPEN |
| S3 | AI model prompt injection | MEDIUM | MEDIUM | Input sanitization, prompt engineering best practices | OPEN |
