package com.bezubaan.app.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.compose.runtime.collectAsState
import com.bezubaan.app.MainViewModel

@Composable
fun MainAppScreen(mainViewModel: MainViewModel) {
    val isVolunteerModeActive by mainViewModel.isVolunteerModeActive.collectAsState()

    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val currentRoute = currentDestination?.route ?: ""

    // We don't want to show the bottom bar on Auth screens, Splash screen, or detailed nested screens if preferred.
    // For now, let's hide it if the route is part of AuthGraph (which includes Splash, Login, Register, etc.)
    // We can also hide it on detailed screens by checking the currentRoute string.
    val hideBottomBarRoutes = listOf(
        SplashRoute::class.qualifiedName,
        WelcomeRoute::class.qualifiedName,
        OnboardingRoute::class.qualifiedName,
        LoginRoute::class.qualifiedName,
        RegisterRoute::class.qualifiedName,
        ForgotPasswordRoute::class.qualifiedName,
        RescueDetailsRoute::class.qualifiedName,
        AnimalDetailsRoute::class.qualifiedName,
        CreatePostRoute::class.qualifiedName,
        AiChatRoute::class.qualifiedName
    )

    val shouldShowBottomBar = hideBottomBarRoutes.none { routeName -> 
        routeName != null && currentRoute.contains(routeName) 
    } && currentRoute.isNotEmpty()

    Scaffold(
        bottomBar = {
            if (shouldShowBottomBar) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .border(3.dp, Color.Black)
                        .padding(horizontal = 8.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
                ) {
                    val currentNavItems = if (isVolunteerModeActive) volunteerBottomNavItems else citizenBottomNavItems
                    
                    currentNavItems.forEach { item ->
                        val itemRouteName = item.route::class.qualifiedName ?: ""
                        val isSelected = currentDestination?.hierarchy?.any {
                            it.route?.contains(itemRouteName) == true
                        } == true

                        Column(
                            horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
                            modifier = Modifier
                                .clickable {
                                    navController.navigate(item.route) {
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                                .let {
                                    if (isSelected) {
                                        val darkGreen = Color(0xFF0D3311)
                                        it.background(darkGreen, shape = RectangleShape)
                                          .padding(horizontal = 12.dp, vertical = 6.dp)
                                    } else {
                                        it.padding(horizontal = 12.dp, vertical = 6.dp)
                                    }
                                }
                        ) {
                            Icon(
                                item.icon, 
                                contentDescription = item.title,
                                tint = if (isSelected) Color.White else Color.Gray,
                                modifier = Modifier.size(24.dp)
                            )
                            if (isSelected) {
                                Text(
                                    text = item.title,
                                    color = Color.White,
                                    fontSize = 9.sp,
                                    fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace,
                                    fontWeight = androidx.compose.ui.text.font.FontWeight.Black
                                )
                            }
                        }
                    }
                }
            }
        }
    ) { innerPadding ->
        BezubaanNavHost(
            modifier = Modifier.padding(innerPadding),
            navController = navController
        )
    }
}
