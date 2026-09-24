package com.bezubaan.app.feature.ai.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.Send
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage

@Composable
fun RescueActionSelectionScreen(
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFCF9F8))
    ) {
        TopHeader()
        
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            item { TopContextBar(onBack) }
            item { TacticalHeadline() }
            item { AnimalSummaryCard() }
            item { PrimaryActionCard() }
            item { SecondaryActionCard() }
            item { TertiaryActionCard() }
            item { EmergencyBanner() }
            
            item { Spacer(modifier = Modifier.height(80.dp)) }
        }
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
                    Icon(Icons.Default.Pets, contentDescription = null, modifier = Modifier.size(20.dp))
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text("BEZUBAAN", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color.Black)
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
private fun TopContextBar(onBack: () -> Unit) {
    Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier
                    .background(Color.White)
                    .shadow(3.dp, androidx.compose.foundation.shape.RoundedCornerShape(0.dp))
                    .border(2.dp, Color.Black)
                    .padding(horizontal = 12.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null, modifier = Modifier.size(12.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("BACK TO ASSESSMENT", fontSize = 12.sp, fontWeight = FontWeight.Black)
            }
            
            Row(
                modifier = Modifier
                    .background(Color(0xFFE5E2E1))
                    .border(2.dp, Color.Black)
                    .padding(horizontal = 12.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("CASE DRAFT #BZ-804", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color(0xFF414942), letterSpacing = 1.sp)
            }
        }
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            Row(
                modifier = Modifier
                    .background(Color(0xFFC0EDCD))
                    .border(2.dp, Color.Black)
                    .padding(horizontal = 8.dp, vertical = 2.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(modifier = Modifier.size(8.dp).background(Color(0xFF002210), CircleShape))
                Spacer(modifier = Modifier.width(4.dp))
                Text("TRIAGE ATTACHED", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color(0xFF002110), letterSpacing = 0.5.sp)
            }
            Row(
                modifier = Modifier
                    .background(Color(0xFFF0EDEC))
                    .border(2.dp, Color.Black)
                    .padding(horizontal = 8.dp, vertical = 2.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.LocationOn, contentDescription = null, modifier = Modifier.size(10.dp), tint = Color.Black)
                Spacer(modifier = Modifier.width(4.dp))
                Text("GPS SYNCD", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color(0xFF414942), letterSpacing = 0.5.sp)
            }
        }
    }
}

@Composable
private fun TacticalHeadline() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text("LET'S GET THIS", fontSize = 32.sp, fontWeight = FontWeight.Black, letterSpacing = (-0.8).sp)
        Box(modifier = Modifier.rotate(-1f).padding(top = 2.dp)) {
            Text(
                "ANIMAL HELP.", 
                fontSize = 32.sp, 
                fontWeight = FontWeight.Black, 
                letterSpacing = (-0.96).sp,
                color = Color(0xFF211B00),
                modifier = Modifier
                    .shadow(4.dp, androidx.compose.foundation.shape.RoundedCornerShape(0.dp))
                    .background(Color(0xFFFFE24E))
                    .border(3.dp, Color.Black)
                    .padding(horizontal = 8.dp, vertical = 2.dp)
            )
        }
        
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            "Choose immediate action. Preliminary VetVision triage data and high-precision coordinates are ready for instant squad handoff.",
            fontSize = 14.sp, 
            fontWeight = FontWeight.Medium, 
            color = Color(0xFF414942), 
            lineHeight = 20.sp
        )
    }
}

