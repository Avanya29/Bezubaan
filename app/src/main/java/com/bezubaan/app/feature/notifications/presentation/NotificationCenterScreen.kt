package com.bezubaan.app.feature.notifications.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// ─── Color Palette ─────────────────────────────────────────────────
private val BgCream        = Color(0xFFFCF9F8)
private val Ink            = Color(0xFF1C1B1B)
private val DarkGreen      = Color(0xFF002210)
private val MidGreen       = Color(0xFF0F3822)
private val MintGreen      = Color(0xFFC0EDCD)
private val Yellow         = Color(0xFFFFE24E)
private val YellowDark     = Color(0xFF211B00)
private val YellowDim      = Color(0xFF524600)
private val LightGray      = Color(0xFFF0EDEC)
private val BorderGray     = Color(0xFFE5E2E1)
private val CardGray       = Color(0xFFEBE7E7)
private val FadedCard      = Color(0xFFF0EDEC)
private val MutedText      = Color(0xFF414942)
private val DimText        = Color(0xFF727972)
private val Crimson        = Color(0xFFBA1A1A)
private val CrimsonDark    = Color(0xFF93000A)
private val PinkBadge      = Color(0xFFFFDAD6)
private val MintAdoption   = Color(0xFFC0EDCD)
private val DonateYellow   = Color(0xFFFFE24E)

@Composable
fun NotificationCenterScreen(
    onBack: () -> Unit = {}
) {
    Box(modifier = Modifier.fillMaxSize().background(BgCream)) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(top = 64.dp, bottom = 40.dp)
        ) {
            item {
                Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)) {
                    Spacer(modifier = Modifier.height(16.dp))
                    PushDispatchBar()
                    Spacer(modifier = Modifier.height(20.dp))
                    OperationalControlBar()
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
            item { FilterPillsRow() }
            item {
                Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)) {
                    Spacer(modifier = Modifier.height(20.dp))
                    CriticalAlertsSection()
                    Spacer(modifier = Modifier.height(20.dp))
                    TodaySection()
                    Spacer(modifier = Modifier.height(20.dp))
                    YesterdaySection()
                    Spacer(modifier = Modifier.height(20.dp))
                    SosFooterBanner()
                    Spacer(modifier = Modifier.height(20.dp))
                    // Android gesture pill
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        Box(
                            modifier = Modifier
                                .width(128.dp).height(6.dp)
                                .background(Ink.copy(alpha = 0.8f), RoundedCornerShape(12.dp))
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
        // Floating header
        NotificationTopBar(onBack)
    }
}

// ─── Top Bar ───────────────────────────────────────────────────────
@Composable
private fun NotificationTopBar(onBack: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .background(BgCream.copy(alpha = 0.9f))
            .drawBehind {
                drawLine(Ink.copy(alpha = 0.1f), Offset(0f, size.height), Offset(size.width, size.height), 1.dp.toPx())
            }
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Left: back + logo + title + badge
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            IconButton(onClick = onBack, modifier = Modifier.size(40.dp)) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Ink, modifier = Modifier.size(16.dp))
            }
            // Logo placeholder
            Box(
                modifier = Modifier.size(32.dp).background(MidGreen, RoundedCornerShape(4.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.Pets, contentDescription = null, tint = Yellow, modifier = Modifier.size(18.dp))
            }
            Spacer(modifier = Modifier.width(4.dp))
            Text("NOTIFICATIONS", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Ink, letterSpacing = (-0.45).sp)
            // Badge "4 NEW"
            Box(
                modifier = Modifier
                    .background(DarkGreen)
                    .neoBrutalistShadow(1.dp)
                    .padding(horizontal = 8.dp, vertical = 2.dp)
            ) {
                Row {
                    Text("4 ", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Yellow)
                    Text("NEW", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Yellow)
                }
            }
        }
        // Right: mark all read + settings + avatar
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = {}, modifier = Modifier.size(44.dp)) {
                Icon(Icons.Default.DoneAll, contentDescription = "Mark all read", tint = Ink, modifier = Modifier.size(18.dp))
            }
            IconButton(onClick = {}, modifier = Modifier.size(44.dp)) {
                Icon(Icons.Default.Settings, contentDescription = "Settings", tint = Ink, modifier = Modifier.size(18.dp))
            }
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(MidGreen),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.Person, contentDescription = "Profile", tint = Yellow, modifier = Modifier.size(18.dp))
            }
        }
    }
}

