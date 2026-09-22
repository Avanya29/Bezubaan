package com.bezubaan.app.feature.profile.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage

val yellow = Color(0xFFE2F163)
val mintGreen = Color(0xFFC4F7D4)
val redAlert = Color(0xFFFF4D4D)
val lightPink = Color(0xFFFFD1D1)
val darkGreen = Color(0xFF0D3311)
val bgGray = Color(0xFFF4F4F0)
val thickBorder = 3.dp

@Composable
fun ProfileScreen(
    onNavigateToEditProfile: () -> Unit = {},
    onNavigateToSettings: () -> Unit = {},
    onNavigateToVolunteerActivation: () -> Unit = {},
    onLogoutClick: () -> Unit = {}
) {
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
            item { IdentityCard() }
            item { RoleDispatchCard(onNavigateToVolunteerActivation) }
            item { ActiveIncidentReports() }
            item { FosterHistoryCard() }
            item { FirstAidFieldKit() }
            item { SettingsActionCard(onLogoutClick) }
            item { Spacer(modifier = Modifier.height(24.dp)) }
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
                    Text("BEZUBAAN", fontSize = 10.sp, fontWeight = FontWeight.Black, fontFamily = FontFamily.Monospace)
                    Text("PROFILE", fontSize = 16.sp, fontWeight = FontWeight.Black)
                }
            }
            Box(
                modifier = Modifier
                    .size(36.dp)
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
private fun IdentityCard() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(8.dp))
            .border(thickBorder, Color.Black, RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .background(darkGreen, RoundedCornerShape(4.dp))
                    .border(2.dp, Color.Black, RoundedCornerShape(4.dp))
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.VerifiedUser, contentDescription = null, tint = yellow, modifier = Modifier.size(12.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("#BZ-VOL-048", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Black, fontFamily = FontFamily.Monospace)
                }
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.NearMe, contentDescription = null, tint = redAlert, modifier = Modifier.size(12.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("BANGALORE SEC 4", fontSize = 9.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace, color = Color.DarkGray)
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .background(Color.LightGray, RoundedCornerShape(8.dp))
                    .border(2.dp, Color.Black, RoundedCornerShape(8.dp))
            ) {
                AsyncImage(
                    model = "https://images.unsplash.com/photo-1534528741775-53994a69daeb?auto=format&fit=crop&w=200&q=80",
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .offset(x = 6.dp, y = 6.dp)
                        .background(yellow, CircleShape)
                        .border(2.dp, Color.Black, CircleShape)
                        .size(16.dp)
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Box(modifier = Modifier.background(yellow).border(1.dp, Color.Black).padding(horizontal = 4.dp, vertical = 2.dp)) {
                    Text("FIELD VOLUNTEER", fontSize = 8.sp, fontWeight = FontWeight.Black)
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text("ADITI SHARMA", fontSize = 18.sp, fontWeight = FontWeight.Black)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Pets, contentDescription = null, modifier = Modifier.size(10.dp), tint = darkGreen)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Verified Citizen & Animal Carer", fontSize = 10.sp, color = Color.DarkGray)
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        HorizontalDivider(thickness = 2.dp, color = Color.Black)
        Spacer(modifier = Modifier.height(12.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            StatBox("14", "RESCUES", modifier = Modifier.weight(1f))
            StatBox("03", "FOSTERED", modifier = Modifier.weight(1f))
            StatBox("₹24.5k", "FUNDED", modifier = Modifier.weight(1.2f))
        }
    }
}

@Composable
private fun StatBox(value: String, label: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .background(Color.White)
            .border(2.dp, Color.Black)
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(value, fontSize = 16.sp, fontWeight = FontWeight.Black)
        Text(label, fontSize = 8.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace, color = Color.DarkGray)
    }
}

@Composable
private fun RoleDispatchCard(onSwitchToVolunteerClick: () -> Unit = {}) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(8.dp))
            .border(thickBorder, Color.Black, RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.SettingsInputAntenna, contentDescription = null, modifier = Modifier.size(16.dp), tint = darkGreen)
                Spacer(modifier = Modifier.width(8.dp))
                Text("ROLE & DISPATCH MODE", fontSize = 12.sp, fontWeight = FontWeight.Black, fontFamily = FontFamily.Monospace)
            }
            Box(modifier = Modifier.background(darkGreen).border(1.dp, Color.Black).padding(horizontal = 6.dp, vertical = 2.dp)) {
                Text("LIVE TRIAGE", color = Color.White, fontSize = 8.sp, fontWeight = FontWeight.Black)
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        HorizontalDivider(thickness = 2.dp, color = Color.Black)
        Spacer(modifier = Modifier.height(12.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Column(
                modifier = Modifier.weight(1f).background(darkGreen, RoundedCornerShape(4.dp)).border(2.dp, Color.Black, RoundedCornerShape(4.dp)).padding(vertical = 12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Person, contentDescription = null, tint = mintGreen, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("USER MODE", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Black)
                }
                Text("REPORTING & CARE", color = mintGreen, fontSize = 8.sp, fontWeight = FontWeight.Bold)
            }
            Column(
                modifier = Modifier.weight(1f).background(Color.White, RoundedCornerShape(4.dp)).border(2.dp, Color.Black, RoundedCornerShape(4.dp)).padding(vertical = 12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Star, contentDescription = null, tint = yellow, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("VOLUNTEER", color = Color.Black, fontSize = 12.sp, fontWeight = FontWeight.Black)
                }
                Text("RESPONDER SOS", color = redAlert, fontSize = 8.sp, fontWeight = FontWeight.Bold)
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        Row {
            Icon(Icons.Default.Info, contentDescription = null, tint = yellow, modifier = Modifier.size(16.dp).padding(top = 2.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text("Switch to Volunteer Mode to receive live emergency dispatches, activate your 5km proximity radar, and take custody of street rescues in real time.", fontSize = 10.sp, lineHeight = 14.sp, color = Color.DarkGray)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = onSwitchToVolunteerClick,
            modifier = Modifier.fillMaxWidth().height(48.dp).border(thickBorder, Color.Black, RoundedCornerShape(4.dp)),
            colors = ButtonDefaults.buttonColors(containerColor = yellow, contentColor = Color.Black),
            shape = RoundedCornerShape(4.dp)
        ) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.PowerSettingsNew, contentDescription = null)
                    Spacer(modifier = Modifier.width(12.dp))
                    Text("SWITCH TO VOLUNTEER MODE", fontSize = 12.sp, fontWeight = FontWeight.Black)
                }
                Text("➔", fontSize = 16.sp, fontWeight = FontWeight.Black)
            }
        }
    }
}

