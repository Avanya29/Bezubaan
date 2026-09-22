package com.bezubaan.app.feature.rescue.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage

@Composable
fun RescueReportSentScreen(
    onTrackRescue: () -> Unit,
    onBackToDashboard: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFCF9F8))
    ) {
        TopHeader()
        
        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            item { StepTelemetry() }
            item { ScreenHeadline() }
            item { RadarVisualCard() }
            item { CaseDossierDetailsCard() }
            item { CriticalProtocolList() }
            item { ActionButtons(onTrackRescue, onBackToDashboard) }
            
            item { Spacer(modifier = Modifier.height(24.dp)) }
        }
        
        // Note: Actual bottom bar will be supplied by NavHost or Scaffolding if applicable
    }
}

@Composable
private fun TopHeader() {
    Column(modifier = Modifier.fillMaxWidth().background(Color(0xFFFCF9F8))) {
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
                        .background(Color(0xFFE2F163))
                        .border(2.dp, Color.Black)
                        .padding(4.dp)
                ) {
                    Icon(Icons.Default.LocationOn, contentDescription = null, modifier = Modifier.size(20.dp))
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text("BEZUBAAN", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color.DarkGray)
                    Text("VETVISION AI TRIAGE", fontSize = 16.sp, fontWeight = FontWeight.Black)
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
    }
}

@Composable
private fun StepTelemetry() {
    Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Row(
                modifier = Modifier
                    .shadow(3.dp, RoundedCornerShape(0.dp))
                    .background(Color(0xFFFFE24E))
                    .border(3.dp, Color.Black)
                    .padding(horizontal = 11.dp, vertical = 7.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Box(modifier = Modifier.size(8.dp).background(Color(0xFFBA1A1A), CircleShape))
                Text("STEP 4 OF 4 • DISPATCH TRANSMITTED", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color(0xFF211B00), letterSpacing = 0.5.sp)
            }
            
            Text("14:32 IST", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color(0xFF414942))
        }
        
        // Progress bar
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            Box(
                modifier = Modifier.weight(1f).background(Color(0xFF002210)).border(2.dp, Color.Black).padding(vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text("1. PHOTO", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color.White, letterSpacing = 0.8.sp)
            }
            Box(
                modifier = Modifier.weight(1f).background(Color(0xFF002210)).border(2.dp, Color.Black).padding(vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text("2. DETAILS", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color.White, letterSpacing = 0.8.sp)
            }
            Box(
                modifier = Modifier.weight(1f).background(Color(0xFF002210)).border(2.dp, Color.Black).padding(vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text("3. SPOT", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color.White, letterSpacing = 0.8.sp)
            }
            Box(
                modifier = Modifier.weight(1f).shadow(2.dp, RoundedCornerShape(0.dp)).background(Color(0xFFFFE24E)).border(2.dp, Color.Black).padding(vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    Icon(Icons.Default.Visibility, contentDescription = null, modifier = Modifier.size(12.dp))
                    Text("4. SENT", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color(0xFF211B00), letterSpacing = 0.8.sp)
                }
            }
        }
    }
}

@Composable
private fun ScreenHeadline() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("REPORT", fontSize = 32.sp, fontWeight = FontWeight.Black, letterSpacing = (-0.8).sp, color = Color(0xFF002210))
            Spacer(modifier = Modifier.width(8.dp))
            Box(modifier = Modifier.rotate(-1f).padding(top = 4.dp)) {
                Text(
                    "SENT!", 
                    fontSize = 32.sp, 
                    fontWeight = FontWeight.Black, 
                    letterSpacing = (-0.96).sp,
                    color = Color(0xFF211B00),
                    modifier = Modifier
                        .shadow(4.dp, RoundedCornerShape(0.dp))
                        .background(Color(0xFFFFE24E))
                        .border(3.dp, Color.Black)
                        .padding(horizontal = 15.dp, vertical = 7.dp)
                )
            }
        }
        
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            "Your rescue alert is live across the Bezubaan Rapid Responder Network. Field squad volunteers within 3 km have received immediate push coordination.",
            fontSize = 14.sp, 
            fontWeight = FontWeight.Medium, 
            color = Color(0xFF414942), 
            lineHeight = 19.sp
        )
    }
}

