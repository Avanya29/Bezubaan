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
import androidx.compose.material.icons.automirrored.filled.Chat
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
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage

@Composable
fun CommunityProfileScreen(
    onBack: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFCF9F8))
    ) {
        ProfileTopBar(onBack)

        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                ProfileCard()
            }
            item {
                SecondaryStatsRow()
            }
            item {
                ActionButtonsRow()
            }
            item {
                TabsRow()
            }
            item {
                FeedCardSheru()
            }
            item {
                FeedCardWaterBowl()
            }
            item {
                FeedCardKittens()
            }
            item {
                EndOfFeedIndicator()
            }
        }
    }
}

@Composable
private fun ProfileTopBar(onBack: () -> Unit) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFFCF9F8))
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onBack, modifier = Modifier.size(24.dp)) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.Black)
                }
                Spacer(modifier = Modifier.width(16.dp))
                Box(
                    modifier = Modifier
                        .background(Color(0xFFFFE24E))
                        .border(2.dp, Color.Black)
                        .padding(4.dp)
                ) {
                    Icon(Icons.Default.LocationOn, contentDescription = null, modifier = Modifier.size(16.dp))
                }
                Spacer(modifier = Modifier.width(12.dp))
                Text("COMMUNITY PROFILE", fontSize = 16.sp, fontWeight = FontWeight.Black, color = Color.Black)
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { /* TODO */ }, modifier = Modifier.size(24.dp)) {
                    Icon(Icons.Default.Search, contentDescription = "Search", tint = Color.Black)
                }
                Spacer(modifier = Modifier.width(12.dp))
                IconButton(onClick = { /* TODO */ }, modifier = Modifier.size(24.dp)) {
                    Icon(Icons.Default.Share, contentDescription = "Share", tint = Color.Black)
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
        HorizontalDivider(thickness = 2.dp, color = Color.Black)
    }
}

