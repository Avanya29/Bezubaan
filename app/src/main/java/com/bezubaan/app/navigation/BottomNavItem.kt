package com.bezubaan.app.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavItem(
    val title: String,
    val icon: ImageVector,
    val route: Any
)

// Define different bottom navigation bars depending on the user role

val citizenBottomNavItems = listOf(
    BottomNavItem("HOME", Icons.Default.Home, HomeRoute),
    BottomNavItem("RESCUE", Icons.Default.AddCircle, RescueReportRoute),
    BottomNavItem("COMMUNITY", Icons.Default.List, CommunityFeedRoute),
    BottomNavItem("DONATE", Icons.Default.Star, DonateRoute),
    BottomNavItem("PROFILE", Icons.Default.AccountCircle, ProfileRoute)
)

val volunteerBottomNavItems = listOf(
    BottomNavItem("HOME", Icons.Default.Home, HomeRoute),
    BottomNavItem("RESCUE", Icons.Default.AddCircle, VolunteerDashboardRoute),
    BottomNavItem("AI VET", Icons.Default.Star, AiChatRoute),
    BottomNavItem("SQUAD", Icons.Default.List, CommunityFeedRoute),
    BottomNavItem("PROFILE", Icons.Default.AccountCircle, ProfileRoute)
)

val ngoBottomNavItems = listOf(
    BottomNavItem("Dashboard", Icons.Default.Home, NgoDashboardRoute),
    BottomNavItem("Cases", Icons.Default.AddCircle, HomeRoute), // Generic placeholder for active cases
    BottomNavItem("Volunteers", Icons.Default.AccountCircle, VolunteerDashboardRoute), // Placeholder for volunteer mgmt
    BottomNavItem("Community", Icons.Default.List, CommunityFeedRoute),
    BottomNavItem("Profile", Icons.Default.AccountCircle, ProfileRoute)
)
