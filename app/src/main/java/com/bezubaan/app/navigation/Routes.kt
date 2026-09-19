package com.bezubaan.app.navigation

import kotlinx.serialization.Serializable

// --- Top Level Graphs ---
@Serializable data object AuthGraph
@Serializable data object MainGraph

// --- Auth Routes ---
@Serializable data object SplashRoute
@Serializable data object OnboardingRoute
@Serializable data object LoginRoute
@Serializable data object RegisterRoute
@Serializable data object ForgotPasswordRoute

// --- Main Flow Routes ---
@Serializable data object HomeRoute

@Serializable data object RescueReportRoute
@Serializable data class RescueDetailsRoute(val rescueId: String)

@Serializable data object AiAssistantRoute
@Serializable data class AiChatRoute(val contextId: String? = null)

@Serializable data object CommunityFeedRoute
@Serializable data object CreatePostRoute

@Serializable data object AdoptionRoute
@Serializable data class AnimalDetailsRoute(val animalId: String)

@Serializable data object FosterRoute
@Serializable data object LostFoundRoute

@Serializable data object VolunteerDashboardRoute
@Serializable data object NgoDashboardRoute

@Serializable data object MapRoute
@Serializable data object VetSearchRoute
@Serializable data object NotificationsRoute

@Serializable data object ProfileRoute
@Serializable data object EditProfileRoute
@Serializable data object SettingsRoute
