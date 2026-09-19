package com.bezubaan.app.feature.lostfound.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.bezubaan.app.ui.components.NeoButton
import com.bezubaan.app.ui.components.NeoCard
import kotlinx.coroutines.delay

data class LostFoundItem(
    val id: String,
    val title: String,
    val description: String,
    val location: String,
    val type: String, // "LOST" or "FOUND"
    val date: String
)

@Composable
fun LostFoundScreen() {
    var isLoading by remember { mutableStateOf(true) }
    var items by remember { mutableStateOf<List<LostFoundItem>>(emptyList()) }
    var selectedFilter by remember { mutableStateOf("ALL") }

    LaunchedEffect(Unit) {
        delay(1000)
        items = listOf(
            LostFoundItem("1", "Lost Golden Retriever", "Last seen near Central Park. Wearing a red collar.", "Pune", "LOST", "2 hours ago"),
            LostFoundItem("2", "Found Kitten", "Small orange tabby kitten found near bus stop.", "Hyderabad", "FOUND", "1 day ago"),
            LostFoundItem("3", "Lost Parrot", "Green parrot, responds to 'Mitthu'. Flew away from balcony.", "Chennai", "LOST", "3 hours ago")
        )
        isLoading = false
    }

    val filteredItems = when (selectedFilter) {
        "LOST" -> items.filter { it.type == "LOST" }
        "FOUND" -> items.filter { it.type == "FOUND" }
        else -> items
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "🔍 Lost & Found",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Black
        )
        Spacer(modifier = Modifier.height(8.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            listOf("ALL" to "All", "LOST" to "Lost", "FOUND" to "Found").forEach { (key, label) ->
                FilterChip(
                    selected = selectedFilter == key,
                    onClick = { selectedFilter = key },
                    label = { Text(label) }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                items(filteredItems, key = { it.id }) { item ->
                    NeoCard(modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = if (item.type == "LOST") "🔴 LOST" else "🟢 FOUND",
                                    style = MaterialTheme.typography.labelLarge,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = item.date,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = item.title,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = item.description,
                                style = MaterialTheme.typography.bodyLarge
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "📍 ${item.location}",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            NeoButton(
                                text = "Contact",
                                onClick = { /* TODO */ },
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }
            }
        }
    }
}
