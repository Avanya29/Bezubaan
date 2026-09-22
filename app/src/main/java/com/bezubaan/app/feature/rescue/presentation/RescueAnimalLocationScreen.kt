package com.bezubaan.app.feature.rescue.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
fun RescueAnimalLocationScreen(
    onBack: () -> Unit,
    onContinue: () -> Unit
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
            item { LocationInputActions() }
            item { MapVisualizer() }
            item { ConfirmedAddressCard() }
            item { PrivacyGuarantee() }
            
            item { Spacer(modifier = Modifier.height(24.dp)) }
        }
        
        BottomNavigationBar(onBack, onContinue)
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
                    .shadow(3.dp, androidx.compose.foundation.shape.RoundedCornerShape(0.dp))
                    .background(Color.White)
                    .border(3.dp, Color.Black)
                    .padding(horizontal = 11.dp, vertical = 7.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("STEP 3 OF 4", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color(0xFF002210), letterSpacing = 0.5.sp)
            }
            
            Row(
                modifier = Modifier
                    .background(Color(0xFFF0EDEC))
                    .border(2.dp, Color.Black)
                    .padding(horizontal = 10.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Box(modifier = Modifier.size(8.dp).background(Color(0xFFC5AB00), CircleShape))
                Text("CASE #BZ-804 • GPS SYNCED", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color(0xFF414942))
            }
        }
        
        // Progress bar
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            Box(
                modifier = Modifier.weight(1f).background(Color(0xFF002210)).border(2.dp, Color.Black).padding(vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text("1. PHOTO ✓", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color.White, letterSpacing = 0.8.sp)
            }
            Box(
                modifier = Modifier.weight(1f).background(Color(0xFF002210)).border(2.dp, Color.Black).padding(vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text("2. DETAILS ✓", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color.White, letterSpacing = 0.8.sp)
            }
            Box(
                modifier = Modifier.weight(1f).shadow(2.dp, androidx.compose.foundation.shape.RoundedCornerShape(0.dp)).background(Color(0xFFFFE24E)).border(2.dp, Color.Black).padding(vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text("3. LOCATION", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color(0xFF211B00), letterSpacing = 0.8.sp)
            }
            Box(
                modifier = Modifier.weight(1f).background(Color(0xFFF6F3F2)).border(2.dp, Color(0xFFC1C8C0)).padding(vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text("4. DISPATCH", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color(0xFF5F5E59), letterSpacing = 0.8.sp)
            }
        }
    }
}

@Composable
private fun ScreenHeadline() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text("WHERE IS", fontSize = 32.sp, fontWeight = FontWeight.Black, letterSpacing = (-0.8).sp, color = Color(0xFF002210))
        Box(modifier = Modifier.rotate(-1f).padding(top = 4.dp)) {
            Text(
                "THE ANIMAL?", 
                fontSize = 32.sp, 
                fontWeight = FontWeight.Black, 
                letterSpacing = (-0.96).sp,
                color = Color(0xFF211B00),
                modifier = Modifier
                    .shadow(4.dp, androidx.compose.foundation.shape.RoundedCornerShape(0.dp))
                    .background(Color(0xFFFFE24E))
                    .border(3.dp, Color.Black)
                    .padding(horizontal = 15.dp, vertical = 7.dp)
            )
        }
        
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            "Pinpoint exact street coordinates and recognizable landmarks so rapid responders reach the animal without delay.",
            fontSize = 14.sp, 
            fontWeight = FontWeight.Medium, 
            color = Color(0xFF414942), 
            lineHeight = 19.sp
        )
    }
}

@Composable
private fun LocationInputActions() {
    Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        // GPS Button
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(4.dp, androidx.compose.foundation.shape.RoundedCornerShape(0.dp))
                .background(Color(0xFFFFE24E))
                .border(3.dp, Color.Black)
                .padding(19.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(Color(0xFF002210))
                        .border(2.dp, Color.Black),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.MyLocation, contentDescription = null, tint = Color.White, modifier = Modifier.size(24.dp))
                }
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text("USE CURRENT GPS", fontSize = 18.sp, fontWeight = FontWeight.Black, color = Color(0xFF211B00))
                    Text("High accuracy • Calibrated ±4m", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF4A3F00))
                }
            }
            
            Box(modifier = Modifier.size(12.dp).background(Color(0xFFBA1A1A), CircleShape))
        }
        
        // Search Input
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(3.dp, androidx.compose.foundation.shape.RoundedCornerShape(0.dp))
                .background(Color.White)
                .border(3.dp, Color.Black)
                .padding(horizontal = 12.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Default.Search, contentDescription = null, tint = Color.Black)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Connaught Place, Block C, Outer Circle", fontSize = 14.sp, fontWeight = FontWeight.Medium, color = Color(0xFF1C1B1B), modifier = Modifier.weight(1f))
            Box(modifier = Modifier.background(Color(0xFFEBE7E7)).border(1.dp, Color.Black).padding(4.dp)) {
                Icon(Icons.Default.Close, contentDescription = "Clear", modifier = Modifier.size(16.dp))
            }
        }
    }
}