@Composable
private fun ProfileCard() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(4.dp, RoundedCornerShape(2.dp))
            .background(Color.White, RoundedCornerShape(2.dp))
            .border(3.dp, Color.Black, RoundedCornerShape(2.dp))
            .padding(16.dp)
    ) {
        // Top Badges
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier.background(Color(0xFFFFE24E)).border(2.dp, Color.Black).padding(horizontal = 8.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.Verified, contentDescription = null, modifier = Modifier.size(12.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("VERIFIED RESCUER", fontSize = 10.sp, fontWeight = FontWeight.Black)
            }
            Text("● SOUTH DELHI SQUAD #4", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color(0xFF414942))
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Avatar and Basic Info
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .border(3.dp, Color(0xFF0F3822), RoundedCornerShape(8.dp))
                    .clip(RoundedCornerShape(8.dp))
            ) {
                AsyncImage(
                    model = "https://images.unsplash.com/photo-1573496359142-b8d87734a5a2?auto=format&fit=crop&w=200&q=80",
                    contentDescription = "Priya Sharma",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .offset(y = 8.dp)
                        .background(Color(0xFF0F3822))
                        .border(1.dp, Color.White)
                        .padding(horizontal = 8.dp, vertical = 2.dp)
                ) {
                    Text("🟢 ACTIVE", fontSize = 8.sp, fontWeight = FontWeight.Black, color = Color.White)
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("PRIYA SHARMA", fontSize = 20.sp, fontWeight = FontWeight.Black, color = Color.Black)
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(Icons.Default.CheckCircle, contentDescription = "Verified", tint = Color(0xFF0F3822), modifier = Modifier.size(16.dp))
                }
                Text("JANPATH TRAUMA LIAISON • SINCE MAR 2022", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color(0xFF414942))
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.background(Color(0xFFC0EDCD)).border(1.dp, Color.Black).padding(horizontal = 8.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.LocationOn, contentDescription = null, tint = Color(0xFF0F3822), modifier = Modifier.size(10.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Saket / Malviya Nagar Sector", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color(0xFF0F3822))
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Bio Box
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .border(2.dp, Color.Black)
                .padding(12.dp)
        ) {
            Text(
                "Street dog advocate & foster mom. Co-founder of Saket Water Bowl Project. Dedicated to helping Delhi NCR's voiceless souls find emergency trauma care, dignity, and forever homes. 🐕🐾",
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black,
                lineHeight = 18.sp
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Main Stats Box Row
        Row(
            modifier = Modifier.fillMaxWidth().height(60.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            StatBox(
                modifier = Modifier.weight(1f),
                bgColor = Color(0xFF0F3822),
                number = "42",
                numberColor = Color(0xFFFFE24E),
                label = "RESCUES",
                labelColor = Color.White
            )
            StatBox(
                modifier = Modifier.weight(1f),
                bgColor = Color.White,
                number = "18",
                numberColor = Color.Black,
                label = "FOSTERS PLACED",
                labelColor = Color.Black
            )
            StatBox(
                modifier = Modifier.weight(1f),
                bgColor = Color(0xFFFFE24E),
                number = "8.4k",
                numberColor = Color.Black,
                label = "PAWS GIVEN",
                labelColor = Color.Black
            )
        }
    }
}

@Composable
private fun StatBox(
    modifier: Modifier,
    bgColor: Color,
    number: String,
    numberColor: Color,
    label: String,
    labelColor: Color
) {
    Column(
        modifier = modifier
            .fillMaxHeight()
            .background(bgColor)
            .border(3.dp, Color.Black)
            .padding(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(number, fontSize = 20.sp, fontWeight = FontWeight.Black, color = numberColor)
        Text(label, fontSize = 8.sp, fontWeight = FontWeight.Black, color = labelColor, textAlign = TextAlign.Center)
    }
}

@Composable
private fun SecondaryStatsRow() {
    Row(
        modifier = Modifier.fillMaxWidth().height(48.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        SecondaryStatBox(modifier = Modifier.weight(1f), number = "1,420", label = "FOLLOWERS", bgColor = Color.White)
        SecondaryStatBox(modifier = Modifier.weight(1f), number = "288", label = "FOLLOWING", bgColor = Color.White)
        SecondaryStatBox(modifier = Modifier.weight(1f), number = "38", label = "HEALED SOULS", bgColor = Color(0xFFC0EDCD))
    }
}

@Composable
private fun SecondaryStatBox(modifier: Modifier, number: String, label: String, bgColor: Color) {
    Column(
        modifier = modifier
            .fillMaxHeight()
            .background(bgColor)
            .border(2.dp, Color.Black)
            .padding(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(number, fontSize = 16.sp, fontWeight = FontWeight.Black, color = Color.Black)
        Text(label, fontSize = 8.sp, fontWeight = FontWeight.Black, color = Color.Black)
    }
}

@Composable
private fun ActionButtonsRow() {
    Row(
        modifier = Modifier.fillMaxWidth().height(48.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Button(
            onClick = { /* TODO */ },
            modifier = Modifier.weight(1f).fillMaxHeight().border(3.dp, Color.Black),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFE24E), contentColor = Color.Black),
            shape = RoundedCornerShape(0.dp)
        ) {
            Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text("FOLLOWING", fontSize = 14.sp, fontWeight = FontWeight.Black)
        }
        
        Box(
            modifier = Modifier.size(48.dp).background(Color.White).border(3.dp, Color.Black),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.AutoMirrored.Filled.Chat, contentDescription = "Message", tint = Color.Black)
        }
        
        Box(
            modifier = Modifier.size(48.dp).background(Color.White).border(3.dp, Color.Black),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Default.Share, contentDescription = "Share", tint = Color.Black)
        }
    }
}

@Composable
private fun TabsRow() {
    Row(
        modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(modifier = Modifier.background(Color.White).border(2.dp, Color.Black).padding(horizontal = 16.dp, vertical = 8.dp)) {
            Text("POSTS (26)", fontSize = 12.sp, fontWeight = FontWeight.Black, color = Color.Black)
        }
        Box(modifier = Modifier.background(Color(0xFF0F3822)).border(2.dp, Color.Black).padding(horizontal = 16.dp, vertical = 8.dp)) {
            Text("🌟 RESCUE STORIES (14)", fontSize = 12.sp, fontWeight = FontWeight.Black, color = Color(0xFFFFE24E))
        }
        Box(modifier = Modifier.background(Color.White).border(2.dp, Color.Black).padding(horizontal = 16.dp, vertical = 8.dp)) {
            Text("SQUADS", fontSize = 12.sp, fontWeight = FontWeight.Black, color = Color.Black)
        }
    }
}

@Composable
private fun FeedCardSheru() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(4.dp, RoundedCornerShape(2.dp))
            .background(Color.White, RoundedCornerShape(2.dp))
            .border(3.dp, Color.Black, RoundedCornerShape(2.dp))
    ) {
        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF0F3822))
                .padding(horizontal = 12.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Healing, contentDescription = null, tint = Color(0xFFFFE24E), modifier = Modifier.size(12.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("100% RECOVERED • CASE #BZ-804", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color.White)
            }
            Box(modifier = Modifier.background(Color(0xFFFFE24E)).padding(horizontal = 6.dp, vertical = 2.dp)) {
                Text("ADOPTED", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color(0xFF0F3822))
            }
        }
        
        // Image
        Box(modifier = Modifier.fillMaxWidth().height(200.dp).drawBehind {
            val strokeWidth = 2.dp.toPx()
            drawLine(
                color = Color.Black,
                start = Offset(0f, size.height - strokeWidth / 2),
                end = Offset(size.width, size.height - strokeWidth / 2),
                strokeWidth = strokeWidth
            )
        }) {
            AsyncImage(
                model = "https://images.unsplash.com/photo-1583511655857-d19b40a7a54e?auto=format&fit=crop&w=800&q=80",
                contentDescription = "Sheru",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .offset(x = 12.dp, y = (-12).dp)
                    .background(Color.White)
                    .border(2.dp, Color.Black)
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text("LODHI GARDENS • DAY 42", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color.Black)
            }
        }
        
        // Content
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                "SHERU'S 42-DAY MIRACLE: FROM MONSOON RAIN TO LODHI GARDENS",
                fontSize = 16.sp,
                fontWeight = FontWeight.Black,
                color = Color.Black,
                lineHeight = 20.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                "When our night squad found Sheru on Kalyan Marg with fractured limbs and severe hypothermia, survival seemed like a mountain. Thanks to Dr. Mehra's surgery and continuous community fosters, he took his first sprint today!",
                fontSize = 14.sp,
                color = Color.Black,
                lineHeight = 20.sp
            )
            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider(thickness = 2.dp, color = Color.Black)
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(
                        modifier = Modifier.background(Color(0xFFE8E9E7)).border(2.dp, Color.Black).padding(horizontal = 8.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.FavoriteBorder, contentDescription = null, modifier = Modifier.size(12.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("248 PAWS", fontSize = 10.sp, fontWeight = FontWeight.Black)
                    }
                    Row(
                        modifier = Modifier.background(Color.White).border(2.dp, Color.Black).padding(horizontal = 8.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.ChatBubbleOutline, contentDescription = null, modifier = Modifier.size(12.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("42 NOTES", fontSize = 10.sp, fontWeight = FontWeight.Black)
                    }
                }
                Icon(Icons.Default.BookmarkBorder, contentDescription = "Save", modifier = Modifier.size(24.dp))
            }
        }
    }
}

