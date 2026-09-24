package com.bezubaan.app.feature.community.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun CreatePostScreen(
    onBack: () -> Unit = {},
    onPostCreated: () -> Unit = {},
    viewModel: CommunityViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var postContent by remember { mutableStateOf("") }
    var selectedPrivacy by remember { mutableStateOf("PUBLIC") }

    // Navigate back on successful post creation
    LaunchedEffect(uiState.postCreatedSuccess) {
        if (uiState.postCreatedSuccess) {
            viewModel.clearPostCreatedFlag()
            onPostCreated()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFCF9F8))
    ) {
        CreatePostHeader(onBack)

        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            item {
                PostContentSection(
                    content = postContent,
                    onContentChange = { postContent = it }
                )
            }
            item {
                PrivacyAudienceSection(
                    selectedPrivacy = selectedPrivacy,
                    onPrivacyChange = { selectedPrivacy = it }
                )
            }
            item {
                SafeguardWarning()
            }
            item {
                // Error message
                if (uiState.createPostError != null) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFFFFDAD6), RoundedCornerShape(2.dp))
                            .border(2.dp, Color.Black, RoundedCornerShape(2.dp))
                            .padding(12.dp)
                    ) {
                        Text(
                            uiState.createPostError ?: "Failed to create post",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF93000A)
                        )
                    }
                }
            }
            item {
                ActionButtons(
                    isLoading = uiState.isCreatingPost,
                    isEnabled = postContent.isNotBlank(),
                    onPublish = {
                        viewModel.createPost(content = postContent)
                    },
                    onSaveDraft = onBack
                )
            }
        }
    }
}

@Composable
private fun CreatePostHeader(onBack: () -> Unit) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFFCF9F8))
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .background(Color.White, RoundedCornerShape(2.dp))
                        .border(2.dp, Color.Black, RoundedCornerShape(2.dp))
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) { onBack() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", modifier = Modifier.size(20.dp))
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text("NEW", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color(0xFF414942))
                    Text("RESCUE POST", fontSize = 18.sp, fontWeight = FontWeight.Black, color = Color(0xFF1C1B1B))
                }
            }
            Box(
                modifier = Modifier
                    .background(Color(0xFFFFE24E))
                    .border(2.dp, Color.Black)
                    .padding(horizontal = 8.dp, vertical = 6.dp)
            ) {
                Text("FIELD LOG", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color(0xFF211B00))
            }
        }
        HorizontalDivider(thickness = 3.dp, color = Color.Black)
    }
}

@Composable
private fun PostContentSection(
    content: String,
    onContentChange: (String) -> Unit
) {
    Column {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("FULL RESCUE NARRATIVE *", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color(0xFF1C1B1B))
            Text("${content.length} / 5000", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color(0xFF414942))
        }
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = content,
            onValueChange = { if (it.length <= 5000) onContentChange(it) },
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 200.dp)
                .shadow(4.dp, RoundedCornerShape(2.dp))
                .background(Color.White, RoundedCornerShape(2.dp))
                .border(3.dp, Color.Black, RoundedCornerShape(2.dp)),
            placeholder = {
                Text(
                    "Describe the rescue situation, what you did, the animal's condition, any medical treatment given, and the current status...",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF8C8C8C),
                    lineHeight = 20.sp
                )
            },
            textStyle = androidx.compose.ui.text.TextStyle(
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF1C1B1B),
                lineHeight = 20.sp
            ),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                cursorColor = Color(0xFF0F3822)
            ),
            shape = RoundedCornerShape(2.dp)
        )
    }
}

