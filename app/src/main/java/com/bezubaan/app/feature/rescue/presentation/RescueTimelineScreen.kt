package com.bezubaan.app.feature.rescue.presentation

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage

@Composable
fun RescueTimelineScreen(
    onBackToDossier: () -> Unit
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
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item { TopContextNavigationBar(onBackToDossier) }
            item { ScreenHeaderBanner() }
            item { PatientResponderMiniHud() }
            item { TimelineStream() }
            item { Spacer(modifier = Modifier.height(24.dp)) }
        }
    }
}

@Composable
private fun TopHeader() {
    Column(modifier = Modifier.fillMaxWidth().background(Color(0xFFFCF9F8))) {
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
                        .background(Color(0xFFFFE24E))
                        .border(2.dp, Color.Black)
                        .padding(4.dp)
                ) {
                    Icon(Icons.Default.LocationOn, contentDescription = null, modifier = Modifier.size(20.dp))
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text("BEZUBAAN", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                    Text("VETVISION AI TRIAGE", fontSize = 14.sp, fontWeight = FontWeight.Black)
                }
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .background(Color(0xFFFFE24E))
                        .padding(4.dp)
                ) {
                    Icon(Icons.Default.Notifications, contentDescription = "Alerts", modifier = Modifier.size(24.dp))
                }
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
private fun TopContextNavigationBar(onBackToDossier: () -> Unit) {
    Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                onClick = onBackToDossier,
                modifier = Modifier
                    .shadow(3.dp, RoundedCornerShape(4.dp)),
                color = Color.White,
                shape = RoundedCornerShape(4.dp),
                border = androidx.compose.foundation.BorderStroke(3.dp, Color(0xFF002210))
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 15.dp, vertical = 11.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null, modifier = Modifier.size(12.dp), tint = Color(0xFF1C1B1B))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("DOSSIER", fontSize = 12.sp, fontWeight = FontWeight.Black, color = Color(0xFF1C1B1B))
                }
            }
            
            Box(
                modifier = Modifier
                    .shadow(2.dp, RoundedCornerShape(4.dp))
                    .border(2.dp, Color(0xFF002210), RoundedCornerShape(4.dp))
                    .background(Color(0xFFFFE24E), RoundedCornerShape(4.dp))
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.size(8.dp).background(Color(0xFFBA1A1A), CircleShape))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("LIVE LIFECYCLE", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color(0xFF211B00))
                }
            }
        }
        
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(4.dp, RoundedCornerShape(4.dp))
                .border(3.dp, Color(0xFF002210), RoundedCornerShape(4.dp))
                .background(Color(0xFF002210), RoundedCornerShape(4.dp))
                .padding(horizontal = 15.dp, vertical = 11.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.FolderOpen, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("CASE #BZ-804", fontSize = 18.sp, fontWeight = FontWeight.Black, color = Color.White)
            }
            Box(modifier = Modifier.background(Color(0xFFFFE24E), RoundedCornerShape(2.dp)).padding(horizontal = 8.dp, vertical = 2.dp)) {
                Text("ETA 7 MINS", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color(0xFF211B00))
            }
        }
    }
}

@Composable
private fun ScreenHeaderBanner() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("RESCUE", fontSize = 32.sp, fontWeight = FontWeight.Black, color = Color(0xFF1C1B1B), letterSpacing = (-0.8).sp)
            Spacer(modifier = Modifier.width(8.dp))
            Box(
                modifier = Modifier
                    .rotate(-1f)
                    .shadow(4.dp, RoundedCornerShape(4.dp))
                    .border(3.dp, Color(0xFF002210), RoundedCornerShape(4.dp))
                    .background(Color(0xFFFFE24E), RoundedCornerShape(4.dp))
                    .padding(horizontal = 15.dp, vertical = 5.dp)
            ) {
                Text("TIMELINE.", fontSize = 32.sp, fontWeight = FontWeight.Black, color = Color(0xFF211B00), letterSpacing = (-0.96).sp)
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            "End-to-end telemetry and verification lifecycle from citizen report to clinical recovery.",
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF414942),
            lineHeight = 19.sp
        )
    }
}