// ─── Push Dispatch Live Bar ────────────────────────────────────────
@Composable
private fun PushDispatchBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(DarkGreen)
            .border(3.dp, Ink)
            .neoBrutalistShadow(3.dp)
            .padding(horizontal = 17.dp, vertical = 13.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
            // Pulsing dot
            Box(modifier = Modifier.size(10.dp), contentAlignment = Alignment.Center) {
                Box(modifier = Modifier.size(10.dp).clip(CircleShape).background(Yellow.copy(alpha = 0.75f)))
                Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(Yellow))
            }
            Text("PUSH DISPATCH: LIVE", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = MintGreen, letterSpacing = 0.6.sp)
        }
        Box(
            modifier = Modifier
                .background(MidGreen)
                .border(2.dp, Yellow.copy(alpha = 0.3f))
                .padding(horizontal = 10.dp, vertical = 4.dp)
        ) {
            Text("SECTOR 4 · 2.5 KM", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Yellow, letterSpacing = (-0.25).sp)
        }
    }
}

// ─── Operational Control Bar ───────────────────────────────────────
@Composable
private fun OperationalControlBar() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .background(DarkGreen)
                .border(3.dp, Ink)
                .neoBrutalistShadow(3.dp)
                .padding(horizontal = 15.dp, vertical = 7.dp)
        ) {
            Text("UNREAD (4)", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Yellow, letterSpacing = (-0.18).sp)
        }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
            // MARK READ
            Row(
                modifier = Modifier
                    .height(40.dp)
                    .background(Color.White)
                    .border(3.dp, Ink)
                    .neoBrutalistShadow(3.dp)
                    .padding(horizontal = 15.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Icon(Icons.Default.Check, contentDescription = null, tint = Ink, modifier = Modifier.size(13.dp))
                Text("MARK READ", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Ink, letterSpacing = 0.72.sp)
            }
            // Filter
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(Color.White)
                    .border(3.dp, Ink)
                    .neoBrutalistShadow(3.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.Tune, contentDescription = "Filter", tint = Ink, modifier = Modifier.size(15.dp))
            }
        }
    }
}

// ─── Filter Pills (Horizontal Scroll) ─────────────────────────────
@Composable
private fun FilterPillsRow() {
    var selected by remember { mutableIntStateOf(0) }

    data class FilterChip(val label: String, val icon: ImageVector?, val activeBg: Color, val activeText: Color)

    val chips = listOf(
        FilterChip("ALL", null, Yellow, YellowDark),
        FilterChip("RESCUE (2)", Icons.Default.Pets, Crimson, Color.White),
        FilterChip("MEDICAL", Icons.Default.MedicalServices, Color.White, Ink),
        FilterChip("ADOPTION", Icons.Default.Favorite, Color.White, Ink),
        FilterChip("FOSTER", Icons.Default.Home, Color.White, Ink),
        FilterChip("COMMUNITY", Icons.Default.Groups, Color.White, Ink),
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(start = 16.dp, end = 16.dp)
                .align(Alignment.Center),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            chips.forEachIndexed { index, chip ->
                val isSelected = selected == index
                val bg = if (isSelected) chip.activeBg else Color.White
                val textColor = if (isSelected) chip.activeText else Ink
                Row(
                    modifier = Modifier
                        .height(38.dp)
                        .background(bg)
                        .border(3.dp, Ink)
                        .neoBrutalistShadow(3.dp)
                        .padding(horizontal = 17.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    chip.icon?.let {
                        Icon(it, contentDescription = null, tint = textColor, modifier = Modifier.size(12.dp))
                    }
                    Text(chip.label, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = textColor, letterSpacing = 0.72.sp)
                    if (index == 0 && isSelected) {
                        Box(modifier = Modifier.background(YellowDark).padding(horizontal = 6.dp)) {
                            Text("14", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Yellow, letterSpacing = 0.8.sp)
                        }
                    }
                }
            }
        }
    }
}

// ═══════════════════════════════════════════════════════════════════
// ──── SECTIONS ─────────────────────────────────────────────────────
// ═══════════════════════════════════════════════════════════════════

