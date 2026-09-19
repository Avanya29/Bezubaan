package com.bezubaan.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil3.compose.AsyncImage
import com.bezubaan.app.ui.theme.BezubaanTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NeoTopBar(
    title: String,
    onBackClick: (() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {},
    modifier: Modifier = Modifier
) {
    val borderThick = BezubaanTheme.dimens.borderThick
    val borderColor = BezubaanTheme.colors.onBackground
    
    TopAppBar(
        title = { 
            Text(
                text = title, 
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleLarge
            ) 
        },
        navigationIcon = {
            if (onBackClick != null) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }
        },
        actions = actions,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = BezubaanTheme.colors.surface,
            titleContentColor = BezubaanTheme.colors.onSurface,
            actionIconContentColor = BezubaanTheme.colors.onSurface,
            navigationIconContentColor = BezubaanTheme.colors.onSurface
        ),
        modifier = modifier.drawBehind {
            val strokeWidth = borderThick.toPx()
            val y = size.height - strokeWidth / 2
            drawLine(
                color = borderColor,
                start = Offset(0f, y),
                end = Offset(size.width, y),
                strokeWidth = strokeWidth
            )
        }
    )
}

data class NeoBottomNavigationItem(
    val label: String,
    val icon: @Composable () -> Unit,
    val route: String
)

@Composable
fun NeoBottomNavigation(
    items: List<NeoBottomNavigationItem>,
    currentRoute: String?,
    onNavigate: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val borderThick = BezubaanTheme.dimens.borderThick
    val borderColor = BezubaanTheme.colors.onBackground

    NavigationBar(
        modifier = modifier.drawBehind {
            val strokeWidth = borderThick.toPx()
            val y = strokeWidth / 2
            drawLine(
                color = borderColor,
                start = Offset(0f, y),
                end = Offset(size.width, y),
                strokeWidth = strokeWidth
            )
        },
        containerColor = BezubaanTheme.colors.surface,
        contentColor = BezubaanTheme.colors.onSurface,
        tonalElevation = 0.dp,
        windowInsets = WindowInsets(0, 0, 0, 0)
    ) {
        items.forEach { item ->
            val selected = currentRoute == item.route
            NavigationBarItem(
                selected = selected,
                onClick = { onNavigate(item.route) },
                icon = item.icon,
                label = { Text(item.label, fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = BezubaanTheme.colors.onPrimary,
                    unselectedIconColor = BezubaanTheme.colors.onSurface,
                    selectedTextColor = BezubaanTheme.colors.onSurface,
                    unselectedTextColor = BezubaanTheme.colors.onSurface,
                    indicatorColor = BezubaanTheme.colors.primary
                )
            )
        }
    }
}

@Composable
fun NeoDialog(
    title: String,
    text: String,
    confirmText: String,
    dismissText: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    val shape = RoundedCornerShape(0.dp)
    val borderThick = BezubaanTheme.dimens.borderThick
    val shadowColor = BezubaanTheme.colors.shadow

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Box(
            modifier = modifier
                .padding(16.dp)
                .fillMaxWidth(0.9f)
        ) {
            // Shadow
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .offset(x = 8.dp, y = 8.dp)
                    .background(shadowColor, shape)
            )
            // Dialog Content
            Column(
                modifier = Modifier
                    .border(borderThick, BezubaanTheme.colors.onBackground, shape)
                    .background(BezubaanTheme.colors.surface, shape)
                    .padding(24.dp)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = BezubaanTheme.colors.onSurface
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = text,
                    style = MaterialTheme.typography.bodyMedium,
                    color = BezubaanTheme.colors.onSurface
                )
                Spacer(modifier = Modifier.height(24.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    NeoOutlinedButton(
                        text = dismissText,
                        onClick = onDismiss
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    NeoButton(
                        text = confirmText,
                        onClick = onConfirm
                    )
                }
            }
        }
    }
}

@Composable
fun NeoAvatar(
    imageUrl: String,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    size: Int = 48
) {
    val borderThick = BezubaanTheme.dimens.borderThick

    Box(
        modifier = modifier
            .size(size.dp)
            .border(borderThick, BezubaanTheme.colors.onBackground, CircleShape)
            .clip(CircleShape)
            .background(BezubaanTheme.colors.surface)
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = contentDescription,
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize()
        )
    }
}
