package com.bezubaan.app.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
data class BezubaanDimens(
    // Spacing
    val spacing2: Dp = 2.dp,
    val spacing4: Dp = 4.dp,
    val spacing8: Dp = 8.dp,
    val spacing12: Dp = 12.dp,
    val spacing16: Dp = 16.dp,
    val spacing20: Dp = 20.dp,
    val spacing24: Dp = 24.dp,
    val spacing32: Dp = 32.dp,
    val spacing40: Dp = 40.dp,
    val spacing48: Dp = 48.dp,
    val spacing56: Dp = 56.dp,
    val spacing64: Dp = 64.dp,

    // Borders
    val borderThin: Dp = 1.dp,
    val borderMedium: Dp = 2.dp,
    val borderThick: Dp = 3.dp,
    val borderExtraThick: Dp = 4.dp,

    // Shadows
    val shadowSmall: Dp = 3.dp,
    val shadowMedium: Dp = 4.dp,
    val shadowLarge: Dp = 6.dp,

    // Icons
    val iconSmall: Dp = 16.dp,
    val iconMedium: Dp = 24.dp,
    val iconLarge: Dp = 32.dp,
    val iconExtraLarge: Dp = 48.dp,

    // Avatars
    val avatarSmall: Dp = 32.dp,
    val avatarMedium: Dp = 48.dp,
    val avatarLarge: Dp = 64.dp,
    val avatarExtraLarge: Dp = 96.dp,

    // Touch
    val minTouchTarget: Dp = 48.dp,

    // Cards
    val cardElevation: Dp = 0.dp,

    // Images
    val heroImageHeight: Dp = 240.dp,
    val feedImageHeight: Dp = 300.dp,

    // TopBar
    val topBarHeight: Dp = 64.dp,

    // BottomBar
    val bottomBarHeight: Dp = 80.dp
)

val defaultBezubaanDimens = BezubaanDimens()

val LocalBezubaanDimens = staticCompositionLocalOf { defaultBezubaanDimens }
