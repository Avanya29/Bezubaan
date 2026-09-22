package com.bezubaan.app.feature.ai.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage

@Composable
fun AiPhotoUploadScreen(
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
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item { TopContextBar(onBack) }
            item { TacticalHeadline() }
            item { CameraReticleDropzone() }
            item { ActionButtons() }
            item { TriageCaptureProtocol() }
            item { RecentRescuePreview() }
            item { EmergencyDispatchNotice() }
            
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
private fun TopContextBar(onBack: () -> Unit) {
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
            Text("BACK TO CHAT", fontSize = 12.sp, fontWeight = FontWeight.Black)
        }
        
        Row(horizontalArrangement = Arrangement.spacedBy(6.dp), verticalAlignment = Alignment.CenterVertically) {
            Row(
                modifier = Modifier
                    .background(Color(0xFF0D3311))
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(modifier = Modifier.size(6.dp).background(Color(0xFFE2F163), CircleShape))
                Spacer(modifier = Modifier.width(4.dp))
                Text("SENSOR READY", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color(0xFFC4F7D4))
            }
            Box(
                modifier = Modifier
                    .background(Color(0xFFE2F163))
                    .shadow(2.dp, androidx.compose.foundation.shape.RoundedCornerShape(0.dp))
                    .border(1.dp, Color.Black)
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text("V2.4 HUD", fontSize = 10.sp, fontWeight = FontWeight.Black)
            }
        }
    }
}

@Composable
private fun TacticalHeadline() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("SHOW ME", fontSize = 32.sp, fontWeight = FontWeight.Black, letterSpacing = (-1).sp)
            Spacer(modifier = Modifier.width(8.dp))
            Box(modifier = Modifier.rotate(-2f)) {
                Text(
                    "THE ANIMAL.", 
                    fontSize = 32.sp, 
                    fontWeight = FontWeight.Black, 
                    letterSpacing = (-1).sp,
                    modifier = Modifier
                        .shadow(4.dp, androidx.compose.foundation.shape.RoundedCornerShape(0.dp))
                        .background(Color(0xFFE2F163))
                        .border(2.dp, Color.Black)
                        .padding(horizontal = 10.dp, vertical = 2.dp)
                )
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(3.dp, androidx.compose.foundation.shape.RoundedCornerShape(0.dp))
                .background(Color(0xFF0D3311))
                .border(2.dp, Color.Black)
                .padding(12.dp),
            verticalAlignment = Alignment.Top
        ) {
            Icon(Icons.Default.Verified, contentDescription = null, tint = Color(0xFFE2F163), modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text("Clear photos help identify visible signs.", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.White)
                Spacer(modifier = Modifier.height(4.dp))
                Text("Ensure daylight, maintain a 1–2m distance, and capture both injury focal point & posture.", fontSize = 12.sp, color = Color(0xFFA5D1B1), lineHeight = 16.sp)
            }
        }
    }
}

@Composable
private fun CameraReticleDropzone() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .shadow(5.dp, androidx.compose.foundation.shape.RoundedCornerShape(0.dp))
            .background(Color.White)
            .border(3.dp, Color.Black)
            .padding(12.dp)
    ) {
        // Viewfinder Corners
        CornerBrackets()
        
        // Top HUD Labels
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 36.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            HUDLabel("FOCUS: AUTO-LOCK")
            HUDLabel("ISO: NOMINAL")
        }
        
        // Center Reticle
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .shadow(4.dp, androidx.compose.foundation.shape.RoundedCornerShape(0.dp))
                    .background(Color(0xFF0D3311))
                    .border(2.dp, Color.Black),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.FilterCenterFocus, contentDescription = null, tint = Color(0xFFE2F163), modifier = Modifier.size(32.dp))
                
                // Red Alert Badge
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .offset(x = 6.dp, y = (-6).dp)
                        .size(20.dp)
                        .background(Color(0xFFB01212), CircleShape)
                        .border(1.dp, Color.Black, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text("!", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color.White)
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text("TAP TO OPEN CAMERA", fontSize = 18.sp, fontWeight = FontWeight.Black, textAlign = TextAlign.Center)
            Spacer(modifier = Modifier.height(4.dp))
            Text("OR DROP FIELD IMAGE HERE", fontSize = 12.sp, fontWeight = FontWeight.Black, color = Color.DarkGray)
        }
        
        // Bottom Telemetry
        Column(modifier = Modifier.align(Alignment.BottomCenter)) {
            HorizontalDivider(thickness = 2.dp, color = Color.LightGray)
            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.GpsFixed, contentDescription = null, modifier = Modifier.size(12.dp), tint = Color.DarkGray)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("28.6139° N, 77.2090° E", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color.DarkGray)
                }
                Box(modifier = Modifier.background(Color(0xFFE0E0E0)).padding(horizontal = 8.dp, vertical = 2.dp)) {
                    Text("WAITING ON CAPTURE", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color.DarkGray)
                }
            }
        }
    }
}

