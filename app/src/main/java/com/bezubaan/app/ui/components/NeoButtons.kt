package com.bezubaan.app.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.bezubaan.app.ui.theme.BezubaanTheme

@Composable
fun NeoButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    containerColor: Color? = null,
    contentColor: Color? = null,
    icon: @Composable (() -> Unit)? = null
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    
    val xOffset = if (isPressed) 0.dp else 4.dp
    val yOffset = if (isPressed) 0.dp else 4.dp
    
    val shape = RoundedCornerShape(0.dp) // Neo-brutalist typically uses sharp corners or specific rounding
    val borderThick = BezubaanTheme.dimens.borderThick
    val shadowColor = BezubaanTheme.colors.shadow
    val resolvedBgColor = containerColor ?: BezubaanTheme.colors.primary
    val resolvedContentColor = contentColor ?: BezubaanTheme.colors.onPrimary
    val finalBgColor = if (enabled) resolvedBgColor else Color.Gray

    Box(modifier = modifier) {
        // Shadow
        Box(
            modifier = Modifier
                .matchParentSize()
                .offset(x = 4.dp, y = 4.dp)
                .background(shadowColor, shape)
        )
        // Button
        Box(
            modifier = Modifier
                .offset(x = xOffset, y = yOffset)
                .border(borderThick, BezubaanTheme.colors.onBackground, shape)
                .background(finalBgColor, shape)
                .clip(shape)
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    enabled = enabled,
                    onClick = onClick
                )
                .padding(horizontal = 16.dp, vertical = 12.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (icon != null) {
                    icon()
                    Spacer(modifier = Modifier.width(8.dp))
                }
                Text(
                    text = text,
                    color = resolvedContentColor,
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
    }
}

@Composable
fun NeoOutlinedButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    
    val xOffset = if (isPressed) 0.dp else 4.dp
    val yOffset = if (isPressed) 0.dp else 4.dp
    
    val shape = RoundedCornerShape(0.dp)
    val borderThick = BezubaanTheme.dimens.borderThick
    val shadowColor = BezubaanTheme.colors.shadow
    val backgroundColor = BezubaanTheme.colors.surface
    val contentColor = if (enabled) BezubaanTheme.colors.onSurface else Color.Gray

    Box(modifier = modifier) {
        Box(
            modifier = Modifier
                .matchParentSize()
                .offset(x = 4.dp, y = 4.dp)
                .background(shadowColor, shape)
        )
        Box(
            modifier = Modifier
                .offset(x = xOffset, y = yOffset)
                .border(borderThick, BezubaanTheme.colors.onBackground, shape)
                .background(backgroundColor, shape)
                .clip(shape)
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    enabled = enabled,
                    onClick = onClick
                )
                .padding(horizontal = 16.dp, vertical = 12.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                color = contentColor,
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}

@Composable
fun NeoTextButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    Text(
        text = text,
        color = if (enabled) BezubaanTheme.colors.primary else Color.Gray,
        style = MaterialTheme.typography.labelLarge,
        modifier = modifier
            .clickable(enabled = enabled, onClick = onClick)
            .padding(8.dp)
    )
}

@Composable
fun NeoFloatingActionButton(
    icon: @Composable () -> Unit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(0.dp)
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    
    val xOffset = if (isPressed) 0.dp else 4.dp
    val yOffset = if (isPressed) 0.dp else 4.dp
    val borderThick = BezubaanTheme.dimens.borderThick

    Box(modifier = modifier) {
        Box(
            modifier = Modifier
                .matchParentSize()
                .offset(x = 4.dp, y = 4.dp)
                .background(BezubaanTheme.colors.shadow, shape)
        )
        Box(
            modifier = Modifier
                .offset(x = xOffset, y = yOffset)
                .border(borderThick, BezubaanTheme.colors.onBackground, shape)
                .background(BezubaanTheme.colors.primary, shape)
                .clip(shape)
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    onClick = onClick
                )
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            icon()
        }
    }
}

@Composable
fun NeoToggle(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .border(BezubaanTheme.dimens.borderThick, BezubaanTheme.colors.onBackground, RoundedCornerShape(16.dp))
            .background(BezubaanTheme.colors.surface, RoundedCornerShape(16.dp))
            .padding(2.dp)
    ) {
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = BezubaanTheme.colors.primary,
                checkedTrackColor = BezubaanTheme.colors.surface,
                uncheckedThumbColor = BezubaanTheme.colors.onSurface,
                uncheckedTrackColor = BezubaanTheme.colors.surface,
                checkedBorderColor = BezubaanTheme.colors.onBackground,
                uncheckedBorderColor = BezubaanTheme.colors.onBackground
            )
        )
    }
}
