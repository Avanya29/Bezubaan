# Changelog

## 2026-11-18

### Added
- Added `NeoPasswordInput` composable in `NeoInputs.kt` with automatic bullet masking (`PasswordVisualTransformation`) and interactive show/hide password toggle button with eye icons (`Icons.Default.Visibility`/`VisibilityOff`).

### Fixed
- Fixed AI triage recommending Karnataka/Bangalore clinics instead of nearest local facilities: Replaced hardcoded fallback coordinates (`12.9716, 77.5946`) in `backend/src/ai/ai.service.ts` with dynamic GPS coordinates passed from Android `AiViewModel` via `LocationTracker`. Updated `AiDto.kt`, `AiRepository`, `AiUseCases`, and backend `ai.controller.ts` to accept device location, and appended discovered local veterinary clinics (`nearby_veterinary_help`) directly into AI chat replies.
- Fixed duplicate `/api/` route prefixes in `RescueApi.kt` and `VolunteerApi.kt`: `RescueApi` used `@POST("api/rescues")` and `@GET("api/rescues")`, which combined with Retrofit's `API_BASE_URL` (`https://bezubaan-api.onrender.com/api/`) into `/api/api/rescues`, causing rescue report submissions to fail with HTTP 404. Removed redundant `api/` prefixes.
- Fixed Socket.IO connection URL in `SocketManager.kt`: Changed from failing `.replace("/api/v1/", "")` to `.substringBefore("/api")` so the WebSocket client connects to root domain `https://bezubaan-api.onrender.com/socket.io/`.
- Fixed missing local push/heads-up notifications: Added `showEmergencyNotification()` in `SocketManager` to build and dispatch Android `NotificationCompat` alerts with high priority, sound, and vibration upon receiving `new_emergency_rescue` payloads.
- Added runtime permission request for `POST_NOTIFICATIONS` on Android 13+ in `MainActivity` to prevent the OS from blocking dispatch alerts.
- Maintained persistent WebSocket radar connection across screens in `MainViewModel` whenever volunteer mode is active.
- Fixed `MissingFieldException: Field 'name' is required` during user registration and profile deserialization: Made `UserDto.name` nullable with default `null` and added fallback to username/email prefix in domain mapper (`UserEntity.kt`), since backend User schema does not store a separate `name` column on the User entity.

### Changed
- Replaced the bottom-sheet Credential Manager popup with the rock-solid, standalone `GoogleSignInClient` Intent flow (`play-services-auth:21.2.0`). This forces Google's dedicated account-selection activity to open on screen every time, completely eliminating the bottom-sheet flickering/hiding bug.
- Implemented `rememberLauncherForActivityResult` with `ActivityResultContracts.StartActivityForResult()` in `LoginScreen` to handle the Google account token payload and pass it to backend verification.
- Added explicit error mapping for Google Sign-In `ApiException` (including Developer Error 10, Network Error, and Cancelled status codes).

## 2026-11-12

### Added
- Dual Persona architecture: `AppPersona.REPORTER` and `AppPersona.VOLUNTEER`.
- Pre-login `PersonaSelectionScreen` with Neo-brutalist styling allowing users to choose their role before entering login/registration.
- Dynamic persona-aware bottom navigation (`reporterBottomNavItems` and `volunteerBottomNavItems`).
- Instant 1-tap persona toggles in `HomeScreen` and `VolunteerDashboardScreen` top bars with zero disruption.
- Bi-directional persona switching and status indicators in `ProfileScreen`'s `RoleDispatchCard`.
- Volunteer test dispatch simulation action in `VolunteerDashboardScreen` and `VolunteerDashboardViewModel` (`simulateEmergencyPing`) to test emergency dispatch dialog, acceptance flow, on-site tactical checklist, and animal tracking telemetry.

### Changed
- `VolunteerPreferences`: extended to persist `AppPersona` alongside `isVolunteerModeActive`.
- `MainViewModel`: exposed `selectedPersona`, `setPersona`, and `togglePersona`.
- `BezubaanNavHost`: integrated `PersonaSelectionRoute` into `AuthGraph` prior to `LoginRoute`.
- `AuthViewModel`: Migrated Google Sign-In button click to explicit `GetSignInWithGoogleOption` with fallback to `GetGoogleIdOption`. Added explicit diagnostic handling for `GetCredentialCustomException` and `NoCredentialException`.
- `LoginScreen`: Added immediate loading spinner and inline error banner directly beneath the Google button to surface any configuration/credential errors on-screen.
- `WelcomeScreen`: Expanded the volunteer login action into a full-width high-contrast touch target and connected directly to the Volunteer persona login pipeline.

## 2026-09-20

### Changed
- Completed Android-specific Git ignore rules while retaining source files, Gradle wrapper files, version catalog, and Room schemas for version control.

## 2026-09-11

### Added
- .agent/ directory with all 11 tracking files
- Implementation plan (10 phases, ~208 files)
- Architecture decisions (5 decisions documented)

### Verification
- Repository confirmed empty (greenfield)
- Dependency versions verified via web research (Sep 2026 stable)
