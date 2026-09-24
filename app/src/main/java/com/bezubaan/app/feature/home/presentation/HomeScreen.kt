package com.bezubaan.app.feature.home.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.bezubaan.app.feature.rescue.domain.model.RescueCase

val yellow = Color(0xFFE2F163)
val mintGreen = Color(0xFFC4F7D4)
val redAlert = Color(0xFFFF4D4D)
val darkGreen = Color(0xFF0D3311)
val bgGray = Color(0xFFF4F4F0)
val thickBorder = 3.dp

@Composable
fun HomeScreen(
    onNavigateToReport: () -> Unit,
    onNavigateToDetails: (String) -> Unit,
    onNavigateToProfile: () -> Unit = {},
    onNavigateToNotifications: () -> Unit = {},
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    val locationPermissionLauncher = androidx.activity.compose.rememberLauncherForActivityResult(
        contract = androidx.activity.result.contract.ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val granted = permissions[android.Manifest.permission.ACCESS_FINE_LOCATION] == true ||
                      permissions[android.Manifest.permission.ACCESS_COARSE_LOCATION] == true
        if (granted) {
            viewModel.fetchLocation()
        }
    }

    LaunchedEffect(Unit) {
        locationPermissionLauncher.launch(
            arrayOf(
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
        // Also attempt to fetch in case permissions were already granted
        viewModel.fetchLocation()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(bgGray)
    ) {
        TopBar(
            locationName = uiState.locationName,
            onProfileClick = onNavigateToProfile,
            onNotificationsClick = onNavigateToNotifications
        )
        LiveTicker()

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            item { NamasteCard(userName = uiState.userName) }
            item { PrimaryCalloutCard(onNavigateToReport) }
            item { QuickActionGrid() }
            item { ActiveRescueSection() }
            item { NearbyRescuesSection(uiState.nearbyCases, onNavigateToDetails) }
            item { RescueStoriesSection() }
            item { OpenLedgerBlock() }
            item { Spacer(modifier = Modifier.height(24.dp)) }
        }
    }
}

@Composable
fun TopBar(
    locationName: String,
    onProfileClick: () -> Unit = {},
    onNotificationsClick: () -> Unit = {}
) {
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
                    .background(yellow, RoundedCornerShape(8.dp))
                    .border(2.dp, Color.Black, RoundedCornerShape(8.dp))
                    .padding(6.dp)
            ) {
                Icon(Icons.Default.LocationOn, contentDescription = null, modifier = Modifier.size(16.dp))
            }
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(locationName.ifEmpty { "LOCATING..." }, fontSize = 9.sp, fontWeight = FontWeight.Black, fontFamily = FontFamily.Monospace)
                Text("Home", fontSize = 20.sp, fontWeight = FontWeight.Black)
            }
        }
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .clickable { onNotificationsClick() }
                    .background(Color.White, CircleShape)
                    .border(2.dp, Color.Black, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.Notifications, contentDescription = "Notifications")
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .offset(x = (-4).dp, y = 4.dp)
                        .size(10.dp)
                        .background(redAlert, CircleShape)
                        .border(1.dp, Color.Black, CircleShape)
                )
            }
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .clickable { onProfileClick() }
                    .background(Color.LightGray, CircleShape)
                    .border(2.dp, Color.Black, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.Person,
                    contentDescription = "Profile",
                    modifier = Modifier.size(26.dp),
                    tint = Color.DarkGray
                )
            }
        }
        } // Close Row (74)
        HorizontalDivider(thickness = thickBorder, color = Color.Black)
    } // Close Column
} // Close fun TopBar

@Composable
fun LiveTicker() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Black)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .background(yellow, RoundedCornerShape(4.dp))
                .padding(horizontal = 6.dp, vertical = 2.dp)
        ) {
            Text("LIVE", color = Color.Black, fontSize = 9.sp, fontWeight = FontWeight.Black, fontFamily = FontFamily.Monospace)
        }
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            "● 3 AMBULANCES ACTIVE // 12 BEDS FREE @ MISSION PAWS...",
            color = Color.White,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Monospace,
            maxLines = 1
        )
    }
}