@Composable
private fun CornerBrackets() {
    // Top Left
    Box(modifier = Modifier.offset(x = (-4).dp, y = (-4).dp)) {
        Box(modifier = Modifier.size(24.dp).border(3.dp, Color.Black).background(Color.White))
        Box(modifier = Modifier.size(20.dp).offset(x = 3.dp, y = 3.dp).background(Color.White))
    }
    // Top Right
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.TopEnd) {
        Box(modifier = Modifier.offset(x = 4.dp, y = (-4).dp)) {
            Box(modifier = Modifier.size(24.dp).border(3.dp, Color.Black).background(Color.White))
            Box(modifier = Modifier.size(20.dp).offset(x = (-3).dp, y = 3.dp).background(Color.White))
        }
    }
    // Bottom Left
    Box(modifier = Modifier.fillMaxHeight(), contentAlignment = Alignment.BottomStart) {
        Box(modifier = Modifier.offset(x = (-4).dp, y = 4.dp)) {
            Box(modifier = Modifier.size(24.dp).border(3.dp, Color.Black).background(Color.White))
            Box(modifier = Modifier.size(20.dp).offset(x = 3.dp, y = (-3).dp).background(Color.White))
        }
    }
    // Bottom Right
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomEnd) {
        Box(modifier = Modifier.offset(x = 4.dp, y = 4.dp)) {
            Box(modifier = Modifier.size(24.dp).border(3.dp, Color.Black).background(Color.White))
            Box(modifier = Modifier.size(20.dp).offset(x = (-3).dp, y = (-3).dp).background(Color.White))
        }
    }
}

@Composable
private fun HUDLabel(text: String) {
    Box(
        modifier = Modifier
            .background(Color(0xFFFCF9F8))
            .shadow(1.dp, androidx.compose.foundation.shape.RoundedCornerShape(0.dp))
            .border(1.dp, Color.LightGray)
            .padding(horizontal = 6.dp, vertical = 2.dp)
    ) {
        Text(text, fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color.DarkGray)
    }
}

@Composable
private fun ActionButtons() {
    Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Button(
            onClick = { },
            modifier = Modifier.fillMaxWidth().height(56.dp).shadow(5.dp, androidx.compose.foundation.shape.RoundedCornerShape(0.dp)).border(3.dp, Color.Black),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE2F163), contentColor = Color.Black),
            shape = androidx.compose.foundation.shape.RoundedCornerShape(0.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.CameraAlt, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("TAKE PHOTO NOW", fontSize = 14.sp, fontWeight = FontWeight.Black)
                Spacer(modifier = Modifier.width(8.dp))
                Icon(Icons.Default.ArrowForward, contentDescription = null, modifier = Modifier.size(14.dp))
            }
        }
        
        Button(
            onClick = { },
            modifier = Modifier.fillMaxWidth().height(52.dp).shadow(4.dp, androidx.compose.foundation.shape.RoundedCornerShape(0.dp)).border(3.dp, Color.Black),
            colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = Color.Black),
            shape = androidx.compose.foundation.shape.RoundedCornerShape(0.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Image, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("CHOOSE FROM PHOTO GALLERY", fontSize = 12.sp, fontWeight = FontWeight.Black)
            }
        }
    }
}

@Composable
private fun TriageCaptureProtocol() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Text("TRIAGE CAPTURE PROTOCOL", fontSize = 12.sp, fontWeight = FontWeight.Black)
            Text("STANDARDS V2", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color.DarkGray)
        }
        Spacer(modifier = Modifier.height(8.dp))
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            ProtocolRule(
                icon = Icons.Default.WbSunny,
                iconBg = Color(0xFFE2F163),
                title = "1. ILLUMINATION",
                badgeText = "CLEAR",
                badgeBg = Color(0xFFC4F7D4),
                badgeTint = Color.Black,
                desc = "Natural daylight preferred. Toggle torch if ambient light is low; avoid cast shadows over wound pockets."
            )
            ProtocolRule(
                icon = Icons.Default.Shield,
                iconBg = Color(0xFFFFE0E0),
                title = "2. SAFE DISTANCE",
                badgeText = "1-2 METERS",
                badgeBg = Color(0xFFFFE0E0),
                badgeTint = Color(0xFFB01212),
                desc = "Keep at least 1-2m clear. Pain triggers reactive bites. Use device digital zoom rather than cornering the animal."
            )
            ProtocolRule(
                icon = Icons.Default.Pets,
                iconBg = Color(0xFF0D3311),
                iconTint = Color.White,
                title = "3. FULL POSTURE",
                badgeText = "CONTEXT",
                badgeBg = Color(0xFFE0E0E0),
                badgeTint = Color.DarkGray,
                desc = "Include full body stance or limb extension so the diagnostic engine can detect spinal deformities or fractures."
            )
        }
    }
}

