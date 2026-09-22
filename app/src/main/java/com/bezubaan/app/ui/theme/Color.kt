package com.bezubaan.app.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Immutable
data class BezubaanColors(
    val primary: Color = Color(0xFFE8743B),
    val onPrimary: Color = Color(0xFFFFFFFF),
    val secondary: Color = Color(0xFF4CAF50),
    val onSecondary: Color = Color(0xFFFFFFFF),
    val accent: Color = Color(0xFFFFC107),
    val onAccent: Color = Color(0xFF1A1A1A),
    val background: Color = Color(0xFFFFF8F0),
    val onBackground: Color = Color(0xFF1A1A1A),
    val surface: Color = Color(0xFFFFFFFF),
    val onSurface: Color = Color(0xFF1A1A1A),
    val surfaceVariant: Color = Color(0xFFF5F0E8),
    val error: Color = Color(0xFFD32F2F),
    val onError: Color = Color(0xFFFFFFFF),
    val success: Color = Color(0xFF2E7D32),
    val onSuccess: Color = Color(0xFFFFFFFF),
    val warning: Color = Color(0xFFED6C02),
    val onWarning: Color = Color(0xFFFFFFFF),
    val ai: Color = Color(0xFF9C7CF4),
    val onAi: Color = Color(0xFFFFFFFF),
    val border: Color = Color(0xFF1A1A1A),
    val shadow: Color = Color(0xFF1A1A1A),
    val textPrimary: Color = Color(0xFF1A1A1A),
    val textSecondary: Color = Color(0xFF5C5C5C),
    val textTertiary: Color = Color(0xFF8C8C8C),
    val highPriority: Color = Color(0xFFD32F2F),
    val mediumPriority: Color = Color(0xFFED6C02),
    val lowPriority: Color = Color(0xFF4CAF50),
    val cardBackground: Color = Color(0xFFFFFFFF),
    val isLight: Boolean = true
)

val lightBezubaanColors = BezubaanColors()

val darkBezubaanColors = BezubaanColors(
    primary = Color(0xFFE8743B),
    onPrimary = Color(0xFFFFFFFF),
    secondary = Color(0xFF4CAF50),
    onSecondary = Color(0xFFFFFFFF),
    accent = Color(0xFFFFC107),
    onAccent = Color(0xFF1A1A1A),
    background = Color(0xFF121212),
    onBackground = Color(0xFFE0E0E0),
    surface = Color(0xFF1E1E1E),
    onSurface = Color(0xFFE0E0E0),
    surfaceVariant = Color(0xFF2C2C2C),
    error = Color(0xFFEF5350),
    onError = Color(0xFFFFFFFF),
    success = Color(0xFF4CAF50),
    onSuccess = Color(0xFFFFFFFF),
    warning = Color(0xFFFF9800),
    onWarning = Color(0xFFFFFFFF),
    ai = Color(0xFFB39DDB),
    onAi = Color(0xFF1A1A1A),
    border = Color(0xFF424242),
    shadow = Color(0xFF000000),
    textPrimary = Color(0xFFE0E0E0),
    textSecondary = Color(0xFFA0A0A0),
    textTertiary = Color(0xFF757575),
    highPriority = Color = Color(0xFFEF5350),
    mediumPriority = Color(0xFFFF9800),
    lowPriority = Color(0xFF4CAF50),
    cardBackground = Color(0xFF1E1E1E),
    isLight = false
)

val LocalBezubaanColors = staticCompositionLocalOf { lightBezubaanColors }

val BezubaanLightColorScheme = lightColorScheme(
    primary = lightBezubaanColors.primary,
    onPrimary = lightBezubaanColors.onPrimary,
    secondary = lightBezubaanColors.secondary,
    onSecondary = lightBezubaanColors.onSecondary,
    tertiary = lightBezubaanColors.accent,
    onTertiary = lightBezubaanColors.onAccent,
    background = lightBezubaanColors.background,
    onBackground = lightBezubaanColors.onBackground,
    surface = lightBezubaanColors.surface,
    onSurface = lightBezubaanColors.onSurface,
    surfaceVariant = lightBezubaanColors.surfaceVariant,
    onSurfaceVariant = lightBezubaanColors.textSecondary,
    error = lightBezubaanColors.error,
    onError = lightBezubaanColors.onError
)

val BezubaanDarkColorScheme = darkColorScheme(
    primary = darkBezubaanColors.primary,
    onPrimary = darkBezubaanColors.onPrimary,
    secondary = darkBezubaanColors.secondary,
    onSecondary = darkBezubaanColors.onSecondary,
    tertiary = darkBezubaanColors.accent,
    onTertiary = darkBezubaanColors.onAccent,
    background = darkBezubaanColors.background,
    onBackground = darkBezubaanColors.onBackground,
    surface = darkBezubaanColors.surface,
    onSurface = darkBezubaanColors.onSurface,
    surfaceVariant = darkBezubaanColors.surfaceVariant,
    onSurfaceVariant = darkBezubaanColors.textSecondary,
    error = darkBezubaanColors.error,
    onError = darkBezubaanColors.onError
)
