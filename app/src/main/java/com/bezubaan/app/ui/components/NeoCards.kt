package com.bezubaan.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.bezubaan.app.ui.theme.BezubaanTheme

@Composable
fun NeoCard(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    content: @Composable BoxScope.() -> Unit
) {
    Box(modifier = modifier) {
        // Shadow offset
        Box(
            modifier = Modifier
                .matchParentSize()
                .offset(x = 6.dp, y = 6.dp)
                .background(Color.Black)
        )
        // Main content
        Box(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface)
                .border(3.dp, Color.Black)
                .let {
                    if (onClick != null) it.clickable(onClick = onClick) else it
                },
            content = content
        )
    }
}

@Composable
fun NeoImageCard(
    imageUrl: String,
    title: String,
    subtitle: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    NeoCard(modifier = modifier, onClick = onClick) {
        Column {
            AsyncImage(
                model = imageUrl,
                contentDescription = title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
            )
            HorizontalDivider(thickness = 3.dp, color = Color.Black)
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Black,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
fun NeoPostCard(
    authorName: String,
    content: String,
    imageUrl: String?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    NeoCard(modifier = modifier, onClick = onClick) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = authorName,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Black
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = content,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium
            )
            if (imageUrl != null) {
                Spacer(modifier = Modifier.height(16.dp))
                Box(modifier = Modifier.border(3.dp, Color.Black)) {
                    AsyncImage(
                        model = imageUrl,
                        contentDescription = "Post image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun NeoAnimalCard(
    name: String,
    breed: String,
    imageUrl: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    NeoImageCard(
        imageUrl = imageUrl,
        title = name,
        subtitle = breed,
        onClick = onClick,
        modifier = modifier
    )
}

@Composable
fun NeoRescueCard(
    title: String,
    distance: String,
    priority: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    NeoCard(modifier = modifier, onClick = onClick) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Black
                )
                NeoStatusBadge(status = priority)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = distance,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
