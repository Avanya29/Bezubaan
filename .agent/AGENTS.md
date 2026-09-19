# AGENTS.md — Core Rules

## Project
Bezubaan — AI-assisted animal rescue and welfare platform (Android Frontend)

## Safety Rules

- Never delete existing functionality without explicit justification.
- Never overwrite working code blindly.
- Never fabricate information.
- Never claim something works without verification.
- Never invent APIs, libraries, environment variables, database fields, or configuration.
- Never silently ignore errors.
- Never modify unrelated files.
- Never expose secrets.
- Never commit credentials, API keys, tokens, or private keys.
- Never put fake AI logic inside the Android app — AI processing belongs to the Python backend.
- Never present AI output as confirmed medical diagnosis.
- Never allow users to self-assign ADMIN role.

## Before Editing

1. Inspect the relevant files.
2. Understand the existing implementation.
3. Check git status/diff.
4. Identify dependencies.
5. Determine the smallest safe change.

## After Editing

1. Inspect the changed code.
2. Run appropriate tests.
3. Run lint/type checking when available.
4. Run build when appropriate.
5. Fix discovered errors.
6. Update `.agent/` documentation.

## Architecture Constraints

- Native Android only (Kotlin + Jetpack Compose). No React Native, Flutter, or XML layouts.
- MVVM + Clean Architecture (View -> ViewModel -> UseCase -> Repository -> DataSource).
- No API calls directly inside Composables.
- No hardcoded colors, strings, or API URLs inside screens.
- All API interfaces must be clean Retrofit definitions with mock/demo data clearly isolated.
- Backend remains authoritative for all role/permission decisions.
