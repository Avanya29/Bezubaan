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
import androidx.compose.material.icons.automirrored.filled.OpenInNew
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
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
private val OrangeRed = Color(0xFFFF5733)
private val HeaderBg = Color(0xFFF6F3F2)
private val PinkBanner = Color(0xFFFFDAD6)
private val CrimsonText = Color(0xFF93000A)
private val FosterGreen = Color(0xFFA5D1B1)
private val EmeraldOnline = Color(0xFF10B981)

@Composable
fun SavedPostsScreen(
    onBack: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BgCream)
    ) {
        SavedPostsTopBar(onBack)

        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            item { OfflineCacheBanner() }
            item { SearchAndFilters() }
            item { SheruRescueStoryCard() }
            item { UrgentBloodDonorCard() }
            item { NeonatalFosterCard() }
            item { StorageFooter() }
        }

        SavedPostsBottomNavBar()
    }
}

// ─── Top Bar ───────────────────────────────────────────────────────
@Composable
private fun SavedPostsTopBar(onBack: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(BgCream)
            .drawBehind {
                drawLine(DarkInk, Offset(0f, size.height), Offset(size.width, size.height), 2.dp.toPx())
            }
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBack, modifier = Modifier.size(24.dp)) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = DarkInk)
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text("SAVED POSTS", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = DeepGreen, letterSpacing = 1.sp)
                Text("COMMUNITY HUB", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = MutedText, letterSpacing = 0.5.sp)
            }
        }
        // Edit tag
        Box(
            modifier = Modifier
                .background(OrangeRed)
                .border(2.dp, DarkInk)
                .padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
            Text("EDIT", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color.White, letterSpacing = 0.8.sp)
        }
    }
}

// ─── Offline Cache Banner ──────────────────────────────────────────
@Composable
private fun OfflineCacheBanner() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Yellow, RoundedCornerShape(4.dp))
            .border(3.dp, DarkInk, RoundedCornerShape(4.dp))
            .neoBrutalistShadow(4.dp)
    ) {
        Row(
            modifier = Modifier.padding(15.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Wifi-off icon in white box
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .background(Color.White)
                    .border(2.dp, DarkInk)
                    .neoBrutalistShadow(2.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.WifiOff, contentDescription = null, tint = DarkInk, modifier = Modifier.size(18.dp))
            }
            Column(modifier = Modifier.weight(1f)) {
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp), verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.background(DarkInk).padding(horizontal = 6.dp, vertical = 2.dp)) {
                        Text("OFFLINE READY", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color.White, letterSpacing = 0.8.sp)
                    }
                    Text("FIELD CACHE", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1C1B1B), letterSpacing = 0.5.sp)
                }
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    "All 18 bookmarks saved locally for zero-signal\nfield rescues.",
                    fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF1C1B1B), lineHeight = 15.sp
                )
            }
        }
    }
}

// ─── Search + Filters ──────────────────────────────────────────────
@Composable
private fun SearchAndFilters() {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        // Search bar
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .background(Color.White, RoundedCornerShape(4.dp))
                .border(3.dp, DarkInk, RoundedCornerShape(4.dp))
                .neoBrutalistShadow(3.dp)
                .padding(horizontal = 15.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(Icons.Default.Search, contentDescription = null, tint = MutedText, modifier = Modifier.size(15.dp))
                Text(
                    "SEARCH SAVED CASES, RESCUERS, TAGS...",
                    fontSize = 12.sp, fontWeight = FontWeight.Bold, color = MutedText,
                    letterSpacing = 0.6.sp, modifier = Modifier.weight(1f)
                )
                Box(
                    modifier = Modifier.size(width = 24.dp, height = 28.dp).background(LightGray).border(2.dp, DarkInk),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.Tune, contentDescription = "Filter", tint = DarkInk, modifier = Modifier.size(12.dp))
                }
            }
        }

        // Filter chips
        var selectedChip by remember { mutableIntStateOf(0) }
        val chips = listOf("ALL SAVED (18)", "\uD83D\uDC3E RESCUE STORIES (8)", "\uD83E\uDE7A MEDICAL LOGS (6)", "\uD83C\uDF7C FOSTER DIARIES (4)")

        Row(
            modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            chips.forEachIndexed { index, label ->
                val isSelected = index == selectedChip
                Box(
                    modifier = Modifier
                        .height(32.dp)
                        .background(if (isSelected) DarkGreen else CardBg, RoundedCornerShape(12.dp))
                        .border(2.dp, DarkInk, RoundedCornerShape(12.dp))
                        .neoBrutalistShadow(2.dp)
                        .padding(horizontal = 14.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        label, fontSize = 10.sp, fontWeight = FontWeight.Bold,
                        color = if (isSelected) Color.White else Color(0xFF1C1B1B),
                        letterSpacing = 0.5.sp
                    )
                }
            }
        }
    }
}

