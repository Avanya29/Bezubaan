package com.bezubaan.app.navigation

import kotlinx.serialization.Serializable

// --- Top Level Graphs ---
@Serializable data object AuthGraph
@Serializable data object MainGraph
@Serializable data object RescueFlowGraph

// --- Auth Routes ---
@Serializable data object SplashRoute
@Serializable data object WelcomeRoute
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
@Serializable data object AiPhotoUploadRoute
@Serializable data class AiPhotoReviewRoute(val imageUri: String)
@Serializable data object RescueActionSelectionRoute
@Serializable data object RescueAnimalDetailsRoute
@Serializable data object RescueAnimalLocationRoute
@Serializable data object RescueReportSentRoute
@Serializable data object RescueCaseDetailsRoute
@Serializable data object RescueTimelineRoute

@Serializable data object CommunityFeedRoute
@Serializable data object CommunityFollowingRoute
@Serializable data object SavedPostsRoute
@Serializable data object CreatePostRoute
@Serializable data object DonateRoute

@Serializable data object AdoptionRoute
@Serializable data class AnimalDetailsRoute(val animalId: String)

@Serializable data object FosterRoute
@Serializable data object LostFoundRoute

@Serializable data object VolunteerDashboardRoute
@Serializable data object NgoDashboardRoute

@Serializable data object EmergencyDispatchRoute
@Serializable data object ActiveRescueRoute
@Serializable data object RescueTrackingRoute

@Serializable data object MapRoute
@Serializable data object VetSearchRoute
@Serializable
object NotificationsRoute

@Serializable
object VolunteerActivationRoute

@Serializable data object ProfileRoute
@Serializable data object EditProfileRoute
@Serializable data object SettingsRoute
