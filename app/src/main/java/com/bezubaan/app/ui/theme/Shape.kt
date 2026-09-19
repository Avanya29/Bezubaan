package com.bezubaan.app.ui.theme

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.dp

@Immutable
data class BezubaanShapes(
    val small: RoundedCornerShape = RoundedCornerShape(8.dp),
    val medium: RoundedCornerShape = RoundedCornerShape(12.dp),
    val large: RoundedCornerShape = RoundedCornerShape(16.dp),
    val extraLarge: RoundedCornerShape = RoundedCornerShape(24.dp),
    val pill: RoundedCornerShape = RoundedCornerShape(50),
    val button: RoundedCornerShape = RoundedCornerShape(12.dp),
    val card: RoundedCornerShape = RoundedCornerShape(16.dp),
    val input: RoundedCornerShape = RoundedCornerShape(12.dp),
    val chip: RoundedCornerShape = RoundedCornerShape(8.dp),
    val avatar: CircleShape = CircleShape,
    val bottomSheet: RoundedCornerShape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
)

val defaultBezubaanShapes = BezubaanShapes()

val LocalBezubaanShapes = staticCompositionLocalOf { defaultBezubaanShapes }

val BezubaanM3Shapes = Shapes(
    small = defaultBezubaanShapes.small,
    medium = defaultBezubaanShapes.medium,
    large = defaultBezubaanShapes.large,
    extraSmall = RoundedCornerShape(4.dp),
    extraLarge = defaultBezubaanShapes.extraLarge
)
