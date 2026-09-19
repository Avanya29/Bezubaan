# Architecture Decisions

## Decision 001 — Package Name & Module Structure

Date: 2026-09-11
Phase: 0

### Problem
Choosing package name and whether to use multi-module or single-module architecture.

### Options Considered
1. Multi-module (core/, feature/, app/) — Better separation, slower to scaffold
2. Single module with package-based Clean Architecture — Faster delivery, can modularize later
3. Hybrid (core module + app module) — Middle ground

### Decision
Single module with package-based Clean Architecture (`com.bezubaan.app`)

### Reason
- Greenfield project, fastest to scaffold
- Package-based separation provides the same logical boundaries
- Can be refactored to multi-module later without changing business logic
- All code is in one compilation unit (faster builds during development)

### Consequences
- All code compiles together (slightly slower incremental builds at scale)
- Must maintain discipline in package dependencies

### Do Not Reconsider Unless
The project grows beyond 500+ files or needs to share modules with other apps.

---

## Decision 002 — Serialization Library

Date: 2026-09-11
Phase: 0

### Problem
Choose between Moshi and kotlinx.serialization for JSON parsing.

### Options Considered
1. Moshi — Mature, well-tested
2. kotlinx.serialization — Native Kotlin, type-safe navigation support, KMP ready

### Decision
kotlinx.serialization 1.11.0

### Reason
- Navigation Compose 2.10.0 uses @Serializable for type-safe routes
- Retrofit 3.0.0 has native converter support
- No annotation processing needed (faster builds)
- Kotlin-first, KMP-compatible

### Consequences
- All DTOs use @Serializable annotation
- Must use kotlinx.serialization.json for custom parsing

### Do Not Reconsider Unless
A critical compatibility issue is found with Retrofit 3.0 converter.

---

## Decision 003 — Neo-Brutalist Design System

Date: 2026-09-11
Phase: 0

### Problem
How to implement the Neo-Brutalist visual identity within Material 3.

### Options Considered
1. Override Material 3 components entirely — Maximum control, more work
2. Wrap Material 3 components with Neo-Brutalist styling — Leverage M3, add visual layer
3. Build from scratch without Material 3 — Maximum freedom, lose M3 benefits

### Decision
Wrap Material 3 components with Neo-Brutalist styling via custom composables (NeoButton, NeoCard, etc.)

### Reason
- Material 3 provides accessibility, interaction states, and theming infrastructure
- Neo-Brutalist styling (borders, shadows, typography) can be applied as a visual layer
- Custom composables provide the distinctive identity while keeping M3 usability

### Consequences
- Every screen uses Neo* components, not raw M3 components
- Theme defines both M3 color scheme and extended Neo-Brutalist properties

### Do Not Reconsider Unless
M3 components fundamentally conflict with neo-brutalist rendering requirements.

---

## Decision 004 — Mock Data Strategy

Date: 2026-09-11
Phase: 0

### Problem
Backend (NestJS) is not yet available. Need to show functional UI.

### Options Considered
1. Hardcode data in ViewModels — Fast but messy
2. Create FakeDataSource implementations behind repository interfaces — Clean, swappable
3. Use mock server (MockWebServer) — More realistic but more setup

### Decision
FakeDataSource implementations behind repository interfaces, with a DI toggle.

### Reason
- Repository pattern already requires interfaces
- FakeDataSource provides realistic data flow through the full architecture
- Swapping to real backend = swap Hilt binding, no UI changes
- Can add MockWebServer for integration tests later

### Consequences
- Each repository interface has two implementations: Real + Fake
- Hilt module uses @Binds to select which implementation
- A BuildConfig flag or DI qualifier controls the selection

### Do Not Reconsider Unless
The real NestJS backend becomes available during development.

---

## Decision 005 — Font Selection

Date: 2026-09-11
Phase: 0

### Problem
Select typography pairing that conveys bold, confident, neo-brutalist identity while remaining readable.

### Options Considered
1. Poppins (display) + Inter (body) — Bold geometric + highly readable
2. Space Grotesk (display) + DM Sans (body) — More techy
3. Outfit (display) + Work Sans (body) — Softer

### Decision
Poppins (Bold/ExtraBold for display) + Inter (Regular/Medium for body)

### Reason
- Poppins Bold has the weight and personality for neo-brutalist headings
- Inter is one of the most readable screen fonts
- Both are free Google Fonts with comprehensive character support
- Hindi support available

### Consequences
- Font files must be bundled in res/font/
- Typography scale uses Poppins for display/headline, Inter for body/label

### Do Not Reconsider Unless
User explicitly requests different fonts.
