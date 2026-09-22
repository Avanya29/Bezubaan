package com.bezubaan.app.feature.community.presentation

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
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// ─── Color Constants ───────────────────────────────────────────────
private val BgCream = Color(0xFFFCF9F8)
private val DarkInk = Color(0xFF121212)
private val DarkGreen = Color(0xFF0F3822)
private val DeepGreen = Color(0xFF002210)
private val Yellow = Color(0xFFFFE142)
private val LightGray = Color(0xFFF0EDEC)
private val CardBg = Color(0xFFFBF8F1)
private val MutedText = Color(0xFF414942)
private val MintGreen = Color(0xFFC0EDCD)
private val EmeraldOnline = Color(0xFF10B981)
private val OrangeRed = Color(0xFFFF5733)
private val VetRibbon = Color(0xFF274F37)
private val FosterRibbon = Color(0xFFA5D1B1)
private val SearchPlaceholder = Color(0xFF727972)
private val PinkBadge = Color(0xFFFFDAD6)
private val FosterBadgeBg = Color(0xFFE5E2DB)

@Composable
fun CommunityFollowingScreen(
    onBack: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BgCream)
    ) {
        FollowingTopBar(onBack)

        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item { NetworkStatusBanner() }
            item { SearchInputField() }
            item { FilterChipsRow() }
            item { RescuerCard() }
            item { VetSurgeonCard() }
            item { SquadCard() }
            item { FosterCard() }
            item { CommunityFeederCard() }
            item { DiscoverMoreCta() }
        }

        FollowingBottomNavBar()
    }
}

// ─── Top Bar ───────────────────────────────────────────────────────
@Composable
private fun FollowingTopBar(onBack: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(BgCream)
            .drawBehind {
                val strokeWidth = 2.dp.toPx()
                drawLine(
                    color = DarkInk,
                    start = Offset(0f, size.height),
                    end = Offset(size.width, size.height),
                    strokeWidth = strokeWidth
                )
            }
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBack, modifier = Modifier.size(24.dp)) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = DarkInk)
            }
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "FOLLOWING",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = DeepGreen,
                letterSpacing = 1.sp
            )
        }
        // Status pill
        NeoTag(text = "LAN: GRID", bgColor = Yellow, textColor = DarkInk)
    }
}

// ─── Network Status Banner ─────────────────────────────────────────
@Composable
private fun NetworkStatusBanner() {
    NeoCard(
        bgColor = Yellow,
        shadowOffset = 4.dp
    ) {
        Row(
            modifier = Modifier.padding(19.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Globe icon in dark green square
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(DarkGreen)
                    .border(2.dp, DarkInk)
                    .neoBrutalistShadow(2.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.Public, contentDescription = null, tint = Color.White, modifier = Modifier.size(22.dp))
            }
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("DELHI RESCUE GRID", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = DeepGreen, letterSpacing = (-0.45).sp)
                    Box(modifier = Modifier.size(10.dp).clip(CircleShape).background(EmeraldOnline).border(1.dp, DarkInk, CircleShape))
                }
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    buildAnnotatedString {
                        append("You are following ")
                        withStyle(SpanStyle(fontWeight = FontWeight.ExtraBold, textDecoration = TextDecoration.Underline)) {
                            append("28 frontline rescue heroes")
                        }
                        append("\nacross Delhi NCR dispatch zones.")
                    },
                    fontSize = 12.sp,
                    color = DarkInk,
                    lineHeight = 16.5.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

// ─── Search Input ──────────────────────────────────────────────────
@Composable
private fun SearchInputField() {
    Box(modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .background(Color.White, RoundedCornerShape(4.dp))
                .border(3.dp, DarkInk, RoundedCornerShape(4.dp))
                .padding(start = 48.dp, end = 44.dp, top = 17.dp, bottom = 17.dp)
        ) {
            Text(
                "Search followed rescuers, vets & squads...",
                fontSize = 14.sp,
                color = SearchPlaceholder,
                fontWeight = FontWeight.Medium
            )
        }
        // Search icon
        Icon(
            Icons.Default.Search, contentDescription = null,
            modifier = Modifier.align(Alignment.CenterStart).padding(start = 16.dp).size(17.dp),
            tint = DarkInk
        )
        // Clear button
        Box(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 12.dp)
                .size(28.dp)
                .background(LightGray)
                .border(2.dp, DarkInk),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Default.Close, contentDescription = "Clear", modifier = Modifier.size(12.dp), tint = DarkInk)
        }
    }
}