@Composable
private fun FeedCardWaterBowl() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(4.dp, RoundedCornerShape(2.dp))
            .background(Color.White, RoundedCornerShape(2.dp))
            .border(3.dp, Color.Black, RoundedCornerShape(2.dp))
    ) {
        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF6F3F2))
                .padding(horizontal = 12.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.WaterDrop, contentDescription = null, tint = Color(0xFF414942), modifier = Modifier.size(12.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("COMMUNITY DRIVE • SAKET BLOCK C", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color(0xFF414942))
            }
            Text("Yesterday • 18:00", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color(0xFF414942))
        }
        
        // Image
        Box(modifier = Modifier.fillMaxWidth().height(200.dp).drawBehind {
            val strokeWidth = 2.dp.toPx()
            drawLine(
                color = Color.Black,
                start = Offset(0f, size.height - strokeWidth / 2),
                end = Offset(size.width, size.height - strokeWidth / 2),
                strokeWidth = strokeWidth
            )
        }) {
            AsyncImage(
                model = "https://images.unsplash.com/photo-1544568100-847a948585b9?auto=format&fit=crop&w=800&q=80",
                contentDescription = "Water Bowl",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .offset(x = (-12).dp, y = 12.dp)
                    .background(Color(0xFFFFE24E))
                    .border(2.dp, Color.Black)
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text("MEDIATED 6 FEB", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color.Black)
            }
        }
        
        // Content
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                "SAKET BLOCK C TERRACOTTA WATER BOWL SETUP",
                fontSize = 16.sp,
                fontWeight = FontWeight.Black,
                color = Color.Black,
                lineHeight = 20.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                "Installed 15 clean terracotta water bowls along the community park boundary. We also tended to Bhura's paw abrasions on site. Please help keep these bowls filled with fresh water this heatwave!",
                fontSize = 14.sp,
                color = Color.Black,
                lineHeight = 20.sp
            )
            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider(thickness = 2.dp, color = Color.Black)
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(
                        modifier = Modifier.background(Color(0xFFE8E9E7)).border(2.dp, Color.Black).padding(horizontal = 8.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.FavoriteBorder, contentDescription = null, modifier = Modifier.size(12.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("48 PAWS", fontSize = 10.sp, fontWeight = FontWeight.Black)
                    }
                    Row(
                        modifier = Modifier.background(Color.White).border(2.dp, Color.Black).padding(horizontal = 8.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.ChatBubbleOutline, contentDescription = null, modifier = Modifier.size(12.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("14 NOTES", fontSize = 10.sp, fontWeight = FontWeight.Black)
                    }
                }
                Icon(Icons.Default.BookmarkBorder, contentDescription = "Save", modifier = Modifier.size(24.dp))
            }
        }
    }
}

