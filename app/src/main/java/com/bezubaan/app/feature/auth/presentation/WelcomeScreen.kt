package com.bezubaan.app.feature.auth.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage

@Composable
fun WelcomeScreen(
    onNavigateToOnboarding: () -> Unit,
    onNavigateToHome: () -> Unit
) {
    val brutalYellow = Color(0xFFFFD54F)
    val brutalRed = Color(0xFFFF5252)
    val brutalDarkGreen = Color(0xFF0A2B10)
    val brutalBg = Color(0xFFFCFCFC)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(brutalBg)
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        // Top Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .background(brutalDarkGreen)
                    .border(2.dp, Color.Black)
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.size(6.dp).background(brutalYellow, androidx.compose.foundation.shape.CircleShape))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("BEZUBAAN HELPING HANDS // 24/7 SOS", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
                }
            }

            Box(
                modifier = Modifier
                    .background(Color.White)
                    .border(2.dp, Color.Black)
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("✱", color = brutalRed, fontSize = 12.sp, fontWeight = FontWeight.Black)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("BLR LIVE", fontSize = 10.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Titles
        Text("HELP", fontSize = 42.sp, fontWeight = FontWeight.Black, letterSpacing = 1.sp)
        Box(
            modifier = Modifier
                .background(brutalYellow)
                .border(4.dp, Color.Black)
                .padding(horizontal = 8.dp, vertical = 0.dp)
        ) {
            Text("AN ANIMAL.", fontSize = 42.sp, fontWeight = FontWeight.Black, letterSpacing = 1.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text("— One report can start a rescue.", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            "Direct citizen triage & emergency veterinary dispatch across Bengaluru.",
            fontSize = 14.sp,
            color = Color.DarkGray
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Image Card
        Box {
            // Shadow
            Box(
                modifier = Modifier
                    .offset(x = 6.dp, y = 6.dp)
                    .fillMaxWidth()
                    .height(220.dp)
                    .background(Color.Black)
            )
            // Image Box
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .background(Color.White)
                    .border(4.dp, Color.Black)
            ) {
                AsyncImage(
                    model = "https://images.unsplash.com/photo-1548681528-6a5c45b66b42?auto=format&fit=crop&q=80&w=800",
                    contentDescription = "Dog in recovery",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                // Overlays
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                        .background(brutalRed)
                        .border(2.dp, Color.Black)
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text("[ CRITICAL CASE #8942 ]", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
                }

                Box(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(8.dp)
                        .background(brutalYellow)
                        .border(2.dp, Color.Black)
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text("[ NOW IN RECOVERY ]", fontSize = 10.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
                }

                Box(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(8.dp)
                        .background(Color.White)
                        .border(2.dp, Color.Black)
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("📍", fontSize = 10.sp)
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("INDIRANAGAR, BLR", fontSize = 10.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Dispatch Pill
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFEBEBEB))
                .border(3.dp, Color.Black)
                .padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(modifier = Modifier.size(10.dp).background(brutalRed).border(1.dp, Color.Black))
                Spacer(modifier = Modifier.width(8.dp))
                Text("ACTIVE UNIT 04 DISPATCHED TO KORAMANGALA 5TH", fontSize = 9.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace, modifier = Modifier.weight(1f))
                Text("3M AGO", fontSize = 9.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace, color = Color.DarkGray)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Buttons
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onNavigateToOnboarding() }
        ) {
            Box(modifier = Modifier.offset(x = 4.dp, y = 4.dp).fillMaxWidth().height(56.dp).background(Color.Black))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .background(brutalYellow)
                    .border(3.dp, Color.Black),
                contentAlignment = Alignment.Center
            ) {
                Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("⟐", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("REPORT AN ANIMAL", fontSize = 16.sp, fontWeight = FontWeight.Black)
                    }
                    Text("→", fontSize = 20.sp, fontWeight = FontWeight.Black)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onNavigateToHome() } // Or Onboarding, depending on logic
        ) {
            Box(modifier = Modifier.offset(x = 4.dp, y = 4.dp).fillMaxWidth().height(48.dp).background(Color.Black))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .background(Color.White)
                    .border(3.dp, Color.Black),
                contentAlignment = Alignment.Center
            ) {
                Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("◎", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("EXPLORE RESCUES & ADOPTIONS", fontSize = 14.sp, fontWeight = FontWeight.Black)
                    }
                    Text("→", fontSize = 18.sp, fontWeight = FontWeight.Black)
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
        Divider(color = Color.LightGray, thickness = 1.dp)
        Spacer(modifier = Modifier.height(16.dp))

        // Footer Section
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("🩺", fontSize = 16.sp)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Volunteer or Medic?", fontSize = 14.sp, color = Color.DarkGray)
            }
            Box(
                modifier = Modifier
                    .background(brutalDarkGreen)
                    .border(2.dp, Color.Black)
                    .padding(horizontal = 12.dp, vertical = 6.dp)
                    .clickable { /* Login Route */ }
            ) {
                Text("VOLUNTEER LOGIN", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Stats Bar
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFFAFAFA))
                .border(2.dp, Color.Black)
                .padding(12.dp)
        ) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.weight(1f)) {
                    Text("4,820+", fontSize = 16.sp, fontWeight = FontWeight.Black)
                    Text("ANIMALS SAVED", fontSize = 8.sp, fontWeight = FontWeight.Bold, color = Color.DarkGray, letterSpacing = 1.sp)
                }
                Box(modifier = Modifier.height(24.dp).width(1.dp).background(Color.LightGray))
                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.weight(1f)) {
                    Text("100%", fontSize = 16.sp, fontWeight = FontWeight.Black)
                    Text("VOLUNTEER RUN", fontSize = 8.sp, fontWeight = FontWeight.Bold, color = Color.DarkGray, letterSpacing = 1.sp)
                }
                Box(modifier = Modifier.height(24.dp).width(1.dp).background(Color.LightGray))
                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.weight(1f)) {
                    Text("~18 MIN", fontSize = 16.sp, fontWeight = FontWeight.Black, color = brutalRed)
                    Text("AVG RESPONSE", fontSize = 8.sp, fontWeight = FontWeight.Bold, color = Color.DarkGray, letterSpacing = 1.sp)
                }
            }
        }
    }
}