@Composable
private fun ActiveIncidentReports() {
    Column {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(modifier = Modifier.size(10.dp).background(darkGreen))
                Spacer(modifier = Modifier.width(8.dp))
                Text("ACTIVE INCIDENT REPORTS", fontSize = 12.sp, fontWeight = FontWeight.Black)
            }
            Text("2 CASES LOGGED", fontSize = 9.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace, color = Color.DarkGray)
        }
        Spacer(modifier = Modifier.height(12.dp))
        // Case 1
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, RoundedCornerShape(8.dp))
                .border(thickBorder, Color.Black, RoundedCornerShape(8.dp))
                .clip(RoundedCornerShape(8.dp))
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().background(yellow).padding(12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.AddBox, contentDescription = null, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("CASE #BZ-8942", fontSize = 10.sp, fontWeight = FontWeight.Black, fontFamily = FontFamily.Monospace)
                }
                Box(modifier = Modifier.background(Color.White).border(1.dp, Color.Black).padding(horizontal = 6.dp, vertical = 2.dp)) {
                    Text("IN TRANSIT TO CLINIC", fontSize = 9.sp, fontWeight = FontWeight.Black)
                }
            }
            HorizontalDivider(thickness = 2.dp, color = Color.Black)
            Row(modifier = Modifier.padding(16.dp)) {
                AsyncImage(
                    model = "https://images.unsplash.com/photo-1543466835-00a7907e9de1?auto=format&fit=crop&w=200&q=80",
                    contentDescription = null,
                    modifier = Modifier.size(64.dp).border(2.dp, Color.Black, RoundedCornerShape(4.dp)).clip(RoundedCornerShape(4.dp)),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Event, contentDescription = null, modifier = Modifier.size(10.dp), tint = Color.DarkGray)
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("TODAY, 11:20 AM • INDIRANAGAR", fontSize = 8.sp, fontWeight = FontWeight.Bold, color = Color.DarkGray)
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("INDIE PUP (FRACTURE)", fontSize = 14.sp, fontWeight = FontWeight.Black)
                    Text("Assigned to Rescuer Rohit V. • ...", fontSize = 10.sp, color = Color.DarkGray)
                }
            }
            HorizontalDivider(thickness = 2.dp, color = Color.Black)
            Row(
                modifier = Modifier.fillMaxWidth().padding(12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("ETA CUPA VET: 18 MIN", fontSize = 10.sp, fontWeight = FontWeight.Black, fontFamily = FontFamily.Monospace)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("LIVE GPS ROUTE", fontSize = 10.sp, fontWeight = FontWeight.Black, textDecoration = TextDecoration.Underline)
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(Icons.Default.OpenInNew, contentDescription = null, modifier = Modifier.size(12.dp))
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        // Case 2
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, RoundedCornerShape(8.dp))
                .border(thickBorder, Color.Black, RoundedCornerShape(8.dp))
                .clip(RoundedCornerShape(8.dp))
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().background(bgGray).padding(12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.CheckCircleOutline, contentDescription = null, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("CASE #BZ-8912", fontSize = 10.sp, fontWeight = FontWeight.Black, fontFamily = FontFamily.Monospace)
                }
                Box(modifier = Modifier.background(mintGreen).border(1.dp, Color.Black).padding(horizontal = 6.dp, vertical = 2.dp)) {
                    Text("RESOLVED & RELEASED", fontSize = 9.sp, fontWeight = FontWeight.Black)
                }
            }
            HorizontalDivider(thickness = 2.dp, color = Color.Black)
            Row(modifier = Modifier.padding(16.dp)) {
                AsyncImage(
                    model = "https://images.unsplash.com/photo-1544568100-847a948585b9?auto=format&fit=crop&w=200&q=80",
                    contentDescription = null,
                    modifier = Modifier.size(48.dp).border(2.dp, Color.Black, RoundedCornerShape(4.dp)).clip(RoundedCornerShape(4.dp)),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text("KORAMANGALA COMMUNITY DOG", fontSize = 12.sp, fontWeight = FontWeight.Black)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("Maggot wound fully sterilized. Returned to local feeder pack.", fontSize = 10.sp, color = Color.DarkGray, lineHeight = 14.sp)
                }
            }
        }
    }
}