// ─── Saved Post 1: Sheru's Rescue Story ────────────────────────────
@Composable
private fun SheruRescueStoryCard() {
    SavedArticleCard {
        // Header bar
        ArticleHeader(
            avatarBg = Yellow,
            avatarIcon = Icons.Default.Pets,
            name = "Priya Sharma",
            subtitle = "SOUTH DELHI SQUAD #4",
            isVerified = true,
            bookmarkBg = Yellow
        )

        // Image area
        ImageArea(
            bgColor = LightGray,
            topBadge = "RESCUE STORY • ADOPTED",
            topBadgeBg = Yellow,
            topBadgeTextColor = DarkInk,
            bottomRightLabel = "CASE #BZ-804",
            bottomRightBg = DarkInk,
            bottomRightTextColor = Color.White
        )

        // Content body
        Column(modifier = Modifier.padding(16.dp)) {
            // Meta tag
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                Icon(Icons.Default.EmojiEvents, contentDescription = null, tint = MutedText, modifier = Modifier.size(11.dp))
                Text("RECOVERY MILESTONES • SAVED 3D AGO", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = MutedText, letterSpacing = 0.5.sp)
            }

            Spacer(modifier = Modifier.height(4.dp))
            Text(
                "Sheru's 42-Day Miracle: From\nMonsoon Rain to Lodhi Gardens",
                fontSize = 18.sp, fontWeight = FontWeight.Bold, color = DeepGreen, letterSpacing = (-0.18).sp, lineHeight = 24.75.sp
            )

            Spacer(modifier = Modifier.height(12.dp))
            Text(
                "When our night squad found Sheru on Kalyan\nMarg with fractured limbs, survival seemed like a...",
                fontSize = 14.sp, fontWeight = FontWeight.Medium, color = MutedText, lineHeight = 22.75.sp,
                maxLines = 2
            )

            Spacer(modifier = Modifier.height(12.dp))
            // Stats pills
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                StatPill("RECOVERY", "42 DAYS", modifier = Modifier.weight(1f))
                StatPill("TREATMENT", "DR. KAUR", modifier = Modifier.weight(1f))
                StatPill("STATUS", "FOREVER\nHOME", modifier = Modifier.weight(1f), valueColor = DarkGreen)
            }

            Spacer(modifier = Modifier.height(16.dp))
            // Action footer
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                ActionButton(
                    text = "OPEN FULL CASE",
                    icon = Icons.AutoMirrored.Filled.OpenInNew,
                    bgColor = Yellow,
                    textColor = DarkInk,
                    modifier = Modifier.weight(1f)
                )
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(Color.White)
                        .border(3.dp, DarkInk)
                        .neoBrutalistShadow(3.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.Share, contentDescription = "Share", tint = DarkInk, modifier = Modifier.size(16.dp))
                }
            }
        }
    }
}

