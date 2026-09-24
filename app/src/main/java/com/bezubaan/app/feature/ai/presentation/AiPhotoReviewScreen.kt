package com.bezubaan.app.feature.ai.presentation

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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage

@Composable
fun AiPhotoReviewScreen(
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
            item { ImagePreviewContainer() }
            item { TriageScanAudit() }
            item { AiObservationWarning() }
            item { ActionButtons() }
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
            Text("RETAKE", fontSize = 12.sp, fontWeight = FontWeight.Black)
        }
        
        Row(
            modifier = Modifier
                .background(Color(0xFFE5E2E1))
                .border(2.dp, Color.Black)
                .padding(horizontal = 12.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(modifier = Modifier.size(8.dp).background(Color(0xFF008744), CircleShape))
            Spacer(modifier = Modifier.width(6.dp))
            Text("12.2 MP • HDR CAPTURED", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color(0xFF002210), letterSpacing = 1.sp)
        }
    }
}

@Composable
private fun TacticalHeadline() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("REVIEW", fontSize = 32.sp, fontWeight = FontWeight.Black, letterSpacing = (-1).sp)
            Spacer(modifier = Modifier.width(8.dp))
            Box(modifier = Modifier.rotate(-2f)) {
                Text(
                    "PHOTO.", 
                    fontSize = 32.sp, 
                    fontWeight = FontWeight.Black, 
                    letterSpacing = (-1).sp,
                    modifier = Modifier
                        .shadow(4.dp, androidx.compose.foundation.shape.RoundedCornerShape(0.dp))
                        .background(Color(0xFFE2F163))
                        .border(3.dp, Color.Black)
                        .padding(horizontal = 10.dp, vertical = 2.dp)
                )
            }
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            "Ensure the injured area, eye response, and animal posture are clearly framed prior to neural triage scan.",
            fontSize = 14.sp, 
            fontWeight = FontWeight.Medium, 
            color = Color(0xFF414942), 
            lineHeight = 20.sp
        )
    }
}

@Composable
private fun ImagePreviewContainer() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .shadow(6.dp, androidx.compose.foundation.shape.RoundedCornerShape(0.dp))
            .background(Color(0xFFE5E2E1))
            .border(4.dp, Color.Black)
            .padding(4.dp)
    ) {
        // Image Area
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 36.dp)
                .background(Color(0xFF002210))
        ) {
            AsyncImage(
                model = "https://images.unsplash.com/photo-1543466835-00a7907e9de1?auto=format&fit=crop&w=600&q=80",
                contentDescription = "Captured Photo",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                alpha = 0.8f
            )
            
            // Central AI Diagnostic Bounding Target
            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .background(Color(0xFF002210).copy(alpha = 0.75f))
                    .border(2.dp, Color(0xFFE2F163))
                    .padding(12.dp)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.FilterCenterFocus, contentDescription = null, tint = Color(0xFFE2F163), modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("READY FOR SCAN", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color(0xFFE2F163), letterSpacing = 1.sp)
                    }
                    Text("ALIGNMENT ACCURACY 98.4%", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = Color.White, letterSpacing = 0.5.sp, modifier = Modifier.padding(top = 2.dp))
                }
            }

            // Top Left HUD Badge
            Box(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(12.dp)
                    .background(Color(0xFF002210))
                    .border(2.dp, Color.White.copy(alpha = 0.4f))
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.CameraAlt, contentDescription = null, tint = Color.White, modifier = Modifier.size(10.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("CAM_FEED_01.RAW", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color.White, letterSpacing = 0.8.sp)
                }
            }

            // Top Right HUD Badge
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(12.dp)
                    .background(Color(0xFF002210).copy(alpha = 0.9f))
                    .border(2.dp, Color(0xFFE2F163))
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.LocationOn, contentDescription = null, tint = Color(0xFFE2F163), modifier = Modifier.size(10.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("28.6139° N, 77.2090° E", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color(0xFFE2F163), letterSpacing = 0.8.sp)
                }
            }
            
            // Bottom Corner HUD Badge
            Row(
                modifier = Modifier.align(Alignment.BottomStart).fillMaxWidth().padding(12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Row(
                    modifier = Modifier
                        .background(Color.White.copy(alpha = 0.95f))
                        .border(2.dp, Color.Black)
                        .padding(horizontal = 8.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.CheckCircleOutline, contentDescription = null, tint = Color(0xFF008744), modifier = Modifier.size(12.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("FOCUS: SHARP (POSTURE\nCONFIRMED)", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color.Black, lineHeight = 12.sp)
                }
                
                Column(
                    modifier = Modifier
                        .background(Color(0xFF002210))
                        .border(2.dp, Color.White.copy(alpha = 0.3f))
                        .padding(horizontal = 8.dp, vertical = 6.dp),
                ) {
                    Text("AUTO-ISO", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color.White)
                    Text("240", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color.White)
                }
            }
            
            // Border overlay
            Box(modifier = Modifier.fillMaxSize().padding(12.dp).border(2.dp, Color(0xFFE2F163).copy(alpha = 0.4f)))
        }

        // Image Metadata Ticker Strip
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .background(Color.White)
                .border(3.dp, Color.Black)
                .padding(horizontal = 12.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Pets, contentDescription = null, modifier = Modifier.size(12.dp), tint = Color.Black)
                Spacer(modifier = Modifier.width(4.dp))
                Text("SPECIES: CANINE (CANIS FAMILIARIS)", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color.Black)
            }
            Text("BATCH #804-BZ", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color.Black)
        }
    }
}