@Composable
private fun MapVisualizer() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(288.dp)
            .shadow(6.dp, androidx.compose.foundation.shape.RoundedCornerShape(0.dp))
            .background(Color(0xFFF0EDEC))
            .border(4.dp, Color.Black)
    ) {
        // Background Map Graphics (Simplified representation)
        // In a real app this would be a map view
        Box(modifier = Modifier.fillMaxSize()) {
            // Horizontal street line
            Box(modifier = Modifier.fillMaxWidth().height(8.dp).align(Alignment.Center).background(Color.DarkGray))
            // Cross street
            Box(modifier = Modifier.fillMaxHeight().width(8.dp).align(Alignment.Center).background(Color.DarkGray))
            
            // Map markers/blocks
            Box(modifier = Modifier.align(Alignment.TopStart).padding(20.dp).size(80.dp).background(Color(0xFFC0EDCD)).border(2.dp, Color.Black))
            Box(modifier = Modifier.align(Alignment.BottomEnd).padding(20.dp).size(60.dp).background(Color(0xFFC0EDCD)).border(2.dp, Color.Black))
            
            // Targeting circles
            Box(modifier = Modifier.size(150.dp).align(Alignment.Center).border(2.dp, Color.Gray, CircleShape))
            Box(modifier = Modifier.size(100.dp).align(Alignment.Center).border(2.dp, Color.Red, CircleShape))
            
            // Target Pin
            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(44.dp)
                    .shadow(3.dp, CircleShape)
                    .background(Color(0xFFFFE24E), CircleShape)
                    .border(3.dp, Color.Black, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier.size(24.dp).background(Color(0xFFBA1A1A), CircleShape).border(1.dp, Color.Black, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.LocationSearching, contentDescription = null, modifier = Modifier.size(14.dp), tint = Color.White)
                }
            }
            
            // Coordinate Tag
            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .offset(y = (-30).dp)
                    .shadow(2.dp, androidx.compose.foundation.shape.RoundedCornerShape(0.dp))
                    .background(Color(0xFF002210))
                    .border(2.dp, Color.Black)
                    .padding(horizontal = 10.dp, vertical = 4.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Pets, contentDescription = null, tint = Color(0xFFFFE24E), modifier = Modifier.size(12.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("28.6139° N, 77.2090° E", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color(0xFFFFE24E))
                }
            }
        }
        
        // Compass
        Row(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(12.dp)
                .shadow(2.dp, androidx.compose.foundation.shape.RoundedCornerShape(0.dp))
                .background(Color.White)
                .border(2.dp, Color.Black)
                .padding(horizontal = 10.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Default.Explore, contentDescription = null, modifier = Modifier.size(14.dp))
            Spacer(modifier = Modifier.width(4.dp))
            Text("N 04°", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color(0xFF002210))
        }
        
        // Map Controls
        Column(
            modifier = Modifier.align(Alignment.TopEnd).padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            MapControlButton(Icons.Default.Add)
            MapControlButton(Icons.Default.Remove)
            MapControlButton(Icons.Default.Layers)
        }
        
        // Bottom Status Banner
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(8.dp)
                .fillMaxWidth()
                .shadow(3.dp, androidx.compose.foundation.shape.RoundedCornerShape(0.dp))
                .background(Color(0xFF0F3822))
                .border(2.dp, Color.Black)
                .padding(horizontal = 12.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Bolt, contentDescription = null, tint = Color(0xFFFFE24E), modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("3 SQUAD RESPONDERS WITHIN 1.4 KM", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color.White)
            }
            Text("ETA: 6M", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color(0xFFFFE24E))
        }
    }
}