// ─── Saved Post 2: Urgent Blood Donor ──────────────────────────────
@Composable
private fun UrgentBloodDonorCard() {
    SavedArticleCard {
        // Urgent crimson header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(OrangeRed)
                .drawBehind {
                    drawLine(DarkInk, Offset(0f, size.height), Offset(size.width, size.height), 3.dp.toPx())
                }
                .padding(horizontal = 12.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .background(Color.White, RoundedCornerShape(12.dp))
                        .border(2.dp, DarkInk, RoundedCornerShape(12.dp))
                        .neoBrutalistShadow(1.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.LocalHospital, contentDescription = null, tint = OrangeRed, modifier = Modifier.size(16.dp))
                }
                Column {
                    Text("BEZUBAAN EMERGENCY DESK", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.White, letterSpacing = 0.56.sp)
                    Text("PRIORITY DISPATCH ALERT", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color.White.copy(alpha = 0.9f), letterSpacing = 0.5.sp)
                }
            }
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .background(Color.White)
                    .border(2.dp, DarkInk)
                    .neoBrutalistShadow(2.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.Bookmark, contentDescription = null, tint = DarkInk, modifier = Modifier.size(14.dp))
            }
        }

        // Image area with urgent badges
        ImageArea(
            bgColor = LightGray,
            topBadge = "URGENT ALERT • PARVO SURVIVOR",
            topBadgeBg = Color(0xFFBA1A1A),
            topBadgeTextColor = Color.White,
            bottomRightLabel = "BLOOD GROUP: DEA 1.1 NEG",
            bottomRightBg = Yellow,
            bottomRightTextColor = DarkInk,
            imageIcon = Icons.Default.Bloodtype
        )

        // Content body
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                Icon(Icons.Default.MedicalServices, contentDescription = null, tint = MutedText, modifier = Modifier.size(11.dp))
                Text("MEDICAL REFERENCE • SAVED YESTERDAY", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = MutedText, letterSpacing = 0.5.sp)
            }

            Spacer(modifier = Modifier.height(4.dp))
            Text(
                "Donor Dog Needed for Tommy (Parvo\n+ Severe Anaemia)",
                fontSize = 18.sp, fontWeight = FontWeight.Bold, color = DeepGreen, letterSpacing = (-0.18).sp, lineHeight = 24.75.sp
            )

            Spacer(modifier = Modifier.height(12.dp))
            Text(
                "Tommy's PCV levels dropped below 12%.\nJanpath Trauma Centre transfusion protocol...",
                fontSize = 14.sp, fontWeight = FontWeight.Medium, color = MutedText, lineHeight = 22.75.sp,
                maxLines = 2
            )

            Spacer(modifier = Modifier.height(12.dp))
            // Protocol banner
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(PinkBanner)
                    .border(2.dp, DarkInk)
                    .padding(12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Icon(Icons.Default.Bloodtype, contentDescription = null, tint = CrimsonText, modifier = Modifier.size(14.dp))
                    Text("TRANSFUSION COMPLETED", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = CrimsonText, letterSpacing = 0.8.sp)
                }
                Box(
                    modifier = Modifier.background(Color.White).border(1.dp, DarkInk).padding(horizontal = 7.dp, vertical = 3.dp)
                ) {
                    Text("PCV: 24% ↑", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = DarkInk, letterSpacing = 0.5.sp)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                ActionButton(
                    text = "VIEW MEDICAL PROTOCOL",
                    icon = Icons.Default.Description,
                    bgColor = DarkGreen,
                    textColor = Color.White,
                    modifier = Modifier.weight(1f)
                )
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(Color.White)
                        .border(3.dp, DarkInk)
                        .neoBrutalistShadow(3.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.Share, contentDescription = "Share", tint = DarkInk, modifier = Modifier.size(16.dp))
                }
            }
        }
    }
}

// ─── Saved Post 3: Neonatal Foster Milestone ───────────────────────
@Composable
private fun NeonatalFosterCard() {
    SavedArticleCard {
        ArticleHeader(
            avatarBg = FosterGreen,
            avatarIcon = Icons.Default.Pets,
            name = "Dr. Ananya Sharma",
            subtitle = "LEAD VET • HAUZ KHAS BAY",
            isVerified = true,
            bookmarkBg = Yellow
        )

        // Image area
        ImageArea(
            bgColor = LightGray,
            topBadge = "FOSTER DIARY • 500G MILESTONE",
            topBadgeBg = DarkGreen,
            topBadgeTextColor = Color.White,
            bottomRightLabel = "PAIR: CHHOTI & MITTU",
            bottomRightBg = Yellow,
            bottomRightTextColor = DarkInk,
            imageIcon = Icons.Default.Pets
        )

        // Content body
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                Icon(Icons.Default.Home, contentDescription = null, tint = MutedText, modifier = Modifier.size(11.dp))
                Text("FOSTER CARE HUB • SAVED OCT 14", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = MutedText, letterSpacing = 0.5.sp)
            }

            Spacer(modifier = Modifier.height(4.dp))
            Text(
                "Chhoti & Mittu Hit The 500g\nMilestone! Eyes Open & Purring",
                fontSize = 18.sp, fontWeight = FontWeight.Bold, color = DeepGreen, letterSpacing = (-0.18).sp, lineHeight = 24.75.sp
            )

            Spacer(modifier = Modifier.height(12.dp))
            Text(
                "Bottle feeding every 3 hours has paid off. Both\nneonatal kittens are dewormed and preparing...",
                fontSize = 14.sp, fontWeight = FontWeight.Medium, color = MutedText, lineHeight = 22.75.sp,
                maxLines = 2
            )

            Spacer(modifier = Modifier.height(12.dp))
            // Stats pills
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                StatPill("WEIGHT", "150g / 504g", modifier = Modifier.weight(1f))
                StatPill("DIET", "KMR + WET MASH", modifier = Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(16.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                ActionButton(
                    text = "VIEW FOSTER LOGS",
                    icon = Icons.Default.Folder,
                    bgColor = Color.White,
                    textColor = DarkInk,
                    modifier = Modifier.weight(1f),
                    borderColor = DarkInk
                )
                IconButton(onClick = {}, modifier = Modifier.size(48.dp)) {
                    Icon(Icons.Default.ChevronLeft, contentDescription = "Previous", tint = DarkInk)
                }
                IconButton(onClick = {}, modifier = Modifier.size(48.dp)) {
                    Icon(Icons.Default.ChevronRight, contentDescription = "Next", tint = DarkInk)
                }
            }
        }
    }
}

