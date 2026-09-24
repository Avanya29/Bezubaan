package com.bezubaan.app.feature.ai.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.clickable
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import android.content.Context
import android.util.Base64
import androidx.compose.ui.platform.LocalContext
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.ByteArrayOutputStream

val bgOffWhite = Color(0xFFF9F9F9)
val yellow = Color(0xFFE2F163)
val mintGreen = Color(0xFFC4F7D4)
val darkGreen = Color(0xFF0D3311)
val redAlert = Color(0xFFFF4D4D)
val darkRed = Color(0xFFB01212)
val lightPink = Color(0xFFFFE0E0)
val thickBorder = 3.dp

@Composable
fun AiChatScreen(
    onBack: () -> Unit,
    viewModel: AiViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(bgOffWhite)
    ) {
        TopHeader()
        
        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            item { SessionBadge() }
            
            items(uiState.chatMessages.size) { index ->
                val msg = uiState.chatMessages[index]
                if (msg.isUser) {
                    if (msg.imageUrl != null) {
                        UserMessageWithImage(text = msg.text, imageUrl = msg.imageUrl, time = "Now")
                    } else {
                        UserMessage(text = msg.text, time = "Now")
                    }
                } else {
                    AiMessage(text = msg.text)
                }
            }
            
            if (uiState.isLoading) {
                item {
                    Text("AI is typing...", color = Color.Black, fontSize = 12.sp, modifier = Modifier.padding(8.dp))
                }
            }
        }
        
        BottomInputArea(
            onSend = { text -> viewModel.sendMessage(text) },
            onImageSelect = { uri -> 
                val base64 = uriToBase64(context, uri)
                if (base64 != null) {
                    val dataUri = "data:image/jpeg;base64,$base64"
                    viewModel.analyzeImage(uri.toString(), dataUri)
                }
            }
        )
    }
}

private fun uriToBase64(context: Context, uri: Uri): String? {
    return try {
        val inputStream = context.contentResolver.openInputStream(uri)
        val bitmap = BitmapFactory.decodeStream(inputStream)
        inputStream?.close()
        
        if (bitmap == null) return null
        
        // Scale down to max 1024px to keep base64 string small
        val maxDim = 1024f
        val scale = Math.min(maxDim / bitmap.width, maxDim / bitmap.height)
        
        val scaledBitmap = if (scale < 1f) {
            Bitmap.createScaledBitmap(bitmap, (bitmap.width * scale).toInt(), (bitmap.height * scale).toInt(), true)
        } else {
            bitmap
        }
        
        val outputStream = ByteArrayOutputStream()
        // Compress to JPEG with 80% quality
        scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 80, outputStream)
        val bytes = outputStream.toByteArray()
        
        Base64.encodeToString(bytes, Base64.NO_WRAP)
    } catch (e: Exception) {
        null
    }
}

