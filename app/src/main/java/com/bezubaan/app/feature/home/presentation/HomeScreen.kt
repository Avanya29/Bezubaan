package com.bezubaan.app.feature.home.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bezubaan.app.feature.rescue.domain.model.RescueCase
import com.bezubaan.app.ui.components.NeoButton
import com.bezubaan.app.ui.components.NeoCard
import com.bezubaan.app.ui.components.NeoSurface

@Composable
fun HomeScreen(
    onNavigateToReport: () -> Unit,
    onNavigateToDetails: (String) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    NeoSurface(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                HeroSection(onNavigateToReport = onNavigateToReport)
            }
            item {
                QuickActionsSection()
            }
            item {
                Text(
                    text = "Nearby Cases",
                    style = MaterialTheme.typography.titleLarge
                )
            }
            if (uiState.isLoading) {
                item {
                    CircularProgressIndicator(modifier = Modifier.padding(16.dp))
                }
            } else {
                items(uiState.nearbyCases) { case ->
                    RescueCaseItem(case = case, onClick = { onNavigateToDetails(case.id) })
                }
            }
        }
    }
}

@Composable
fun HeroSection(onNavigateToReport: () -> Unit) {
    NeoCard(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Help a Bezubaan Today",
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(modifier = Modifier.height(8.dp))
            NeoButton(
                text = "Report a Rescue",
                onClick = onNavigateToReport,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun QuickActionsSection() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        NeoButton(text = "Adoption", onClick = { })
        NeoButton(text = "Foster", onClick = { })
        NeoButton(text = "Vet Search", onClick = { })
    }
}

@Composable
fun RescueCaseItem(case: RescueCase, onClick: () -> Unit) {
    NeoCard(modifier = Modifier.fillMaxWidth().clickable(onClick = onClick)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = case.title, style = MaterialTheme.typography.titleMedium)
            Text(text = case.location, style = MaterialTheme.typography.bodyMedium)
            Text(text = "Status: ${case.status}", style = MaterialTheme.typography.bodySmall)
        }
    }
}
