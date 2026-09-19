package com.bezubaan.app.feature.rescue.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bezubaan.app.ui.components.NeoButton
import com.bezubaan.app.ui.components.NeoInput
import com.bezubaan.app.ui.components.NeoSurface

@Composable
fun RescueReportScreen(
    onBack: () -> Unit,
    viewModel: RescueReportViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    NeoSurface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(text = "Report a Rescue", style = MaterialTheme.typography.headlineMedium)

            NeoInput(
                value = uiState.animalType,
                onValueChange = viewModel::updateAnimalType,
                label = "Animal Type (e.g. Dog, Cat)",
                modifier = Modifier.fillMaxWidth()
            )

            NeoInput(
                value = uiState.description,
                onValueChange = viewModel::updateDescription,
                label = "Description",
                modifier = Modifier.fillMaxWidth()
            )

            NeoInput(
                value = uiState.location,
                onValueChange = viewModel::updateLocation,
                label = "Location",
                modifier = Modifier.fillMaxWidth()
            )

            NeoInput(
                value = uiState.urgency,
                onValueChange = viewModel::updateUrgency,
                label = "Urgency (Normal, High, Critical)",
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.weight(1f))

            if (uiState.isSubmitting) {
                CircularProgressIndicator()
            } else if (uiState.isSuccess) {
                Text("Report submitted successfully!", color = MaterialTheme.colorScheme.primary)
                NeoButton(
                    text = "Go Back",
                    onClick = onBack,
                    modifier = Modifier.fillMaxWidth()
                )
            } else {
                NeoButton(
                    text = "Submit Rescue",
                    onClick = viewModel::submitReport,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}