@Composable
private fun CriticalAlertsSection() {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        SectionHeader(
            dotColor = Crimson,
            title = "CRITICAL & RESCUE ALERTS",
            subtitle = "2 ACTION REQUIRED",
            subtitleColor = Crimson
        )
        // Card 1: Hit-and-Run Critical
        CriticalIncidentCard()
        // Card 2: Patient Intake
        PatientIntakeCard()
    }
}

@Composable
private fun TodaySection() {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        SectionHeader(dotColor = DarkGreen, title = "TODAY", subtitle = "2 UPDATES", subtitleColor = MutedText)
        // Card 3: Medical Lab
        MedicalLabCard()
        // Card 4: Foster Milestone
        FosterMilestoneCard()
    }
}

@Composable
private fun YesterdaySection() {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        SectionHeader(dotColor = DimText, title = "YESTERDAY", subtitle = "ARCHIVE", subtitleColor = MutedText)
        // Card 5: Adoption
        AdoptionApprovedCard()
        // Card 6: Community Discussion
        CommunityDiscussionCard()
        // Card 7: Donation
        DonationDispatchCard()
    }
}

// ─── Section Header ────────────────────────────────────────────────
@Composable
private fun SectionHeader(dotColor: Color, title: String, subtitle: String, subtitleColor: Color) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.size(12.dp).background(dotColor).border(2.dp, Ink))
            Text(title, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Ink, letterSpacing = (-0.45).sp)
        }
        Text(subtitle, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = subtitleColor, letterSpacing = 0.5.sp)
    }
}

// ─── Card 1: Critical Incident Dispatch ────────────────────────────
@Composable
private fun CriticalIncidentCard() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .border(3.dp, Ink)
            .neoBrutalistShadow(5.dp)
    ) {
        // Hazard ribbon header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(36.dp)
                .background(Crimson)
                .drawBehind {
                    drawLine(Ink, Offset(0f, size.height), Offset(size.width, size.height), 3.dp.toPx())
                }
                .padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                Icon(Icons.Default.LocalHospital, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                Text("CRITICAL INCIDENT DISPATCH", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.White, letterSpacing = 0.7.sp)
            }
            Box(modifier = Modifier.background(Ink).padding(horizontal = 6.dp, vertical = 2.dp)) {
                Text("3M AGO", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Yellow, letterSpacing = 0.8.sp)
            }
        }
        // Body
        Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Box(modifier = Modifier.background(PinkBadge).border(2.dp, Ink).padding(horizontal = 10.dp, vertical = 4.dp)) {
                            Text("CASE #BZ-9024", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = CrimsonDark, letterSpacing = 0.8.sp)
                        }
                        Box(modifier = Modifier.background(Yellow).border(2.dp, Ink).padding(horizontal = 10.dp, vertical = 4.dp)) {
                            Text("800M AWAY", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = YellowDark, letterSpacing = 0.8.sp)
                        }
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        "HIT-AND-RUN CANINE ON\nOUTER RING RD",
                        fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Ink, letterSpacing = (-0.18).sp, lineHeight = 22.5.sp
                    )
                }
                // Thumbnail with crimson corner badge
                Box {
                    Box(
                        modifier = Modifier
                            .size(64.dp)
                            .background(LightGray)
                            .border(3.dp, Ink)
                            .neoBrutalistShadow(2.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Pets, contentDescription = null, tint = MutedText.copy(alpha = 0.4f), modifier = Modifier.size(28.dp))
                    }
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .size(12.dp)
                            .background(Crimson)
                            .border(2.dp, Ink)
                    )
                }
            }
            Text(
                buildAnnotatedString {
                    append("Severe posterior fracture reported near Sector 3 footover bridge. Volunteer vehicle ")
                    withStyle(SpanStyle(fontWeight = FontWeight.Bold, color = Ink)) { append("DL-4C-9921") }
                    append("\nen route with Dr. Rohit. Perimeter cordoning needed immediately.")
                },
                fontSize = 14.sp, color = MutedText, lineHeight = 20.sp
            )
            // Action buttons
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .background(Yellow)
                        .border(3.dp, Ink)
                        .neoBrutalistShadow(4.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text("ACCEPT DISPATCH", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = YellowDark, letterSpacing = (-0.18).sp)
                        Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, tint = YellowDark, modifier = Modifier.size(14.dp))
                    }
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .background(CardGray)
                        .border(3.dp, Ink)
                        .neoBrutalistShadow(3.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        Icon(Icons.Default.Phone, contentDescription = null, tint = Ink, modifier = Modifier.size(14.dp))
                        Text("CALL AMBULANCE", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Ink, letterSpacing = 0.72.sp)
                    }
                }
            }
        }
    }
}

