package com.bezubaan.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.bezubaan.app.ui.theme.BezubaanTheme

@Composable
fun NeoInput(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String = "",
    isError: Boolean = false,
    errorMessage: String? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    val shape = RoundedCornerShape(0.dp)
    val borderThick = BezubaanTheme.dimens.borderThick

    Column(modifier = modifier) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(text = label) },
            placeholder = { Text(text = placeholder) },
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
            isError = isError,
            modifier = Modifier.fillMaxWidth(),
            shape = shape,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = BezubaanTheme.colors.onBackground,
                unfocusedBorderColor = BezubaanTheme.colors.onBackground,
                errorBorderColor = BezubaanTheme.colors.error,
                focusedContainerColor = BezubaanTheme.colors.surface,
                unfocusedContainerColor = BezubaanTheme.colors.surface,
                focusedLabelColor = BezubaanTheme.colors.onBackground,
                unfocusedLabelColor = BezubaanTheme.colors.onBackground,
                cursorColor = BezubaanTheme.colors.primary
            )
        )
        if (isError && errorMessage != null) {
            Text(
                text = errorMessage,
                color = BezubaanTheme.colors.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(top = 4.dp, start = 4.dp)
            )
        }
    }
}

@Composable
fun NeoChip(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    
    val xOffset = if (isPressed || selected) 0.dp else 4.dp
    val yOffset = if (isPressed || selected) 0.dp else 4.dp
    
    val shape = RoundedCornerShape(0.dp)
    val borderThick = BezubaanTheme.dimens.borderThick
    val shadowColor = BezubaanTheme.colors.shadow
    val backgroundColor = if (selected) BezubaanTheme.colors.primary else BezubaanTheme.colors.surface
    val contentColor = if (selected) BezubaanTheme.colors.onPrimary else BezubaanTheme.colors.onSurface

    Box(modifier = modifier) {
        if (!selected) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .offset(x = 4.dp, y = 4.dp)
                    .background(shadowColor, shape)
            )
        }
        Box(
            modifier = Modifier
                .offset(x = xOffset, y = yOffset)
                .border(borderThick, BezubaanTheme.colors.onBackground, shape)
                .background(backgroundColor, shape)
                .clip(shape)
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    onClick = onClick
                )
                .padding(horizontal = 16.dp, vertical = 8.dp),
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
