# Skills

## Repository Understanding
- Read existing architecture before modifying it.
- Identify entry points.
- Identify dependencies.
- Identify configuration.
- Identify testing strategy.

## Android / Kotlin / Compose
- Kotlin 2.4.20 with KSP (not KAPT)
- Jetpack Compose with BOM 2026.08.00
- Material 3 1.4.0 with custom Neo-Brutalist design system
- Type-safe Navigation Compose 2.10.0 with @Serializable routes
- Hilt 2.60.1 for dependency injection
- Room 2.8.4 for local persistence
- DataStore 1.2.1 for preferences
- Retrofit 3.0.0 + OkHttp 5.5.0 for networking
- kotlinx.serialization 1.11.0
- Coil 3.6.2 (io.coil-kt.coil3) for image loading
- Firebase BOM 34.18.0 for FCM (no -ktx artifacts)
- Google Maps Compose 8.6.0
- Google Places SDK 5.3.0
- AGP 9.4.0, Gradle 9.7.1, compileSdk/targetSdk 36

## Neo-Brutalist UI Design
- Thick black borders (2-4dp)
- Offset shadows (4-6dp)
- Bold typography (Poppins display + Inter body)
- Large CTAs and touch targets
- Flat colors with strong contrast
- Rounded but geometric components
- Warm earthy palette (orange, green, yellow, cream)

## Coding
- Follow existing project conventions.
- Prefer small, maintainable changes.
- Avoid unnecessary refactoring.
- Reuse existing utilities.
- MVVM + Clean Architecture strictly.

## Debugging
- Reproduce the problem.
- Read the actual error.
- Identify root cause.
- Apply the smallest reliable fix.
- Re-run the failing test.

## Testing
- Unit tests (ViewModel, UseCase, Repository)
- Compose UI tests
- Navigation tests
- Integration tests

## Security
- Never hardcode secrets.
- JWT authentication with secure token storage.
- Role-based UI (backend authoritative).
- Permission handling (location, camera, notifications).

## Git
- Inspect git status before changes.
- Keep changes logically grouped.
- Never discard unrelated user changes.
- Review diff before completion.

## Documentation
- Update documentation when behavior changes.
- Record architectural decisions.
- Keep agent state synchronized.
