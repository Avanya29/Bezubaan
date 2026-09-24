package com.bezubaan.app.feature.rescue.presentation

import androidx.compose.foundation.Image
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage

@Composable
fun RescueCaseDetailsScreen(
    onBack: () -> Unit
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
            item { TopActionNavStrip(onBack) }
            item { PrimaryCaseDossierHeader() }
            item { ProminentLiveStatusCard() }
            item { AnimalIdentityCard() }
            item { IncidentLocationCard() }
            item { RescuerFieldIntelCard() }
            item { AiVetVisionAssessmentSummary() }
            item { MedicalClinicalIntakeCard() }
            item { DispatchPipeline() }
            item { Spacer(modifier = Modifier.height(24.dp)) }
        }
        
        StickyBottomActions()
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
                    Text("BEZUBAAN HELPING HANDS", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                    Text("RESCUE", fontSize = 16.sp, fontWeight = FontWeight.Black)
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
private fun TopActionNavStrip(onBack: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            onClick = onBack,
            modifier = Modifier
                .height(44.dp)
                .shadow(1.dp, RoundedCornerShape(4.dp)),
            color = Color.White,
            shape = RoundedCornerShape(4.dp)
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null, modifier = Modifier.size(12.dp), tint = Color(0xFF002210))
                Spacer(modifier = Modifier.width(6.dp))
                Text("BACK TO DISPATCH", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color(0xFF002210))
            }
        }
        
        Row(horizontalArrangement = Arrangement.spacedBy(4.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .background(Color(0xFF002210), RoundedCornerShape(4.dp))
                    .padding(horizontal = 10.dp, vertical = 6.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.size(8.dp).background(Color(0xFFFFE24E), CircleShape))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("LIVE SYNC", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color.White)
                }
            }
            
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .shadow(1.dp, RoundedCornerShape(4.dp))
                    .background(Color.White, RoundedCornerShape(4.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.Share, contentDescription = "Share", modifier = Modifier.size(16.dp))
            }
        }
    }
}

@Composable
private fun PrimaryCaseDossierHeader() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("INCIDENT DOSSIER • CASE #BZ-804", fontSize = 12.sp, fontWeight = FontWeight.Black, color = Color(0xFF414942))
            Box(
                modifier = Modifier.background(Color(0xFFFFE24E), RoundedCornerShape(2.dp)).padding(horizontal = 10.dp, vertical = 4.dp)
            ) {
                Text("ACTIVE", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color(0xFF211B00))
            }
        }
        
        Spacer(modifier = Modifier.height(4.dp))
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("RESCUE CASE", fontSize = 32.sp, fontWeight = FontWeight.Black, color = Color(0xFF1C1B1B), letterSpacing = (-0.8).sp)
            Box(
                modifier = Modifier.background(Color(0xFF002210), RoundedCornerShape(2.dp)).padding(horizontal = 8.dp, vertical = 2.dp)
            ) {
                Text("LIVE INCIDENT", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color.White)
            }
        }
    }
}

@Composable
private fun ProminentLiveStatusCard() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(4.dp, RoundedCornerShape(8.dp))
            .background(Color(0xFFFFE24E), RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.CrisisAlert, contentDescription = null, modifier = Modifier.size(20.dp), tint = Color(0xFF211B00))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("STATUS: REPORTED", fontSize = 18.sp, fontWeight = FontWeight.Black, color = Color(0xFF211B00))
                }
                Box(
                    modifier = Modifier.background(Color(0xFF002210), RoundedCornerShape(2.dp)).padding(horizontal = 8.dp, vertical = 2.dp)
                ) {
                    Text("P2 ALERT", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color.White)
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Schedule, contentDescription = null, modifier = Modifier.size(12.dp), tint = Color(0xFF4A3F00))
                Spacer(modifier = Modifier.width(6.dp))
                Text("Reported at 14:28 IST • Connaught Place, New Delhi", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF4A3F00))
            }
        }
    }
}