@Composable
fun NamasteCard(userName: String) {
    val displayName = if (userName.isNotBlank()) userName.uppercase() else "CITIZEN"
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(12.dp))
            .border(thickBorder, Color.Black, RoundedCornerShape(12.dp))
            .padding(16.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Text("NAMASTE, $displayName \uD83D\uDC4B", fontSize = 16.sp, fontWeight = FontWeight.Black, fontFamily = FontFamily.Monospace)
            Box(
                modifier = Modifier
                    .background(mintGreen, RoundedCornerShape(4.dp))
                    .border(1.dp, Color.Black, RoundedCornerShape(4.dp))
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text("● ON DUTY // CITIZEN DISPATCH", color = darkGreen, fontSize = 9.sp, fontWeight = FontWeight.Black, fontFamily = FontFamily.Monospace)
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        HorizontalDivider(thickness = 2.dp, color = Color.Black)
        Spacer(modifier = Modifier.height(12.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Column {
                Text("COVERAGE AREA", fontSize = 9.sp, color = Color.Black, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
                Text("Indiranagar, Bengaluru (1.2 km radius)", fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }
            Column(horizontalAlignment = Alignment.End) {
                Text("SECTOR", fontSize = 9.sp, color = Color.Black, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
                Text("GRID #04-E", fontSize = 12.sp, fontWeight = FontWeight.Black, fontFamily = FontFamily.Monospace)
            }
        }
    }
}

@Composable
fun PrimaryCalloutCard(onNavigateToReport: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(16.dp))
            .border(thickBorder, Color.Black, RoundedCornerShape(16.dp))
            .padding(20.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("HELP ", fontSize = 28.sp, fontWeight = FontWeight.Black)
            Box(
                modifier = Modifier
                    .background(yellow)
                    .border(3.dp, Color.Black)
                    .padding(horizontal = 8.dp, vertical = 2.dp)
            ) {
                Text("AN ANIMAL.", fontSize = 28.sp, fontWeight = FontWeight.Black)
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            "One report triggers our citizen grid. Spot an injured or distressed stray? Tap below for zero-delay triage & ambulance routing.",
            fontSize = 14.sp,
            fontFamily = FontFamily.Monospace,
            lineHeight = 20.sp
        )
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = onNavigateToReport,
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .border(thickBorder, Color.Black, RoundedCornerShape(8.dp)),
            colors = ButtonDefaults.buttonColors(containerColor = yellow, contentColor = Color.Black),
            shape = RoundedCornerShape(8.dp)
        ) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Warning, contentDescription = null, tint = redAlert)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("REPORT AN ANIMAL", fontSize = 16.sp, fontWeight = FontWeight.Black)
                }
                Text("➔", fontSize = 20.sp, fontWeight = FontWeight.Black)
            }
        }
    }
}

