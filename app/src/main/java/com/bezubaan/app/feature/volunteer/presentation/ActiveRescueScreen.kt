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
fun ActiveRescueScreen(
    onMarkRescued: () -> Unit,
    onCallVet: () -> Unit,
    onChat: () -> Unit,
    onIssue: () -> Unit
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
            Icon(Icons.Default.Close, contentDescription = "Close", modifier = Modifier.size(24.dp))
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("EMERGENCY DISPATCH", fontSize = 16.sp, fontWeight = FontWeight.Black)
            }
            Box(modifier = Modifier.background(darkGreen).padding(horizontal = 4.dp)) {
                Text("LIVE", color = yellow, fontSize = 10.sp, fontWeight = FontWeight.Bold)
            }
        }
        HorizontalDivider(thickness = 3.dp, color = Color.Black)

        LazyColumn(
            modifier = Modifier.weight(1f),
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
                    Text("ETA: --", fontSize = 10.sp, color = darkRed, fontWeight = FontWeight.Black)
                }
            }

            item {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.background(yellow).padding(horizontal = 8.dp, vertical = 4.dp).border(1.dp, Color.Black)) {
                        Text("STAGE 6 OF 7: LIVE ON SITE", fontSize = 10.sp, fontWeight = FontWeight.Black)
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Timer, contentDescription = null, tint = darkRed, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("ON SITE: 12:35 MIN", fontSize = 10.sp, color = darkRed, fontWeight = FontWeight.Black)
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Column {
                        Text("INCIDENT PROTOCOL", fontSize = 10.sp, color = Color.DarkGray, fontWeight = FontWeight.Bold)
                        Text("RESCUE IN PROGRESS", fontSize = 20.sp, fontWeight = FontWeight.Black, color = darkGreen)
                    }
                    Box(modifier = Modifier.background(darkGreen).padding(horizontal = 8.dp, vertical = 4.dp)) {
                        Text("CASE BZ-904", color = yellow, fontSize = 12.sp, fontWeight = FontWeight.Black)
                    }
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
                        Box(modifier = Modifier.background(darkRed).padding(horizontal = 8.dp, vertical = 4.dp).align(Alignment.TopEnd).padding(8.dp)) {
                            Text("PRIORITY 1", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Black)
                        }
                    }
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("DESI DOG (MALE, ~3 YRS)", fontSize = 18.sp, fontWeight = FontWeight.Black, lineHeight = 20.sp)
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Column {
                                Text("VOLUNTEER", fontSize = 10.sp, color = Color.DarkGray, fontWeight = FontWeight.Bold)
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.Person, contentDescription = null, modifier = Modifier.size(16.dp))
                                    Text("Aakash V.", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                }
                            }
                            Column {
                                Text("REPORTER", fontSize = 10.sp, color = Color.DarkGray, fontWeight = FontWeight.Bold)
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.Person, contentDescription = null, modifier = Modifier.size(16.dp))
                                    Text("Priya S. (On Site)", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
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
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("TACTICAL FIELD PROTOCOL", fontSize = 12.sp, fontWeight = FontWeight.Black)
                        Text("STEP 2 OF 4 COMPLETE", fontSize = 10.sp, color = Color.DarkGray, fontWeight = FontWeight.Bold)
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    ProtocolChecklistItem("Animal gently moved onto clean thermal blanket", true)
                    ProtocolChecklistItem("Soft gauze muzzle secured, steady respiration verified", true)
                    ProtocolChecklistItem("Hydration drop offered, bleeding swabbed and dressed", false)
                    ProtocolChecklistItem("Ready for rigid crate transfer & ambulance lift", false)
                }
            }
            
            item { Spacer(modifier = Modifier.height(40.dp)) }
        }

        // Bottom Actions
        Column(modifier = Modifier.background(Color.White).border(3.dp, Color.Black).padding(16.dp)) {
            Button(
                onClick = onMarkRescued,
                modifier = Modifier.fillMaxWidth().height(56.dp).border(3.dp, Color.Black),
                colors = ButtonDefaults.buttonColors(containerColor = yellowBtn, contentColor = Color.Black),
                shape = RoundedCornerShape(0.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.CheckBox, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("MARK RESCUED", fontSize = 16.sp, fontWeight = FontWeight.Black)
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(
                    onClick = onCallVet,
                    modifier = Modifier.weight(1f).height(48.dp).border(2.dp, Color.Black),
                    colors = ButtonDefaults.buttonColors(containerColor = darkGreen, contentColor = Color.White),
                    shape = RoundedCornerShape(0.dp),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text("CALL VET", fontSize = 12.sp, fontWeight = FontWeight.Black)
                }
                Button(
                    onClick = onChat,
                    modifier = Modifier.weight(1f).height(48.dp).border(2.dp, Color.Black),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = Color.Black),
                    shape = RoundedCornerShape(0.dp),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text("CHAT (2)", fontSize = 12.sp, fontWeight = FontWeight.Black)
                }
                Button(
                    onClick = onIssue,
                    modifier = Modifier.weight(1f).height(48.dp).border(2.dp, Color.Black),
                    colors = ButtonDefaults.buttonColors(containerColor = darkRed, contentColor = Color.White),
                    shape = RoundedCornerShape(0.dp),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text("ISSUE", fontSize = 12.sp, fontWeight = FontWeight.Black)
                }
            }
        }
    }
}

@Composable
private fun ProtocolChecklistItem(text: String, checked: Boolean) {
    Row(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp), verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier.size(24.dp).background(if (checked) darkGreen else Color.White).border(2.dp, Color.Black),
            contentAlignment = Alignment.Center
        ) {
            if (checked) {
                Icon(Icons.Default.Check, contentDescription = null, tint = yellowBtn, modifier = Modifier.size(16.dp))
            }
        }
        Spacer(modifier = Modifier.width(12.dp))
        Text(text, fontSize = 12.sp, fontWeight = if (checked) FontWeight.Black else FontWeight.Normal)
    }
}