@Composable
private fun TopHeader() {
    Column(modifier = Modifier.fillMaxWidth().background(Color.White)) {
        // App Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .background(yellow)
                        .border(2.dp, Color.Black)
                        .padding(4.dp)
                ) {
                    Icon(Icons.Default.Pets, contentDescription = null, modifier = Modifier.size(20.dp))
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text("BEZUBAAN", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                    Text("VETVISION AI", fontSize = 16.sp, fontWeight = FontWeight.Black)
                }
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.NotificationsNone, contentDescription = "Alerts", modifier = Modifier.size(24.dp))
                Spacer(modifier = Modifier.width(12.dp))
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .border(2.dp, Color.Black, CircleShape)
                ) {
                    AsyncImage(
                        model = "https://images.unsplash.com/photo-1534528741775-53994a69daeb?auto=format&fit=crop&w=100&q=80",
                        contentDescription = "Profile",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }
        HorizontalDivider(thickness = 2.dp, color = Color.Black)
        
        // Co-Pilot Subheader
        Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.SmartToy, contentDescription = null, modifier = Modifier.size(20.dp), tint = darkGreen)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("VETVISION CO-PILOT", fontSize = 14.sp, fontWeight = FontWeight.Black, color = darkGreen)
                }
                Row(
                    modifier = Modifier.border(1.dp, Color.Black).padding(horizontal = 6.dp, vertical = 2.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(modifier = Modifier.size(6.dp).background(redAlert, CircleShape))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("AI TRIAGE LIVE", fontSize = 9.sp, fontWeight = FontWeight.Black, color = darkGreen)
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text("MODEL: VETVISION-2.4B MULTIMODAL", fontSize = 9.sp, fontWeight = FontWeight.Black, color = Color.Black, fontFamily = FontFamily.Monospace)
            Spacer(modifier = Modifier.height(8.dp))
            Box(
                modifier = Modifier
                    .background(yellow)
                    .border(1.dp, Color.Black)
                    .padding(horizontal = 8.dp, vertical = 2.dp)
            ) {
                Text("⚠ NON-VET TRIAGE ONLY", fontSize = 9.sp, fontWeight = FontWeight.Black)
            }
        }
        HorizontalDivider(thickness = thickBorder, color = darkGreen)
    }
}

@Composable
private fun SessionBadge() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFE0E0E0))
            .border(1.dp, Color.Black)
            .padding(8.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(Icons.Default.GpsFixed, contentDescription = null, modifier = Modifier.size(12.dp))
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            "SESSION #AI-7D92 • GPS LOCKED (28.6139° N, 77.2090° E)",
            fontSize = 9.sp,
            fontWeight = FontWeight.Black,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun UserMessage(text: String, time: String) {
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.End) {
        Text("YOU // ADITI (CITIZEN)", fontSize = 9.sp, fontWeight = FontWeight.Black, color = Color.Black)
        Spacer(modifier = Modifier.height(4.dp))
        Box(
            modifier = Modifier
                .widthIn(max = 250.dp)
                .background(Color.White)
                .border(2.dp, Color.Black)
                .padding(16.dp)
        ) {
            Column {
                Text(text, fontSize = 14.sp, fontWeight = FontWeight.Medium)
                Spacer(modifier = Modifier.height(8.dp))
                Row(modifier = Modifier.align(Alignment.End), verticalAlignment = Alignment.CenterVertically) {
                    Text(time, fontSize = 9.sp, fontWeight = FontWeight.Black, color = Color.Black)
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(Icons.Default.DoneAll, contentDescription = null, modifier = Modifier.size(12.dp), tint = Color.Black)
                }
            }
        }
    }
}

@Composable
private fun UserMessageWithImage(text: String, imageUrl: String, time: String) {
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.End) {
        Text("YOU // ADITI (CITIZEN)", fontSize = 9.sp, fontWeight = FontWeight.Black, color = Color.Black)
        Spacer(modifier = Modifier.height(4.dp))
        Box(
            modifier = Modifier
                .widthIn(max = 250.dp)
                .background(Color.White)
                .border(2.dp, Color.Black)
                .padding(8.dp)
        ) {
            Column {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .border(1.dp, Color.Black)
                ) {
                    AsyncImage(
                        model = imageUrl,
                        contentDescription = "Injured Dog",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(8.dp)
                            .background(Color.Black.copy(alpha = 0.7f))
                            .padding(horizontal = 4.dp, vertical = 2.dp)
                    ) {
                        Text("⛶ INJURY_PREV...", fontSize = 8.sp, fontWeight = FontWeight.Bold, color = yellow)
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
                Text(text, fontSize = 14.sp, fontWeight = FontWeight.Medium)
                Spacer(modifier = Modifier.height(8.dp))
                Row(modifier = Modifier.align(Alignment.End), verticalAlignment = Alignment.CenterVertically) {
                    Text(time, fontSize = 9.sp, fontWeight = FontWeight.Black, color = Color.Black)
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(Icons.Default.DoneAll, contentDescription = null, modifier = Modifier.size(12.dp), tint = Color.Black)
                }
            }
        }
    }
}

