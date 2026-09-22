package com.bezubaan.app.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable

@Composable
fun BezubaanTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) darkBezubaanColors else lightBezubaanColors
    val m3Colors = if (darkTheme) BezubaanDarkColorScheme else BezubaanLightColorScheme
    val dimens = defaultBezubaanDimens
    val shapes = defaultBezubaanShapes

    CompositionLocalProvider(
        LocalBezubaanColors provides colors,
        LocalBezubaanDimens provides dimens,
        LocalBezubaanShapes provides shapes
    ) {
        MaterialTheme(
            colorScheme = m3Colors,
            typography = BezubaanTypography,
            shapes = BezubaanM3Shapes,
            content = content
        )
    }
}

object BezubaanTheme {
    val colors: BezubaanColors
        @Composable
        @ReadOnlyComposable
        get() = LocalBezubaanColors.current

    val dimens: BezubaanDimens
        @Composable
        @ReadOnlyComposable
        get() = LocalBezubaanDimens.current

    val shapes: BezubaanShapes
        @Composable
        @ReadOnlyComposable
        get() = LocalBezubaanShapes.current
}
