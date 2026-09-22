package com.bezubaan.app.feature.ai.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage



@Composable
fun AiAssistantScreen(
    onNavigateToChat: () -> Unit,
    onNavigateToPhotoUpload: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(bgOffWhite)
    ) {
        TopHeader()

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item { HeroSection() }
            item { FeatureHighlightCards() }
            item { AiOutputIncludesSection() }
            item { CriticalMedicalSafeguard() }
            item { ActionButtons(onNavigateToChat, onNavigateToPhotoUpload) }
            
            item { Spacer(modifier = Modifier.height(80.dp)) }
        }
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
                    Text("BEZUBAAN", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color.DarkGray)
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
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier.background(darkGreen, RoundedCornerShape(2.dp)).padding(horizontal = 6.dp, vertical = 2.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(modifier = Modifier.size(6.dp).background(yellow, CircleShape))
                Spacer(modifier = Modifier.width(6.dp))
                Text("VETVISION AI // CO-PILOT", fontSize = 9.sp, fontWeight = FontWeight.Black, color = Color.White)
            }
            Row(
                modifier = Modifier.border(1.dp, Color.Black).background(Color(0xFFE0E0E0)).padding(horizontal = 6.dp, vertical = 2.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("⚡ V2.4 MULTIMODAL", fontSize = 9.sp, fontWeight = FontWeight.Black, color = Color.DarkGray)
            }
        }
        HorizontalDivider(thickness = thickBorder, color = Color.Black)
    }
}

@Composable
private fun HeroSection() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .background(yellow)
                    .border(2.dp, Color.Black)
                    .padding(8.dp)
            ) {
                Icon(Icons.Default.DocumentScanner, contentDescription = null, modifier = Modifier.size(24.dp))
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text("BEZUBAAN FIELD AI", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color.DarkGray)
                Text("CLINICAL TRIAGE", fontSize = 16.sp, fontWeight = FontWeight.Black, color = darkGreen)
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        Text("MEET YOUR", fontSize = 28.sp, fontWeight = FontWeight.Black, letterSpacing = (-1).sp)
        Box(modifier = Modifier.padding(top = 4.dp)) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .offset(x = (-4).dp, y = 4.dp)
                    .background(yellow)
            )
            Text(
                "RESCUE ASSISTANT.", 
                fontSize = 28.sp, 
                fontWeight = FontWeight.Black, 
                letterSpacing = (-1).sp,
                modifier = Modifier.border(2.dp, Color.Black).background(yellow).padding(horizontal = 8.dp, vertical = 2.dp)
            )
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            "Upload an animal photo or describe clinical signs in the field. Powered by VetVision AI trained on 40,000+ street rescue cases across urban zones.",
            fontSize = 12.sp,
            color = Color.DarkGray,
            lineHeight = 16.sp
        )
    }
}

@Composable
private fun FeatureHighlightCards() {
    Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        
        // Snap / Scan Photo
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .border(thickBorder, Color.Black)
                .padding(16.dp),
            verticalAlignment = Alignment.Top
        ) {
            Box(
                modifier = Modifier
                    .background(darkGreen)
                    .border(2.dp, Color.Black)
                    .padding(8.dp)
            ) {
                Icon(Icons.Default.CameraAlt, contentDescription = null, tint = Color.White, modifier = Modifier.size(20.dp))
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Text("SNAP / SCAN PHOTO", fontSize = 14.sp, fontWeight = FontWeight.Black)
                    Box(modifier = Modifier.background(yellow).border(1.dp, Color.Black).padding(horizontal = 4.dp, vertical = 2.dp)) {
                        Text("INSTANT", fontSize = 8.sp, fontWeight = FontWeight.Black)
                    }
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text("Real-time trauma inspection, limb alignment, open wound classification & skin disease detection.", fontSize = 10.sp, color = Color.DarkGray, lineHeight = 14.sp)
            }
        }
        
        // Describe Symptoms
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .border(thickBorder, Color.Black)
                .padding(16.dp),
            verticalAlignment = Alignment.Top
        ) {
            Box(
                modifier = Modifier
                    .background(yellow)
                    .border(2.dp, Color.Black)
                    .padding(8.dp)
            ) {
                Icon(Icons.Default.ChatBubbleOutline, contentDescription = null, modifier = Modifier.size(20.dp))
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Text("DESCRIBE SYMPTOMS", fontSize = 14.sp, fontWeight = FontWeight.Black)
                    Box(modifier = Modifier.background(Color.LightGray).border(1.dp, Color.Black).padding(horizontal = 4.dp, vertical = 2.dp)) {
                        Text("GUIDED", fontSize = 8.sp, fontWeight = FontWeight.Black)
                    }
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text("Rapid voice/text triage: respiratory rate, alertness levels, pupil response & mobility status.", fontSize = 10.sp, color = Color.DarkGray, lineHeight = 14.sp)
            }
        }

        // Real Road Emergencies Image
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .border(thickBorder, Color.Black)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.size(6.dp).background(redAlert, CircleShape))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("TRAINED ON REAL ROAD EMERGENCIES", fontSize = 9.sp, fontWeight = FontWeight.Black)
                }
                Box(modifier = Modifier.background(darkGreen).padding(horizontal = 4.dp, vertical = 2.dp)) {
                    Text("HIGH ACCURACY", fontSize = 8.sp, fontWeight = FontWeight.Black, color = Color.White)
                }
            }
            Box(modifier = Modifier.fillMaxWidth().height(160.dp).background(Color.Black)) {
                AsyncImage(
                    model = "https://images.unsplash.com/photo-1543466835-00a7907e9de1?auto=format&fit=crop&w=400&q=80",
                    contentDescription = "AI Detection Example",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop,
                    colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(Color.Gray, blendMode = androidx.compose.ui.graphics.BlendMode.Multiply)
                )
                // Overlays
                Box(
                    modifier = Modifier.padding(12.dp).border(1.dp, yellow).background(Color.Black.copy(alpha=0.6f)).padding(horizontal = 6.dp, vertical = 4.dp)
                ) {
                    Text("[ 96.2% DETECTED: LATERAL FORELEG SWELLING ]", fontSize = 9.sp, fontWeight = FontWeight.Black, color = yellow)
                }
                Box(
                    modifier = Modifier.align(Alignment.BottomEnd).padding(12.dp).background(yellow).border(1.dp, Color.Black).padding(horizontal = 6.dp, vertical = 4.dp)
                ) {
                    Text("PRIORITY 2: URGENT", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color.Black)
                }
            }
        }
    }
}