@Composable
private fun AiMessage(text: String) {
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.Start) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.SmartToy, contentDescription = null, modifier = Modifier.size(12.dp), tint = darkGreen)
            Spacer(modifier = Modifier.width(4.dp))
            Text("VETVISION AI • TRIAGE ENGINE", fontSize = 9.sp, fontWeight = FontWeight.Black, color = darkGreen)
        }
        Spacer(modifier = Modifier.height(4.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .background(Color.White)
                .border(2.dp, Color.Black)
                .padding(16.dp)
        ) {
            Text(text, fontSize = 14.sp, fontWeight = FontWeight.Medium)
        }
    }
}



@Composable
private fun RuleItem(number: String, text: String) {
    Row(verticalAlignment = Alignment.Top) {
        Box(modifier = Modifier.background(darkGreen).padding(horizontal = 6.dp, vertical = 2.dp)) {
            Text(number, fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color.White)
        }
        Spacer(modifier = Modifier.width(8.dp))
        Text(text, fontSize = 11.sp, lineHeight = 14.sp)
    }
}

@Composable
private fun ActionButton(text: String, modifier: Modifier = Modifier, textColor: Color = Color.Black, bgColor: Color = Color.White) {
    Box(
        modifier = modifier
            .background(bgColor)
            .border(2.dp, Color.Black)
            .padding(vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text, fontSize = 9.sp, fontWeight = FontWeight.Black, color = textColor)
    }
}