// ─── Card 2: Patient Intake Complete ──────────────────────────────
@Composable
private fun PatientIntakeCard() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .border(3.dp, Ink)
            .neoBrutalistShadow(4.dp)
            .padding(17.dp),
        verticalArrangement = Arrangement.spacedBy(9.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                Box(modifier = Modifier.background(DarkGreen).border(2.dp, Ink).padding(horizontal = 10.dp, vertical = 4.dp)) {
                    Text("RESCUE INTAKE", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = MintGreen, letterSpacing = 0.8.sp)
                }
                Text("#BZ-804", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = MutedText, letterSpacing = 0.8.sp)
            }
            Row(horizontalArrangement = Arrangement.spacedBy(6.dp), verticalAlignment = Alignment.CenterVertically) {
                Box(modifier = Modifier.size(10.dp).clip(CircleShape).background(Color(0xFFC5AB00)).border(1.dp, Ink, CircleShape))
                Text("22m ago", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = MutedText, letterSpacing = 0.8.sp)
            }
        }
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text("PATIENT INTAKE COMPLETE: SHERU", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Ink, letterSpacing = (-0.18).sp, lineHeight = 24.75.sp)
            Text(
                "Volunteer Rohit verified patient intake at Janpath Trauma Centre with Dr. Ananya. Stabilized in Bay 3 with IV saline and anti-tetanus.",
                fontSize = 14.sp, color = MutedText, lineHeight = 20.sp
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .drawBehind {
                    drawLine(Color(0xFFE5E2E1), Offset(0f, 0f), Offset(size.width, 0f), 2.dp.toPx())
                }
                .padding(top = 7.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("DRIVER: VIKRAM S. (+91 9811...)", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = DarkGreen, letterSpacing = 0.8.sp)
            Text(
                buildAnnotatedString {
                    withStyle(SpanStyle(textDecoration = TextDecoration.Underline, fontWeight = FontWeight.Bold)) {
                        append("VIEW CASE LOG ↗")
                    }
                },
                fontSize = 12.sp, color = Ink, letterSpacing = 0.72.sp
            )
        }
    }
}

// ─── Card 3: Medical Lab - PARVO PANEL ────────────────────────────
@Composable
private fun MedicalLabCard() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .border(3.dp, Ink)
            .neoBrutalistShadow(4.dp)
            .padding(17.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                Box(modifier = Modifier.background(MidGreen).border(2.dp, Ink).padding(horizontal = 10.dp, vertical = 4.dp)) {
                    Text("MEDICAL · LAB", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = MintGreen, letterSpacing = 0.8.sp)
                }
                Text("PATIENT: TOMMY", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Ink, letterSpacing = 0.8.sp)
            }
            Box(modifier = Modifier.background(Yellow).border(1.dp, Ink).padding(horizontal = 7.dp, vertical = 3.dp)) {
                Text("UNREAD", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = YellowDark, letterSpacing = 0.8.sp)
            }
        }
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.Top) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(Color(0xFFE5E2DB))
                    .border(2.dp, Ink)
                    .neoBrutalistShadow(2.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.Science, contentDescription = null, tint = MidGreen, modifier = Modifier.size(18.dp))
            }
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text("PARVO PANEL & PCV STABILIZED", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Ink, letterSpacing = (-0.18).sp, lineHeight = 22.5.sp)
                Text(
                    "PCV count improved from 14% to 24% after secondary blood transfusion. Dr. Ananya Sharma cleared Tommy for soft oral nutrition.",
                    fontSize = 14.sp, color = MutedText, lineHeight = 20.sp
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF6F3F2))
                .border(2.dp, Ink)
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Description, contentDescription = null, tint = Ink, modifier = Modifier.size(14.dp))
                Text("LAB-REPORT-902-FINAL.PDF", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Ink, letterSpacing = 0.8.sp)
            }
            Box(
                modifier = Modifier.background(Color.White).border(2.dp, Ink).neoBrutalistShadow(2.dp).padding(horizontal = 14.dp, vertical = 6.dp)
            ) {
                Text("VIEW CHART", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Ink, letterSpacing = 0.8.sp)
            }
        }
    }
}