// ─── Filter Chips Row ──────────────────────────────────────────────
@Composable
private fun FilterChipsRow() {
    var selectedIndex by remember { mutableIntStateOf(0) }
    val filters = listOf("ALL (28)", "RESCUERS (14)", "VETS & CLINICS (6)", "FOSTER HOMES (8)")

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        filters.forEachIndexed { index, label ->
            val isSelected = index == selectedIndex
            Box(
                modifier = Modifier
                    .height(36.dp)
                    .background(if (isSelected) Yellow else Color.White)
                    .border(2.dp, DarkInk)
                    .neoBrutalistShadow(if (isSelected) 3.dp else 2.dp)
                    .padding(horizontal = 14.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    label,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.8.sp,
                    color = DarkInk,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

// ─── Card 1: Priya Sharma — Rescuer ────────────────────────────────
@Composable
private fun RescuerCard() {
    FollowingArticleCard(
        ribbonBg = DarkGreen,
        ribbonLabel = "DISPATCH SECTOR: SOUTH DELHI",
        ribbonLabelColor = Color.White,
        ribbonRight = "ID #DEL-9082",
        ribbonRightColor = Yellow,
        ribbonIcon = Icons.Default.LocationOn
    ) {
        // Header row: avatar + identity + more
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                // Avatar with ON DUTY badge
                Box {
                    Box(
                        modifier = Modifier
                            .size(64.dp)
                            .background(Color(0xFFE5E2E1), RoundedCornerShape(4.dp))
                            .border(3.dp, DarkInk, RoundedCornerShape(4.dp))
                            .neoBrutalistShadow(2.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Person, contentDescription = null, tint = MutedText, modifier = Modifier.size(32.dp))
                    }
                    // ON DUTY badge
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .offset(x = 6.dp, y = (-6).dp)
                            .background(EmeraldOnline)
                            .border(2.dp, DarkInk)
                            .neoBrutalistShadow(1.dp)
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text("ON DUTY", fontSize = 9.sp, fontWeight = FontWeight.Medium, color = Color.White)
                    }
                }
                // Identity
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text("Priya Sharma", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = DeepGreen, letterSpacing = (-0.45).sp)
                        Icon(Icons.Default.Verified, contentDescription = null, tint = EmeraldOnline, modifier = Modifier.size(16.dp))
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        NeoTag(text = "VERIFIED RESCUER", bgColor = Yellow, textColor = DarkInk, hasShadow = true)
                        NeoTag(text = "SQUAD #4", bgColor = LightGray, textColor = DarkInk)
                    }
                }
            }
            MoreOptionsButton()
        }

        Spacer(modifier = Modifier.height(12.dp))
        // Bio
        Text(
            "Street dog advocate & foster mom. Co-founder\nof Saket Water Bowl Project. Specializing in\nemergency night trauma dispatches.",
            fontSize = 14.sp, color = MutedText, fontWeight = FontWeight.Medium, lineHeight = 22.75.sp
        )

        Spacer(modifier = Modifier.height(12.dp))
        // Stats strip
        StatsStrip(
            stats = listOf(
                StatItem("RESCUES", "42 CASES"),
                StatItem("FOSTERED", "18 PUPS"),
                StatItem("AREA", "SOUTH DEL")
            )
        )

        Spacer(modifier = Modifier.height(8.dp))
        // Action buttons
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
            FollowingButton(modifier = Modifier.weight(1f))
            ActionChipButton(text = "MSG", icon = Icons.Default.ChatBubble, bgColor = Color.White, textColor = DarkInk)
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .background(Yellow)
                    .border(2.dp, DarkInk)
                    .neoBrutalistShadow(3.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.NotificationsActive, contentDescription = "Notify", tint = DarkInk, modifier = Modifier.size(17.dp))
            }
        }
    }
}

