package com.bezubaan.app.feature.auth.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import kotlinx.coroutines.launch

@Composable
fun OnboardingScreen(
    onFinishOnboarding: () -> Unit
) {
    val brutalYellow = Color(0xFFFFD54F)
    val brutalRed = Color(0xFFFF5252)
    val brutalDarkGreen = Color(0xFF0A2B10)
    val brutalBg = Color(0xFFFCFCFC)

    val pagerState = rememberPagerState(pageCount = { 3 })
    val coroutineScope = rememberCoroutineScope()

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
                    .background(Color.White)
                    .border(2.dp, Color.Black)
                    .padding(horizontal = 8.dp, vertical = 6.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.size(6.dp).background(brutalRed, androidx.compose.foundation.shape.CircleShape))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("BEZUBAAN ONBOARDING // 0${pagerState.currentPage + 1} OF 03", fontSize = 10.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
                }
            }

            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                for (i in 0..2) {
                    val isActive = pagerState.currentPage == i
                    val isPast = pagerState.currentPage > i
                    Box(
                        modifier = Modifier
                            .background(if (isActive) brutalDarkGreen else Color.White)
                            .border(2.dp, Color.Black)
                            .padding(horizontal = 6.dp, vertical = 4.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        if (isPast) {
                            Text("✓", color = Color.Black, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                        } else {
                            Text("0${i + 1}", color = if (isActive) Color.White else Color.Transparent, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxWidth(),
            userScrollEnabled = false
        ) { page ->
            when (page) {
                0 -> OnboardingPageOne(brutalYellow, brutalRed, brutalDarkGreen)
                1 -> OnboardingPageTwo(brutalYellow, brutalRed, brutalDarkGreen)
                2 -> OnboardingPageThree(brutalYellow, brutalRed, brutalDarkGreen)
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Navigation Buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Skip / Back button
            Box(
                modifier = Modifier
                    .weight(0.4f)
                    .clickable {
                        if (pagerState.currentPage > 0) {
                            coroutineScope.launch { pagerState.animateScrollToPage(pagerState.currentPage - 1) }
                        } else {
                            onFinishOnboarding()
                        }
                    }
            ) {
                Box(modifier = Modifier.offset(x = 4.dp, y = 4.dp).fillMaxWidth().height(56.dp).background(Color.Black))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .background(Color.White)
                        .border(3.dp, Color.Black),
                    contentAlignment = Alignment.Center
                ) {
                    Text(if (pagerState.currentPage == 0) "SKIP" else "← BACK", fontSize = 14.sp, fontWeight = FontWeight.Black)
                }
            }

            // Continue / Finish button
            Box(
                modifier = Modifier
                    .weight(0.6f)
                    .clickable {
                        if (pagerState.currentPage < 2) {
                            coroutineScope.launch { pagerState.animateScrollToPage(pagerState.currentPage + 1) }
                        } else {
                            onFinishOnboarding()
                        }
                    }
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
                    Text(if (pagerState.currentPage < 2) "CONTINUE →" else "GET STARTED →", fontSize = 16.sp, fontWeight = FontWeight.Black)
                }
            }
        }
    }
}

@Composable
fun OnboardingPageOne(yellow: Color, red: Color, darkGreen: Color) {
    Column {
        Box {
            Box(modifier = Modifier.offset(x = 6.dp, y = 6.dp).fillMaxWidth().height(200.dp).background(Color.Black))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(Color.White)
                    .border(4.dp, Color.Black)
            ) {
                AsyncImage(
                    model = "https://images.unsplash.com/photo-1548681528-6a5c45b66b42?auto=format&fit=crop&q=80&w=800",
                    contentDescription = "Dog",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                Box(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(8.dp)
                        .background(darkGreen)
                        .border(2.dp, Color.Black)
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text("((•)) STEP 01: RAPID DETECTION", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
                }
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(8.dp)
                        .background(yellow)
                        .border(2.dp, Color.Black)
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("📍", fontSize = 10.sp)
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("VERIFIED FIELD DISPATCH", fontSize = 10.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
        Text("SPOT.", fontSize = 36.sp, fontWeight = FontWeight.Black)
        Box(modifier = Modifier.background(yellow).border(3.dp, Color.Black).padding(horizontal = 8.dp)) {
            Text("REPORT.", fontSize = 36.sp, fontWeight = FontWeight.Black)
        }
        Text("RESCUE.", fontSize = 36.sp, fontWeight = FontWeight.Black)

        Spacer(modifier = Modifier.height(16.dp))
        Text(
            "Notice an animal in distress? Snap a photo, drop the location, and alert our 24/7 volunteer veterinary response network across the city within seconds.",
            fontSize = 14.sp, color = Color.DarkGray
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            InfoPill("⚡ INSTANT GEO-DISPATCH")
            InfoPill("🩺 FIRST-AID TRIAGE")
        }
        Spacer(modifier = Modifier.height(8.dp))
        InfoPill("🤝 LIVE VOLUNTEER TRACKING")
    }
}

@Composable
fun OnboardingPageTwo(yellow: Color, red: Color, darkGreen: Color) {
    Column {
        Box {
            Box(modifier = Modifier.offset(x = 6.dp, y = 6.dp).fillMaxWidth().height(200.dp).background(Color.Black))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(Color.White)
                    .border(4.dp, Color.Black)
            ) {
                AsyncImage(
                    model = "https://images.unsplash.com/photo-1548681528-6a5c45b66b42?auto=format&fit=crop&q=80&w=800",
                    contentDescription = "Dog AI",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                // AI Overlay Box
                Box(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .fillMaxWidth(0.8f)
                        .height(120.dp)
                        .border(2.dp, yellow)
                )
                
                Box(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(8.dp)
                        .background(darkGreen)
                        .border(2.dp, Color.Black)
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text("● ((•)) VETVISION AI // ACTIVE", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
                }
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .offset(y = (-16).dp)
                        .background(yellow)
                        .border(2.dp, Color.Black)
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text("DETECTED: SUPERFICIAL LACERATION // CONFIDENCE: 94%", fontSize = 10.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
                }
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(8.dp)
                        .background(Color.White)
                        .border(2.dp, Color.Black)
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text("⚡ REAL-TIME SENSOR", fontSize = 10.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
        Text("AI CAN HELP YOU", fontSize = 28.sp, fontWeight = FontWeight.Black)
        Box(modifier = Modifier.background(yellow).border(3.dp, Color.Black).padding(horizontal = 8.dp)) {
            Text("WHAT YOU SEE.", fontSize = 36.sp, fontWeight = FontWeight.Black)
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text(
            "VetVision AI instantly analyzes visible symptoms, providing preliminary observations, severity markers, and immediate safe first-aid protocols while emergency responders are en route.",
            fontSize = 14.sp, color = Color.DarkGray
        )

        Spacer(modifier = Modifier.height(16.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
            Box(modifier = Modifier.weight(1f).border(2.dp, Color.Black).padding(8.dp), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("🔍", fontSize = 16.sp)
                    Text("SYMPTOM SCAN", fontSize = 9.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
                }
            }
            Box(modifier = Modifier.weight(1f).border(2.dp, Color.Black).padding(8.dp), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("⏱", fontSize = 16.sp)
                    Text("LEVEL 1-4 TRIAGE", fontSize = 9.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
                }
            }
            Box(modifier = Modifier.weight(1f).border(2.dp, Color.Black).padding(8.dp), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("🩺", fontSize = 16.sp)
                    Text("FIRST AID PLAN", fontSize = 9.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFFFF59D))
                .border(2.dp, Color.Black)
                .padding(12.dp)
        ) {
            Row(verticalAlignment = Alignment.Top) {
                Box(modifier = Modifier.background(red).border(2.dp, Color.Black).padding(4.dp)) {
                    Text("⚠", color = Color.White, fontSize = 12.sp)
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text("ETHICAL SAFEGUARD", fontSize = 10.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
                    Text("Preliminary triage assistance only. VetVision AI does not provide definitive veterinary diagnosis or replace licensed clinical examination.", fontSize = 12.sp)
                }
            }
        }
    }
}

@Composable
fun OnboardingPageThree(yellow: Color, red: Color, darkGreen: Color) {
    Column {
        Text("PEOPLE MAKE THE", fontSize = 28.sp, fontWeight = FontWeight.Black)
        Box(modifier = Modifier.background(yellow).border(3.dp, Color.Black).padding(horizontal = 8.dp)) {
            Text("RESCUE HAPPEN.", fontSize = 36.sp, fontWeight = FontWeight.Black)
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text(
            "Behind this app sits an agile human frontline: everyday citizens, active field dispatchers, licensed veterinary trauma teams, and verified fosters united in real-time response.",
            fontSize = 14.sp, color = Color.DarkGray
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Pipeline
        PipelineStep("01 SPOT", "CITIZEN ALERT", "Instant photo capture & geo-tagged SOS", yellow, "📸")
        PipelineConnector()
        PipelineStep("02 DISPATCH", "FIELD RESPONDER", "Paramedic volunteer on-site in ~18 min", darkGreen, "🚲", true)
        PipelineConnector()
        PipelineStep("03 TREAT", "VET PARTNER ICU", "Surgical triage, scans & stabilization", red, "🏥", true)
        PipelineConnector()
        PipelineStep("04 HEAL", "FOSTER & ADOPTION", "Shelter rehabilitation & forever home", yellow, "🐕")
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF5F5F5))
                .border(2.dp, Color.Black)
                .padding(16.dp)
        ) {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("🛡", fontSize = 18.sp)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("100% PUBLIC VERIFIABILITY", fontSize = 14.sp, fontWeight = FontWeight.Black)
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text("Every rescue case receives a cryptographic public tracking ledger. Trace initial SOS telemetry down to clinic bills and final adoption updates.", fontSize = 12.sp, color = Color.DarkGray)
                Spacer(modifier = Modifier.height(12.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Box(modifier = Modifier.background(Color.White).border(1.dp, Color.Black).padding(horizontal = 6.dp, vertical = 2.dp)) {
                        Text("4,820+ SAVED", fontSize = 9.sp, fontWeight = FontWeight.Bold)
                    }
                    Box(modifier = Modifier.background(Color.White).border(1.dp, Color.Black).padding(horizontal = 6.dp, vertical = 2.dp)) {
                        Text("VERIFIED NGOS", fontSize = 9.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
fun PipelineStep(tag: String, title: String, desc: String, color: Color, emoji: String, titleWhite: Boolean = false) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .border(2.dp, Color.Black)
            .padding(12.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(color)
                    .border(2.dp, Color.Black),
                contentAlignment = Alignment.Center
            ) {
                Text(emoji, fontSize = 24.sp)
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.background(Color.Black).padding(horizontal = 4.dp, vertical = 2.dp)) {
                        Text(tag, color = Color.White, fontSize = 9.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
                    }
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(title, fontSize = 16.sp, fontWeight = FontWeight.Black)
                }
                Text(desc, fontSize = 12.sp, color = Color.DarkGray)
            }
        }
    }
}

@Composable
fun PipelineConnector() {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth().padding(start = 32.dp)) {
        Box(modifier = Modifier.width(2.dp).height(12.dp).background(Color.Black))
        Box(modifier = Modifier.size(20.dp).background(Color(0xFFFFD54F), androidx.compose.foundation.shape.CircleShape).border(2.dp, Color.Black, androidx.compose.foundation.shape.CircleShape), contentAlignment = Alignment.Center) {
            Text("↓", fontSize = 10.sp, fontWeight = FontWeight.Bold)
        }
        Box(modifier = Modifier.width(2.dp).height(12.dp).background(Color.Black))
    }
}

@Composable
fun InfoPill(text: String) {
    Box(
        modifier = Modifier
            .background(Color.White)
            .border(2.dp, Color.Black)
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Text(text, fontSize = 10.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
    }
}
