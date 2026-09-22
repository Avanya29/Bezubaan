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
) {
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
            item { UserMessage(text = "I found an injured dog.", time = "14:22") }
            item { TriageEngineMessage() }
            item { UserMessageWithImage(text = "He is shivering and holding his back leg up. Not aggressive but whining.", time = "14:24") }
            item { MultimodalObservationMessage() }
            item { Spacer(modifier = Modifier.height(16.dp)) }
        }
        
        BottomInputArea()
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
            Text("MODEL: VETVISION-2.4B MULTIMODAL", fontSize = 9.sp, fontWeight = FontWeight.Black, color = Color.DarkGray, fontFamily = FontFamily.Monospace)
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
        Text("YOU // ADITI (CITIZEN)", fontSize = 9.sp, fontWeight = FontWeight.Black, color = Color.DarkGray)
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
                    Text(time, fontSize = 9.sp, fontWeight = FontWeight.Black, color = Color.DarkGray)
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(Icons.Default.DoneAll, contentDescription = null, modifier = Modifier.size(12.dp), tint = Color.DarkGray)
                }
            }
        }
    }
}

@Composable
private fun UserMessageWithImage(text: String, time: String) {
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.End) {
        Text("YOU // ADITI (CITIZEN)", fontSize = 9.sp, fontWeight = FontWeight.Black, color = Color.DarkGray)
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
                        model = "https://images.unsplash.com/photo-1543466835-00a7907e9de1?auto=format&fit=crop&w=400&q=80",
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
                    Text(time, fontSize = 9.sp, fontWeight = FontWeight.Black, color = Color.DarkGray)
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(Icons.Default.DoneAll, contentDescription = null, modifier = Modifier.size(12.dp), tint = Color.DarkGray)
                }
            }
        }
    }
}

@Composable
private fun TriageEngineMessage() {
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
                .shadow(4.dp, androidx.compose.foundation.shape.RoundedCornerShape(0.dp))
                .background(yellow)
                .border(thickBorder, Color.Black)
                .padding(12.dp)
        ) {
            Column {
                Box(
                    modifier = Modifier.background(Color.White).border(2.dp, Color.Black).padding(16.dp)
                ) {
                    Text(
                        "\"I can help you understand visible signs and suggest safe next steps. I cannot provide a veterinary diagnosis.\"",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        lineHeight = 18.sp
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
                Box(
                    modifier = Modifier.background(bgOffWhite).border(2.dp, Color.Black).padding(16.dp)
                ) {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Shield, contentDescription = null, tint = redAlert, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("IMMEDIATE SAFETY RULES:", fontSize = 11.sp, fontWeight = FontWeight.Black)
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                        RuleItem("1", "Keep safe distance — distressed or injured animals may bite from fear.")
                        Spacer(modifier = Modifier.height(8.dp))
                        RuleItem("2", "Do not offer food or water if shock or internal trauma is suspected.")
                        Spacer(modifier = Modifier.height(8.dp))
                        RuleItem("3", "Snap a clear photo of the injury and posture for real-time AI assessment.")
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(
                        onClick = { },
                        modifier = Modifier.weight(1f).height(40.dp).border(2.dp, Color.Black),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = Color.Black),
                        shape = androidx.compose.foundation.shape.RoundedCornerShape(0.dp),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.CameraAlt, contentDescription = null, modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("ANALYZE PHOTO WITH AI", fontSize = 9.sp, fontWeight = FontWeight.Black)
                        }
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    ActionButton("CANINE POSTURE GUIDE", Modifier.weight(1f))
                    ActionButton("⚠ SEVERE BLEEDING?", Modifier.weight(1f), textColor = redAlert, bgColor = lightPink)
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    ActionButton("★ CALL AMBULANCE", Modifier.weight(1f), textColor = yellow, bgColor = darkGreen)
                }
            }
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
                    Text("TRIAGED AT 14:24:41\nIST", fontSize = 8.sp, fontWeight = FontWeight.Black, color = Color.DarkGray, lineHeight = 10.sp)
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
private fun BottomInputArea() {
    Column(modifier = Modifier.fillMaxWidth().background(bgOffWhite)) {
        HorizontalDivider(thickness = thickBorder, color = Color.Black)
        
        // Chips
        LazyRow(
            modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp, horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item { SuggestionChip("🩸 HEAVY BLEEDING") }
            item { SuggestionChip("🚕 VEHICLE HIT") }
            item { SuggestionChip("🐶 PUPPY / HYPOTHERMIA") }
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
            Box(modifier = Modifier.border(2.dp, Color.Black).padding(10.dp).background(Color.White)) {
                Icon(Icons.Default.CameraAlt, contentDescription = null, modifier = Modifier.size(20.dp))
            }
            Box(modifier = Modifier.border(2.dp, Color.Black).padding(10.dp).background(Color.White)) {
                Icon(Icons.Default.Image, contentDescription = null, modifier = Modifier.size(20.dp))
            }
            Box(
                modifier = Modifier
                    .weight(1f)
                    .background(Color.White)
                    .border(2.dp, Color.Black)
                    .padding(12.dp)
            ) {
                Text("Describe what you see...", fontSize = 12.sp, color = Color.DarkGray)
            }
            Box(
                modifier = Modifier
                    .background(yellow)
                    .border(2.dp, Color.Black)
                    .padding(12.dp)
            ) {
                Icon(Icons.Default.Send, contentDescription = null, modifier = Modifier.size(20.dp), tint = Color.Black)
            }
        }
    }
}

@Composable
private fun SuggestionChip(text: String) {
    Box(
        modifier = Modifier
            .background(Color.White)
            .border(2.dp, Color.Black)
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Text(text, fontSize = 9.sp, fontWeight = FontWeight.Black)
    }
}