// ─── Card 2: Dr. Ananya Sharma — Vet Surgeon ──────────────────────
@Composable
private fun VetSurgeonCard() {
    FollowingArticleCard(
        ribbonBg = VetRibbon,
        ribbonLabel = "24/7 SURGICAL ADVISORY",
        ribbonLabelColor = Color.White,
        ribbonRight = "CLINIC ACTIVE",
        ribbonRightColor = MintGreen,
        ribbonIcon = Icons.Default.MedicalServices
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                // Vet avatar
                Box {
                    Box(
                        modifier = Modifier
                            .size(64.dp)
                            .background(MintGreen, RoundedCornerShape(4.dp))
                            .border(3.dp, DarkInk, RoundedCornerShape(4.dp))
                            .neoBrutalistShadow(2.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.LocalHospital, contentDescription = null, tint = DarkGreen, modifier = Modifier.size(32.dp))
                    }
                    // Stethoscope badge
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .offset(x = 4.dp, y = 4.dp)
                            .size(18.dp)
                            .background(DeepGreen)
                            .border(1.dp, DarkInk),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.MedicalServices, contentDescription = null, tint = Color.White, modifier = Modifier.size(10.dp))
                    }
                }
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text("Dr. Ananya Sharma", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = DeepGreen, letterSpacing = (-0.45).sp)
                        Icon(Icons.Default.Verified, contentDescription = null, tint = EmeraldOnline, modifier = Modifier.size(14.dp))
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        NeoTag(text = "VET SURGEON", bgColor = DarkGreen, textColor = Color.White, hasShadow = true)
                        NeoTag(text = "JANPATH CLINIC", bgColor = LightGray, textColor = DarkInk)
                    }
                }
            }
            MoreOptionsButton()
        }

        Spacer(modifier = Modifier.height(12.dp))
        Text(
            "Senior Trauma Surgeon at Janpath Animal\nTrauma Centre. Orthopedic bone pinning,\nwound telemetry & emergency stabilization.",
            fontSize = 14.sp, color = MutedText, fontWeight = FontWeight.Medium, lineHeight = 22.75.sp
        )

        Spacer(modifier = Modifier.height(12.dp))
        // Stats — centered layout for vet
        StatsStripCentered(
            stats = listOf(
                StatItem("SURGERIES", "180+"),
                StatItem("RECOVERY RATE", "98%"),
                StatItem("AVG RESPONSE", "15 MIN")
            )
        )

        Spacer(modifier = Modifier.height(8.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
            FollowingButton(modifier = Modifier.weight(1f))
            ActionChipButton(text = "TELE-VET", icon = Icons.Default.Phone, bgColor = OrangeRed, textColor = Color.White)
        }
    }
}

// ─── Card 3: South Delhi Squad #4 ─────────────────────────────────
@Composable
private fun SquadCard() {
    FollowingArticleCard(
        ribbonBg = OrangeRed,
        ribbonLabel = "DISPATCH AMBULANCE CLUSTER",
        ribbonLabelColor = Color.White,
        ribbonRight = null,
        ribbonRightColor = Color.White,
        ribbonIcon = Icons.Default.LocalShipping,
        ribbonBadge = "24/7 PATROL"
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                // Squad emblem
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .background(Yellow, RoundedCornerShape(4.dp))
                        .border(3.dp, DarkInk, RoundedCornerShape(4.dp))
                        .neoBrutalistShadow(2.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Default.LocalShipping, contentDescription = null, tint = DarkInk, modifier = Modifier.size(20.dp))
                        Text("SQ-04", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = DarkInk)
                    }
                }
                Column {
                    Text("South Delhi Squad #4", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = DeepGreen, letterSpacing = (-0.45).sp)
                    Spacer(modifier = Modifier.height(4.dp))
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        NeoTag(text = "OFFICIAL SQUAD", bgColor = PinkBadge, textColor = Color(0xFFBA1A1A), hasShadow = true)
                        NeoTag(text = "HAUZ KHAS BASE", bgColor = FosterBadgeBg, textColor = DarkInk)
                    }
                }
            }
            MoreOptionsButton()
        }

        Spacer(modifier = Modifier.height(12.dp))
        Text(
            "24/7 Rapid response ambulance unit covering\nMalviya Nagar, Saket, Mehrauli, and Hauz Khas.\nCoordinated via Bezubaan Radar.",
            fontSize = 14.sp, color = MutedText, fontWeight = FontWeight.Medium, lineHeight = 22.75.sp
        )

        Spacer(modifier = Modifier.height(12.dp))
        StatsStrip(
            stats = listOf(
                StatItem("LIFETIME DISPATCHES", "2,410 TRIPS"),
                StatItem("VOLUNTEERS ON-CALL", "12 ACTIVE NOW")
            )
        )

        Spacer(modifier = Modifier.height(8.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
            FollowingButton(modifier = Modifier.weight(1f))
            ActionChipButton(text = "DISPATCH", icon = Icons.Default.Campaign, bgColor = Yellow, textColor = DarkInk)
        }
    }
}

