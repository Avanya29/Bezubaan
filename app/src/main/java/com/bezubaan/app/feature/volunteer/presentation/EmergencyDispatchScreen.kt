package com.bezubaan.app.feature.volunteer.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage

@Composable
fun EmergencyDispatchScreen(
    onAccept: () -> Unit,
    onDecline: () -> Unit,
    onBack: () -> Unit
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
            IconButton(onClick = onBack, modifier = Modifier.size(24.dp)) {
                Icon(Icons.Default.Close, contentDescription = "Close")
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("EMERGENCY DISPATCH", fontSize = 16.sp, fontWeight = FontWeight.Black)
            }
            Box(modifier = Modifier.background(darkGreen).padding(horizontal = 4.dp)) {
                Text("LIVE", color = yellow, fontSize = 10.sp, fontWeight = FontWeight.Bold)
            }
        }
        HorizontalDivider(thickness = 3.dp, color = Color.Black)

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth().background(lightPink).border(1.dp, darkRed).padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row {
                        Icon(Icons.Default.Warning, tint = darkRed, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("PRIORITY DISPATCH // CRITICAL", fontSize = 10.sp, color = darkRed, fontWeight = FontWeight.Black)
                    }
                    Text("ETA ~6 MIN", fontSize = 10.sp, color = darkRed, fontWeight = FontWeight.Black)
                }
            }

            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .border(3.dp, Color.Black)
                ) {
                    Box(modifier = Modifier.fillMaxWidth().height(200.dp)) {
                        AsyncImage(
                            model = "https://images.unsplash.com/photo-1543466835-00a7907e9de1?auto=format&fit=crop&w=600&q=80",
                            contentDescription = "Animal",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                        Box(modifier = Modifier.background(darkRed).padding(horizontal = 8.dp, vertical = 4.dp).align(Alignment.TopEnd)) {
                            Text("P1 CRITICAL INJURY", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Black)
                        }
                    }
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("RIGHT HIND LEG FRACTURE • MILD TRAUMA", fontSize = 18.sp, fontWeight = FontWeight.Black, lineHeight = 20.sp)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            "Dog is conscious, alert, and frightened. Lying on burlap sack near the metro stairs. Friendly temperament reported, but in visible distress.",
                            fontSize = 12.sp, color = Color.DarkGray
                        )
                    }
                }
            }

            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .border(3.dp, Color.Black)
                        .padding(16.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.LocationOn, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("INCIDENT LOCATION", fontSize = 14.sp, fontWeight = FontWeight.Black)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("OUTER CIRCLE CP, GATE 3", fontSize = 16.sp, fontWeight = FontWeight.Black)
                    Text("Near Rajiv Chowk Metro Pillar 42, New Delhi", fontSize = 12.sp, color = Color.DarkGray)
                    
                    Spacer(modifier = Modifier.height(12.dp))
                    Box(modifier = Modifier.fillMaxWidth().height(100.dp).background(darkGreen).border(2.dp, Color.Black)) {
                        Text("MAP PLACEHOLDER", color = Color.White, modifier = Modifier.align(Alignment.Center))
                    }
                }
            }

            item {
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp), modifier = Modifier.fillMaxWidth()) {
                    Button(
                        onClick = onDecline,
                        modifier = Modifier.weight(1f).height(56.dp).border(3.dp, Color.Black),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = Color.Black),
                        shape = RoundedCornerShape(0.dp)
                    ) {
                        Text("DECLINE", fontSize = 14.sp, fontWeight = FontWeight.Black)
                    }
                    Button(
                        onClick = onAccept,
                        modifier = Modifier.weight(2f).height(56.dp).border(3.dp, Color.Black),
                        colors = ButtonDefaults.buttonColors(containerColor = yellowBtn, contentColor = Color.Black),
                        shape = RoundedCornerShape(0.dp)
                    ) {
                        Text("⚡ ACCEPT RESCUE", fontSize = 14.sp, fontWeight = FontWeight.Black)
                    }
                }
            }
            item { Spacer(modifier = Modifier.height(40.dp)) }
        }
    }
}
