package com.bezubaan.app.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Groups
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.MedicalServices
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Psychology
import androidx.compose.material.icons.outlined.Radar
import androidx.compose.material.icons.outlined.VolunteerActivism
import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavItem(
    val title: String,
    val icon: ImageVector,
    val route: Any
)

// Reporter / Citizen bottom navigation bar
val reporterBottomNavItems = listOf(
    BottomNavItem("HOME", Icons.Outlined.Home, HomeRoute),
    BottomNavItem("REPORT", Icons.Outlined.LocationOn, RescueFlowGraph),
    BottomNavItem("AI VET", Icons.Outlined.Psychology, AiChatRoute()),
    BottomNavItem("SQUAD", Icons.Outlined.Groups, CommunityFeedRoute),
    BottomNavItem("PROFILE", Icons.Outlined.Person, ProfileRoute)
)

// Volunteer / Responder bottom navigation bar
val volunteerBottomNavItems = listOf(
    BottomNavItem("RADAR", Icons.Outlined.Radar, VolunteerDashboardRoute),
    BottomNavItem("CASES", Icons.Outlined.LocationOn, RescueFlowGraph),
    BottomNavItem("AI VET", Icons.Outlined.Psychology, AiChatRoute()),
    BottomNavItem("SQUAD", Icons.Outlined.Groups, CommunityFeedRoute),
    BottomNavItem("PROFILE", Icons.Outlined.Person, ProfileRoute)
)

// Define universal bottom navigation bar matching the design
val universalBottomNavItems = reporterBottomNavItems
