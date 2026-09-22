package com.bezubaan.app.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.bezubaan.app.feature.ai.presentation.AiAssistantScreen
import com.bezubaan.app.feature.ai.presentation.AiChatScreen
import com.bezubaan.app.feature.community.presentation.CommunityFeedScreen
import com.bezubaan.app.feature.community.presentation.CreatePostScreen
import com.bezubaan.app.feature.adoption.presentation.AdoptionScreen
import com.bezubaan.app.feature.adoption.presentation.AnimalDetailsScreen
import com.bezubaan.app.feature.foster.presentation.FosterScreen
import com.bezubaan.app.feature.lostfound.presentation.LostFoundScreen
import com.bezubaan.app.feature.volunteer.presentation.VolunteerDashboardScreen
import com.bezubaan.app.feature.ngo.presentation.NgoDashboardScreen
import com.bezubaan.app.feature.map.presentation.MapScreen
import com.bezubaan.app.feature.vet.presentation.VetSearchScreen
import com.bezubaan.app.feature.notifications.presentation.NotificationsScreen
import com.bezubaan.app.feature.profile.presentation.ProfileScreen
import com.bezubaan.app.feature.profile.presentation.EditProfileScreen
import com.bezubaan.app.feature.profile.presentation.SettingsScreen
import com.bezubaan.app.feature.auth.presentation.ForgotPasswordScreen
import com.bezubaan.app.feature.auth.presentation.LoginScreen
import com.bezubaan.app.feature.auth.presentation.OnboardingScreen
import com.bezubaan.app.feature.auth.presentation.RegisterScreen
import com.bezubaan.app.feature.auth.presentation.SplashScreen
import com.bezubaan.app.feature.home.presentation.HomeScreen
import com.bezubaan.app.feature.rescue.presentation.RescueDetailsScreen
import com.bezubaan.app.feature.rescue.presentation.RescueReportScreen

@Composable
fun BezubaanNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: Any = AuthGraph
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        // --- Auth Graph ---
        navigation<AuthGraph>(startDestination = SplashRoute) {
            composable<SplashRoute> {
                SplashScreen(
                    onNavigateToOnboarding = {
                        navController.navigate(OnboardingRoute) {
                            popUpTo<SplashRoute> { inclusive = true }
                        }
                    },
                    onNavigateToHome = {
                        navController.navigate(MainGraph) {
                            popUpTo<AuthGraph> { inclusive = true }
                        }
                    }
                )
            }
            composable<OnboardingRoute> {
                OnboardingScreen(
                    onFinishOnboarding = {
                        navController.navigate(LoginRoute) {
                            popUpTo<OnboardingRoute> { inclusive = true }
                        }
                    }
                )
            }
            composable<LoginRoute> {
                LoginScreen(
                    onNavigateToRegister = { navController.navigate(RegisterRoute) },
                    onNavigateToForgot = { navController.navigate(ForgotPasswordRoute) },
                    onLoginSuccess = {
                        navController.navigate(MainGraph) {
                            popUpTo<AuthGraph> { inclusive = true }
                        }
                    }
                )
            }
            composable<RegisterRoute> {
                RegisterScreen(
                    onNavigateToLogin = { navController.popBackStack() },
                    onRegisterSuccess = {
                        navController.navigate(MainGraph) {
                            popUpTo<AuthGraph> { inclusive = true }
                        }
                    }
                )
            }
            composable<ForgotPasswordRoute> {
                ForgotPasswordScreen(
                    onBackToLogin = { navController.popBackStack() }
                )
            }
        }

        // --- Main Flow ---
        navigation<MainGraph>(startDestination = HomeRoute) {
            composable<HomeRoute> { 
                HomeScreen(
                    onNavigateToReport = { navController.navigate(RescueReportRoute) },
                    onNavigateToDetails = { id -> navController.navigate(RescueDetailsRoute(rescueId = id)) }
                )
            }

            composable<RescueReportRoute> { 
                RescueReportScreen(
                    onBack = { navController.popBackStack() }
                )
            }
            
            composable<RescueDetailsRoute> { backStackEntry ->
                val route = backStackEntry.toRoute<RescueDetailsRoute>()
                RescueDetailsScreen(
                    rescueId = route.rescueId,
                    onBack = { navController.popBackStack() }
                )
            }

            composable<AiAssistantRoute> { 
                AiAssistantScreen(
                    onNavigateToChat = { navController.navigate(AiChatRoute) }
                )
            }
            
            composable<AiChatRoute> { 
                AiChatScreen(
                    onBack = { navController.popBackStack() }
                )
            }

            composable<CommunityFeedRoute> { 
                CommunityFeedScreen(onNavigateToCreatePost = { navController.navigate(CreatePostRoute) }) 
            }
            composable<CreatePostRoute> { 
                CreatePostScreen(
                    onBack = { navController.popBackStack() },
                    onPostCreated = { navController.popBackStack() }
                ) 
            }

            composable<AdoptionRoute> { 
                AdoptionScreen(onNavigateToDetails = { id -> navController.navigate(AnimalDetailsRoute(id)) }) 
            }
            composable<AnimalDetailsRoute> { backStackEntry ->
                val route = backStackEntry.toRoute<AnimalDetailsRoute>()
                AnimalDetailsScreen(
                    animalId = route.animalId,
                    onBack = { navController.popBackStack() }
                )
            }

            composable<FosterRoute> { FosterScreen() }
            composable<LostFoundRoute> { LostFoundScreen() }

            composable<VolunteerDashboardRoute> { VolunteerDashboardScreen() }
            composable<NgoDashboardRoute> { NgoDashboardScreen() }

            composable<MapRoute> { MapScreen() }
            composable<VetSearchRoute> { VetSearchScreen() }
            composable<NotificationsRoute> { NotificationsScreen() }

            composable<ProfileRoute> { 
                ProfileScreen(
                    onNavigateToEditProfile = { navController.navigate(EditProfileRoute) },
                    onNavigateToSettings = { navController.navigate(SettingsRoute) }
                ) 
            }
            composable<EditProfileRoute> { EditProfileScreen() }
            composable<SettingsRoute> { SettingsScreen() }
        }
    }
}

@Composable
fun PlaceholderScreen(title: String) {
    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        Text(text = title)
    }
}
