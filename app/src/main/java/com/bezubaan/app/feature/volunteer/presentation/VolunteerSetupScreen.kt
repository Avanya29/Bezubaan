package com.bezubaan.app.feature.volunteer.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage

val yellowBtn = Color(0xFFE2F163)
val darkGrn = Color(0xFF0D3311)

@Composable
fun VolunteerSetupScreen(
    onKeepOff: () -> Unit,
    onEnableLocation: () -> Unit,
    onManualStaging: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF7F7F7))
    ) {
        // Top Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("← BACK", fontSize = 12.sp, fontWeight = FontWeight.Black)
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("VOLUNTEER MODE", fontSize = 16.sp, fontWeight = FontWeight.Black)
                Box(modifier = Modifier.background(yellowBtn).padding(horizontal = 4.dp)) {
                    Text("DISPATCH READY", fontSize = 10.sp, fontWeight = FontWeight.Bold)
                }
            }
            Box(modifier = Modifier.size(24.dp).background(Color.Gray, RoundedCornerShape(12.dp)))
        }

        Divider(thickness = 3.dp, color = Color.Black)

        Column(modifier = Modifier.padding(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Box(modifier = Modifier.background(darkGrn).padding(horizontal = 8.dp, vertical = 4.dp)) {
                    Text("RADAR GEOFENCE SETUP", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Black)
                }
                Box(modifier = Modifier.border(1.dp, Color.Black).padding(horizontal = 8.dp, vertical = 4.dp)) {
                    Text("SECURE LINK", fontSize = 10.sp, fontWeight = FontWeight.Black)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            
            Text("WHERE CAN YOU HELP?", fontSize = 24.sp, fontWeight = FontWeight.Black, color = darkGrn)
            Text(
                "Your location helps us dispatch verified rescue requests directly to your patrol perimeter.",
                fontSize = 12.sp, color = Color.DarkGray, modifier = Modifier.padding(vertical = 8.dp)
            )

            // Map Placeholder
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .border(3.dp, Color.Black)
            ) {
                AsyncImage(
                    model = "https://images.unsplash.com/photo-1524661135-423995f22d0b?auto=format&fit=crop&w=600&q=80",
                    contentDescription = "Map View",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Current Location Button
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(yellowBtn)
                    .border(3.dp, Color.Black)
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row {
                    Icon(Icons.Default.MyLocation, contentDescription = null, tint = darkGrn)
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Box(modifier = Modifier.background(Color.Black).padding(horizontal = 4.dp)) {
                            Text("RECOMMENDED", fontSize = 8.sp, color = Color.White, fontWeight = FontWeight.Black)
                        }
                        Text("USE CURRENT LOCATION", fontSize = 16.sp, fontWeight = FontWeight.Black, color = darkGrn)
                        Text("Instant auto-sync via satellite", fontSize = 10.sp, color = Color.DarkGray)
                    }
                }
                Icon(Icons.Default.ArrowForward, contentDescription = null, tint = darkGrn)
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Manual Staging
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .border(3.dp, Color.Black)
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Box(modifier = Modifier.background(Color.LightGray).padding(horizontal = 4.dp)) {
                        Text("MANUAL STAGING", fontSize = 8.sp, color = Color.DarkGray, fontWeight = FontWeight.Black)
                    }
                    Text("CHOOSE ON MAP", fontSize = 16.sp, fontWeight = FontWeight.Black, color = darkGrn)
                    Text("Pin custom volunteer HQ or neighborhood base.", fontSize = 10.sp, color = Color.DarkGray)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Privacy
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Color.Black)
                    .padding(12.dp)
            ) {
                Icon(Icons.Default.Shield, contentDescription = null, tint = darkGrn, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    "ZERO CITIZEN TRACKING GUARANTEE. Your location is used solely for volunteer availability.",
                    fontSize = 10.sp, color = Color.DarkGray
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            // Keep Off Button
            Button(
                onClick = onKeepOff,
                modifier = Modifier.fillMaxWidth().height(48.dp).border(3.dp, Color.Black),
                colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray, contentColor = Color.Black),
                shape = RoundedCornerShape(0.dp)
            ) {
                Text("[ KEEP VOLUNTEER MODE OFF ]", fontSize = 14.sp, fontWeight = FontWeight.Black)
            }
            
            Text("AWAITING LOCATION AUTHORIZATION", fontSize = 8.sp, color = Color.Gray, modifier = Modifier.align(Alignment.CenterHorizontally).padding(top = 8.dp))
        }
    }
}
