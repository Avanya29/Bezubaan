package com.bezubaan.app.feature.volunteer.presentation

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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage

val bgGray = Color(0xFFF7F7F7)
val mintGreen = Color(0xFFC4F7D4)
val darkGreen = Color(0xFF0D3311)
val yellow = Color(0xFFE2F163)
val redAlert = Color(0xFFFF4D4D)
val darkRed = Color(0xFFB01212)
val lightPink = Color(0xFFFFE0E0)
val thickBorder = 3.dp

@Composable
fun VolunteerDashboardScreen(
    viewModel: VolunteerDashboardViewModel = hiltViewModel(),
    onNavigateToEmergencyDispatch: () -> Unit = {}
) {
    val isOffline by viewModel.isOffline.collectAsStateWithLifecycle()
    val emergencyRescue by viewModel.emergencyRescue.collectAsStateWithLifecycle()

    // Show Dialog when an emergency comes in
    if (emergencyRescue != null) {
        Dialog(
            onDismissRequest = { viewModel.dismissEmergency() },
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f))
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                NewRescueDispatchCard(
                    // Pass actual data if needed: emergencyRescue
                    onAcceptPing = {
                        viewModel.dismissEmergency()
                        onNavigateToEmergencyDispatch()
                    },
                    onPass = { viewModel.dismissEmergency() }
                )
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(bgGray)
    ) {
        TopBar()
        
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item { UnitCallsignCard(isOffline) }
            
            if (isOffline) {
                item { OfflineStatusCard(onGoAvailable = { viewModel.setOffline(false) }) }
                item { CriticalSurgeCard() }
                item { ProximityRadarCard() }
                item { OfflinePreFlightReadinessList() }
                item { ShiftLogCard() }
            } else {
                item { ResponderStatusCard(onGoUnavailable = { viewModel.setOffline(true) }) }
                item { RadarActiveStrip() }
                // The dispatch card is now shown as a Dialog popup instead of inline!
                item { EquipmentCard() }
                item { StatsRow() }
                item { DirectCommandHotlineCard() }
            }
            
            item { Spacer(modifier = Modifier.height(80.dp)) } // Padding for bottom nav
        }
    }
}