@Composable
private fun PatientResponderMiniHud() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(4.dp, RoundedCornerShape(8.dp))
            .border(3.dp, Color(0xFF002210), RoundedCornerShape(8.dp))
            .background(Color.White, RoundedCornerShape(8.dp))
    ) {
        // Top row - Patient
        Row(
            modifier = Modifier.fillMaxWidth().padding(15.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .border(2.dp, Color(0xFF002210), RoundedCornerShape(4.dp))
                    .clip(RoundedCornerShape(4.dp))
            ) {
                AsyncImage(
                    model = "https://images.unsplash.com/photo-1544568100-847a948585b9?auto=format&fit=crop&w=300&q=80",
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                Box(
                    modifier = Modifier.align(Alignment.BottomEnd).background(Color(0xFFBA1A1A)).padding(horizontal = 4.dp, vertical = 2.dp)
                ) {
                    Text("P2", fontSize = 9.sp, fontWeight = FontWeight.Black, color = Color.White)
                }
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Text("RUSTY", fontSize = 18.sp, fontWeight = FontWeight.Black, color = Color(0xFF1C1B1B))
                    Box(modifier = Modifier.background(Color(0xFFF0EDEC), RoundedCornerShape(2.dp)).border(1.dp, Color(0xFF002210), RoundedCornerShape(2.dp)).padding(horizontal = 7.dp, vertical = 3.dp)) {
                        Text("STREET TAG", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color(0xFF1C1B1B))
                    }
                }
                Spacer(modifier = Modifier.height(2.dp))
                Text("Desi Dog • Connaught Place, CP Radial 3", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF414942))
                Spacer(modifier = Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.size(8.dp).background(Color(0xFF0F3822), CircleShape))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("RESPONDER ASSIGNED", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color(0xFF002210))
                }
            }
        }
        
        // Bottom row - Responder
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF6F3F2))
                .border(2.dp, Color(0xFF002210), RoundedCornerShape(bottomStart = 6.dp, bottomEnd = 6.dp))
                .padding(horizontal = 12.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(modifier = Modifier.size(28.dp).background(Color(0xFF002210), CircleShape), contentAlignment = Alignment.Center) {
                    Text("AV", fontSize = 16.sp, fontWeight = FontWeight.Black, color = Color.White)
                }
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text("LEAD AAKASH V.", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color(0xFF1C1B1B))
                    Text("Bajaj Pulsar • 1.1 km away", fontSize = 11.sp, fontWeight = FontWeight.Normal, color = Color(0xFF414942))
                }
            }
            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .shadow(2.dp, RoundedCornerShape(4.dp))
                        .border(2.dp, Color(0xFF002210), RoundedCornerShape(4.dp))
                        .background(Color(0xFFFFE24E), RoundedCornerShape(4.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.Phone, contentDescription = "Call", modifier = Modifier.size(16.dp))
                }
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .shadow(2.dp, RoundedCornerShape(4.dp))
                        .border(2.dp, Color(0xFF002210), RoundedCornerShape(4.dp))
                        .background(Color.White, RoundedCornerShape(4.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.ChatBubbleOutline, contentDescription = "Message", modifier = Modifier.size(16.dp))
                }
            }
        }
    }
}