@Composable
private fun MapControlButton(icon: androidx.compose.ui.graphics.vector.ImageVector) {
    Box(
        modifier = Modifier
            .size(36.dp)
            .shadow(2.dp, androidx.compose.foundation.shape.RoundedCornerShape(0.dp))
            .background(Color.White)
            .border(2.dp, Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Icon(icon, contentDescription = null, modifier = Modifier.size(20.dp))
    }
}

@Composable
private fun ConfirmedAddressCard() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(4.dp, androidx.compose.foundation.shape.RoundedCornerShape(0.dp))
            .background(Color.White)
            .border(3.dp, Color.Black)
    ) {
        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF002210))
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Verified, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("CONFIRMED INCIDENT ADDRESS", fontSize = 12.sp, fontWeight = FontWeight.Black, color = Color.White, letterSpacing = 0.6.sp)
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("EDIT", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color(0xFFFFE24E))
                Spacer(modifier = Modifier.width(2.dp))
                Icon(Icons.Default.Edit, contentDescription = null, tint = Color(0xFFFFE24E), modifier = Modifier.size(12.dp))
            }
        }
        
        // Content
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.Top) {
                Icon(Icons.Default.LocationOn, contentDescription = null, modifier = Modifier.size(24.dp).padding(top = 2.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(
                        "Behind Sharma Tea Stall, Outer Circle", 
                        fontSize = 18.sp, 
                        fontWeight = FontWeight.Black, 
                        color = Color(0xFF002210),
                        lineHeight = 22.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        "Connaught Place Block C, New Delhi, Delhi 110001",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF414942)
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Geocode Pills
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFEBE7E7))
                    .border(2.dp, Color.Black)
                    .padding(10.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Text("LAT: 28.613904", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color(0xFF002210))
                Text("|", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color(0xFF727972))
                Text("LNG: 77.209021", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color(0xFF002210))
                Text("|", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color(0xFF727972))
                Text("ELEV: 216M", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color(0xFF002210))
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Critical Access Notes
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Warning, contentDescription = null, tint = Color.Red, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("CRITICAL ACCESS NOTES FOR RESCUE VEHICLE", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color(0xFF002210))
                }
                Spacer(modifier = Modifier.height(8.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(2.dp, androidx.compose.foundation.shape.RoundedCornerShape(0.dp))
                        .background(Color(0xFFFCF9F8))
                        .border(2.dp, Color.Black)
                        .padding(8.dp)
                ) {
                    Text(
                        "Near Metro Gate #4, tucked in alley behind the blue tea kiosk. Scooter or foot access recommended; ambulance van must park at Outer Circle perimeter.",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF1C1B1B)
                    )
                }
            }
        }
    }
}

@Composable
private fun PrivacyGuarantee() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(4.dp, androidx.compose.foundation.shape.RoundedCornerShape(0.dp))
            .background(Color(0xFFE5E2DB))
            .border(3.dp, Color.Black)
            .padding(19.dp),
        verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .background(Color(0xFF002210))
                .border(2.dp, Color.Black),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Default.Shield, contentDescription = null, tint = Color(0xFFFFE24E), modifier = Modifier.size(16.dp))
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(
                "LOCATION PRIVACY & ANTI-CRUELTY GUARANTEE", 
                fontSize = 12.sp, 
                fontWeight = FontWeight.Black, 
                color = Color(0xFF002210),
                lineHeight = 16.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                "Coordinates are encrypted end-to-end and dispatched strictly to verified Bezubaan emergency drivers. Exact locations are NEVER broadcast to public feeds to safeguard stray animals from abuse or retaliation.",
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF474742),
                lineHeight = 16.sp
            )
        }
    }
}

@Composable
private fun BottomNavigationBar(onBack: () -> Unit, onContinue: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .border(2.dp, Color.Black)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = onBack,
                modifier = Modifier
                    .weight(0.35f)
                    .height(56.dp)
                    .shadow(4.dp, androidx.compose.foundation.shape.RoundedCornerShape(0.dp))
                    .border(3.dp, Color.Black),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = Color(0xFF002210)),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(0.dp)
            ) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null, modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("BACK", fontSize = 14.sp, fontWeight = FontWeight.Black)
            }
            
            Button(
                onClick = onContinue,
                modifier = Modifier
                    .weight(0.65f)
                    .height(56.dp)
                    .shadow(5.dp, androidx.compose.foundation.shape.RoundedCornerShape(0.dp))
                    .border(3.dp, Color.Black),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFE24E), contentColor = Color(0xFF211B00)),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(0.dp)
            ) {
                Text("CONFIRM LOCATION", fontSize = 18.sp, fontWeight = FontWeight.Black)
                Spacer(modifier = Modifier.width(8.dp))
                Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, modifier = Modifier.size(20.dp))
            }
        }
        
        // SOS Button
        Button(
            onClick = { /* Handle SOS */ },
            modifier = Modifier
                .fillMaxWidth()
                .shadow(3.dp, androidx.compose.foundation.shape.RoundedCornerShape(0.dp))
                .border(2.dp, Color.Black),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFBA1A1A), contentColor = Color.White),
            shape = androidx.compose.foundation.shape.RoundedCornerShape(0.dp),
            contentPadding = PaddingValues(vertical = 12.dp)
        ) {
            Icon(Icons.Default.Sos, contentDescription = null, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                "CRITICAL CRISIS? SOS DISPATCH CALL: 1800-BEZUBAAN", 
                fontSize = 12.sp, 
                fontWeight = FontWeight.Black,
                textAlign = TextAlign.Center
            )
        }
    }
}