@Composable
private fun AnimalIdentityCard() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(4.dp, RoundedCornerShape(8.dp))
            .background(Color.White, RoundedCornerShape(8.dp))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(268.dp)
                .background(Color(0xFFEBE7E7))
        ) {
            AsyncImage(
                model = "https://images.unsplash.com/photo-1544568100-847a948585b9?auto=format&fit=crop&w=800&q=80",
                contentDescription = "Animal photo",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Row(
                modifier = Modifier.padding(12.dp).align(Alignment.TopStart),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(modifier = Modifier.background(Color.White, RoundedCornerShape(4.dp)).padding(horizontal = 10.dp, vertical = 4.dp)) {
                    Text("CANINE • FEMALE", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color(0xFF1C1B1B))
                }
                Box(modifier = Modifier.background(Color(0xFFBA1A1A), RoundedCornerShape(4.dp)).padding(horizontal = 10.dp, vertical = 4.dp)) {
                    Text("PRIORITY P2 (MODERATE)", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color.White)
                }
            }
        }
        
        Column(modifier = Modifier.padding(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.Top) {
                Column {
                    Text("CASE PATIENT", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color(0xFF414942))
                    Text("Rusty (Street Tag)", fontSize = 22.sp, fontWeight = FontWeight.Black, color = Color(0xFF1C1B1B))
                }
                Box(modifier = Modifier.background(Color(0xFFC0EDCD), RoundedCornerShape(2.dp)).padding(horizontal = 8.dp, vertical = 4.dp)) {
                    Text("INDIE DESI DOG", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color(0xFF274F37))
                }
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Box(modifier = Modifier.weight(1f).background(Color(0xFFF6F3F2), RoundedCornerShape(4.dp)).padding(10.dp)) {
                    Column {
                        Text("DEMEANOR & AGE", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color(0xFF414942))
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("Adult (approx 3y) •\nGuarded, calm", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF1C1B1B))
                    }
                }
                Box(modifier = Modifier.weight(1f).background(Color(0xFFF6F3F2), RoundedCornerShape(4.dp)).padding(10.dp)) {
                    Column {
                        Text("DISTINGUISHING MARKS", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color(0xFF414942))
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("V-notch right ear, white\nchest star", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF1C1B1B))
                    }
                }
            }
        }
    }
}

@Composable
private fun IncidentLocationCard() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(4.dp, RoundedCornerShape(8.dp))
            .background(Color.White, RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.Top) {
            Row(verticalAlignment = Alignment.Top) {
                Icon(Icons.Default.LocationOn, contentDescription = null, modifier = Modifier.size(16.dp), tint = Color(0xFF414942))
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text("INCIDENT LOCATION", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color(0xFF414942))
                    Text("Behind Sharma Tea Stall", fontSize = 18.sp, fontWeight = FontWeight.Black, color = Color(0xFF1C1B1B))
                    Text("Outer Circle, Connaught Place Block\nC, New Delhi", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF414942), lineHeight = 16.sp)
                }
            }
            Box(modifier = Modifier.background(Color(0xFFFFE24E), RoundedCornerShape(2.dp)).padding(horizontal = 8.dp, vertical = 4.dp)) {
                Text("ETA: 8-12M", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color(0xFF211B00))
            }
        }
        
        Spacer(modifier = Modifier.height(12.dp))
        
        // Map Mock
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(128.dp)
                .background(Color(0xFFF0EDEC), RoundedCornerShape(4.dp)),
            contentAlignment = Alignment.Center
        ) {
            Box(modifier = Modifier.background(Color.White, RoundedCornerShape(4.dp)).padding(horizontal = 12.dp, vertical = 6.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.GpsFixed, contentDescription = null, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("28.6139° N, 77.2090° E", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color(0xFF1C1B1B))
                }
            }
        }
        
        Spacer(modifier = Modifier.height(12.dp))
        
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF6F3F2), RoundedCornerShape(4.dp))
                .padding(10.dp)
        ) {
            Row(verticalAlignment = Alignment.Top) {
                Icon(Icons.Default.DirectionsRun, contentDescription = null, modifier = Modifier.size(14.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Access Note: Scooter or foot access recommended; narrow alley behind kiosk #4.",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF1C1B1B),
                    lineHeight = 16.sp
                )
            }
        }
    }
}