@Composable
fun QuickActionGrid() {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            QuickActionCard(
                title = "AI TRIAGE CAM", 
                subtitle = "Instant VetVision Scan", 
                icon = Icons.Default.CameraAlt,
                modifier = Modifier.weight(1f)
            )
            QuickActionCard(
                title = "ADOPT INDIES", 
                subtitle = "28 Pups Waiting", 
                icon = Icons.Default.Pets,
                modifier = Modifier.weight(1f)
            )
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            QuickActionCard(
                title = "URGENT FOSTER", 
                subtitle = "Post-Op Recovery", 
                icon = Icons.Default.Home,
                modifier = Modifier.weight(1f)
            )
            QuickActionCard(
                title = "BANDAGE FUND", 
                subtitle = "Direct Med Grants", 
                icon = Icons.Default.Favorite,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun QuickActionCard(title: String, subtitle: String, icon: ImageVector, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .background(Color.White, RoundedCornerShape(12.dp))
            .border(2.dp, Color.Black, RoundedCornerShape(12.dp))
            .clickable { }
            .padding(16.dp)
    ) {
        Column {
            Icon(icon, contentDescription = title, modifier = Modifier.size(28.dp))
            Spacer(modifier = Modifier.height(12.dp))
            Text(title, fontSize = 12.sp, fontWeight = FontWeight.Black, fontFamily = FontFamily.Monospace)
            Spacer(modifier = Modifier.height(4.dp))
            Text(subtitle, fontSize = 10.sp, color = Color.Black)
        }
    }
}

@Composable
fun ActiveRescueSection() {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(redAlert)
                .border(thickBorder, Color.Black)
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("● ⚡ ACTIVE RESCUE IN PROGRESS", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Black, fontFamily = FontFamily.Monospace)
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .border(thickBorder, Color.Black)
                .padding(16.dp)
        ) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text("CASE #BZ-8942 // INDIE PUP (LEG INJURY)", fontSize = 10.sp, fontWeight = FontWeight.Black, fontFamily = FontFamily.Monospace)
                Box(modifier = Modifier.background(Color.Black).padding(horizontal = 6.dp, vertical = 2.dp)) {
                    Text("PRIORITY: P1", color = redAlert, fontSize = 9.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .border(2.dp, Color.Black, RoundedCornerShape(8.dp))
            ) {
                AsyncImage(
                    model = "https://images.unsplash.com/photo-1543466835-00a7907e9de1?auto=format&fit=crop&w=800&q=80",
                    contentDescription = "Injured Pup",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                Box(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(8.dp)
                        .background(redAlert, RoundedCornerShape(4.dp))
                        .border(1.dp, Color.Black, RoundedCornerShape(4.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text("TRIAGE: P1 CRITICAL", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Black, fontFamily = FontFamily.Monospace)
                }
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(8.dp)
                        .background(Color.White, RoundedCornerShape(4.dp))
                        .border(1.dp, Color.Black, RoundedCornerShape(4.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text("📍 12th Main Road Corner", color = Color.Black, fontSize = 10.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(modifier = Modifier.background(darkGreen).padding(8.dp)) {
                    Icon(Icons.Default.DirectionsCar, contentDescription = null, tint = Color.White)
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text("Ambulance En Route (~6 mins away)", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    Text("Lead Paramedic: Vikram S. • Mobile Trauma Kit #02", fontSize = 10.sp, color = Color.Black)
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .border(2.dp, Color.Black, RoundedCornerShape(8.dp)),
                colors = ButtonDefaults.buttonColors(containerColor = darkGreen, contentColor = Color.White),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("TRACK LIVE DISPATCH 🛰️", fontWeight = FontWeight.Black)
            }
        }
    }
}

@Composable
fun NearbyRescuesSection(cases: List<RescueCase>, onNavigateToDetails: (String) -> Unit) {
    Column {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Text("NEARBY RESCUES (4 WITHIN 3 KM)", fontSize = 12.sp, fontWeight = FontWeight.Black, fontFamily = FontFamily.Monospace)
            Text("VIEW MAP ➔", fontSize = 10.sp, fontWeight = FontWeight.Bold, textDecoration = androidx.compose.ui.text.style.TextDecoration.Underline, color = Color.Black)
        }
        Spacer(modifier = Modifier.height(12.dp))
        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            item { NeoFilterChip(text = "ALL (4)", isSelected = true) }
            item { NeoFilterChip(text = "DOGS (3)", isSelected = false) }
            item { NeoFilterChip(text = "CATS (1)", isSelected = false) }
            item { NeoFilterChip(text = "BIRDS (0)", isSelected = false) }
        }
        Spacer(modifier = Modifier.height(16.dp))
        
        // Card 1
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, RoundedCornerShape(12.dp))
                .border(thickBorder, Color.Black, RoundedCornerShape(12.dp))
                .padding(16.dp)
        ) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text("CASE #BZ-8938 // CLINICAL ADMISSION", fontSize = 9.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
                Box(modifier = Modifier.background(mintGreen).border(1.dp, Color.Black).padding(horizontal = 4.dp, vertical = 2.dp)) {
                    Text("SECURED", fontSize = 8.sp, fontWeight = FontWeight.Bold)
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            Row {
                AsyncImage(
                    model = "https://images.unsplash.com/photo-1599839619722-39751411ea63?auto=format&fit=crop&w=200&q=80",
                    contentDescription = null,
                    modifier = Modifier.size(72.dp).border(2.dp, Color.Black),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Box(modifier = Modifier.background(Color.Black).padding(horizontal = 4.dp, vertical = 2.dp)) {
                        Text("STATUS: AT CLINIC", color = yellow, fontSize = 8.sp, fontWeight = FontWeight.Bold)
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("INDIE PUPPY W/ FRACTURED PAW", fontSize = 12.sp, fontWeight = FontWeight.Black)
                    Text("100ft Rd, Indiranagar • 800m away", fontSize = 10.sp, color = Color.Black)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("Dr. Rekha V. • STABLE", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = darkGreen)
                }
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Card 2
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, RoundedCornerShape(12.dp))
                .border(thickBorder, Color.Black, RoundedCornerShape(12.dp))
                .padding(16.dp)
        ) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text("CASE #BZ-8929 // RESCUE STAGING", fontSize = 9.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
                Box(modifier = Modifier.background(redAlert).border(1.dp, Color.Black).padding(horizontal = 4.dp, vertical = 2.dp)) {
                    Text("URGENT FOSTER", fontSize = 8.sp, fontWeight = FontWeight.Bold, color = Color.White)
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text("ADULT DESI DOG WITH SEVERE MANGE", fontSize = 12.sp, fontWeight = FontWeight.Black)
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(top = 4.dp)) {
                Box(modifier = Modifier.background(yellow).border(1.dp, Color.Black).padding(horizontal = 4.dp, vertical = 2.dp)) {
                    Text("P3 STABLE", fontSize = 8.sp, fontWeight = FontWeight.Black)
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text("Domlur Flyover Underpass • 1.9 km away", fontSize = 10.sp, color = Color.Black)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(
                    onClick = { },
                    modifier = Modifier.weight(1f).height(40.dp).border(2.dp, Color.Black, RoundedCornerShape(4.dp)),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = Color.Black),
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Text("OFFER 7-DAY FOSTER", fontSize = 9.sp, fontWeight = FontWeight.Black)
                }
                Button(
                    onClick = { },
                    modifier = Modifier.weight(1f).height(40.dp).border(2.dp, Color.Black, RoundedCornerShape(4.dp)),
                    colors = ButtonDefaults.buttonColors(containerColor = yellow, contentColor = Color.Black),
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Text("SPONSOR MEDS", fontSize = 9.sp, fontWeight = FontWeight.Black)
                }
            }
        }
    }
}

@Composable
fun NeoFilterChip(text: String, isSelected: Boolean) {
    Box(
        modifier = Modifier
            .background(if (isSelected) darkGreen else Color.White, RoundedCornerShape(16.dp))
            .border(2.dp, Color.Black, RoundedCornerShape(16.dp))
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Text(
            text, 
            color = if (isSelected) Color.White else Color.Black,
            fontSize = 10.sp,
            fontWeight = FontWeight.Black,
            fontFamily = FontFamily.Monospace
        )
    }
}

@Composable
fun RescueStoriesSection() {
    Column {
        Box(
            modifier = Modifier
                .background(mintGreen, RoundedCornerShape(4.dp))
                .border(2.dp, Color.Black, RoundedCornerShape(4.dp))
                .padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
            Text("100% REHABILITATED", fontSize = 12.sp, fontWeight = FontWeight.Black, fontFamily = FontFamily.Monospace)
        }
        Spacer(modifier = Modifier.height(12.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, RoundedCornerShape(12.dp))
                .border(thickBorder, Color.Black, RoundedCornerShape(12.dp))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
            ) {
                AsyncImage(
                    model = "https://images.unsplash.com/photo-1544568100-847a948585b9?auto=format&fit=crop&w=800&q=80",
                    contentDescription = "Adopted Dog",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                Column(modifier = Modifier.align(Alignment.TopStart).padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Box(modifier = Modifier.background(yellow).border(1.dp, Color.Black).padding(horizontal = 6.dp, vertical = 4.dp)) {
                        Text("🎉 ADOPTED BY ANANYA M.", fontSize = 9.sp, fontWeight = FontWeight.Black)
                    }
                    Box(modifier = Modifier.background(Color.White).border(1.dp, Color.Black).padding(horizontal = 6.dp, vertical = 4.dp)) {
                        Text("₹14,200 RAISED • FULLY FUNDED", fontSize = 9.sp, fontWeight = FontWeight.Black)
                    }
                }
            }
            Column(modifier = Modifier.padding(16.dp)) {
                Text("SHERU: FROM HIGHWAY COLLISION TO FOREVER HOME", fontSize = 16.sp, fontWeight = FontWeight.Black, lineHeight = 20.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    "Rescued off Old Airport Road with a broken pelvic joint. 42 days of citizen-funded hydrotherapy and love restored Sheru to full sprints.",
                    fontSize = 12.sp,
                    color = Color.Black,
                    lineHeight = 18.sp
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text("READ RECOVERY JOURNEY 📖 ➔", fontSize = 12.sp, fontWeight = FontWeight.Black, color = darkGreen)
            }
        }
    }
}

@Composable
fun OpenLedgerBlock() {
    Column(modifier = Modifier.fillMaxWidth().background(Color.Black)) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(Icons.Default.Security, contentDescription = null, tint = Color.White, modifier = Modifier.size(32.dp))
            Spacer(modifier = Modifier.height(12.dp))
            Text("BEZUBAAN OPEN LEDGER", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Black, fontFamily = FontFamily.Monospace)
            Spacer(modifier = Modifier.height(4.dp))
            Text("4,820+ Stray Lives Saved", color = yellow, fontSize = 24.sp, fontWeight = FontWeight.Black)
            Spacer(modifier = Modifier.height(12.dp))
            Box(modifier = Modifier.background(Color.White).padding(horizontal = 8.dp, vertical = 4.dp)) {
                Text("AUDITED", color = Color.Black, fontSize = 10.sp, fontWeight = FontWeight.Black, fontFamily = FontFamily.Monospace)
            }
            } // Close Column
        } // Close Box
        HorizontalDivider(thickness = 8.dp, color = yellow)
    } // Close Column
} // Close fun OpenLedgerBlock