@Composable
private fun RadarVisualCard() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(5.dp, RoundedCornerShape(8.dp))
            .background(Color.White, RoundedCornerShape(8.dp))
            .border(3.dp, Color.Black, RoundedCornerShape(8.dp))
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            // Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.CellTower, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("SQUAD RADAR BEAM // ACTIVE", fontSize = 10.sp, fontWeight = FontWeight.Black)
                }
                Box(
                    modifier = Modifier
                        .background(Color(0xFF002210))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text("4 IN VICINITY", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color.White)
                }
            }
            HorizontalDivider(color = Color.Black, thickness = 2.dp)
            
            // Radar UI
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentAlignment = Alignment.Center
            ) {
                // Concentric circles (mock)
                Box(modifier = Modifier.size(140.dp).border(1.dp, Color.LightGray, CircleShape))
                Box(modifier = Modifier.size(100.dp).border(1.dp, Color.LightGray, CircleShape))
                Box(modifier = Modifier.size(60.dp).border(1.dp, Color.LightGray, CircleShape))
                
                // Center Node
                Box(modifier = Modifier.rotate(-3f)) {
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .shadow(4.dp, RoundedCornerShape(8.dp))
                            .background(Color(0xFFFFE24E), RoundedCornerShape(8.dp))
                            .border(3.dp, Color.Black, RoundedCornerShape(8.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Pets, contentDescription = null, modifier = Modifier.size(36.dp), tint = Color.Black)
                    }
                }
                
                // Dispatched Chip
                Box(
                    modifier = Modifier
                        .offset(x = 30.dp, y = (-40).dp)
                        .rotate(6f)
                        .shadow(2.5.dp, RoundedCornerShape(4.dp))
                        .background(Color(0xFF002210), RoundedCornerShape(4.dp))
                        .border(2.dp, Color.Black, RoundedCornerShape(4.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.CheckCircleOutline, contentDescription = null, tint = Color.White, modifier = Modifier.size(12.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("DISPATCHED", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color.White)
                    }
                }
                
                // Bottom Broadcast Tag
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 16.dp)
                        .background(Color(0xFFF0EDEC))
                        .border(1.dp, Color.Black)
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(modifier = Modifier.size(8.dp).background(Color(0xFFBA1A1A), CircleShape))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("BROADCAST TRANSMITTED • RADIUS 3.0 KM", fontSize = 10.sp, fontWeight = FontWeight.Black)
                    }
                }
            }
        }
    }
}

