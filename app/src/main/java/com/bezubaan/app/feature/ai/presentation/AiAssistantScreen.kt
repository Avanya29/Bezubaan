package com.bezubaan.app.feature.ai.presentation

import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bezubaan.app.ui.components.NeoButton
import com.bezubaan.app.ui.components.NeoCard
import com.bezubaan.app.ui.theme.BezubaanTypography

@Composable
fun AiAssistantScreen(
    onNavigateToChat: () -> Unit,
    viewModel: AiViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Analyze Animal",
            style = BezubaanTypography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        NeoCard(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Image Placeholder")
            }
        }

        NeoButton(
            text = "Upload Image",
            onClick = {
                viewModel.analyzeImage(Uri.parse("content://dummy/image.jpg"))
            },
            modifier = Modifier.fillMaxWidth()
        )
        
        NeoButton(
            text = "Open Chat Assistant",
            onClick = onNavigateToChat,
            modifier = Modifier.fillMaxWidth()
        )

        if (uiState.isLoading) {
            CircularProgressIndicator()
        }

        uiState.result?.let { result ->
            NeoCard(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Analysis Result",
                        style = BezubaanTypography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(text = result)
                }
            }
        }
    }
}