@Composable
private fun PrivacyAudienceSection(
    selectedPrivacy: String,
    onPrivacyChange: (String) -> Unit
) {
    Column {
        Text("POST PRIVACY & AUDIENCE *", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color(0xFF1C1B1B))
        Spacer(modifier = Modifier.height(8.dp))

        // Public Option
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(if (selectedPrivacy == "PUBLIC") 4.dp else 2.dp, RoundedCornerShape(2.dp))
                .background(
                    if (selectedPrivacy == "PUBLIC") Color(0xFFFFE24E) else Color.White,
                    RoundedCornerShape(2.dp)
                )
                .border(3.dp, Color.Black, RoundedCornerShape(2.dp))
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) { onPrivacyChange("PUBLIC") }
                .padding(16.dp),
            verticalAlignment = Alignment.Top
        ) {
            RadioButton(
                selected = selectedPrivacy == "PUBLIC",
                onClick = { onPrivacyChange("PUBLIC") },
                colors = RadioButtonDefaults.colors(selectedColor = Color.Black)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("PUBLIC (COMMUNITY FEED)", fontSize = 14.sp, fontWeight = FontWeight.Black, color = Color.Black)
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(Icons.Default.Public, contentDescription = null, modifier = Modifier.size(16.dp), tint = Color.Black)
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    "Broadcast to all volunteers, vetted adopters, and animal lovers. Eligible for community amplification.",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = if (selectedPrivacy == "PUBLIC") Color(0xFF211B00) else Color(0xFF414942),
                    lineHeight = 16.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Private Option
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(if (selectedPrivacy == "PRIVATE") 4.dp else 2.dp, RoundedCornerShape(2.dp))
                .background(
                    if (selectedPrivacy == "PRIVATE") Color(0xFFFFE24E) else Color.White,
                    RoundedCornerShape(2.dp)
                )
                .border(3.dp, Color.Black, RoundedCornerShape(2.dp))
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) { onPrivacyChange("PRIVATE") }
                .padding(16.dp),
            verticalAlignment = Alignment.Top
        ) {
            RadioButton(
                selected = selectedPrivacy == "PRIVATE",
                onClick = { onPrivacyChange("PRIVATE") },
                colors = RadioButtonDefaults.colors(unselectedColor = Color.Black, selectedColor = Color.Black)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Text("PRIVATE (TRIAGE & VOLUNTEERS ONLY)", fontSize = 14.sp, fontWeight = FontWeight.Black, color = Color(0xFF414942), modifier = Modifier.weight(1f))
                    Icon(Icons.Default.Lock, contentDescription = null, modifier = Modifier.size(16.dp), tint = Color(0xFF414942))
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    "Internal documentation visible only to registered Bezubaan rescuers, field drivers, and shelter medics.",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF414942),
                    lineHeight = 16.sp
                )
            }
        }
    }
}

@Composable
private fun SafeguardWarning() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFE8E9E7))
            .border(2.dp, Color.Black)
            .padding(12.dp),
        verticalAlignment = Alignment.Top
    ) {
        Icon(Icons.Default.Shield, contentDescription = null, tint = Color(0xFFBA1A1A), modifier = Modifier.size(16.dp))
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            "Community Safeguard: Please ensure graphic surgical wounds include content warnings. Bezubaan strictly vets all adoption applicants.",
            fontSize = 10.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF1C1B1B),
            lineHeight = 14.sp
        )
    }
}

@Composable
private fun ActionButtons(
    isLoading: Boolean,
    isEnabled: Boolean,
    onPublish: () -> Unit,
    onSaveDraft: () -> Unit
) {
    Column {
        Button(
            onClick = onPublish,
            enabled = isEnabled && !isLoading,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .border(3.dp, Color.Black, RoundedCornerShape(2.dp))
                .shadow(4.dp, RoundedCornerShape(2.dp)),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFFFE24E),
                contentColor = Color.Black,
                disabledContainerColor = Color(0xFFE8E9E7),
                disabledContentColor = Color(0xFF8C8C8C)
            ),
            shape = RoundedCornerShape(2.dp)
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    color = Color.Black,
                    strokeWidth = 2.dp
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("PUBLISHING...", fontSize = 14.sp, fontWeight = FontWeight.Black)
            } else {
                Icon(Icons.Default.Pets, contentDescription = null, modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("PUBLISH RESCUE STORY", fontSize = 14.sp, fontWeight = FontWeight.Black)
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        Button(
            onClick = onSaveDraft,
            enabled = !isLoading,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .border(3.dp, Color.Black, RoundedCornerShape(2.dp))
                .shadow(2.dp, RoundedCornerShape(2.dp)),
            colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = Color.Black),
            shape = RoundedCornerShape(2.dp)
        ) {
            Icon(Icons.Default.Save, contentDescription = null, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text("DISCARD", fontSize = 14.sp, fontWeight = FontWeight.Black)
        }
    }
}