@Composable
private fun TimelineStream() {
    Box(modifier = Modifier.fillMaxWidth()) {
        // The Spine
        val completedSpineColor = Color(0xFF002210)
        val pendingSpineColor = Color(0xFFC1C8C0)
        
        Canvas(modifier = Modifier.fillMaxWidth().height(1600.dp)) {
            // Hardcode rough spine length based on content height
            drawLine(
                color = completedSpineColor,
                start = Offset(x = 16.dp.toPx() + 16.dp.toPx(), y = 24.dp.toPx()),
                end = Offset(x = 16.dp.toPx() + 16.dp.toPx(), y = 800.dp.toPx()),
                strokeWidth = 3.5.dp.toPx()
            )
            
            drawLine(
                color = pendingSpineColor,
                start = Offset(x = 16.dp.toPx() + 16.dp.toPx(), y = 800.dp.toPx()),
                end = Offset(x = 16.dp.toPx() + 16.dp.toPx(), y = 1400.dp.toPx()),
                strokeWidth = 3.5.dp.toPx(),
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
            )
        }
        
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            TimelineNode(
                stageNum = "01",
                time = "14:28 IST",
                title = "REPORTED",
                desc = "Logged by citizen reporter Priya M. GPS fix at 28.6139° N, 77.2090° E with 3 photo evidence frames.",
                state = NodeState.COMPLETED
            )
            TimelineNode(
                stageNum = "02",
                time = "14:29 IST",
                title = "UNDER REVIEW",
                desc = "Intake dispatcher validated telemetry, incident duplicates & traffic safety perimeter at Inner Circle.",
                state = NodeState.COMPLETED
            )
            
            // Action Panel injected between 2 and 3
            Column(modifier = Modifier.padding(start = 48.dp, bottom = 8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(
                    onClick = { /* Share */ },
                    modifier = Modifier.fillMaxWidth().height(48.dp).shadow(3.dp, RoundedCornerShape(4.dp)).border(2.dp, Color.Black, RoundedCornerShape(4.dp)),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFE24E), contentColor = Color(0xFF1C1B1B)),
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Icon(Icons.Default.Share, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("SHARE LIVE CASE TRACKING", fontSize = 14.sp, fontWeight = FontWeight.Black)
                }
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(
                        onClick = { /* Flag */ },
                        modifier = Modifier.weight(1f).height(36.dp).shadow(2.dp, RoundedCornerShape(4.dp)).border(2.dp, Color.Black, RoundedCornerShape(4.dp)),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFEBEB), contentColor = Color(0xFFBA1A1A)),
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Icon(Icons.Default.Flag, contentDescription = null, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("FLAG ANOMALY", fontSize = 10.sp, fontWeight = FontWeight.Black)
                    }
                    Button(
                        onClick = { /* Dispatch Desk */ },
                        modifier = Modifier.weight(1f).height(36.dp).shadow(2.dp, RoundedCornerShape(4.dp)).border(2.dp, Color.Black, RoundedCornerShape(4.dp)),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = Color(0xFF414942)),
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Icon(Icons.Default.HeadsetMic, contentDescription = null, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("DISPATCH DESK", fontSize = 10.sp, fontWeight = FontWeight.Black)
                    }
                }
            }

            TimelineNode(
                stageNum = "03 • AI ENGINE",
                time = "14:29 IST",
                title = "AI ANALYZED",
                desc = "VetVision Vision Model triage: 92% confidence, P2 moderate soft-tissue trauma on left paw, no arterial hemorrhaging detected.",
                state = NodeState.COMPLETED
            )
            TimelineNode(
                stageNum = "04",
                time = "14:30 IST",
                title = "EQUIPMENT TRIAGED",
                desc = "Medical kit requisitions: Medium nylon crate, sterile gauze, betadine solution & safety muzzle.",
                state = NodeState.COMPLETED
            )
            TimelineNode(
                stageNum = "05",
                time = "14:30 IST",
                title = "VERIFIED AUTHENTIC",
                desc = "Incident cross-referenced and cleared by Central Delhi Dispatch Officer #D-12.",
                state = NodeState.COMPLETED
            )
            TimelineNode(
                stageNum = "06",
                time = "14:31 IST",
                title = "RADAR BROADCAST",
                desc = "Pinged 4 registered volunteers within 3 km geo-fence. 2 acknowledgments received.",
                state = NodeState.COMPLETED
            )
            TimelineNode(
                stageNum = "07",
                time = "14:32 IST",
                title = "VOLUNTEER LOCKED",
                desc = "Squad Lead Aakash V. locked case-ownership and mobilized from Barakhamba staging base.",
                state = NodeState.COMPLETED
            )
            
            // ACTIVE NODE (Stage 08)
            ActiveTimelineNode(
                stageNum = "08 - IN PROGRESS",
                title = "EN ROUTE TO PATIENT",
                desc = "Aakash V. navigating through heavy Janpath traffic. Riding with trauma first-aid kit and secure pet carrier.",
                distance = "1.1 KM",
                speed = "28 KM/H",
                eta = "~7 MIN"
            )

            // PENDING NODES
            PendingTimelineNode(
                stageNum = "09",
                statusBadge = "UPCOMING",
                title = "CONTAINMENT & RESCUE",
                desc = "Safe physical transfer into medical crate, field vital check, and wound cleaning on site."
            )
            PendingTimelineNode(
                stageNum = "10",
                statusBadge = "PENDING",
                title = "AT CLINICAL HOSPITAL",
                desc = "Dr. Sharma's 24/7 Trauma Veterinary Hospital reserved for paw X-ray and antibacterial dressing."
            )
            PendingTimelineNode(
                stageNum = "11",
                statusBadge = "FINAL",
                title = "FOSTER & ADOPTION",
                desc = "Post-operative shelter recuperation, community food sponsor assignment, and public adoption roster."
            )
        }
    }
}