@Composable
private fun TopBar() {
    Column(modifier = Modifier.fillMaxWidth().background(Color.White)) {
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
                        .background(yellow, RoundedCornerShape(4.dp))
                        .border(2.dp, Color.Black, RoundedCornerShape(4.dp))
                        .padding(4.dp)
                ) {
                    Icon(Icons.Default.LocationOn, contentDescription = null, modifier = Modifier.size(20.dp))
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text("BEZUBAAN", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color.DarkGray)
                    Text("RESCUE", fontSize = 16.sp, fontWeight = FontWeight.Black)
                }
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
private fun UnitCallsignCard(isOffline: Boolean) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .border(thickBorder, Color.Black)
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text("CALL-SIGN", fontSize = 9.sp, fontWeight = FontWeight.Black, color = Color.DarkGray, fontFamily = FontFamily.Monospace)
            Spacer(modifier = Modifier.height(2.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(modifier = Modifier.size(8.dp).background(if (isOffline) Color.Gray else darkGreen, CircleShape).border(1.dp, Color.Black, CircleShape))
                Spacer(modifier = Modifier.width(6.dp))
                Text("ADITI-VOL-048", fontSize = 16.sp, fontWeight = FontWeight.Black)
            }
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (isOffline) {
                Box(modifier = Modifier.border(2.dp, Color.Black).padding(horizontal = 8.dp, vertical = 4.dp)) {
                    Text("VOLUNTEER", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color.DarkGray)
                }
            } else {
                Row(
                    modifier = Modifier.background(mintGreen).border(2.dp, Color.Black).padding(horizontal = 8.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(modifier = Modifier.size(6.dp).background(darkGreen, CircleShape))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("DUTY ON", fontSize = 10.sp, fontWeight = FontWeight.Black, color = darkGreen)
                }
            }
            Spacer(modifier = Modifier.width(8.dp))
            Row(modifier = Modifier.border(2.dp, Color.Black).padding(horizontal = 8.dp, vertical = 4.dp), verticalAlignment = Alignment.CenterVertically) {
                if (isOffline) {
                    Icon(Icons.Default.Logout, contentDescription = null, tint = redAlert, modifier = Modifier.size(10.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                }
                Text("EXIT", fontSize = 10.sp, fontWeight = FontWeight.Black, color = redAlert)
            }
        }
    }
}

// --- OFFLINE COMPONENTS ---

@Composable
private fun OfflineStatusCard(onGoAvailable: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(4.dp, RoundedCornerShape(0.dp))
            .background(Color.White)
            .border(thickBorder, Color.Black)
            .padding(16.dp)
    ) {
        Column {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.Top) {
                Row(
                    modifier = Modifier.border(2.dp, Color.Black).padding(horizontal = 8.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(modifier = Modifier.size(6.dp).background(Color.Gray, CircleShape))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("STATUS: OFFLINE // RESTING", fontSize = 9.sp, fontWeight = FontWeight.Black, color = Color.DarkGray, letterSpacing = 1.sp)
                }
                Box(modifier = Modifier.size(40.dp).background(Color.LightGray, CircleShape), contentAlignment = Alignment.Center) {
                    Text("GRID 4-8", fontSize = 8.sp, fontWeight = FontWeight.Black, color = Color.DarkGray, textAlign = TextAlign.Center)
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text("DISPATCH CONSOLE", fontSize = 11.sp, fontWeight = FontWeight.Black, color = Color.DarkGray)
            Text(
                "VOLUNTEER",
                fontSize = 28.sp,
                fontWeight = FontWeight.Black,
                fontFamily = FontFamily.Monospace,
                letterSpacing = (-1).sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Box(
                modifier = Modifier.background(Color.Black).padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text("UNAVAILABLE", fontSize = 16.sp, fontWeight = FontWeight.Black, color = Color.White)
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                "You are currently off-duty. You will not receive emergency dispatch pings or nearby verified SOS triage requests.",
                fontSize = 12.sp,
                color = Color.DarkGray,
                lineHeight = 16.sp
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = onGoAvailable,
                modifier = Modifier.fillMaxWidth().height(48.dp).border(thickBorder, Color.Black),
                colors = ButtonDefaults.buttonColors(containerColor = yellow, contentColor = Color.Black),
                shape = RoundedCornerShape(0.dp)
            ) {
                Text("⚡ GO AVAILABLE ➔", fontSize = 14.sp, fontWeight = FontWeight.Black)
            }
        }
    }
}

@Composable
private fun CriticalSurgeCard() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(lightPink)
            .border(thickBorder, Color.Black)
            .padding(16.dp),
        verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier
                .background(darkRed, RoundedCornerShape(4.dp))
                .border(2.dp, Color.Black, RoundedCornerShape(4.dp))
                .padding(8.dp)
        ) {
            Icon(Icons.Default.WarningAmber, contentDescription = null, tint = Color.White, modifier = Modifier.size(20.dp))
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(modifier = Modifier.background(darkRed).padding(horizontal = 6.dp, vertical = 2.dp)) {
                    Text("CRITICAL SURGE", fontSize = 8.sp, fontWeight = FontWeight.Black, color = Color.White)
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text("PEAK TRAFFIC HOURS", fontSize = 8.sp, fontWeight = FontWeight.Black, color = darkRed)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text("2 INJURED STRAYS PENDING TRIAGE", fontSize = 14.sp, fontWeight = FontWeight.Black, color = darkRed, lineHeight = 18.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                "Reported within 2.4 km of your perimeter. Responders needed for stabilization and clinic transport.",
                fontSize = 11.sp,
                color = darkRed,
                lineHeight = 16.sp
            )
        }
    }
}

@Composable
private fun ProximityRadarCard() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .border(thickBorder, Color.Black)
            .padding(12.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Radar, contentDescription = null, modifier = Modifier.size(16.dp), tint = darkGreen)
                Spacer(modifier = Modifier.width(8.dp))
                Text("PROXIMITY RADAR [STANDBY]", fontSize = 11.sp, fontWeight = FontWeight.Black)
            }
            Box(modifier = Modifier.border(1.dp, Color.Black).padding(horizontal = 6.dp, vertical = 2.dp)) {
                Text("MUTED", fontSize = 9.sp, fontWeight = FontWeight.Black, color = Color.DarkGray)
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .background(Color.LightGray)
                .border(2.dp, Color.Black),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = "https://images.unsplash.com/photo-1524661135-423995f22d0b?auto=format&fit=crop&w=400&q=80",
                contentDescription = "Muted Map",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                alpha = 0.3f
            )
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Box(modifier = Modifier.background(Color.White).border(1.dp, Color.Black).padding(8.dp), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("INDIRANAGAR // KORAMANGALA SECTOR", fontSize = 8.sp, fontWeight = FontWeight.Black)
                        Text("3 ACTIVE CITIZEN REPORTS", fontSize = 12.sp, fontWeight = FontWeight.Black, color = darkRed)
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text("SWITCH TO AVAILABLE TO UNLOCK LIVE BEACON PINS", fontSize = 8.sp, fontWeight = FontWeight.Black, color = Color.White)
            }
        }
    }
}