@Composable
private fun RescuerFieldIntelCard() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(4.dp, RoundedCornerShape(8.dp))
            .background(Color.White, RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.ElectricScooter, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("VOLUNTEER DISPATCH", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color(0xFF414942))
            }
            Box(modifier = Modifier.background(Color(0xFFFFE24E), RoundedCornerShape(2.dp)).padding(horizontal = 8.dp, vertical = 2.dp)) {
                Text("EN ROUTE (~9 MINS ETA)", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color(0xFF211B00))
            }
        }
        
        Spacer(modifier = Modifier.height(12.dp))
        
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.weight(1f)) {
                Text("Aakash V.", fontSize = 18.sp, fontWeight = FontWeight.Black, color = Color(0xFF1C1B1B))
                Text("Squad Lead • Connaught\nRapid Response Unit", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF414942))
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(
                    onClick = { /* Call */ },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEBE7E7), contentColor = Color(0xFF002210)),
                    shape = RoundedCornerShape(4.dp),
                    contentPadding = PaddingValues(horizontal = 12.dp),
                    modifier = Modifier.height(44.dp)
                ) {
                    Icon(Icons.Default.Phone, contentDescription = null, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("CALL", fontSize = 10.sp, fontWeight = FontWeight.Black)
                }
                Button(
                    onClick = { /* Chat */ },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEBE7E7), contentColor = Color(0xFF1C1B1B)),
                    shape = RoundedCornerShape(4.dp),
                    contentPadding = PaddingValues(horizontal = 12.dp),
                    modifier = Modifier.height(44.dp)
                ) {
                    Icon(Icons.Default.ChatBubbleOutline, contentDescription = null, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("CHAT", fontSize = 10.sp, fontWeight = FontWeight.Black, lineHeight = 12.sp)
                        Text("(3)", fontSize = 10.sp, fontWeight = FontWeight.Black, lineHeight = 12.sp)
                    }
                }
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        HorizontalDivider(color = Color(0x4DC1C8C0))
        Spacer(modifier = Modifier.height(12.dp))
        
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.AssignmentInd, contentDescription = null, modifier = Modifier.size(14.dp), tint = Color(0xFF414942))
            Spacer(modifier = Modifier.width(6.dp))
            Text("FIRST REPORTER INTEL (PRIYA M. • VERIFIED CITIZEN)", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color(0xFF414942))
        }
        Spacer(modifier = Modifier.height(8.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF6F3F2), RoundedCornerShape(4.dp))
                .padding(10.dp)
        ) {
            Text(
                "\"Found limping heavily on rear right paw, whimpering near the tea stall. Given clean water. Animal is resting under the green tarp and drinking peacefully.\"",
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                color = Color(0xFF1C1B1B),
                lineHeight = 16.sp
            )
        }
    }
}

@Composable
private fun AiVetVisionAssessmentSummary() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(4.dp, RoundedCornerShape(8.dp))
            .background(Color(0xFF0F3822), RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.RemoveRedEye, contentDescription = null, tint = Color.White, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("PRELIMINARY AI ASSESSMENT", fontSize = 18.sp, fontWeight = FontWeight.Black, color = Color.White)
            }
            Box(modifier = Modifier.background(Color(0xFFFFE24E), RoundedCornerShape(2.dp)).padding(horizontal = 8.dp, vertical = 4.dp)) {
                Text("92% CONF.", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color(0xFF211B00))
            }
        }
        
        Spacer(modifier = Modifier.height(12.dp))
        
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0x26FFFFFF), RoundedCornerShape(2.dp))
                .padding(horizontal = 10.dp, vertical = 4.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.VerifiedUser, contentDescription = null, tint = Color(0xFFFFE24E), modifier = Modifier.size(12.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("ASSISTIVE TRIAGE SCAN • NOT FINAL DIAGNOSIS", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color(0xFFFFE24E))
            }
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0x1AFFFFFF), RoundedCornerShape(4.dp))
                .padding(12.dp)
        ) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("OBSERVED ANOMALIES", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color(0xFF78A285))
                Text("STAGE 2 INJURY", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color(0xFFFFE24E))
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                "Soft tissue swelling & superficial laceration on distal right hind limb. No visible arterial hemorrhage. Weight bearing impaired.",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color.White,
                lineHeight = 20.sp
            )
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0x26FFFFFF), RoundedCornerShape(4.dp))
                .padding(12.dp)
        ) {
            Text("STABILIZATION DIRECTIVES", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color(0xFFFFE24E))
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                "1. Perform sterile saline rinse • 2. Net stabilization & low-stress wrap • 3. Direct transport to Pet Trauma center within 2-4 hours.",
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White,
                lineHeight = 16.sp
            )
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Row(verticalAlignment = Alignment.Top) {
            Icon(Icons.Default.Info, contentDescription = null, tint = Color(0xFF78A285), modifier = Modifier.size(12.dp).padding(top = 2.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                "INTERNAL ASSISTIVE AI DATA ONLY. CLINICAL INTAKE REQUIRES ON-SITE VETERINARY VERIFICATION.",
                fontSize = 11.sp,
                fontWeight = FontWeight.Black,
                color = Color(0xFF78A285),
                lineHeight = 16.sp
            )
        }
    }
}