enum class NodeState { COMPLETED, PENDING }

@Composable
private fun TimelineNode(
    stageNum: String,
    time: String,
    title: String,
    desc: String,
    state: NodeState
) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .padding(top = 8.dp)
                .size(32.dp)
                .shadow(2.dp, RoundedCornerShape(4.dp))
                .border(2.dp, Color(0xFF002210), RoundedCornerShape(4.dp))
                .background(Color(0xFF002210), RoundedCornerShape(4.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Default.Check, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
        }
        Spacer(modifier = Modifier.width(16.dp))
        
        Column(
            modifier = Modifier
                .weight(1f)
                .shadow(3.dp, RoundedCornerShape(4.dp))
                .border(2.dp, Color(0xFF002210), RoundedCornerShape(4.dp))
                .background(Color.White, RoundedCornerShape(4.dp))
                .padding(14.dp)
        ) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text("STAGE $stageNum", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color(0xFF414942))
                Box(modifier = Modifier.background(Color(0xFFF0EDEC), RoundedCornerShape(2.dp)).padding(horizontal = 6.dp, vertical = 2.dp)) {
                    Text(time, fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color(0xFF1C1B1B))
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(title, fontSize = 18.sp, fontWeight = FontWeight.Black, color = Color(0xFF1C1B1B))
            Spacer(modifier = Modifier.height(2.dp))
            Text(desc, fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF414942), lineHeight = 16.sp)
        }
    }
}

