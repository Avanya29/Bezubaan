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

import androidx.compose.material.icons.outlined.Groups
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Psychology
import androidx.compose.material.icons.outlined.VolunteerActivism

// Define universal bottom navigation bar matching the design
val universalBottomNavItems = listOf(
    BottomNavItem("HOME", Icons.Outlined.Home, HomeRoute),
    BottomNavItem("RESCUE", Icons.Outlined.LocationOn, RescueReportRoute),
    BottomNavItem("AI VET", Icons.Outlined.Psychology, AiChatRoute),
    BottomNavItem("SQUAD", Icons.Outlined.Groups, CommunityFeedRoute),
    BottomNavItem("DONATE", Icons.Outlined.VolunteerActivism, DonateRoute)
)
