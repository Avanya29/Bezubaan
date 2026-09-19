package com.bezubaan.app.feature.rescue.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bezubaan.app.ui.components.NeoButton
import com.bezubaan.app.ui.components.NeoCard
import com.bezubaan.app.ui.components.NeoSurface

@Composable
fun RescueDetailsScreen(
    rescueId: String,
    onBack: () -> Unit
) {
    NeoSurface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Rescue Case Details",
                style = MaterialTheme.typography.headlineMedium
            )
            
            Text(
                text = "Case ID: $rescueId",
                style = MaterialTheme.typography.bodySmall
            )

            NeoCard(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Timeline", style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("1. Reported - 10:00 AM", style = MaterialTheme.typography.bodyMedium)
                    Text("2. Volunteer En Route - 10:15 AM", style = MaterialTheme.typography.bodyMedium)
                    Text("3. Rescued - 11:00 AM", style = MaterialTheme.typography.bodyMedium)
                }
            }

            Spacer(modifier = Modifier.weight(1f))
            
            NeoButton(
                text = "Back",
                onClick = onBack,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