@Composable
private fun MedicalClinicalIntakeCard() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(4.dp, RoundedCornerShape(8.dp))
            .background(Color.White, RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.MedicalServices, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("MEDICAL & CLINICAL\nINTAKE", fontSize = 18.sp, fontWeight = FontWeight.Black, color = Color(0xFF1C1B1B), lineHeight = 24.sp)
            }
            Box(modifier = Modifier.background(Color(0xFFC0EDCD), RoundedCornerShape(2.dp)).padding(horizontal = 8.dp, vertical = 2.dp)) {
                Text("BED #04\nRESERVED", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color(0xFF274F37))
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("Dr. Sharma's 24/7 Trauma\nVeterinary Hospital", fontSize = 14.sp, fontWeight = FontWeight.Black, color = Color(0xFF002210), lineHeight = 20.sp)
            Text("1.8 KM\nAWAY", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color(0xFF414942), textAlign = TextAlign.Right)
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            "Requisition Kit: Canine Antiseptic Pack #3, Sterile Saline 500ml, Hind-leg Splint S-2. On-duty vet notified for trauma intake.",
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF414942),
            lineHeight = 16.sp
        )
    }
}

@Composable
private fun DispatchPipeline() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Text("DISPATCH PIPELINE", fontSize = 16.sp, fontWeight = FontWeight.Black, color = Color(0xFF1C1B1B))
            Box(modifier = Modifier.background(Color(0xFFEBE7E7), RoundedCornerShape(2.dp)).padding(horizontal = 6.dp, vertical = 2.dp)) {
                Text("STEP 3 OF 6", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color(0xFF414942))
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        PipelineStep(
            step = 1,
            title = "1. REPORTED",
            desc = "Incident logged with GPS tag and photo triage by Priya M.",
            time = "14:28 IST",
            state = StepState.COMPLETED
        )
        PipelineStep(
            step = 2,
            title = "2. DISPATCH BROADCAST",
            desc = "Pushed to 4 verified volunteers within 3km Connaught Place perimeter.",
            time = "14:29 IST",
            state = StepState.COMPLETED
        )
        PipelineStep(
            step = 3,
            title = "3. VOLUNTEER ASSIGNED",
            desc = "Squad Lead Aakash V. accepted dispatch.\nApproaching on 2-wheeler. ETA ~9 mins.",
            badge = "EN ROUTE",
            state = StepState.ACTIVE
        )
        PipelineStep(
            step = 4,
            title = "4. ON-SITE SECURE & FIRST AID",
            desc = "Volunteers display calm approach net and antiseptic compression wrap.",
            state = StepState.PENDING
        )
        PipelineStep(
            step = 5,
            title = "5. CLINIC HANDOFF",
            desc = "Transit to Dr. Sharma's 24/7 Trauma Veterinary Hospital.",
            state = StepState.PENDING
        )
        PipelineStep(
            step = 6,
            title = "6. RECOVERY & FOSTER",
            desc = "Post-treatment recovery dossier and community foster care pipeline.",
            state = StepState.PENDING,
            isLast = true
        )
    }
}

enum class StepState { COMPLETED, ACTIVE, PENDING }