// ─── Card 4: Foster Milestone ──────────────────────────────────────
@Composable
private fun FosterMilestoneCard() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(FadedCard)
            .border(3.dp, Ink)
            .neoBrutalistShadow(3.dp)
            .padding(17.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                Box(modifier = Modifier.background(Color(0xFFE5E2E1)).border(2.dp, Ink).padding(horizontal = 10.dp, vertical = 4.dp)) {
                    Text("FOSTER CARE", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = MutedText, letterSpacing = 0.8.sp)
                }
                Text("CHHOTI & MITTU", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = MutedText, letterSpacing = 0.8.sp)
            }
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp), verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Schedule, contentDescription = null, tint = MutedText, modifier = Modifier.size(10.dp))
                Text("3h ago", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = MutedText, letterSpacing = 0.8.sp)
            }
        }
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.Top) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .background(LightGray)
                    .border(2.dp, Ink)
                    .neoBrutalistShadow(2.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.Pets, contentDescription = null, tint = MutedText.copy(alpha = 0.4f), modifier = Modifier.size(24.dp))
            }
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text("WEIGHT MILESTONE: 512G\nTARGET MET!", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Ink, letterSpacing = (-0.18).sp, lineHeight = 22.5.sp)
                Text(
                    "Foster guardian Meera Verma uploaded morning weigh-in logs. Both siblings have cleared neonatal underweight protocols.",
                    fontSize = 14.sp, color = MutedText, lineHeight = 20.sp
                )
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("NEXT VET WEIGH-IN: TUESDAY", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = MutedText, letterSpacing = 0.8.sp)
            Text(
                buildAnnotatedString {
                    withStyle(SpanStyle(textDecoration = TextDecoration.Underline, fontWeight = FontWeight.Bold)) { append("SAY THANKS 🐾") }
                },
                fontSize = 10.sp, color = DarkGreen, letterSpacing = 0.8.sp
            )
        }
    }
}

// ─── Card 5: Adoption Approved ─────────────────────────────────────
@Composable
private fun AdoptionApprovedCard() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(FadedCard)
            .border(3.dp, Ink)
            .neoBrutalistShadow(3.dp)
            .padding(17.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(modifier = Modifier.background(MintAdoption).border(2.dp, Ink).padding(horizontal = 10.dp, vertical = 4.dp)) {
                Text("ADOPTION · MATCHED 🎉", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color(0xFF002110), letterSpacing = 0.8.sp)
            }
            Text("Yesterday, 4:15 PM", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = MutedText, letterSpacing = 0.8.sp)
        }
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.Top) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(DarkGreen)
                    .border(2.dp, Ink)
                    .neoBrutalistShadow(2.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.Favorite, contentDescription = null, tint = MintGreen, modifier = Modifier.size(18.dp))
            }
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text("ADOPTION APPLICATION\nAPPROVED: BRUNO", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Ink, letterSpacing = (-0.18).sp, lineHeight = 22.5.sp)
                Text(
                    "Home verification protocol passed for Kabir Anand (Vasant Kunj). Formal adoption sign-off scheduled for Saturday at 11:00 AM.",
                    fontSize = 14.sp, color = MutedText, lineHeight = 20.sp
                )
            }
        }
    }
}

// ─── Card 6: Community Discussion Reply ───────────────────────────
@Composable
private fun CommunityDiscussionCard() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(FadedCard)
            .border(3.dp, Ink)
            .neoBrutalistShadow(3.dp)
            .padding(17.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(modifier = Modifier.background(Color(0xFFE5E2E1)).border(2.dp, Ink).padding(horizontal = 10.dp, vertical = 4.dp)) {
                Text("COMMUNITY · DISCUSSION", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = MutedText, letterSpacing = 0.8.sp)
            }
            Text("Yesterday, 11:30 AM", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = MutedText, letterSpacing = 0.8.sp)
        }
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.Top) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(CardGray)
                    .border(2.dp, Ink)
                    .neoBrutalistShadow(2.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.ChatBubble, contentDescription = null, tint = MutedText, modifier = Modifier.size(20.dp))
            }
            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text("PRIYA SHARMA REPLIED TO YOUR\nSAKET FEED NOTE", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Ink, letterSpacing = (-0.18).sp, lineHeight = 22.5.sp)
                // Quote box
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .border(2.dp, Ink)
                        .padding(10.dp)
                ) {
                    Text(
                        "\u201cThank you so much Aarav! I refilled bowls #4 and #6 near the Community Park gate.\u201d",
                        fontSize = 14.sp,
                        fontStyle = FontStyle.Italic,
                        color = Ink,
                        lineHeight = 20.sp
                    )
                }
            }
        }
    }
}