// ─── Card 4: Meera Verma — Foster Haven ────────────────────────────
@Composable
private fun FosterCard() {
    FollowingArticleCard(
        ribbonBg = FosterRibbon,
        ribbonLabel = "CERTIFIED FOSTER HAVEN",
        ribbonLabelColor = DeepGreen,
        ribbonRight = "CAPACITY: 4/5",
        ribbonRightColor = DeepGreen,
        ribbonIcon = Icons.Default.Home
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Box {
                    Box(
                        modifier = Modifier
                            .size(64.dp)
                            .background(LightGray, RoundedCornerShape(4.dp))
                            .border(3.dp, DarkInk, RoundedCornerShape(4.dp))
                            .neoBrutalistShadow(2.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Pets, contentDescription = null, tint = MutedText, modifier = Modifier.size(32.dp))
                    }
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .offset(x = 6.dp, y = (-6).dp)
                            .background(Yellow)
                            .border(2.dp, DarkInk)
                            .neoBrutalistShadow(1.dp)
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text("FOSTER", fontSize = 9.sp, fontWeight = FontWeight.Medium, color = DarkInk)
                    }
                }
                Column {
                    Text("Meera Verma", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = DeepGreen, letterSpacing = (-0.45).sp)
                    Spacer(modifier = Modifier.height(4.dp))
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        NeoTag(text = "CERTIFIED FOSTER", bgColor = MintGreen, textColor = DeepGreen, hasShadow = true)
                        NeoTag(text = "HAUZ KHAS #11", bgColor = LightGray, textColor = DarkInk)
                    }
                }
            }
            MoreOptionsButton()
        }

        Spacer(modifier = Modifier.height(12.dp))
        Text(
            "Neonatal kitten syringe feeder & post-op dog\nfoster. Caring for sensitive trauma recoveries\nuntil adoption day.",
            fontSize = 14.sp, color = MutedText, fontWeight = FontWeight.Medium, lineHeight = 22.75.sp
        )

        Spacer(modifier = Modifier.height(12.dp))
        StatsStrip(
            stats = listOf(
                StatItem("HEALED SOULS", "34 ADOPTED"),
                StatItem("CURRENT GUESTS", "3 KITTENS + 1 DOG")
            )
        )

        Spacer(modifier = Modifier.height(8.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
            FollowingButton(modifier = Modifier.weight(1f))
            ActionChipButton(text = "ADOPT", icon = Icons.Default.Favorite, bgColor = MintGreen, textColor = DeepGreen)
        }
    }
}

// ─── Card 5: Kabir Anand — Community Feeder ────────────────────────
@Composable
private fun CommunityFeederCard() {
    FollowingArticleCard(
        ribbonBg = Color.White,
        ribbonLabel = "COMMUNITY FEEDER & ABC ADVOCATE",
        ribbonLabelColor = MutedText,
        ribbonRight = "SECTOR 2",
        ribbonRightColor = MutedText,
        ribbonIcon = Icons.Default.Restaurant,
        ribbonBorderBottom = true
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .background(LightGray, RoundedCornerShape(4.dp))
                        .border(3.dp, DarkInk, RoundedCornerShape(4.dp))
                        .neoBrutalistShadow(2.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.Person, contentDescription = null, tint = MutedText, modifier = Modifier.size(32.dp))
                }
                Column {
                    Text("Kabir Anand", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = DeepGreen, letterSpacing = (-0.45).sp)
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        NeoTag(text = "COMMUNITY FEEDER", bgColor = Color(0xFFD4F5DC), textColor = DeepGreen, hasShadow = true)
                    }
                }
            }
            MoreOptionsButton()
        }

        Spacer(modifier = Modifier.height(12.dp))
        Text(
            "Daily feeder of 65 street Indies in Saket Block C.\nManaging sterilizations (ABC program) & rabies\nbooster drives.",
            fontSize = 14.sp, color = MutedText, fontWeight = FontWeight.Medium, lineHeight = 22.75.sp
        )

        Spacer(modifier = Modifier.height(12.dp))
        StatsStripCentered(
            stats = listOf(
                StatItem("INDIES FED DAILY", "65"),
                StatItem("VACCINATED", "100%"),
                StatItem("FIELD SERVICE", "4 YRS")
            )
        )

        Spacer(modifier = Modifier.height(8.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
            FollowingButton(modifier = Modifier.weight(1f))
            ActionChipButton(text = "FEED-FUND", icon = Icons.Default.VolunteerActivism, bgColor = Yellow, textColor = DarkInk)
        }
    }
}