@Composable
private fun TriageScanAudit() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(4.dp, androidx.compose.foundation.shape.RoundedCornerShape(0.dp))
            .background(Color.White)
            .border(3.dp, Color.Black)
            .padding(12.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Checklist, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("TRIAGE SCAN AUDIT", fontSize = 14.sp, fontWeight = FontWeight.Black)
            }
            Box(
                modifier = Modifier
                    .background(Color(0xFFC4F7D4))
                    .border(1.dp, Color.Black)
                    .padding(horizontal = 8.dp, vertical = 2.dp)
            ) {
                Text("PASS 2/2", fontSize = 10.sp, fontWeight = FontWeight.Black)
            }
        }
        
        Spacer(modifier = Modifier.height(12.dp))
        
        // Checklist item 1
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF6F3F2))
                .border(2.dp, Color.Black)
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.WbSunny, contentDescription = null, modifier = Modifier.size(16.dp), tint = Color(0xFF008744))
                Spacer(modifier = Modifier.width(8.dp))
                Text("LIGHTING & AMBIENCE", fontSize = 12.sp, fontWeight = FontWeight.Black)
            }
            Box(
                modifier = Modifier
                    .background(Color(0xFFD4EDDA))
                    .border(1.dp, Color.Black)
                    .padding(horizontal = 8.dp, vertical = 2.dp)
            ) {
                Text("OPTIMAL", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color(0xFF155724))
            }
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        
        // Checklist item 2
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF6F3F2))
                .border(2.dp, Color.Black)
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Pets, contentDescription = null, modifier = Modifier.size(16.dp), tint = Color(0xFFC5AB00))
                Spacer(modifier = Modifier.width(8.dp))
                Text("ANATOMICAL FOCUS", fontSize = 12.sp, fontWeight = FontWeight.Black)
            }
            Box(
                modifier = Modifier
                    .background(Color(0xFFE2F163))
                    .border(1.dp, Color.Black)
                    .padding(horizontal = 8.dp, vertical = 2.dp)
            ) {
                Text("HIND LIMB VISIBLE", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color(0xFF211B00))
            }
        }
    }
}

@Composable
private fun AiObservationWarning() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(4.dp, androidx.compose.foundation.shape.RoundedCornerShape(0.dp))
            .background(Color(0xFFF6F3F2))
            .border(3.dp, Color.Black)
            .padding(12.dp),
        verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .shadow(2.dp, androidx.compose.foundation.shape.RoundedCornerShape(0.dp))
                .background(Color(0xFFB01212))
                .border(2.dp, Color.Black),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Default.MedicalServices, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text("AI PRELIMINARY OBSERVATIONS ONLY", fontSize = 14.sp, fontWeight = FontWeight.Black, letterSpacing = (-0.5).sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                "VetVision algorithms suggest potential conditions and urgency level. Not a certified veterinary diagnosis. If animal displays arterial hemorrhage or unresponsive vital signs, bypass triage and call paramedic dispatch instantly.",
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black,
                lineHeight = 16.sp
            )
        }
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
                Text("ANALYZE WITH AI", fontSize = 18.sp, fontWeight = FontWeight.Black)
                Spacer(modifier = Modifier.width(8.dp))
                Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, modifier = Modifier.size(18.dp))
            }
        }
        
        Button(
            onClick = { },
            modifier = Modifier.fillMaxWidth().height(48.dp).shadow(4.dp, androidx.compose.foundation.shape.RoundedCornerShape(0.dp)).border(3.dp, Color.Black),
            colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = Color.Black),
            shape = androidx.compose.foundation.shape.RoundedCornerShape(0.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Refresh, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("RETAKE PHOTO", fontSize = 14.sp, fontWeight = FontWeight.Black)
            }
        }
    }
}

@Composable
private fun EmergencyDispatchNotice() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(3.dp, androidx.compose.foundation.shape.RoundedCornerShape(0.dp))
            .background(Color(0xFF002210))
            .border(2.dp, Color.Black)
            .padding(14.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.PhoneCallback, contentDescription = null, tint = Color(0xFFE2F163), modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text("NEED IMMEDIATE PARAMEDIC?", fontSize = 12.sp, fontWeight = FontWeight.Black, color = Color.White)
        }
        Spacer(modifier = Modifier.height(8.dp))
        Box(
            modifier = Modifier
                .background(Color(0xFFE2F163))
                .border(2.dp, Color.Black)
                .padding(horizontal = 14.dp, vertical = 6.dp)
        ) {
            Text("DISPATCH: 1800-BEZUBAAN", fontSize = 12.sp, fontWeight = FontWeight.Black, color = Color.Black)
        }
    }
}