// ─── Card 7: Donation Dispatch ─────────────────────────────────────
@Composable
private fun DonationDispatchCard() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(FadedCard)
            .border(3.dp, Ink)
            .neoBrutalistShadow(3.dp)
            .padding(17.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(modifier = Modifier.background(DonateYellow).border(2.dp, Ink).padding(horizontal = 10.dp, vertical = 4.dp)) {
                Text("DONATION DISPATCH · ₹700", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = YellowDark, letterSpacing = 0.8.sp)
            }
            Text("2 days ago", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = MutedText, letterSpacing = 0.8.sp)
        }
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.Top) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(DarkGreen)
                    .border(2.dp, Ink)
                    .neoBrutalistShadow(2.dp),
                contentAlignment = Alignment.Center
            ) {
                Text("₹", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = MintGreen, textAlign = androidx.compose.ui.text.style.TextAlign.Center)
            }
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text("AMBULANCE FUEL SPONSORED:\nSQUAD #4", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Ink, letterSpacing = (-0.18).sp, lineHeight = 22.5.sp)
                Text(
                    "Your micro-grant directly sponsored 7.2 liters of CNG fuel for Hauz Khas rapid emergency van responding to 4 canine incidents.",
                    fontSize = 14.sp, color = MutedText, lineHeight = 20.sp
                )
            }
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
            Row(
                modifier = Modifier
                    .height(36.dp)
                    .background(Color.White)
                    .border(2.dp, Ink)
                    .neoBrutalistShadow(2.dp)
                    .padding(horizontal = 14.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Icon(Icons.Default.Receipt, contentDescription = null, tint = Ink, modifier = Modifier.size(13.dp))
                Text("DOWNLOAD 80G TAX RECEIPT", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Ink, letterSpacing = 0.72.sp)
            }
        }
    }
}

// ─── SOS Audio & Radius Triggers Banner ───────────────────────────
@Composable
private fun SosFooterBanner() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Yellow)
            .border(3.dp, Ink)
            .neoBrutalistShadow(5.dp)
            .padding(horizontal = 19.dp, vertical = 19.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Icon(Icons.Default.NotificationsActive, contentDescription = null, tint = YellowDark, modifier = Modifier.size(24.dp))
            Text("SOS AUDIO & RADIUS TRIGGERS", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = YellowDark, letterSpacing = (-0.45).sp)
        }
        Text(
            buildAnnotatedString {
                withStyle(SpanStyle(color = YellowDim)) {
                    append("Control high-pitch emergency ambulance sirens, radius distance limit (currently ")
                }
                withStyle(SpanStyle(fontWeight = FontWeight.Bold, color = YellowDark)) { append("3.0 km") }
                withStyle(SpanStyle(color = YellowDim)) { append("),\nand night foster alert schedules.") }
            },
            fontSize = 14.sp, lineHeight = 19.25.sp
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .background(Ink)
                .border(2.dp, Ink)
                .neoBrutalistShadow(3.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("CONFIGURE DISPATCH\nPREFERENCES", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Yellow, letterSpacing = (-0.18).sp, textAlign = androidx.compose.ui.text.style.TextAlign.Center, lineHeight = 24.sp)
                Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, tint = Yellow, modifier = Modifier.size(14.dp))
            }
        }
    }
}

// ─── Shadow Extension ──────────────────────────────────────────────
private fun Modifier.neoBrutalistShadow(offset: Dp): Modifier = this.drawBehind {
    val px = offset.toPx()
    drawRect(color = Ink, topLeft = Offset(px, px), size = size)
}