// ─── Discover More CTA ─────────────────────────────────────────────
@Composable
private fun DiscoverMoreCta() {
    Column(
        modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        NeoCard(bgColor = Yellow, shadowOffset = 5.dp) {
            Row(
                modifier = Modifier.fillMaxWidth().height(56.dp).padding(horizontal = 19.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(Icons.Default.Explore, contentDescription = null, tint = DarkInk, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    "DISCOVER MORE RESCUERS NEAR YOU",
                    fontWeight = FontWeight.Bold, fontSize = 14.sp, color = DarkInk,
                    letterSpacing = 0.56.sp, textAlign = TextAlign.Center
                )
            }
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Icon(Icons.Default.VerifiedUser, contentDescription = null, tint = MutedText, modifier = Modifier.size(12.dp))
            Text(
                "100% ID VERIFIED GRASSROOTS RESCUERS",
                fontSize = 10.sp, fontWeight = FontWeight.Bold, color = MutedText,
                letterSpacing = 0.8.sp
            )
        }
    }
}

// ─── Bottom Nav Bar ────────────────────────────────────────────────
@Composable
private fun FollowingBottomNavBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .drawBehind {
                drawLine(DarkInk, Offset(0f, 0f), Offset(size.width, 0f), 2.dp.toPx())
            }
            .background(BgCream)
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        BottomNavItem(Icons.Default.Home, "HOME", false)
        BottomNavItem(Icons.Default.Pets, "RESCUE", false)
        BottomNavItem(Icons.Default.Groups, "COMMUNITY", true)
        BottomNavItem(Icons.Default.Redeem, "DONATE", false)
        BottomNavItem(Icons.Default.Person, "PROFILE", false)
    }
}

@Composable
private fun BottomNavItem(icon: ImageVector, label: String, isSelected: Boolean) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(horizontal = 4.dp)
    ) {
        Icon(
            icon, contentDescription = label,
            tint = if (isSelected) DarkGreen else MutedText,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            label, fontSize = 9.sp, fontWeight = FontWeight.Bold,
            color = if (isSelected) DarkGreen else MutedText,
            letterSpacing = 0.5.sp
        )
        if (isSelected) {
            Spacer(modifier = Modifier.height(2.dp))
            Box(modifier = Modifier.width(24.dp).height(2.dp).background(DarkGreen))
        }
    }
}

// ═══════════════════════════════════════════════════════════════════
// ──── REUSABLE COMPONENTS ──────────────────────────────────────────
// ═══════════════════════════════════════════════════════════════════

/** Generic Article Card wrapper with colored ribbon at top */
@Composable
private fun FollowingArticleCard(
    ribbonBg: Color,
    ribbonLabel: String,
    ribbonLabelColor: Color,
    ribbonRight: String?,
    ribbonRightColor: Color,
    ribbonIcon: ImageVector,
    ribbonBadge: String? = null,
    ribbonBorderBottom: Boolean = false,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(CardBg, RoundedCornerShape(4.dp))
            .border(3.dp, DarkInk, RoundedCornerShape(4.dp))
            .neoBrutalistShadow(4.dp)
    ) {
        // Ribbon
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(ribbonBg)
                .then(
                    if (ribbonBorderBottom) Modifier.drawBehind {
                        drawLine(DarkInk, Offset(0f, size.height), Offset(size.width, size.height), 3.dp.toPx())
                    } else Modifier.drawBehind {
                        drawLine(DarkInk, Offset(0f, size.height), Offset(size.width, size.height), 3.dp.toPx())
                    }
                )
                .padding(horizontal = 12.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                Icon(ribbonIcon, contentDescription = null, tint = ribbonLabelColor, modifier = Modifier.size(12.dp))
                Text(ribbonLabel, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = ribbonLabelColor, letterSpacing = 1.sp)
            }
            Row(horizontalArrangement = Arrangement.spacedBy(6.dp), verticalAlignment = Alignment.CenterVertically) {
                if (ribbonRight != null) {
                    Text(ribbonRight, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = ribbonRightColor, letterSpacing = 0.8.sp)
                }
                if (ribbonBadge != null) {
                    Box(modifier = Modifier.background(DarkInk).padding(horizontal = 6.dp, vertical = 2.dp)) {
                        Text(ribbonBadge, fontSize = 9.sp, fontWeight = FontWeight.Medium, color = Color.White)
                    }
                }
            }
        }
        // Card body
        Column(modifier = Modifier.padding(16.dp)) {
            content()
        }
    }
}