@Composable
private fun AnimalSummaryCard() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(4.dp, androidx.compose.foundation.shape.RoundedCornerShape(0.dp))
            .background(Color(0xFFF6F3F2))
            .border(3.dp, Color.Black)
    ) {
        // Photo Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(176.dp)
                .background(Color(0xFFE5E2E1))
        ) {
            AsyncImage(
                model = "https://images.unsplash.com/photo-1543466835-00a7907e9de1?auto=format&fit=crop&w=600&q=80",
                contentDescription = "Animal Photo",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            
            Box(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(start = 8.dp, top = 8.dp)
                    .rotate(-1.5f)
            ) {
                Text(
                    "🐾 CANINE #804 • MODERATE (P2)", 
                    fontSize = 10.sp, 
                    fontWeight = FontWeight.Black,
                    letterSpacing = 0.5.sp,
                    modifier = Modifier
                        .shadow(3.dp, androidx.compose.foundation.shape.RoundedCornerShape(0.dp))
                        .background(Color.White)
                        .border(2.dp, Color.Black)
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }
            
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(8.dp)
                    .shadow(2.dp, androidx.compose.foundation.shape.RoundedCornerShape(0.dp))
                    .background(Color(0xFF0F3822))
                    .border(1.dp, Color.Black)
                    .padding(horizontal = 8.dp, vertical = 2.dp)
            ) {
                Text("AI VERIFIED", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color(0xFFE2F163), letterSpacing = 0.5.sp)
            }
            
            // Bottom border
            Box(modifier = Modifier.align(Alignment.BottomCenter).fillMaxWidth().height(3.dp).background(Color.Black))
        }

        // Telemetry Data
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Icon(Icons.Default.LocationOn, contentDescription = null, modifier = Modifier.size(16.dp), tint = Color.Black)
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text("INCIDENT LOCATION", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color(0xFF414942), letterSpacing = 0.5.sp)
                    Text("28.6139° N, 77.2090° E", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                    Text("Connaught Place Sector 4, New Delhi", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF414942))
                }
            }
            
            Row(modifier = Modifier.fillMaxWidth()) {
                Icon(Icons.Default.MedicalServices, contentDescription = null, modifier = Modifier.size(16.dp), tint = Color.Black)
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text("CLINICAL ASSESSMENT", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color(0xFF414942), letterSpacing = 0.5.sp)
                    Text("Soft tissue swelling & superficial laceration on right hind limb.", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.Black, lineHeight = 16.sp)
                    Spacer(modifier = Modifier.height(2.dp))
                    Text("Conscious, guarded posture, responsive reflexes.", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF414942))
                }
            }
            
            Row(modifier = Modifier.fillMaxWidth()) {
                Icon(Icons.Default.Timer, contentDescription = null, modifier = Modifier.size(16.dp), tint = Color.Black)
                Spacer(modifier = Modifier.width(8.dp))
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text("DISPATCH PROTOCOL", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color(0xFF414942), letterSpacing = 0.5.sp)
                    Box(
                        modifier = Modifier
                            .shadow(1.dp, androidx.compose.foundation.shape.RoundedCornerShape(0.dp))
                            .background(Color(0xFFE5E2DB))
                            .border(1.dp, Color.Black)
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text("STABILIZE WITHIN 2-4 HOURS", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color(0xFF1C1C18), letterSpacing = 0.8.sp)
                    }
                }
            }
            
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFF0EDEC))
                    .border(2.dp, Color.Black)
                    .padding(8.dp),
                verticalAlignment = Alignment.Top
            ) {
                Icon(Icons.Default.Info, contentDescription = null, modifier = Modifier.size(14.dp), tint = Color.Black)
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    "Non-clinical triage report auto-forwards to authorized responders.",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF414942),
                    lineHeight = 16.sp
                )
            }
        }
    }
}

@Composable
private fun PrimaryActionCard() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(5.dp, androidx.compose.foundation.shape.RoundedCornerShape(0.dp))
            .background(Color.White)
            .border(3.dp, Color.Black)
            .padding(16.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .shadow(2.dp, androidx.compose.foundation.shape.RoundedCornerShape(0.dp))
                    .background(Color(0xFFFFE24E))
                    .border(2.dp, Color.Black)
                    .padding(horizontal = 8.dp, vertical = 2.dp)
            ) {
                Text("RECOMMENDED ACTION", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color(0xFF211B00), letterSpacing = 1.sp)
            }
            Text("SQUAD ETA ~14 MINS", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color(0xFF414942), letterSpacing = 0.8.sp)
        }
        
        Button(
            onClick = { },
            modifier = Modifier.fillMaxWidth().height(56.dp).shadow(5.dp, androidx.compose.foundation.shape.RoundedCornerShape(0.dp)).border(3.dp, Color.Black),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFE24E), contentColor = Color(0xFF211B00)),
            shape = androidx.compose.foundation.shape.RoundedCornerShape(0.dp)
        ) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text("REPORT FOR RESCUE", fontSize = 18.sp, fontWeight = FontWeight.Black, letterSpacing = 0.9.sp)
                Icon(Icons.AutoMirrored.Filled.Send, contentDescription = null, modifier = Modifier.size(20.dp))
            }
        }
        
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            "Alert nearest Bezubaan volunteer rescue squads and emergency transport within 5km radius.",
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF414942),
            lineHeight = 16.sp
        )
        
        Spacer(modifier = Modifier.height(12.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            BadgeTag("⚡", "INSTANT DISPATCH")
            BadgeTag("📡", "LIVE RADAR")
            BadgeTag("✓", "100% FREE")
        }
    }
}