@Composable
private fun OfflinePreFlightReadinessList() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(bgGray)
    ) {
        Row(modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Checklist, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("PRE-FLIGHT READINESS", fontSize = 12.sp, fontWeight = FontWeight.Black)
            }
            Text("3 OF 3 READY", fontSize = 9.sp, fontWeight = FontWeight.Black, color = Color.DarkGray)
        }
        
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            OfflineChecklistItem("FIRST-AID RESCUE KIT", "Gloves, muzzle, antiseptic spray, sterile gau...")
            OfflineChecklistItem("DEVICE BATTERY // 82%", "GPS precision high • Cellular nominal")
            OfflineChecklistItem("DEFAULT RECEPTION CLINIC", "Mission Paws 24/7 Trauma Wing (1.8 km)")
        }
    }
}

@Composable
private fun OfflineChecklistItem(title: String, subtitle: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .border(thickBorder, Color.Black)
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .background(darkGreen)
                    .border(2.dp, Color.Black),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.Check, contentDescription = null, tint = mintGreen, modifier = Modifier.size(16.dp))
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(title, fontSize = 11.sp, fontWeight = FontWeight.Black)
                Spacer(modifier = Modifier.height(2.dp))
                Text(subtitle, fontSize = 10.sp, color = Color.DarkGray)
            }
        }
        Icon(Icons.Default.AddBox, contentDescription = null, tint = Color.DarkGray, modifier = Modifier.size(20.dp))
    }
}

@Composable
private fun ShiftLogCard() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(bgGray)
    ) {
        Row(modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.History, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("SHIFT LOG // RECENT ACTIVITY", fontSize = 11.sp, fontWeight = FontWeight.Black)
            }
            Text("TODAY", fontSize = 9.sp, fontWeight = FontWeight.Black, color = Color.DarkGray)
        }
        
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .border(thickBorder, Color.Black)
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(mintGreen)
                    .border(2.dp, Color.Black),
                contentAlignment = Alignment.Center
            ) {
                Text("#89", fontSize = 14.sp, fontWeight = FontWeight.Black)
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("CASE #BZ-8921", fontSize = 11.sp, fontWeight = FontWeight.Black)
                    Spacer(modifier = Modifier.width(8.dp))
                    Box(modifier = Modifier.background(mintGreen).border(1.dp, Color.Black).padding(horizontal = 4.dp, vertical = 2.dp)) {
                        Text("RESOLVED", fontSize = 8.sp, fontWeight = FontWeight.Black, color = darkGreen)
                    }
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text("Fractured Paw Stabilized • Admitt...", fontSize = 10.sp, color = Color.DarkGray)
            }
            Column(horizontalAlignment = Alignment.End) {
                Text("3H AGO", fontSize = 9.sp, fontWeight = FontWeight.Black)
                Text("14:12 IST", fontSize = 9.sp, color = Color.DarkGray)
            }
        }
    }
}


// --- ACTIVE COMPONENTS ---

@Composable
private fun ResponderStatusCard(onGoUnavailable: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(4.dp, RoundedCornerShape(0.dp))
            .background(mintGreen)
            .border(thickBorder, Color.Black)
            .padding(16.dp)
    ) {
        Column {
            Row(
                modifier = Modifier.background(darkGreen, RoundedCornerShape(4.dp)).padding(horizontal = 8.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(modifier = Modifier.size(6.dp).background(mintGreen, CircleShape))
                Spacer(modifier = Modifier.width(6.dp))
                Text("GPS BROADCAST ACTIVE", fontSize = 9.sp, fontWeight = FontWeight.Black, color = mintGreen, letterSpacing = 1.sp)
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text("RESPONDER STATUS", fontSize = 11.sp, fontWeight = FontWeight.Black, color = darkGreen)
            Text(
                "VOLUNTEER MODE",
                fontSize = 28.sp,
                fontWeight = FontWeight.Black,
                fontFamily = FontFamily.Monospace,
                letterSpacing = (-1).sp,
                color = darkGreen
            )
            Spacer(modifier = Modifier.height(8.dp))
            Box(
                modifier = Modifier.background(darkGreen).padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text("AVAILABLE", fontSize = 16.sp, fontWeight = FontWeight.Black, color = Color.White)
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                "Your location beacon is live. You are prioritized for immediate street triage & transit rescue within 3.5 km.",
                fontSize = 12.sp,
                color = darkGreen,
                lineHeight = 16.sp
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = onGoUnavailable,
                modifier = Modifier.fillMaxWidth().height(48.dp).border(thickBorder, Color.Black),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = darkRed),
                shape = RoundedCornerShape(0.dp)
            ) {
                Text("✋ GO UNAVAILABLE (PAUSE)", fontSize = 12.sp, fontWeight = FontWeight.Black)
            }
        }
    }
}