/** Neo-Brutalist Card wrapper */
@Composable
private fun NeoCard(bgColor: Color, shadowOffset: androidx.compose.ui.unit.Dp, content: @Composable () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(bgColor, RoundedCornerShape(4.dp))
            .border(3.dp, DarkInk, RoundedCornerShape(4.dp))
            .neoBrutalistShadow(shadowOffset)
    ) {
        content()
    }
}

/** Small tag/badge */
@Composable
private fun NeoTag(text: String, bgColor: Color, textColor: Color, hasShadow: Boolean = false) {
    Box(
        modifier = Modifier
            .background(bgColor)
            .border(2.dp, DarkInk)
            .then(if (hasShadow) Modifier.neoBrutalistShadow(1.5.dp) else Modifier)
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(text, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = textColor, letterSpacing = 0.8.sp)
    }
}

/** More options (three dots) button */
@Composable
private fun MoreOptionsButton() {
    Box(
        modifier = Modifier
            .size(32.dp)
            .background(LightGray)
            .border(2.dp, DarkInk)
            .neoBrutalistShadow(2.dp),
        contentAlignment = Alignment.Center
    ) {
        Icon(Icons.Default.MoreVert, contentDescription = "More", tint = DarkInk, modifier = Modifier.size(16.dp))
    }
}

/** Stats strip with labels on top, values on bottom, separated by dividers */
@Composable
private fun StatsStrip(stats: List<StatItem>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(LightGray)
            .border(2.dp, DarkInk)
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        stats.forEachIndexed { index, stat ->
            Column {
                Text(stat.label, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = MutedText, letterSpacing = 0.8.sp)
                Text(stat.value, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = DeepGreen, letterSpacing = (-0.18).sp)
            }
            if (index < stats.lastIndex) {
                Box(modifier = Modifier.width(2.dp).height(24.dp).background(DarkInk))
            }
        }
    }
}

/** Stats strip — centered variant (value on top, label below) */
@Composable
private fun StatsStripCentered(stats: List<StatItem>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(LightGray)
            .border(2.dp, DarkInk)
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        stats.forEachIndexed { index, stat ->
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(stat.value, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = DeepGreen, letterSpacing = (-0.18).sp, textAlign = TextAlign.Center)
                Text(stat.label, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = MutedText, letterSpacing = 0.8.sp, textAlign = TextAlign.Center)
            }
            if (index < stats.lastIndex) {
                Box(modifier = Modifier.width(2.dp).height(24.dp).background(DarkInk))
            }
        }
    }
}

/** "FOLLOWING" primary action button (dark green) */
@Composable
private fun FollowingButton(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .height(44.dp)
            .background(DarkGreen)
            .border(2.dp, DarkInk)
            .neoBrutalistShadow(3.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            Icon(Icons.Default.Check, contentDescription = null, tint = Color.White, modifier = Modifier.size(12.dp))
            Text("FOLLOWING", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color.White, letterSpacing = 0.8.sp)
        }
    }
}

/** Action chip button (MSG, TELE-VET, DISPATCH, ADOPT, FEED-FUND) */
@Composable
private fun ActionChipButton(text: String, icon: ImageVector, bgColor: Color, textColor: Color) {
    Box(
        modifier = Modifier
            .height(44.dp)
            .background(bgColor)
            .border(2.dp, DarkInk)
            .neoBrutalistShadow(3.dp)
            .padding(horizontal = 14.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            Icon(icon, contentDescription = null, tint = textColor, modifier = Modifier.size(14.dp))
            Text(text, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = textColor, letterSpacing = 0.8.sp)
        }
    }
}

/** Data class for stats */
private data class StatItem(val label: String, val value: String)

// ─── Shadow Extension ──────────────────────────────────────────────
private fun Modifier.neoBrutalistShadow(offset: androidx.compose.ui.unit.Dp): Modifier = this.drawBehind {
    val shadowPx = offset.toPx()
    drawRect(
        color = DarkInk,
        topLeft = Offset(shadowPx, shadowPx),
        size = size
    )
}
