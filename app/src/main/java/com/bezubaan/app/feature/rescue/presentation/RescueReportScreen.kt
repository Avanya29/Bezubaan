package com.bezubaan.app.feature.rescue.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.bezubaan.app.ui.components.NeoButton
import com.bezubaan.app.ui.components.NeoSurface

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RescueReportScreen(
    onBack: () -> Unit,
    onStartRescue: () -> Unit,
    viewModel: RescueReportViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "VETVISION AI TRIAGE",
                        fontWeight = FontWeight.Black,
                        fontSize = 14.sp,
                        letterSpacing = 1.sp
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Filled.Person, contentDescription = "Profile")
                    }
                },
                actions = {
                    IconButton(onClick = { /* TODO */ }) {
                        Icon(Icons.Filled.Notifications, contentDescription = "Notifications")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFFFD54F),
                    titleContentColor = Color.Black,
                    navigationIconContentColor = Color.Black,
                    actionIconContentColor = Color.Black
                ),
                modifier = Modifier.drawBehind {
                    val strokeWidth = 3.dp.toPx()
                    val y = size.height - strokeWidth / 2
                    drawLine(
                        color = Color.Black,
                        start = androidx.compose.ui.geometry.Offset(0f, y),
                        end = androidx.compose.ui.geometry.Offset(size.width, y),
                        strokeWidth = strokeWidth
                    )
                }
            )
        }
    ) { innerPadding ->
        NeoSurface(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                
                // Hero Header
                Text(
                    text = buildAnnotatedString {
                        append("LET'S GET THIS\n")
                        withStyle(style = SpanStyle(background = Color(0xFFFFD54F))) {
                            append("ANIMAL HELP.")
                        }
                    },
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Black,
                    lineHeight = 40.sp,
                    color = Color.Black
                )

                Text(
                    text = "Choose immediate action. Preliminary VetVision triage data and high-precision coordinates are ready for instant squad handoff.",
                    fontSize = 14.sp,
                    color = Color.Black,
                    lineHeight = 20.sp
                )

                // Image Placeholder Card
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .border(3.dp, Color.Black)
                        .background(Color.LightGray)
                ) {
                    // Placeholder for image
                    AsyncImage(
                        model = "https://images.unsplash.com/photo-1548681528-6a5c45b66b42?q=80&w=600&auto=format&fit=crop",
                        contentDescription = "Animal Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                    
                    // Label overlay
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .fillMaxWidth()
                            .background(Color.Black.copy(alpha = 0.7f))
                            .padding(12.dp)
                    ) {
                        Text(
                            text = "Soft tissue swelling & superficial laceration on right hind limb...\nConscious, low alertness, responsive on reflex.",
                            color = Color.White,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                // Vital Signs Badge
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFE8F5E9)) // Light Green
                        .border(2.dp, Color(0xFF2E7D32)) // Dark Green Border
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "VITAL SIGNS: COLLAPSE IMMINENT. RAPID TRANSPORT TO AUTHORIZED RESPONDER.",
                        color = Color(0xFF1B5E20),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Black,
                        letterSpacing = 0.5.sp
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Action Buttons
                Text("RECOMMENDED ACTION", fontWeight = FontWeight.Black, fontSize = 12.sp, color = Color.Black)
                
                NeoButton(
                    text = "REPORT FOR RESCUE",
                    onClick = onStartRescue,
                    modifier = Modifier.fillMaxWidth().height(60.dp),
                    containerColor = Color(0xFFFFD54F),
                    contentColor = Color.Black
                )
                
                Text(
                    text = "Alert nearest Bezubaan volunteer rescue squads and emergency transport within 5km radius.",
                    fontSize = 12.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(horizontal = 4.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text("OTHER OPTIONS", fontWeight = FontWeight.Black, fontSize = 12.sp, color = Color.Black)

                NeoButton(
                    text = "FIND A VET NEARBY",
                    onClick = { /* TODO */ },
                    modifier = Modifier.fillMaxWidth(),
                    containerColor = Color.White,
                    contentColor = Color.Black
                )

                NeoButton(
                    text = "ASK VETVISION CO-PILOT",
                    onClick = { /* TODO */ },
                    modifier = Modifier.fillMaxWidth(),
                    containerColor = Color.White,
                    contentColor = Color.Black
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Emergency Call Button
                NeoButton(
                    text = "CALL 24/7 AMBULANCE: 1800-BEZUBAAN",
                    onClick = { /* TODO */ },
                    modifier = Modifier.fillMaxWidth().height(60.dp),
                    containerColor = Color(0xFFD32F2F),
                    contentColor = Color.White
                )
            }
        }
    }
}