@Composable
private fun FosterHistoryCard() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(8.dp))
            .border(thickBorder, Color.Black, RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Pets, contentDescription = null, modifier = Modifier.size(16.dp), tint = darkGreen)
                Spacer(modifier = Modifier.width(8.dp))
                Text("FOSTER HISTORY & \nCHECKUPS", fontSize = 12.sp, fontWeight = FontWeight.Black, lineHeight = 14.sp)
            }
            Box(modifier = Modifier.background(bgGray).border(1.dp, Color.Black).padding(horizontal = 8.dp, vertical = 4.dp)) {
                Text("1 ACTIVE", fontSize = 9.sp, fontWeight = FontWeight.Black)
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        HorizontalDivider(thickness = 2.dp, color = Color.Black)
        Spacer(modifier = Modifier.height(16.dp))
        Row(modifier = Modifier.fillMaxWidth().background(yellow).border(2.dp, Color.Black).padding(12.dp)) {
            Box(modifier = Modifier.size(32.dp).border(2.dp, Color.Black), contentAlignment = Alignment.Center) {
                Icon(Icons.Default.MedicalServices, contentDescription = null, modifier = Modifier.size(16.dp))
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("MILO (GINGER CAT)", fontSize = 12.sp, fontWeight = FontWeight.Black)
                    Text("NOV 04", color = redAlert, fontSize = 9.sp, fontWeight = FontWeight.Black, fontFamily = FontFamily.Monospace)
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text("Rabies Booster & Deworming Due in 2 days. Clinic booking confirmed.", fontSize = 10.sp, lineHeight = 14.sp)
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Text("PAST FOSTERS: BRUNO (ADOPTED), RANI\n(ADOPTED)", fontSize = 8.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace, color = Color.DarkGray, modifier = Modifier.weight(1f))
            Text("VIEW LOG BOOK", fontSize = 9.sp, fontWeight = FontWeight.Black, textDecoration = TextDecoration.Underline, color = darkGreen)
        }
    }
}