@Composable
private fun PipelineStep(
    step: Int,
    title: String,
    desc: String,
    time: String? = null,
    badge: String? = null,
    state: StepState,
    isLast: Boolean = false
) {
    Row(modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Min)) {
        // Icon Column
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.width(32.dp)) {
            Box(
                modifier = Modifier
                    .size(20.dp)
                    .background(
                        when(state) {
                            StepState.COMPLETED -> Color(0xFF002210)
                            StepState.ACTIVE -> Color(0xFFFFE24E)
                            StepState.PENDING -> Color(0xFFF6F3F2)
                        },
                        CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                when(state) {
                    StepState.COMPLETED -> Icon(Icons.Default.Check, contentDescription = null, tint = Color.White, modifier = Modifier.size(12.dp))
                    StepState.ACTIVE -> Icon(Icons.Default.Sync, contentDescription = null, tint = Color.Black, modifier = Modifier.size(12.dp))
                    StepState.PENDING -> Text(step.toString(), fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color(0xFF727972))
                }
            }
            
            if (!isLast) {
                Box(modifier = Modifier.width(1.dp).weight(1f).background(if (state != StepState.PENDING) Color(0xFF002210) else Color(0xFFE5E2DB)))
            }
        }
        
        // Content Column
        Column(modifier = Modifier.weight(1f).padding(bottom = 24.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        title, 
                        fontSize = 12.sp, 
                        fontWeight = FontWeight.Black, 
                        color = if (state == StepState.PENDING) Color(0xFF727972) else Color(0xFF1C1B1B)
                    )
                    if (badge != null) {
                        Spacer(modifier = Modifier.width(6.dp))
                        Box(modifier = Modifier.background(Color(0xFFFFE24E), RoundedCornerShape(2.dp)).padding(horizontal = 4.dp, vertical = 2.dp)) {
                            Text(badge, fontSize = 8.sp, fontWeight = FontWeight.Black, color = Color(0xFF211B00))
                        }
                    }
                }
                if (time != null) {
                    Text(time, fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color(0xFF414942))
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                desc, 
                fontSize = 12.sp, 
                fontWeight = FontWeight.SemiBold, 
                color = if (state == StepState.PENDING) Color(0xFF727972) else Color(0xFF414942), 
                lineHeight = 16.sp
            )
        }
    }
}

@Composable
private fun StickyBottomActions() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFFCF9F8))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Button(
            onClick = { /* TODO */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .shadow(4.dp, RoundedCornerShape(4.dp)),
            shape = RoundedCornerShape(4.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFE24E), contentColor = Color(0xFF211B00))
        ) {
            Text("TRACK LIVE RESPONDER", fontSize = 16.sp, fontWeight = FontWeight.Black)
            Spacer(modifier = Modifier.width(8.dp))
            Icon(Icons.Default.Navigation, contentDescription = null, modifier = Modifier.size(18.dp))
        }
        
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(
                onClick = { /* TODO */ },
                modifier = Modifier
                    .weight(1f)
                    .height(44.dp)
                    .shadow(1.dp, RoundedCornerShape(4.dp)),
                shape = RoundedCornerShape(4.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = Color(0xFF414942))
            ) {
                Icon(Icons.Default.Phone, contentDescription = null, modifier = Modifier.size(14.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("CALL AAKASH", fontSize = 12.sp, fontWeight = FontWeight.Black)
            }
            
            Button(
                onClick = { /* TODO */ },
                modifier = Modifier
                    .weight(1f)
                    .height(44.dp)
                    .shadow(1.dp, RoundedCornerShape(4.dp)),
                shape = RoundedCornerShape(4.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = Color(0xFF414942))
            ) {
                Icon(Icons.Default.ChatBubbleOutline, contentDescription = null, modifier = Modifier.size(14.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("CASE CHAT (3)", fontSize = 12.sp, fontWeight = FontWeight.Black)
            }
        }
        
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFFFEBEB), RoundedCornerShape(4.dp))
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Warning, contentDescription = null, tint = Color(0xFFBA1A1A), modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text("CRITICAL ESCALATION", fontSize = 8.sp, fontWeight = FontWeight.Black, color = Color(0xFFBA1A1A))
                    Text("Condition worsening? Contact Emergency", fontSize = 11.sp, fontWeight = FontWeight.Black, color = Color(0xFFBA1A1A))
                }
            }
            
            Box(modifier = Modifier.background(Color(0xFFBA1A1A), RoundedCornerShape(2.dp)).padding(horizontal = 8.dp, vertical = 6.dp)) {
                Text("SOS CALL", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color.White)
            }
        }
    }
}
