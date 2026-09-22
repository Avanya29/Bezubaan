package com.bezubaan.app.feature.profile.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage

@Composable
fun VolunteerActivationScreen(
    onBack: () -> Unit,
    onActivate: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(bgGray)
    ) {
        TopBar(onBack)
        
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item { ProtocolHeader() }
            item { HeroSection() }
            item { RadarZoneCard() }
            item { PreFlightReadinessList() }
            item { SafetyProtocolNotice() }
            item { ActionButtons(onActivate, onBack) }
            item { Spacer(modifier = Modifier.height(24.dp)) }
        }
    }
}

@Composable
private fun TopBar(onBack: () -> Unit) {
    Column(modifier = Modifier.fillMaxWidth().background(Color.White)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onBack, modifier = Modifier.size(24.dp)) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                }
                Spacer(modifier = Modifier.width(16.dp))
                Box(
                    modifier = Modifier
                        .background(yellow, RoundedCornerShape(4.dp))
                        .border(2.dp, Color.Black, RoundedCornerShape(4.dp))
                        .padding(4.dp)
                ) {
                    Icon(Icons.Default.LocationOn, contentDescription = null, modifier = Modifier.size(20.dp))
                }
                Spacer(modifier = Modifier.width(12.dp))
                Text("VOLUNTEER ACTIVATION", fontSize = 14.sp, fontWeight = FontWeight.Black, color = Color.Black)
            }
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
        HorizontalDivider(thickness = thickBorder, color = Color.Black)
    }
}

@Composable
private fun ProtocolHeader() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.background(Color(0xFFE5E5E5)).border(1.dp, Color.Black).padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null, modifier = Modifier.size(10.dp))
            Spacer(modifier = Modifier.width(4.dp))
            Text("CITIZEN PROFILE", fontSize = 9.sp, fontWeight = FontWeight.Black, color = Color.Black)
        }
        Row(
            modifier = Modifier.background(yellow).border(1.dp, Color.Black).padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(modifier = Modifier.size(6.dp).background(redAlert, CircleShape))
            Spacer(modifier = Modifier.width(6.dp))
            Text("DISPATCH PROTOCOL V2.4", fontSize = 9.sp, fontWeight = FontWeight.Black, color = Color.Black)
        }
    }
}

@Composable
private fun HeroSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(8.dp))
            .border(thickBorder, Color.Black, RoundedCornerShape(8.dp))
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(72.dp)
                .background(yellow, RoundedCornerShape(16.dp))
                .border(thickBorder, Color.Black, RoundedCornerShape(16.dp)),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(darkGreen, RoundedCornerShape(8.dp))
                    .border(2.dp, Color.Black, RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.Pets, contentDescription = null, tint = Color.White, modifier = Modifier.size(24.dp))
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            "ENTER",
            fontSize = 32.sp,
            fontWeight = FontWeight.Black,
            fontFamily = FontFamily.Monospace,
            letterSpacing = (-1).sp,
            color = Color.Black
        )
        Box(modifier = Modifier.background(yellow).padding(horizontal = 8.dp)) {
            Text(
                "VOLUNTEER MODE.",
                fontSize = 28.sp,
                fontWeight = FontWeight.Black,
                fontFamily = FontFamily.Monospace,
                letterSpacing = (-1).sp,
                color = Color.Black
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            "You are activating frontline responder mode. Your live geolocation will stream to the emergency radar to route nearby animal rescue alerts.",
            fontSize = 12.sp,
            color = Color.DarkGray,
            textAlign = TextAlign.Center,
            lineHeight = 18.sp
        )
    }
}

@Composable
private fun RadarZoneCard() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(8.dp))
            .border(thickBorder, Color.Black, RoundedCornerShape(8.dp))
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(64.dp)
                .background(Color.LightGray, RoundedCornerShape(4.dp))
                .border(2.dp, Color.Black, RoundedCornerShape(4.dp))
        ) {
            AsyncImage(
                model = "https://images.unsplash.com/photo-1524661135-423995f22d0b?auto=format&fit=crop&w=200&q=80",
                contentDescription = "Map",
                modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(4.dp)),
                contentScale = ContentScale.Crop
            )
            Icon(Icons.Default.NearMe, contentDescription = null, tint = yellow, modifier = Modifier.align(Alignment.Center))
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text("RADAR ZONE", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = darkGreen, fontFamily = FontFamily.Monospace)
                Text("ACTIVE LOCK", fontSize = 9.sp, fontWeight = FontWeight.Black, color = Color.Black)
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text("Indiranagar Sector", fontSize = 16.sp, fontWeight = FontWeight.Black, color = Color.Black)
            Text("Bangalore Central East • ±4m High-Precision", fontSize = 10.sp, color = Color.DarkGray)
        }
    }
}