@Composable
private fun RadarActiveStrip() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .border(thickBorder, Color.Black)
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(yellow, RoundedCornerShape(4.dp))
                .border(2.dp, Color.Black, RoundedCornerShape(4.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Default.AdsClick, contentDescription = null, tint = Color.Black)
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("RADAR ACTIVE // 3.5 KM", fontSize = 10.sp, fontWeight = FontWeight.Black)
                Text("  •  AUTO-DISPATCH", fontSize = 9.sp, fontWeight = FontWeight.Black, color = Color.DarkGray)
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text("Scanning: Indiranagar 100ft, Halasuru Lake & Do...", fontSize = 11.sp, color = Color.DarkGray, maxLines = 1)
        }
    }
}

@Composable
private fun NewRescueDispatchCard(onAcceptPing: () -> Unit, onPass: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp) 
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(440.dp) 
                .offset(x = 8.dp, y = 8.dp)
                .background(Color.Black)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .border(thickBorder, Color.Black)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(darkRed)
                    .padding(12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.WarningAmber, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("EMERGENCY DISPATCH", fontSize = 14.sp, fontWeight = FontWeight.Black, color = Color.White)
                }
                Box(modifier = Modifier.background(Color.Black).padding(horizontal = 6.dp, vertical = 2.dp)) {
                    Text("LIVE", fontSize = 9.sp, fontWeight = FontWeight.Black, color = redAlert)
                }
            }
            
            Column(modifier = Modifier.padding(16.dp)) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Box(modifier = Modifier.background(Color.Black).padding(horizontal = 6.dp, vertical = 2.dp)) {
                        Text("CASE #BZ-8950", fontSize = 9.sp, fontWeight = FontWeight.Black, color = Color.White)
                    }
                    Box(modifier = Modifier.background(Color(0xFFFFE0E0)).border(1.dp, Color.Black).padding(horizontal = 6.dp, vertical = 2.dp)) {
                        Text("P1 CRITICAL", fontSize = 9.sp, fontWeight = FontWeight.Black, color = darkRed)
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text("Indie Pup Injured in Hit & Run", fontSize = 18.sp, fontWeight = FontWeight.Black, lineHeight = 22.sp)
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    "Pelvic injury, conscious but unable to move. Safely cordoned off near roadside tea stall.",
                    fontSize = 12.sp,
                    color = Color.DarkGray,
                    lineHeight = 16.sp
                )
                Spacer(modifier = Modifier.height(12.dp))
                
                Row(
                    modifier = Modifier.fillMaxWidth().height(100.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .weight(2f)
                            .fillMaxHeight()
                            .background(Color.LightGray)
                            .border(2.dp, Color.Black)
                    ) {
                        AsyncImage(
                            model = "https://images.unsplash.com/photo-1543466835-00a7907e9de1?auto=format&fit=crop&w=400&q=80",
                            contentDescription = "Live Photo",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .background(Color(0xFFE5E5E5))
                            .border(2.dp, Color.Black),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.background(Color.White).border(1.dp, Color.Black).padding(6.dp)
                        ) {
                            Icon(Icons.Default.LocationOn, contentDescription = null, tint = darkRed, modifier = Modifier.size(16.dp))
                            Text("850M", fontSize = 9.sp, fontWeight = FontWeight.Black)
                        }
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
                
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    InfoBadge(Icons.Default.NearMe, "100FT RD (OPP. TOIT)")
                    InfoBadge(Icons.Default.Person, "CITIZEN RAHUL M.")
                }
                
                Spacer(modifier = Modifier.height(24.dp))
                
                Button(
                    onClick = onAcceptPing,
                    modifier = Modifier.fillMaxWidth().height(56.dp).border(thickBorder, Color.Black),
                    colors = ButtonDefaults.buttonColors(containerColor = yellow, contentColor = Color.Black),
                    shape = RoundedCornerShape(0.dp)
                ) {
                    Text("ACCEPT DISPATCH ➔", fontSize = 16.sp, fontWeight = FontWeight.Black)
                }
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = onPass,
                    modifier = Modifier.fillMaxWidth().height(48.dp).border(thickBorder, Color.Black),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE5E5E5), contentColor = Color.DarkGray),
                    shape = RoundedCornerShape(0.dp)
                ) {
                    Text("✕ PASS TO NEXT VOLUNTEER", fontSize = 12.sp, fontWeight = FontWeight.Black)
                }
            }
        }
    }
}