@Composable
private fun ActiveTimelineNode(
    stageNum: String,
    title: String,
    desc: String,
    distance: String,
    speed: String,
    eta: String
) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .padding(top = 8.dp)
                .size(32.dp)
                .shadow(2.dp, RoundedCornerShape(4.dp))
                .border(2.dp, Color(0xFF002210), RoundedCornerShape(4.dp))
                .background(Color(0xFFFFE24E), RoundedCornerShape(4.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Default.ElectricScooter, contentDescription = null, tint = Color(0xFF002210), modifier = Modifier.size(16.dp))
        }
        Spacer(modifier = Modifier.width(16.dp))
        
        Column(
            modifier = Modifier
                .weight(1f)
                .shadow(4.dp, RoundedCornerShape(4.dp))
                .border(3.dp, Color(0xFF002210), RoundedCornerShape(4.dp))
                .background(Color.White, RoundedCornerShape(4.dp))
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth().background(Color(0xFFFFE24E)).padding(horizontal = 12.dp, vertical = 6.dp).border(1.dp, Color.Transparent), // Border to keep consistent width/height bounds
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.size(8.dp).background(Color(0xFFBA1A1A), CircleShape))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("STAGE $stageNum", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color(0xFF211B00))
                }
                Box(modifier = Modifier.background(Color(0xFF002210), RoundedCornerShape(2.dp)).padding(horizontal = 6.dp, vertical = 2.dp)) {
                    Text("ACTIVE", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color.White)
                }
            }
            HorizontalDivider(color = Color(0xFF002210), thickness = 2.dp)
            
            // Content
            Column(modifier = Modifier.padding(14.dp)) {
                Text(title, fontSize = 18.sp, fontWeight = FontWeight.Black, color = Color(0xFF1C1B1B))
                Spacer(modifier = Modifier.height(4.dp))
                Text(desc, fontSize = 12.sp, fontWeight = FontWeight.Medium, color = Color(0xFF414942), lineHeight = 16.sp)
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Telemetry metrics
                Row(
                    modifier = Modifier.fillMaxWidth().background(Color(0xFFF0EDEC), RoundedCornerShape(4.dp)).border(1.dp, Color(0xFF002210), RoundedCornerShape(4.dp)).padding(10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text("DISTANCE", fontSize = 8.sp, fontWeight = FontWeight.Black, color = Color(0xFF414942))
                        Text(distance, fontSize = 14.sp, fontWeight = FontWeight.Black, color = Color(0xFF1C1B1B))
                    }
                    Box(modifier = Modifier.width(1.dp).height(24.dp).background(Color(0xFFC1C8C0)))
                    Column {
                        Text("SPEED", fontSize = 8.sp, fontWeight = FontWeight.Black, color = Color(0xFF414942))
                        Text(speed, fontSize = 14.sp, fontWeight = FontWeight.Black, color = Color(0xFF1C1B1B))
                    }
                    Box(modifier = Modifier.width(1.dp).height(24.dp).background(Color(0xFFC1C8C0)))
                    Column {
                        Text("ETA", fontSize = 8.sp, fontWeight = FontWeight.Black, color = Color(0xFF414942))
                        Text(eta, fontSize = 14.sp, fontWeight = FontWeight.Black, color = Color(0xFF1C1B1B))
                    }
                }
                
                Spacer(modifier = Modifier.height(12.dp))
                
                // Mini map fake
                Box(
                    modifier = Modifier.fillMaxWidth().height(80.dp).background(Color(0xFFEBE7E7), RoundedCornerShape(4.dp)).border(1.dp, Color(0xFF002210), RoundedCornerShape(4.dp))
                ) {
                    AsyncImage(
                        model = "https://images.unsplash.com/photo-1524661135-423995f22d0b?auto=format&fit=crop&w=800&q=80",
                        contentDescription = "Map",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(4.dp))
                    )
                    Box(
                        modifier = Modifier.align(Alignment.BottomStart).padding(8.dp).background(Color.White, RoundedCornerShape(4.dp)).border(1.dp, Color.Black, RoundedCornerShape(4.dp)).padding(horizontal = 6.dp, vertical = 4.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Navigation, contentDescription = null, tint = Color(0xFFBA1A1A), modifier = Modifier.size(10.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("CP RADIAL 3 - LIVE PING", fontSize = 8.sp, fontWeight = FontWeight.Black, color = Color(0xFF1C1B1B))
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Action Buttons
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(
                        onClick = { /* Call */ },
                        modifier = Modifier.weight(1f).height(40.dp).border(2.dp, Color.Black, RoundedCornerShape(4.dp)),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = Color(0xFF1C1B1B)),
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Icon(Icons.Default.Phone, contentDescription = null, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("CALL", fontSize = 10.sp, fontWeight = FontWeight.Black)
                    }
                    Button(
                        onClick = { /* Radar */ },
                        modifier = Modifier.weight(1f).height(40.dp).border(2.dp, Color.Black, RoundedCornerShape(4.dp)),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF002210), contentColor = Color.White),
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Icon(Icons.Default.Radar, contentDescription = null, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("LIVE RADAR", fontSize = 10.sp, fontWeight = FontWeight.Black)
                    }
                }
            }
        }
    }
}

@Composable
private fun PendingTimelineNode(
    stageNum: String,
    statusBadge: String,
    title: String,
    desc: String
) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .padding(top = 8.dp)
                .size(32.dp)
                .background(Color.White, RoundedCornerShape(4.dp))
                .border(2.dp, Color(0xFFC1C8C0), RoundedCornerShape(4.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(stageNum, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color(0xFF727972))
        }
        Spacer(modifier = Modifier.width(16.dp))
        
        Column(
            modifier = Modifier
                .weight(1f)
                .background(Color.White, RoundedCornerShape(4.dp))
                .border(2.dp, Color(0xFFC1C8C0), RoundedCornerShape(4.dp))
                .padding(14.dp)
        ) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text("STAGE $stageNum", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color(0xFF727972))
                Box(modifier = Modifier.background(Color(0xFFF0EDEC), RoundedCornerShape(2.dp)).padding(horizontal = 6.dp, vertical = 2.dp)) {
                    Text(statusBadge, fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color(0xFF727972))
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(title, fontSize = 16.sp, fontWeight = FontWeight.Black, color = Color(0xFF727972))
            Spacer(modifier = Modifier.height(2.dp))
            Text(desc, fontSize = 12.sp, fontWeight = FontWeight.Medium, color = Color(0xFF727972), lineHeight = 16.sp)
        }
    }
}