@Composable
private fun CaseDossierDetailsCard() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(5.dp, RoundedCornerShape(8.dp))
            .background(Color.White, RoundedCornerShape(8.dp))
            .border(3.dp, Color.Black, RoundedCornerShape(8.dp))
    ) {
        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text("OFFICIAL RESCUE CASE", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color(0xFF414942))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("#BZ-804", fontSize = 28.sp, fontWeight = FontWeight.Black, letterSpacing = (-0.56).sp)
                    Spacer(modifier = Modifier.width(8.dp))
                    Box(
                        modifier = Modifier
                            .background(Color(0xFFF0EDEC))
                            .border(1.dp, Color.Black)
                            .padding(horizontal = 8.dp, vertical = 2.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.ContentCopy, contentDescription = null, modifier = Modifier.size(12.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("COPY", fontSize = 11.sp, fontWeight = FontWeight.Black)
                        }
                    }
                }
            }
            
            Box(
                modifier = Modifier
                    .background(Color(0xFFFFE24E), RoundedCornerShape(4.dp))
                    .border(2.dp, Color.Black, RoundedCornerShape(4.dp))
                    .padding(horizontal = 10.dp, vertical = 6.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.End) {
                    Text("STATUS", fontSize = 9.sp, fontWeight = FontWeight.Black, letterSpacing = 0.9.sp)
                    Text("ACTIVE P2", fontSize = 12.sp, fontWeight = FontWeight.Black)
                }
            }
        }
        HorizontalDivider(color = Color.Black, thickness = 2.dp)
        
        // Items
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            DossierRow(icon = Icons.Default.Pets, title = "ANIMAL TRIAGED", desc = "Desi Dog (\"Rusty\") • Adult Female (Brown/Tan)")
            DossierRow(icon = Icons.Default.LocationOn, title = "INCIDENT LOCATION", desc = "Connaught Place Outer Circle, Behind Sharma Tea Stall")
            DossierRow(icon = Icons.Default.LocalHospital, title = "AI CLINICAL TRIAGE ASSIGNMENT", desc = "Limping Left Foreleg • Superficial Abrasion • Stable Vitals")
            
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.Top) {
                Box(
                    modifier = Modifier
                        .size(28.dp)
                        .background(Color(0xFFFFE24E), RoundedCornerShape(2.dp))
                        .border(1.5.dp, Color.Black, RoundedCornerShape(2.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.DirectionsRun, contentDescription = null, modifier = Modifier.size(16.dp))
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text("RESPONDER ESTIMATE", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color(0xFF414942))
                    Text("Squad Volunteer \"Aakash V.\" (ETA 8–12 mins)", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
private fun DossierRow(icon: androidx.compose.ui.graphics.vector.ImageVector, title: String, desc: String) {
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.Top) {
        Box(
            modifier = Modifier
                .size(28.dp)
                .background(Color(0xFFF0EDEC), RoundedCornerShape(2.dp))
                .border(1.5.dp, Color.Black, RoundedCornerShape(2.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, contentDescription = null, modifier = Modifier.size(16.dp))
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(title, fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color(0xFF414942))
            Text(desc, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
        }
    }
}

@Composable
private fun CriticalProtocolList() {
    Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text("CRITICAL ON-SITE PROTOCOL", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color(0xFF414942))
        
        ProtocolCard("1", "MAINTAIN SAFE DISTANCE", "Keep visual surveillance from 3–4 meters away. Avoid sudden crowds or startling the canine.")
        ProtocolCard("2", "INCOMING VOLUNTEER CALL", "Your mobile will ring shortly from verified handler. Answer promptly for precise curb pickup.")
        ProtocolCard("3", "WATER ACCESSIBLE", "Provide clean room-temp water in a shallow bowl nearby if dog approaches calmly.")
    }
}

@Composable
private fun ProtocolCard(step: String, title: String, desc: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(3.dp, RoundedCornerShape(4.dp))
            .background(Color(0xFFF6F3F2), RoundedCornerShape(4.dp))
            .border(2.dp, Color.Black, RoundedCornerShape(4.dp))
            .padding(12.dp),
        verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier
                .size(24.dp)
                .background(Color(0xFF002210), RoundedCornerShape(2.dp))
                .border(1.dp, Color.Black, RoundedCornerShape(2.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(step, fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color.White)
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(title, fontSize = 12.sp, fontWeight = FontWeight.Black)
            Text(desc, fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF414942), lineHeight = 16.sp)
        }
    }
}

@Composable
private fun ActionButtons(onTrackRescue: () -> Unit, onBackToDashboard: () -> Unit) {
    Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Button(
            onClick = onTrackRescue,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .shadow(5.dp, RoundedCornerShape(8.dp))
                .border(3.dp, Color.Black, RoundedCornerShape(8.dp)),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFE24E), contentColor = Color(0xFF211B00)),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text("TRACK RESCUE IN REAL-TIME", fontSize = 18.sp, fontWeight = FontWeight.Black)
            Spacer(modifier = Modifier.width(8.dp))
            Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, modifier = Modifier.size(20.dp))
        }
        
        Button(
            onClick = onBackToDashboard,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .shadow(4.dp, RoundedCornerShape(4.dp))
                .border(2.dp, Color.Black, RoundedCornerShape(4.dp)),
            colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = Color(0xFF1C1B1B)),
            shape = RoundedCornerShape(4.dp)
        ) {
            Icon(Icons.Default.Home, contentDescription = null, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text("BACK TO DISPATCH DASHBOARD", fontSize = 14.sp, fontWeight = FontWeight.Black)
        }
        
        Spacer(modifier = Modifier.height(12.dp))
        
        Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Condition deteriorated suddenly?", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF414942))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Phone, contentDescription = null, tint = Color(0xFFBA1A1A), modifier = Modifier.size(12.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("EMERGENCY HOTLINE: 1800-BEZUBAAN", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color(0xFFBA1A1A))
            }
        }
    }
}