@Composable
private fun InfoBadge(icon: androidx.compose.ui.graphics.vector.ImageVector, text: String) {
    Row(
        modifier = Modifier.border(1.dp, Color.Black).padding(horizontal = 6.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null, modifier = Modifier.size(10.dp))
        Spacer(modifier = Modifier.width(4.dp))
        Text(text, fontSize = 9.sp, fontWeight = FontWeight.Black)
    }
}

@Composable
private fun EquipmentCard() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .border(thickBorder, Color.Black)
            .padding(16.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Text("EQUIPMENT ON VEHICLE", fontSize = 12.sp, fontWeight = FontWeight.Black)
            Box(modifier = Modifier.background(darkGreen).padding(horizontal = 6.dp, vertical = 2.dp)) {
                Text("READY", fontSize = 9.sp, fontWeight = FontWeight.Black, color = Color.White)
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        HorizontalDivider(thickness = 2.dp, color = Color.Black)
        Spacer(modifier = Modifier.height(12.dp))
        
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            EquipmentItem("FIRST-AID KIT", modifier = Modifier.weight(1f))
            EquipmentItem("TRANSFER CRATE", modifier = Modifier.weight(1f))
        }
        Spacer(modifier = Modifier.height(12.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            EquipmentItem("MUZZLE & LEASH", modifier = Modifier.weight(1f))
            EquipmentItem("BLANKETS & \nTOWELS", modifier = Modifier.weight(1f))
        }
    }
}

@Composable
private fun EquipmentItem(name: String, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .border(2.dp, Color.Black)
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(modifier = Modifier.size(16.dp).background(darkGreen))
        Spacer(modifier = Modifier.width(8.dp))
        Text(name, fontSize = 9.sp, fontWeight = FontWeight.Black, lineHeight = 12.sp)
    }
}

@Composable
private fun StatsRow() {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        StatBox("14", "RESCUES THIS\nMONTH", modifier = Modifier.weight(1f))
        StatBox("98%", "RESPONSE\nACCURACY", modifier = Modifier.weight(1f), color = Color(0xFF8B6B1D)) 
        StatBox("4.9★", "TRIAGE\nRELIABILITY", modifier = Modifier.weight(1f), color = darkGreen)
    }
}

@Composable
private fun StatBox(value: String, label: String, modifier: Modifier = Modifier, color: Color = Color.Black) {
    Column(
        modifier = modifier
            .background(Color.White)
            .border(thickBorder, Color.Black)
            .padding(vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(value, fontSize = 24.sp, fontWeight = FontWeight.Black, fontFamily = FontFamily.Monospace, color = color)
        Spacer(modifier = Modifier.height(4.dp))
        Text(label, fontSize = 7.sp, fontWeight = FontWeight.Black, textAlign = TextAlign.Center, lineHeight = 10.sp, letterSpacing = 0.5.sp)
    }
}

@Composable
private fun DirectCommandHotlineCard() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Black)
            .border(thickBorder, Color.Black)
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .background(yellow)
                    .border(2.dp, Color.Black)
                    .padding(8.dp)
            ) {
                Icon(Icons.Default.Phone, contentDescription = null, tint = Color.Black, modifier = Modifier.size(16.dp))
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text("DIRECT COMMAND HOTLINE", fontSize = 8.sp, fontWeight = FontWeight.Black, color = yellow)
                Text("+91 80 2294 2000", fontSize = 16.sp, fontWeight = FontWeight.Black, color = Color.White)
            }
        }
        Box(
            modifier = Modifier
                .background(Color.White)
                .padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            Text("CALL OPS", fontSize = 10.sp, fontWeight = FontWeight.Black)
        }
    }
}