@Composable
private fun FirstAidFieldKit() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(8.dp))
            .border(thickBorder, Color.Black, RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.MedicalServices, contentDescription = null, modifier = Modifier.size(16.dp), tint = redAlert)
                Spacer(modifier = Modifier.width(8.dp))
                Text("FIRST-AID FIELD KIT", fontSize = 12.sp, fontWeight = FontWeight.Black)
            }
            Text("3 / 4 READY", fontSize = 9.sp, fontWeight = FontWeight.Black, fontFamily = FontFamily.Monospace)
        }
        Spacer(modifier = Modifier.height(16.dp))
        HorizontalDivider(thickness = 2.dp, color = Color.Black)
        Spacer(modifier = Modifier.height(16.dp))
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                KitItem("BETADINE & COTTON", true, modifier = Modifier.weight(1f))
                KitItem("MUZZLE & LEASH", true, modifier = Modifier.weight(1f))
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                KitItem("THERMAL SHEET", true, modifier = Modifier.weight(1f))
                KitItem("ORS ELECTROLYTES", false, modifier = Modifier.weight(1f))
            }
        }
    }
}

@Composable
private fun KitItem(name: String, isReady: Boolean, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .background(bgGray)
            .border(2.dp, Color.Black)
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(14.dp)
                .background(if (isReady) darkGreen else Color.White)
                .border(2.dp, Color.Black)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = name,
            fontSize = 8.sp,
            fontWeight = FontWeight.Black,
            fontFamily = FontFamily.Monospace,
            color = if (isReady) Color.Black else redAlert
        )
    }
}

@Composable
private fun SettingsActionCard(onLogoutClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(8.dp))
            .border(thickBorder, Color.Black, RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().background(bgGray).border(2.dp, Color.Black).padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.VpnKey, contentDescription = null, modifier = Modifier.size(12.dp), tint = darkGreen)
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text("RESCUER DISPATCH KEY", fontSize = 8.sp, fontWeight = FontWeight.Bold, color = Color.DarkGray)
                    Text("#KEY-9942-SEC-BLR", fontSize = 10.sp, fontWeight = FontWeight.Black, fontFamily = FontFamily.Monospace)
                }
            }
            Box(modifier = Modifier.background(Color.White).border(1.dp, Color.Black).padding(horizontal = 8.dp, vertical = 4.dp)) {
                Text("COPY", fontSize = 8.sp, fontWeight = FontWeight.Black)
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        SettingsItem(Icons.Default.NotificationsActive, "EMERGENCY ALERT GEOFENCE\n(5KM)")
        HorizontalDivider(thickness = 1.dp, color = Color.LightGray)
        SettingsItem(Icons.Default.Badge, "OFFICIAL VOLUNTEER ID &\nPERMIT")
        HorizontalDivider(thickness = 1.dp, color = Color.LightGray)
        SettingsItem(Icons.Default.Tune, "PREFERENCES & LANGUAGE")
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = onLogoutClick,
            modifier = Modifier.fillMaxWidth().height(48.dp).border(thickBorder, Color.Black, RoundedCornerShape(4.dp)),
            colors = ButtonDefaults.buttonColors(containerColor = lightPink, contentColor = redAlert),
            shape = RoundedCornerShape(4.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Logout, contentDescription = null)
                Spacer(modifier = Modifier.width(12.dp))
                Text("LOG OUT RESCUER SESSION", fontSize = 12.sp, fontWeight = FontWeight.Black)
            }
        }
    }
}

@Composable
private fun SettingsItem(icon: androidx.compose.ui.graphics.vector.ImageVector, title: String) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(icon, contentDescription = null, tint = Color.DarkGray, modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.width(16.dp))
            Text(title, fontSize = 10.sp, fontWeight = FontWeight.Black)
        }
        Icon(Icons.Default.ChevronRight, contentDescription = null, tint = Color.DarkGray)
    }
}
