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
import com.bezubaan.app.feature.community.presentation.CommunityFollowingScreen
import com.bezubaan.app.feature.community.presentation.SavedPostsScreen
import com.bezubaan.app.feature.community.presentation.CreatePostScreen
import com.bezubaan.app.feature.notifications.presentation.NotificationCenterScreen
import com.bezubaan.app.feature.adoption.presentation.AdoptionScreen
import com.bezubaan.app.feature.adoption.presentation.AnimalDetailsScreen
import com.bezubaan.app.feature.foster.presentation.FosterScreen
import com.bezubaan.app.feature.lostfound.presentation.LostFoundScreen
import com.bezubaan.app.feature.volunteer.presentation.VolunteerDashboardScreen
import com.bezubaan.app.feature.volunteer.presentation.EmergencyDispatchScreen
import com.bezubaan.app.feature.volunteer.presentation.ActiveRescueScreen
import com.bezubaan.app.feature.volunteer.presentation.RescueTrackingScreen
import com.bezubaan.app.feature.ngo.presentation.NgoDashboardScreen
import com.bezubaan.app.feature.map.presentation.MapScreen
import com.bezubaan.app.feature.vet.presentation.VetSearchScreen
import com.bezubaan.app.feature.notifications.presentation.NotificationsScreen
import com.bezubaan.app.feature.profile.presentation.ProfileScreen
import com.bezubaan.app.feature.profile.presentation.VolunteerActivationScreen
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
                    onNavigateToNext = {
                        navController.navigate(WelcomeRoute) {
                            popUpTo<SplashRoute> { inclusive = true }
                        }
                    },
                    onNavigateToHome = {
                        navController.navigate(HomeRoute) {
                            popUpTo<AuthGraph> { inclusive = true }
                        }
                    }
                )
            }
            composable<WelcomeRoute> {
                com.bezubaan.app.feature.auth.presentation.WelcomeScreen(
                    onNavigateToOnboarding = {
                        navController.navigate(OnboardingRoute) {
                            popUpTo<WelcomeRoute> { inclusive = true }
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
                    onNavigateToDetails = { id -> navController.navigate(RescueDetailsRoute(rescueId = id)) },
                    onNavigateToProfile = { navController.navigate(ProfileRoute) },
                    onNavigateToNotifications = { navController.navigate(NotificationsRoute) }
                )
            }

            composable<RescueReportRoute> { 
                RescueReportScreen(
                    onBack = { navController.popBackStack() },
                    onStartRescue = { navController.navigate(RescueAnimalDetailsRoute) }
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
                    onNavigateToChat = { navController.navigate(AiChatRoute()) },
                    onNavigateToPhotoUpload = { navController.navigate(AiPhotoUploadRoute) }
                )
            }
            
            composable<AiChatRoute> { 
                AiChatScreen(
                    onBack = { navController.popBackStack() }
                )
            }

            navigation<RescueFlowGraph>(startDestination = AiPhotoUploadRoute) {
                composable<AiPhotoUploadRoute> { backStackEntry ->
                    val parentEntry = androidx.compose.runtime.remember(backStackEntry) {
                        navController.getBackStackEntry(RescueFlowGraph)
                    }
                    val sharedViewModel: com.bezubaan.app.feature.rescue.presentation.SharedRescueViewModel = androidx.hilt.navigation.compose.hiltViewModel(parentEntry)
                    com.bezubaan.app.feature.ai.presentation.AiPhotoUploadScreen(
                        sharedViewModel = sharedViewModel,
                        onBack = { navController.popBackStack() },
                        onContinue = { navController.navigate(RescueAnimalDetailsRoute) }
                    )
                }
                
                composable<AiPhotoReviewRoute> {
                    com.bezubaan.app.feature.ai.presentation.AiPhotoReviewScreen(
                        onBack = { navController.popBackStack() }
                    )
                }
                
                composable<RescueActionSelectionRoute> {
                    com.bezubaan.app.feature.ai.presentation.RescueActionSelectionScreen(
                        onBack = { navController.popBackStack() }
                    )
                }

                composable<RescueAnimalDetailsRoute> { backStackEntry ->
                    val parentEntry = androidx.compose.runtime.remember(backStackEntry) {
                        navController.getBackStackEntry(RescueFlowGraph)
                    }
                    val sharedViewModel: com.bezubaan.app.feature.rescue.presentation.SharedRescueViewModel = androidx.hilt.navigation.compose.hiltViewModel(parentEntry)
                    com.bezubaan.app.feature.rescue.presentation.RescueAnimalDetailsScreen(
                        sharedViewModel = sharedViewModel,
                        onBack = { navController.popBackStack() },
                        onContinue = { navController.navigate(RescueAnimalLocationRoute) }
                    )
                }

                composable<RescueAnimalLocationRoute> { backStackEntry ->
                    val parentEntry = androidx.compose.runtime.remember(backStackEntry) {
                        navController.getBackStackEntry(RescueFlowGraph)
                    }
                    val sharedViewModel: com.bezubaan.app.feature.rescue.presentation.SharedRescueViewModel = androidx.hilt.navigation.compose.hiltViewModel(parentEntry)
                    com.bezubaan.app.feature.rescue.presentation.RescueAnimalLocationScreen(
                        sharedViewModel = sharedViewModel,
                        onBack = { navController.popBackStack() },
                        onContinue = { navController.navigate(RescueReportSentRoute) }
                    )
                }

                composable<RescueReportSentRoute> {
                    com.bezubaan.app.feature.rescue.presentation.RescueReportSentScreen(
                        onTrackRescue = { 
                            navController.navigate(RescueTimelineRoute) {
                                popUpTo(RescueFlowGraph) { inclusive = true }
                            }
                        },
                        onBackToDashboard = { 
                            navController.navigate(HomeRoute) {
                                popUpTo(RescueFlowGraph) { inclusive = true }
                            }
                        }
                    )
                }
            }

            composable<RescueCaseDetailsRoute> {
                com.bezubaan.app.feature.rescue.presentation.RescueCaseDetailsScreen(
                    onBack = { navController.popBackStack() }
                )
            }

            composable<RescueTimelineRoute> {
                com.bezubaan.app.feature.rescue.presentation.RescueTimelineScreen(
                    onBackToDossier = { navController.navigate(RescueCaseDetailsRoute) }
                )
            }

            composable<CommunityFeedRoute> { 
                CommunityFeedScreen(onNavigateToCreatePost = { navController.navigate(CreatePostRoute) }) 
            }
            composable<CommunityFollowingRoute> {
                CommunityFollowingScreen(onBack = { navController.popBackStack() })
            }
            composable<SavedPostsRoute> {
                SavedPostsScreen(onBack = { navController.popBackStack() })
            }
            composable<CreatePostRoute> { 
                CreatePostScreen(
                    onBack = { navController.popBackStack() },
                    onPostCreated = { navController.popBackStack() }
                ) 
            }
            composable<DonateRoute> {
                androidx.compose.foundation.layout.Box(
                    modifier = androidx.compose.ui.Modifier.fillMaxSize(),
                    contentAlignment = androidx.compose.ui.Alignment.Center
                ) {
                    androidx.compose.material3.Text("Donate Screen Coming Soon", fontWeight = androidx.compose.ui.text.font.FontWeight.Black)
                }
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

            composable<VolunteerDashboardRoute> { 
                VolunteerDashboardScreen(
                    onNavigateToEmergencyDispatch = { navController.navigate(EmergencyDispatchRoute) }
                ) 
            }
            composable<EmergencyDispatchRoute> { 
                EmergencyDispatchScreen(
                    onAccept = { 
                        navController.popBackStack()
                        navController.navigate(ActiveRescueRoute)
                    },
                    onDecline = { navController.popBackStack() },
                    onBack = { navController.popBackStack() }
                ) 
            }
            composable<ActiveRescueRoute> {
                ActiveRescueScreen(
                    onMarkRescued = { navController.popBackStack() },
                    onCallVet = { },
                    onChat = { },
                    onIssue = { }
                )
            }
            composable<RescueTrackingRoute> {
                RescueTrackingScreen(
                    onCallVolunteer = { },
                    onChat = { },
                    onBack = { navController.popBackStack() }
                )
            }
            composable<NgoDashboardRoute> { NgoDashboardScreen() }

            composable<MapRoute> { MapScreen() }
            composable<VetSearchRoute> { VetSearchScreen() }
            composable<NotificationsRoute> { NotificationCenterScreen(onBack = { navController.popBackStack() }) }

            composable<ProfileRoute> { 
                val authViewModel: com.bezubaan.app.feature.auth.presentation.AuthViewModel = androidx.hilt.navigation.compose.hiltViewModel()
                ProfileScreen(
                    onNavigateToEditProfile = { navController.navigate(EditProfileRoute) },
                    onNavigateToSettings = { navController.navigate(SettingsRoute) },
                    onNavigateToVolunteerActivation = { navController.navigate(VolunteerActivationRoute) },
                    onLogoutClick = {
                        authViewModel.logout()
                        navController.navigate(LoginRoute) {
                            popUpTo(0) { inclusive = true }
                        }
                    }
                ) 
            }
            composable<VolunteerActivationRoute> {
                val viewModel: com.bezubaan.app.feature.volunteer.presentation.VolunteerSetupViewModel = androidx.hilt.navigation.compose.hiltViewModel()
                VolunteerActivationScreen(
                    onBack = { navController.popBackStack() },
                    onActivate = {
                        viewModel.activateVolunteerMode()
                        navController.navigate(VolunteerDashboardRoute) {
                            popUpTo(HomeRoute) { inclusive = true }
                        }
                    }
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
