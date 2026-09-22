# Bezubaan — Animal Rescue & Welfare Platform

Bezubaan is an AI-assisted animal rescue and welfare platform built as a modern Android application. It connects citizens, volunteers, NGOs, and veterinarians to quickly respond to animal emergencies, facilitate adoptions, and foster a supportive community.

## Design Identity
The app features a distinctive **Neo-Brutalist** visual identity combining bold typography, high-contrast borders, off-axis shadows, and vibrant colors to create an energetic, urgent, and accessible user experience.

## Tech Stack
- **Language**: Kotlin 2.1.20
- **UI Toolkit**: Jetpack Compose (Material 3 base + Custom Neo-Brutalist components)
- **Architecture**: MVVM + Clean Architecture (Data, Domain, Presentation layers)
- **Dependency Injection**: Dagger Hilt
- **Local Database**: Room
- **Networking**: Retrofit + OkHttp
- **Serialization**: kotlinx.serialization
- **Asynchronous**: Kotlin Coroutines & Flow

## Features
- **Rapid Rescue Reporting**: Quickly report injured or abandoned animals with location tracking and photo uploads.
- **AI Assistant**: Analyze photos to determine breed and urgency, and get immediate first-aid suggestions.
- **Community Feed**: Connect with other animal lovers, share updates, and spread awareness.
- **Adoption & Foster**: Browse adoptable and foster-ready animals with detailed profiles.
- **Lost & Found**: Report and track lost or found pets.
- **Role-based Dashboards**: Specialized views for Volunteers (tracking tasks) and NGOs (managing rescues).
- **Offline Support**: Queue rescue reports locally when offline, automatically syncing when connectivity is restored.

## Getting Started
1. Open the project in Android Studio (Jellyfish or later recommended).
2. Sync Gradle files (using AGP 9.4.0).
3. Provide a Google Maps API key in `local.properties` (e.g. `MAPS_API_KEY=your_key_here`) for full functionality.
4. Build and run on an emulator or physical device.

## Note on Backend
This Android application acts as the frontend client. The backend (Node.js/NestJS) and AI processing (Python) are handled via the configured `API_BASE_URL` in the Gradle build configuration. Currently, the data layer utilizes mock implementations to demonstrate the UI and architecture without requiring a live backend.