@Composable
private fun PreFlightReadinessList() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(8.dp))
            .border(thickBorder, Color.Black, RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Checklist, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("PRE-FLIGHT READINESS", fontSize = 14.sp, fontWeight = FontWeight.Black, color = Color.Black)
            }
            Box(modifier = Modifier.background(mintGreen, RoundedCornerShape(12.dp)).padding(horizontal = 8.dp, vertical = 4.dp)) {
                Text("4 / 4 PASS", fontSize = 9.sp, fontWeight = FontWeight.Black, fontFamily = FontFamily.Monospace, color = Color.Black)
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        
        ChecklistItem(
            title = "FIELD MEDICAL KIT READY",
            subtitle = "Sterile saline, antiseptic, leashes, muzzle & transport carrier bag"
        )
        ChecklistItem(
            title = "EMERGENCY VET LINE PAIRED",
            subtitle = "+91 80-BEZUBAAN (Rapid Triage Dispatch)",
            badge = "24/7 LIVE"
        )
        ChecklistItem(
            title = "VEHICLE MOBILITY RIG",
            subtitle = "2-Wheeler with safety harness crate attachment",
            badge = "SCOOTER"
        )
        ChecklistItem(
            title = "LIVE TELEMETRY GRANTED",
            subtitle = "Continuous background updates active during shifts",
            isLast = true
        )
    }
}

@Composable
private fun ChecklistItem(title: String, subtitle: String, badge: String? = null, isLast: Boolean = false) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp),
            verticalAlignment = Alignment.Top
        ) {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .background(darkGreen, RoundedCornerShape(4.dp))
                    .border(2.dp, Color.Black, RoundedCornerShape(4.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.Check, contentDescription = null, tint = mintGreen, modifier = Modifier.size(16.dp))
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Text(title, fontSize = 12.sp, fontWeight = FontWeight.Black, color = Color.Black)
                    if (badge != null) {
                        Text(badge, fontSize = 8.sp, fontWeight = FontWeight.Black, fontFamily = FontFamily.Monospace, color = Color.Black)
                    }
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(subtitle, fontSize = 10.sp, color = Color.DarkGray, lineHeight = 14.sp)
            }
        }
        if (!isLast) {
            HorizontalDivider(thickness = 1.dp, color = Color.LightGray)
        }
    }
}

@Composable
private fun SafetyProtocolNotice() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(lightPink, RoundedCornerShape(8.dp))
            .border(thickBorder, Color.Black, RoundedCornerShape(8.dp))
            .padding(16.dp),
        verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier
                .background(Color(0xFFB01212), RoundedCornerShape(4.dp))
                .border(2.dp, Color.Black, RoundedCornerShape(4.dp))
                .padding(8.dp)
        ) {
            Icon(Icons.Default.WarningAmber, contentDescription = null, tint = Color.White, modifier = Modifier.size(20.dp))
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text("SAFETY PROTOCOL NOTICE", fontSize = 12.sp, fontWeight = FontWeight.Black, color = Color(0xFFB01212))
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                "Assess scene safety before animal contact. Do not attempt hazardous highway extractions or approach rabies suspects without formal shelter back-up.",
                fontSize = 11.sp,
                color = Color(0xFFB01212),
                lineHeight = 16.sp
            )
        }
    }
}

@Composable
private fun ActionButtons(onActivate: () -> Unit, onCancel: () -> Unit) {
    Column {
        Button(
            onClick = onActivate,
            modifier = Modifier.fillMaxWidth().height(56.dp).border(thickBorder, Color.Black, RoundedCornerShape(8.dp)),
            colors = ButtonDefaults.buttonColors(containerColor = yellow, contentColor = Color.Black),
            shape = RoundedCornerShape(8.dp)
        ) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                Text("CONFIRM & ACTIVATE VOLUNTEER MODE", fontSize = 12.sp, fontWeight = FontWeight.Black, color = Color.Black)
                Spacer(modifier = Modifier.width(8.dp))
                Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, modifier = Modifier.size(16.dp))
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        Button(
            onClick = onCancel,
            modifier = Modifier.fillMaxWidth().height(48.dp).border(2.dp, Color.Black, RoundedCornerShape(8.dp)),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE5E5E5), contentColor = Color.DarkGray),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text("CANCEL // REMAIN IN CITIZEN MODE", fontSize = 10.sp, fontWeight = FontWeight.Black, fontFamily = FontFamily.Monospace)
        }
    }
}