@Composable
private fun FeedCardKittens() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(4.dp, RoundedCornerShape(2.dp))
            .background(Color.White, RoundedCornerShape(2.dp))
            .border(3.dp, Color.Black, RoundedCornerShape(2.dp))
    ) {
        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFC0EDCD))
                .padding(horizontal = 12.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.FavoriteBorder, contentDescription = null, tint = Color(0xFF0F3822), modifier = Modifier.size(12.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("NURSERY FOSTER MILESTONE", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color(0xFF0F3822))
            }
            Text("2 DAYS AGO", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color(0xFF0F3822))
        }
        
        // Image
        Box(modifier = Modifier.fillMaxWidth().height(250.dp).drawBehind {
            val strokeWidth = 2.dp.toPx()
            drawLine(
                color = Color.Black,
                start = Offset(0f, size.height - strokeWidth / 2),
                end = Offset(size.width, size.height - strokeWidth / 2),
                strokeWidth = strokeWidth
            )
        }) {
            AsyncImage(
                model = "https://images.unsplash.com/photo-1513360371669-4adf3dd7dff8?auto=format&fit=crop&w=800&q=80",
                contentDescription = "Kittens",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .offset(x = (-12).dp, y = (-12).dp)
                    .background(Color(0xFFFFE24E))
                    .border(2.dp, Color.Black)
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text("FOSTER WEEK #12", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color.Black)
            }
        }
        
        // Content
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                "CHHOTI & MITTU HIT THE 500G MILESTONE!",
                fontSize = 16.sp,
                fontWeight = FontWeight.Black,
                color = Color.Black,
                lineHeight = 20.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                "Bottle feeding every 3 hours has officially paid off. Both kittens are dewormed, hyperactive, and ready for pre-adoption applications starting this weekend!",
                fontSize = 14.sp,
                color = Color.Black,
                lineHeight = 20.sp
            )
            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider(thickness = 2.dp, color = Color.Black)
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(
                        modifier = Modifier.background(Color(0xFFE8E9E7)).border(2.dp, Color.Black).padding(horizontal = 8.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.FavoriteBorder, contentDescription = null, modifier = Modifier.size(12.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("142 PAWS", fontSize = 10.sp, fontWeight = FontWeight.Black)
                    }
                    Row(
                        modifier = Modifier.background(Color.White).border(2.dp, Color.Black).padding(horizontal = 8.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.ChatBubbleOutline, contentDescription = null, modifier = Modifier.size(12.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("22 NOTES", fontSize = 10.sp, fontWeight = FontWeight.Black)
                    }
                }
                Icon(Icons.Default.BookmarkBorder, contentDescription = "Save", modifier = Modifier.size(24.dp))
            }
        }
    }
}

@Composable
private fun EndOfFeedIndicator() {
    val stroke = androidx.compose.ui.graphics.drawscope.Stroke(width = 4f, pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f))
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
            .drawBehind {
                drawRoundRect(
                    color = Color(0xFF747775),
                    style = stroke,
                    cornerRadius = androidx.compose.ui.geometry.CornerRadius(8.dp.toPx())
                )
            }
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(Icons.Default.FavoriteBorder, contentDescription = null, tint = Color(0xFF0F3822), modifier = Modifier.size(24.dp))
            Spacer(modifier = Modifier.height(12.dp))
            Text("YOU'VE REACHED THE BEGINNING OF PRIYA'S JOURNEY", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color(0xFF0F3822), textAlign = TextAlign.Center)
            Spacer(modifier = Modifier.height(4.dp))
            Text("Supporting stray dogs & cats across Delhi since 2022", fontSize = 10.sp, fontWeight = FontWeight.Medium, color = Color(0xFF414942), textAlign = TextAlign.Center)
        }
    }
}