@Composable
private fun MultimodalObservationMessage() {
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.Start) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.Visibility, contentDescription = null, modifier = Modifier.size(12.dp), tint = darkGreen)
            Spacer(modifier = Modifier.width(4.dp))
            Text("VETVISION AI • MULTIMODAL OBSERVATION", fontSize = 9.sp, fontWeight = FontWeight.Black, color = darkGreen)
        }
        Spacer(modifier = Modifier.height(4.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .background(Color.White)
                .border(thickBorder, Color.Black)
                .padding(12.dp)
        ) {
            Column {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.Top) {
                    Text("VISIBLE\nOBSERVATIONS:", fontSize = 16.sp, fontWeight = FontWeight.Black, lineHeight = 18.sp)
                    Box(modifier = Modifier.background(yellow).border(2.dp, Color.Black).padding(horizontal = 6.dp, vertical = 4.dp)) {
                        Text("CONFIDENCE\n94%", fontSize = 9.sp, fontWeight = FontWeight.Black, lineHeight = 10.sp)
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
                HorizontalDivider(thickness = 2.dp, color = Color.Black)
                Spacer(modifier = Modifier.height(12.dp))
                
                ObservationBlock(
                    icon = Icons.Default.CheckCircleOutline,
                    title = "ANATOMICAL FOCUS:",
                    text = "Laceration and focal soft-tissue swelling noted on right hind limb. Paw remains aligned; no compound bone exposure detected."
                )
                Spacer(modifier = Modifier.height(8.dp))
                ObservationBlock(
                    icon = Icons.Default.ErrorOutline,
                    title = "URGENCY CLASSIFICATION:",
                    text = "MODERATE (P2 PRIORITY) — Requires wound antisepsis, stabilization splint, and anti-inflammatory injection within 3 hours.",
                    bgColor = lightPink,
                    titleColor = redAlert
                )
                Spacer(modifier = Modifier.height(8.dp))
                ObservationBlock(
                    icon = Icons.Default.MedicalServices,
                    title = "IMMEDIATE FIELD ACTION:",
                    text = "Keep animal warm and sheltered with cardboard or blanket. Minimize movement. Nearest Bezubaan Dispatch Squad alerted 1.8km away.",
                    bgColor = mintGreen,
                    titleColor = darkGreen
                )
                
                Spacer(modifier = Modifier.height(24.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.Bottom) {
                    Text("TRIAGED AT 14:24:41\nIST", fontSize = 8.sp, fontWeight = FontWeight.Black, color = Color.Black, lineHeight = 10.sp)
                    Button(
                        onClick = { },
                        modifier = Modifier.height(48.dp).border(thickBorder, Color.Black),
                        colors = ButtonDefaults.buttonColors(containerColor = darkGreen, contentColor = yellow),
                        shape = androidx.compose.foundation.shape.RoundedCornerShape(0.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.LocationOn, contentDescription = null, modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("DISPATCH\nSQUAD", fontSize = 12.sp, fontWeight = FontWeight.Black, lineHeight = 14.sp)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ObservationBlock(
    icon: ImageVector,
    title: String,
    text: String,
    bgColor: Color = Color.White,
    titleColor: Color = Color.Black
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(bgColor)
            .border(1.dp, Color.Black)
            .padding(12.dp),
        verticalAlignment = Alignment.Top
    ) {
        Icon(icon, contentDescription = null, modifier = Modifier.size(16.dp), tint = titleColor)
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(title, fontSize = 9.sp, fontWeight = FontWeight.Black, color = titleColor)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text, fontSize = 11.sp, lineHeight = 16.sp, color = Color.Black)
        }
    }
}

@Composable
private fun BottomInputArea(onSend: (String) -> Unit, onImageSelect: (Uri) -> Unit) {
    var text by remember { mutableStateOf("") }
    
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { onImageSelect(it) }
    }

    Column(modifier = Modifier.fillMaxWidth().background(bgOffWhite)) {
        HorizontalDivider(thickness = thickBorder, color = Color.Black)
        
        // Chips
        LazyRow(
            modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp, horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item { SuggestionChip("🩸 HEAVY BLEEDING", onClick = { text = "Heavy Bleeding" }) }
            item { SuggestionChip("🚕 VEHICLE HIT", onClick = { text = "Vehicle Hit" }) }
            item { SuggestionChip("🐶 PUPPY / HYPOTHERMIA", onClick = { text = "Puppy / Hypothermia" }) }
        }
        
        // Input Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .padding(bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(modifier = Modifier
                .border(2.dp, Color.Black)
                .clickable { /* Camera logic usually needs a temp file URI, just using gallery for now */ galleryLauncher.launch("image/*") }
                .padding(10.dp)
                .background(Color.White)
            ) {
                Icon(Icons.Default.CameraAlt, contentDescription = null, modifier = Modifier.size(20.dp))
            }
            Box(modifier = Modifier
                .border(2.dp, Color.Black)
                .clickable { galleryLauncher.launch("image/*") }
                .padding(10.dp)
                .background(Color.White)
            ) {
                Icon(Icons.Default.Image, contentDescription = null, modifier = Modifier.size(20.dp))
            }
            Box(
                modifier = Modifier
                    .weight(1f)
                    .background(Color.White)
                    .border(2.dp, Color.Black)
                    .padding(12.dp)
            ) {
                if (text.isEmpty()) {
                    Text("Describe what you see...", fontSize = 12.sp, color = Color.Black)
                }
                BasicTextField(
                    value = text,
                    onValueChange = { text = it },
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = androidx.compose.ui.text.TextStyle(fontSize = 12.sp, color = Color.Black)
                )
            }
            Box(
                modifier = Modifier
                    .background(yellow)
                    .border(2.dp, Color.Black)
                    .clickable { 
                        if (text.isNotBlank()) {
                            onSend(text)
                            text = ""
                        }
                    }
                    .padding(12.dp)
            ) {
                Icon(Icons.Default.Send, contentDescription = null, modifier = Modifier.size(20.dp), tint = Color.Black)
            }
        }
    }
}

@Composable
private fun SuggestionChip(text: String, onClick: () -> Unit = {}) {
    Box(
        modifier = Modifier
            .background(Color.White)
            .border(2.dp, Color.Black)
            .clickable(onClick = onClick)
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Text(text, fontSize = 9.sp, fontWeight = FontWeight.Black)
    }
}
