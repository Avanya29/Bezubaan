package com.bezubaan.app.feature.community.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bezubaan.app.ui.components.NeoButton
import com.bezubaan.app.ui.components.NeoInput

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreatePostScreen(
    onBack: () -> Unit,
    onPostCreated: () -> Unit,
    viewModel: CommunityViewModel = hiltViewModel()
) {
    var content by remember { mutableStateOf("") }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Create Post") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            NeoInput(
                value = content,
                onValueChange = { content = it },
                label = "What's on your mind?",
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            NeoButton(
                text = "Attach Image",
                onClick = { /* TODO: Implement image picker */ },
                modifier = Modifier.fillMaxWidth()
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            NeoButton(
                text = "Post",
                onClick = {
                    if (content.isNotBlank()) {
                        viewModel.createPost(content)
                        onPostCreated()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