@Composable
private fun AiOutputIncludesSection() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("AI OUTPUT INCLUDES", fontSize = 12.sp, fontWeight = FontWeight.Black)
            Spacer(modifier = Modifier.width(8.dp))
            HorizontalDivider(modifier = Modifier.weight(1f), thickness = 2.dp, color = Color.Black)
        }
        Spacer(modifier = Modifier.height(16.dp))
        
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            OutputRow(icon = Icons.Default.Visibility, title = "VISIBLE OBSERVATIONS", desc = "Pinpoints shock signs, hemorrhage vectors, maggot lesions, or hip fractures.")
            OutputRow(icon = Icons.Default.Speed, title = "URGENCY SEVERITY INDEX", desc = "Classifies level P1 (Trauma Crash), P2 (Severe Pain), to P4 (Dermatology).", iconBg = lightPink, iconTint = darkRed)
            OutputRow(icon = Icons.Default.VerifiedUser, title = "SAFE ON-FIELD ACTIONS", desc = "Immediate stabilization tips: tourniquet caution, warming, and safe crate transfer.", iconBg = yellow, iconTint = Color.Black)
            OutputRow(icon = Icons.Default.NearMe, title = "DISPATCH ESCALATION", desc = "Direct one-tap broadcast with geocoded telemetry to closest ambulance network.", iconBg = darkGreen, iconTint = Color.White)
        }
    }
}

@Composable
private fun OutputRow(
    icon: ImageVector,
    title: String,
    desc: String,
    iconBg: Color = Color(0xFFE0E0E0),
    iconTint: Color = Color.DarkGray
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .border(2.dp, Color.Black)
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .background(iconBg)
                .border(1.dp, Color.Black)
                .padding(8.dp)
        ) {
            Icon(icon, contentDescription = null, tint = iconTint, modifier = Modifier.size(16.dp))
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(title, fontSize = 11.sp, fontWeight = FontWeight.Black)
            Spacer(modifier = Modifier.height(2.dp))
            Text(desc, fontSize = 10.sp, color = Color.DarkGray, lineHeight = 12.sp)
        }
    }
}

@Composable
private fun CriticalMedicalSafeguard() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(lightPink)
            .border(thickBorder, Color.Black)
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.WarningAmber, contentDescription = null, tint = darkRed, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text("CRITICAL MEDICAL SAFEGUARD", fontSize = 9.sp, fontWeight = FontWeight.Black, color = darkRed)
        }
        Spacer(modifier = Modifier.height(8.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .border(2.dp, darkRed)
                .padding(12.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                "AI ASSISTANCE ≠ VETERINARY\nDIAGNOSIS",
                fontSize = 14.sp,
                fontWeight = FontWeight.Black,
                color = darkRed,
                textAlign = TextAlign.Center
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            "VetVision AI offers preliminary triage and field stabilization guidance only. It does not replace clinical inspection, surgical intervention, or prescription medications by a certified veterinary surgeon.",
            fontSize = 10.sp,
            color = darkRed,
            lineHeight = 14.sp
        )
    }
}

@Composable
private fun ActionButtons(onNavigateToChat: () -> Unit, onNavigateToPhotoUpload: () -> Unit) {
    Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        
        Button(
            onClick = onNavigateToChat,
            modifier = Modifier.fillMaxWidth().height(56.dp).border(thickBorder, Color.Black),
            colors = ButtonDefaults.buttonColors(containerColor = yellow, contentColor = Color.Black),
            shape = RoundedCornerShape(0.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.ChatBubbleOutline, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("[ START CHAT TRIAGE ]", fontSize = 14.sp, fontWeight = FontWeight.Black)
            }
        }
        
        Button(
            onClick = onNavigateToPhotoUpload,
            modifier = Modifier.fillMaxWidth().height(56.dp).border(thickBorder, Color.Black),
            colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = Color.Black),
            shape = RoundedCornerShape(0.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.CameraAlt, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("[ UPLOAD RESCUE PHOTO ]", fontSize = 14.sp, fontWeight = FontWeight.Black)
            }
        }
        
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFE0E0E0))
                .border(thickBorder, Color.Black)
                .padding(8.dp)
        ) {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.size(6.dp).background(redAlert, CircleShape))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("CRITICAL EMERGENCY?", fontSize = 9.sp, fontWeight = FontWeight.Black)
                }
                Spacer(modifier = Modifier.height(4.dp))
                Button(
                    onClick = { },
                    modifier = Modifier.fillMaxWidth().height(48.dp).border(2.dp, Color.Black),
                    colors = ButtonDefaults.buttonColors(containerColor = darkRed, contentColor = Color.White),
                    shape = RoundedCornerShape(0.dp)
                ) {
                    Text("BYPASS AI → CALL AMBULANCE", fontSize = 12.sp, fontWeight = FontWeight.Black)
                }
            }
        }
    }
}