// ─── Storage Footer ────────────────────────────────────────────────
@Composable
private fun StorageFooter() {
    Column(
        modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(DarkGreen, RoundedCornerShape(4.dp))
                .border(2.dp, DarkInk, RoundedCornerShape(4.dp))
                .padding(horizontal = 14.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Icon(Icons.Default.Storage, contentDescription = null, tint = Yellow, modifier = Modifier.size(14.dp))
                Column {
                    Text("STORAGE USED: 4.8 MB", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.White, letterSpacing = 0.5.sp)
                    Text("18 Posts Cached Locally", fontSize = 10.sp, fontWeight = FontWeight.Medium, color = Color.White.copy(alpha = 0.7f))
                }
            }
            Icon(Icons.Default.DeleteSweep, contentDescription = null, tint = OrangeRed, modifier = Modifier.size(18.dp))
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(LightGray, RoundedCornerShape(4.dp))
                .border(2.dp, DarkInk, RoundedCornerShape(4.dp))
                .padding(horizontal = 14.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Icon(Icons.Default.FolderOpen, contentDescription = null, tint = MutedText, modifier = Modifier.size(14.dp))
                Text("MANAGE FOLDERS", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = MutedText, letterSpacing = 0.8.sp)
            }
            Icon(Icons.Default.ChevronRight, contentDescription = null, tint = MutedText, modifier = Modifier.size(18.dp))
        }
    }
}

// ─── Bottom Nav Bar ────────────────────────────────────────────────
@Composable
private fun SavedPostsBottomNavBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .drawBehind { drawLine(DarkInk, Offset(0f, 0f), Offset(size.width, 0f), 2.dp.toPx()) }
            .background(BgCream)
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        SavedBottomNavItem(Icons.Default.Home, "HOME", false)
        SavedBottomNavItem(Icons.Default.Pets, "RESCUE", false)
        SavedBottomNavItem(Icons.Default.Groups, "COMMUNITY", true)
        SavedBottomNavItem(Icons.Default.Redeem, "DONATE", false)
        SavedBottomNavItem(Icons.Default.Person, "PROFILE", false)
    }
}

@Composable
private fun SavedBottomNavItem(icon: ImageVector, label: String, isSelected: Boolean) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(horizontal = 4.dp)) {
        Icon(icon, contentDescription = label, tint = if (isSelected) DarkGreen else MutedText, modifier = Modifier.size(24.dp))
        Spacer(modifier = Modifier.height(2.dp))
        Text(label, fontSize = 9.sp, fontWeight = FontWeight.Bold, color = if (isSelected) DarkGreen else MutedText, letterSpacing = 0.5.sp)
        if (isSelected) {
            Spacer(modifier = Modifier.height(2.dp))
            Box(modifier = Modifier.width(24.dp).height(2.dp).background(DarkGreen))
        }
    }
}

// ═══════════════════════════════════════════════════════════════════
// ──── REUSABLE COMPONENTS ──────────────────────────────────────────
// ═══════════════════════════════════════════════════════════════════

/** Saved article card wrapper */
@Composable
private fun SavedArticleCard(content: @Composable ColumnScope.() -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(CardBg, RoundedCornerShape(4.dp))
            .border(3.dp, DarkInk, RoundedCornerShape(4.dp))
            .neoBrutalistShadow(4.dp)
    ) {
        content()
    }
}