@Composable
private fun ProtocolRule(
    icon: ImageVector,
    iconBg: Color,
    iconTint: Color = Color.Black,
    title: String,
    badgeText: String,
    badgeBg: Color,
    badgeTint: Color,
    desc: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(3.dp, androidx.compose.foundation.shape.RoundedCornerShape(0.dp))
            .background(Color(0xFFF6F3F2))
            .border(2.dp, Color.Black)
            .padding(8.dp),
        verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier
                .shadow(2.dp, androidx.compose.foundation.shape.RoundedCornerShape(0.dp))
                .background(iconBg)
                .border(1.dp, Color.Black)
                .size(36.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, contentDescription = null, tint = iconTint, modifier = Modifier.size(18.dp))
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(title, fontSize = 12.sp, fontWeight = FontWeight.Black)
                Spacer(modifier = Modifier.width(6.dp))
                Box(modifier = Modifier.background(badgeBg).padding(horizontal = 6.dp)) {
                    Text(badgeText, fontSize = 10.sp, fontWeight = FontWeight.Black, color = badgeTint)
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(desc, fontSize = 12.sp, fontWeight = FontWeight.Medium, color = Color.DarkGray, lineHeight = 16.sp)
        }
    }
}

@Composable
private fun RecentRescuePreview() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(3.dp, androidx.compose.foundation.shape.RoundedCornerShape(0.dp))
            .background(Color.White)
            .border(2.dp, Color.Black)
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(64.dp)
                .shadow(2.dp, androidx.compose.foundation.shape.RoundedCornerShape(0.dp))
                .border(1.dp, Color.Black)
        ) {
            AsyncImage(
                model = "https://images.unsplash.com/photo-1543466835-00a7907e9de1?auto=format&fit=crop&w=200&q=80",
                contentDescription = "Previous Scan",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(Color.Gray, blendMode = androidx.compose.ui.graphics.BlendMode.Multiply)
            )
            Box(
                modifier = Modifier.align(Alignment.BottomCenter).fillMaxWidth().background(Color.Black.copy(alpha=0.8f)).padding(vertical = 2.dp),
                contentAlignment = Alignment.Center
            ) {
                Text("PREV SCAN", fontSize = 8.sp, fontWeight = FontWeight.Black, color = Color.White)
            }
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("CASE #BZ-4109 IN SHELTER", fontSize = 12.sp, fontWeight = FontWeight.Black)
                Spacer(modifier = Modifier.width(8.dp))
                Box(modifier = Modifier.size(8.dp).background(Color(0xFFC5AB00), CircleShape))
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text("Target stabilized post-triage at Shelter Station 4. Fracture immobilized with splint.", fontSize = 12.sp, fontWeight = FontWeight.Medium, color = Color.DarkGray, lineHeight = 16.sp)
        }
    }
}

@Composable
private fun EmergencyDispatchNotice() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(4.dp, androidx.compose.foundation.shape.RoundedCornerShape(0.dp))
            .background(Color(0xFFFFE0E0))
            .border(2.dp, Color.Black)
            .padding(8.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.WarningAmber, contentDescription = null, tint = Color(0xFFB01212), modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text("CLINICAL SAFEGUARD", fontSize = 18.sp, fontWeight = FontWeight.Black, color = Color(0xFFB01212))
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            "AI visual analysis offers early field triage only. If the animal exhibits pulsatile arterial hemorrhage, cyanotic gums, or comatose collapse, bypass visual upload immediately.",
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFB01212),
            lineHeight = 16.sp
        )
        Spacer(modifier = Modifier.height(12.dp))
        Button(
            onClick = { },
            modifier = Modifier.fillMaxWidth().height(46.dp).shadow(3.dp, androidx.compose.foundation.shape.RoundedCornerShape(0.dp)).border(2.dp, Color.Black),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB01212), contentColor = Color.White),
            shape = androidx.compose.foundation.shape.RoundedCornerShape(0.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Emergency, contentDescription = null, modifier = Modifier.size(14.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("CALL RESCUE DISPATCH NOW", fontSize = 12.sp, fontWeight = FontWeight.Black)
            }
        }
    }
}