@Composable
private fun BadgeTag(icon: String, text: String) {
    Row(
        modifier = Modifier
            .background(Color(0xFFF0EDEC))
            .border(1.dp, Color.Black)
            .padding(horizontal = 6.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(icon, fontSize = 10.sp)
        Spacer(modifier = Modifier.width(4.dp))
        Text(text, fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color(0xFF414942), letterSpacing = 0.8.sp)
    }
}

@Composable
private fun SecondaryActionCard() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(4.dp, androidx.compose.foundation.shape.RoundedCornerShape(0.dp))
            .background(Color(0xFFF6F3F2))
            .border(3.dp, Color.Black)
            .padding(16.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Text("DIRECT CLINIC DROP", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color(0xFF414942), letterSpacing = 1.sp)
            Text("3 OPEN NOW", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color(0xFF002210), letterSpacing = 0.8.sp)
        }
        
        Button(
            onClick = { },
            modifier = Modifier.fillMaxWidth().height(48.dp).shadow(4.dp, androidx.compose.foundation.shape.RoundedCornerShape(0.dp)).border(3.dp, Color.Black),
            colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = Color.Black),
            shape = androidx.compose.foundation.shape.RoundedCornerShape(0.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.AddBox, contentDescription = null, modifier = Modifier.size(18.dp), tint = Color(0xFF008744))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("FIND A VET NEARBY", fontSize = 16.sp, fontWeight = FontWeight.Black, letterSpacing = 0.9.sp)
                }
                Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, modifier = Modifier.size(18.dp))
            }
        }
        
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            "Locate and call verified 24/7 animal hospitals with active surgical & orthopedic wings.",
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF414942),
            lineHeight = 16.sp
        )
        
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF0EDEC))
                .border(2.dp, Color.Black)
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text("Dr. Sharma's 24/7 Pet Trauma", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color.Black, letterSpacing = 0.8.sp)
                Text("1.2 km away • Avg triage wait: 5 min", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF414942))
            }
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .shadow(2.dp, androidx.compose.foundation.shape.RoundedCornerShape(0.dp))
                    .background(Color.White)
                    .border(2.dp, Color.Black),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.Phone, contentDescription = null, modifier = Modifier.size(14.dp), tint = Color.Black)
            }
        }
    }
}

@Composable
private fun TertiaryActionCard() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(4.dp, androidx.compose.foundation.shape.RoundedCornerShape(0.dp))
            .background(Color(0xFFEBE7E7))
            .border(3.dp, Color.Black)
            .padding(16.dp)
    ) {
        Button(
            onClick = { },
            modifier = Modifier.fillMaxWidth().height(48.dp).shadow(4.dp, androidx.compose.foundation.shape.RoundedCornerShape(0.dp)).border(3.dp, Color.Black),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE5E2DB), contentColor = Color(0xFF1C1C18)),
            shape = androidx.compose.foundation.shape.RoundedCornerShape(0.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Chat, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("ASK VETVISION CO-PILOT", fontSize = 16.sp, fontWeight = FontWeight.Black, letterSpacing = 0.9.sp)
                }
                Icon(Icons.Default.ChatBubbleOutline, contentDescription = null, modifier = Modifier.size(16.dp))
            }
        }
        
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            "Need instructions on how to gently keep the animal safe from traffic or control limb discomfort while waiting? Ask here.",
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF474742),
            lineHeight = 16.sp
        )
    }
}

@Composable
private fun EmergencyBanner() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(4.dp, androidx.compose.foundation.shape.RoundedCornerShape(0.dp))
            .background(Color(0xFFFFDAD6))
            .border(3.dp, Color.Black)
            .padding(16.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.Warning, contentDescription = null, modifier = Modifier.size(20.dp), tint = Color(0xFF93000A))
            Spacer(modifier = Modifier.width(8.dp))
            Text("LIFE-THREATENING COLLAPSE OR ARTERIAL BLEED?", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color(0xFF93000A), letterSpacing = 0.5.sp)
        }
        
        Spacer(modifier = Modifier.height(12.dp))
        
        Button(
            onClick = { },
            modifier = Modifier.fillMaxWidth().height(56.dp).shadow(5.dp, androidx.compose.foundation.shape.RoundedCornerShape(0.dp)).border(3.dp, Color.Black),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFBA1A1A), contentColor = Color.White),
            shape = androidx.compose.foundation.shape.RoundedCornerShape(0.dp)
        ) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Phone, contentDescription = null, modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("CALL 24/7 AMBULANCE: 1800-BEZUBAAN", fontSize = 14.sp, fontWeight = FontWeight.Black, letterSpacing = 0.9.sp)
            }
        }
    }
}