/** Standard article header with avatar, name, subtitle, bookmark */
@Composable
private fun ArticleHeader(
    avatarBg: Color,
    avatarIcon: ImageVector,
    name: String,
    subtitle: String,
    isVerified: Boolean,
    bookmarkBg: Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(HeaderBg)
            .drawBehind {
                drawLine(DarkInk, Offset(0f, size.height), Offset(size.width, size.height), 3.dp.toPx())
            }
            .padding(horizontal = 12.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .background(avatarBg, RoundedCornerShape(12.dp))
                    .border(2.dp, DarkInk, RoundedCornerShape(12.dp))
                    .neoBrutalistShadow(1.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(avatarIcon, contentDescription = null, tint = DarkInk, modifier = Modifier.size(15.dp))
            }
            Column {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(name, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = DeepGreen, letterSpacing = 0.56.sp)
                    if (isVerified) {
                        Icon(Icons.Default.Verified, contentDescription = null, tint = EmeraldOnline, modifier = Modifier.size(13.dp))
                    }
                }
                Text(subtitle, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = MutedText, letterSpacing = 0.5.sp)
            }
        }
        Box(
            modifier = Modifier
                .size(32.dp)
                .background(bookmarkBg)
                .border(2.dp, DarkInk)
                .neoBrutalistShadow(2.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Default.Bookmark, contentDescription = "Saved", tint = DarkInk, modifier = Modifier.size(14.dp))
        }
    }
}

/** Image area placeholder with top badge and bottom-right label */
@Composable
private fun ImageArea(
    bgColor: Color,
    topBadge: String,
    topBadgeBg: Color,
    topBadgeTextColor: Color,
    bottomRightLabel: String,
    bottomRightBg: Color,
    bottomRightTextColor: Color,
    imageIcon: ImageVector = Icons.Default.Image
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(16f / 10f)
            .background(bgColor)
            .drawBehind {
                drawLine(DarkInk, Offset(0f, size.height), Offset(size.width, size.height), 3.dp.toPx())
            }
    ) {
        // Placeholder icon
        Icon(imageIcon, contentDescription = null, tint = MutedText.copy(alpha = 0.3f), modifier = Modifier.size(64.dp).align(Alignment.Center))

        // Top-left tilted badge
        Box(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 10.dp, top = 8.dp)
                .background(topBadgeBg)
                .border(2.dp, DarkInk)
                .neoBrutalistShadow(2.dp)
                .padding(horizontal = 10.dp, vertical = 4.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                Icon(Icons.Default.Star, contentDescription = null, tint = topBadgeTextColor, modifier = Modifier.size(11.dp))
                Text(topBadge, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = topBadgeTextColor, letterSpacing = 0.5.sp)
            }
        }

        // Bottom-right label
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 10.dp, bottom = 8.dp)
                .background(bottomRightBg)
                .border(if (bottomRightBg == DarkInk) 1.dp else 2.dp, DarkInk)
                .then(if (bottomRightBg != DarkInk) Modifier.neoBrutalistShadow(1.dp) else Modifier)
                .padding(horizontal = 10.dp, vertical = 4.dp)
        ) {
            Text(bottomRightLabel, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = bottomRightTextColor, letterSpacing = 0.5.sp)
        }
    }
}

/** Stat pill box */
@Composable
private fun StatPill(label: String, value: String, modifier: Modifier = Modifier, valueColor: Color = DeepGreen) {
    Column(
        modifier = modifier
            .background(LightGray)
            .border(2.dp, DarkInk)
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(label, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = MutedText, letterSpacing = 0.8.sp, textAlign = TextAlign.Center)
        Text(value, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = valueColor, letterSpacing = 0.72.sp, textAlign = TextAlign.Center)
    }
}

/** Action button */
@Composable
private fun ActionButton(
    text: String,
    icon: ImageVector,
    bgColor: Color,
    textColor: Color,
    modifier: Modifier = Modifier,
    borderColor: Color = DarkInk
) {
    Box(
        modifier = modifier
            .height(48.dp)
            .background(bgColor)
            .border(3.dp, borderColor)
            .neoBrutalistShadow(4.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            Icon(icon, contentDescription = null, tint = textColor, modifier = Modifier.size(15.dp))
            Text(text, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = textColor, letterSpacing = 0.6.sp)
        }
    }
}

// ─── Shadow Extension ──────────────────────────────────────────────
private fun Modifier.neoBrutalistShadow(offset: Dp): Modifier = this.drawBehind {
    val shadowPx = offset.toPx()
    drawRect(color = DarkInk, topLeft = Offset(shadowPx, shadowPx), size = size)
}
