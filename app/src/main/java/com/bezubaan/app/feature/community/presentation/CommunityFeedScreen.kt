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
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.automirrored.filled.*
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
fun CommunityFeedScreen(
    onNavigateToCreatePost: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFCF9F8))
    ) {
        CommunityTopHeader()

        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(bottom = 24.dp)
        ) {
            item {
                Column(modifier = Modifier.padding(16.dp)) {
                    ImpactTicker()
                    Spacer(modifier = Modifier.height(16.dp))
                    MobilizationHeroBar(onNavigateToCreatePost)
                    Spacer(modifier = Modifier.height(16.dp))
                    SegmentSelectionPills()
                }
            }
            
            item {
                FeedPostBeforeAfter()
            }
            
            item {
                FeedPostUrgent()
            }
            
            item {
                FeedPostFoster()
            }
            
            item {
                BottomCallToAction(onNavigateToCreatePost)
            }
        }
    }
}

@Composable
private fun CommunityTopHeader() {
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
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("BEZUBAAN", fontSize = 16.sp, fontWeight = FontWeight.Black)
                        Spacer(modifier = Modifier.width(6.dp))
                        Box(modifier = Modifier.background(Color(0xFFFFE24E)).border(1.dp, Color.Black).padding(horizontal = 4.dp, vertical = 2.dp)) {
                            Text("HELPING HANDS", fontSize = 8.sp, fontWeight = FontWeight.Black, color = Color(0xFF211B00))
                        }
                    }
                    Text("COMMUNITY", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color.DarkGray)
                }
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .background(Color(0xFFBA1A1A))
                        .border(2.dp, Color.Black)
                        .padding(horizontal = 8.dp, vertical = 6.dp)
                ) {
                    Text("SOS 935", fontSize = 12.sp, fontWeight = FontWeight.Black, color = Color.White)
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
private fun ImpactTicker() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(4.dp, RoundedCornerShape(2.dp))
            .background(Color(0xFFFFE24E), RoundedCornerShape(2.dp))
            .border(3.dp, Color.Black, RoundedCornerShape(2.dp))
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier.horizontalScroll(rememberScrollState()),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .background(Color.Black, RoundedCornerShape(4.dp))
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Bolt, contentDescription = null, tint = Color(0xFFFFE24E), modifier = Modifier.size(12.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("THIS WEEK", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color.White)
                }
            }
            Spacer(modifier = Modifier.width(12.dp))
            
            TickerItem(Color(0xFF002210), "18 RESCUES")
            TickerDot()
            TickerItem(Color(0xFFFF5733), "7 ADOPTIONS")
            TickerDot()
            TickerItem(Color(0xFF0F3822), "12 FOSTERS")
            TickerDot()
            TickerItem(Color(0xFF6D5E00), "₹48.5K RAISED")
        }
    }
}

@Composable
private fun TickerItem(dotColor: Color, text: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(modifier = Modifier.size(8.dp).background(dotColor, CircleShape))
        Spacer(modifier = Modifier.width(6.dp))
        Text(text, fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color(0xFF1C1B1B))
    }
}

@Composable
private fun TickerDot() {
    Text(" • ", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color(0xFF1C1B1B), modifier = Modifier.padding(horizontal = 8.dp))
}

@Composable
private fun MobilizationHeroBar(onPostStoryClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(3.dp, RoundedCornerShape(2.dp))
            .background(Color(0xFFF4EFE6), RoundedCornerShape(2.dp))
            .border(3.dp, Color.Black, RoundedCornerShape(2.dp))
            .padding(horizontal = 12.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.size(10.dp).background(Color(0xFF10B981), CircleShape))
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text("DELHI NCR SQUAD", fontSize = 12.sp, fontWeight = FontWeight.Black, color = Color(0xFF1C1B1B))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.People, contentDescription = null, modifier = Modifier.size(12.dp), tint = Color(0xFF414942))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("2,418 Active Rescuers", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF414942))
                }
            }
        }
        Button(
            onClick = onPostStoryClick,
            shape = RoundedCornerShape(2.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFE24E), contentColor = Color(0xFF121212)),
            modifier = Modifier.border(2.dp, Color.Black, RoundedCornerShape(2.dp)).height(36.dp),
            contentPadding = PaddingValues(horizontal = 12.dp)
        ) {
            Icon(Icons.Default.AddBox, contentDescription = null, modifier = Modifier.size(14.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text("POST STORY", fontSize = 10.sp, fontWeight = FontWeight.Black)
        }
    }
}

@Composable
private fun SegmentSelectionPills() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        PillButton("ALL STORIES (142)", Icons.Default.Public, true)
        PillButton("BEFORE & AFTER", Icons.Default.Compare, false)
        PillButton("RECOVERY DIARY", Icons.AutoMirrored.Filled.MenuBook, false)
        PillButton("URGENT S.O.S", Icons.Default.Warning, false, isRed = true)
    }
}

@Composable
private fun PillButton(text: String, icon: androidx.compose.ui.graphics.vector.ImageVector, isSelected: Boolean, isRed: Boolean = false) {
    val bgColor = if (isSelected) Color(0xFF0F3822) else if (isRed) Color(0xFFFFDAD6) else Color(0xFFFBF8F1)
    val contentColor = if (isSelected) Color.White else if (isRed) Color(0xFF93000A) else Color(0xFF1C1B1B)
    
    Row(
        modifier = Modifier
            .shadow(2.dp, RoundedCornerShape(2.dp))
            .background(bgColor, RoundedCornerShape(2.dp))
            .border(2.dp, Color.Black, RoundedCornerShape(2.dp))
            .padding(horizontal = 14.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null, tint = contentColor, modifier = Modifier.size(14.dp))
        Spacer(modifier = Modifier.width(6.dp))
        Text(text, fontSize = 10.sp, fontWeight = FontWeight.Black, color = contentColor)
    }
}

@Composable
private fun FeedPostBeforeAfter() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp)
            .shadow(4.dp, RoundedCornerShape(2.dp))
            .background(Color(0xFFFBF8F1), RoundedCornerShape(2.dp))
            .border(3.dp, Color.Black, RoundedCornerShape(2.dp))
    ) {
        // Post Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF4EFE6))
                .padding(14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Row {
                Box(modifier = Modifier.size(40.dp).border(2.dp, Color.Black, RoundedCornerShape(2.dp)).shadow(2.dp, RoundedCornerShape(2.dp))) {
                    AsyncImage(
                        model = "https://images.unsplash.com/photo-1573496359142-b8d87734a5a2?auto=format&fit=crop&w=100&q=80",
                        contentDescription = "Profile",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("Priya Sharma", fontSize = 18.sp, fontWeight = FontWeight.Black, color = Color(0xFF002210))
                        Spacer(modifier = Modifier.width(6.dp))
                        Icon(Icons.Default.Verified, contentDescription = "Verified", tint = Color(0xFF10B981), modifier = Modifier.size(14.dp))
                    }
                    Text("Squad #4 • 2h ago • Connaught Place", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF414942))
                }
            }
            Box(
                modifier = Modifier
                    .background(Color(0xFFC0EDCD))
                    .border(2.dp, Color.Black)
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text("#BZ-804 CELEBRATION", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color(0xFF002110))
            }
        }
        HorizontalDivider(thickness = 3.dp, color = Color.Black)
        
        // Content
        Column(modifier = Modifier.padding(14.dp)) {
            Text(
                "From Abandoned in Monsoon Rain to Running in Lodhi Gardens: Sheru's 42-Day Miracle!",
                fontSize = 22.sp,
                fontWeight = FontWeight.Black,
                color = Color.Black,
                lineHeight = 28.sp
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // Dual Media Container
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(224.dp)
                    .border(3.dp, Color.Black)
                    .background(Color.Black)
            ) {
                Row(modifier = Modifier.fillMaxSize()) {
                    // Before
                    Box(modifier = Modifier.weight(1f).fillMaxHeight()) {
                        AsyncImage(
                            model = "https://images.unsplash.com/photo-1544568100-847a948585b9?auto=format&fit=crop&w=400&q=80",
                            contentDescription = "Before",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop,
                            colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(Color(0x59FFFFFF), androidx.compose.ui.graphics.BlendMode.Saturation)
                        )
                        Box(
                            modifier = Modifier
                                .padding(8.dp)
                                .background(Color.Black)
                                .border(1.dp, Color(0xFFFFE24E))
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text("OCT 12: CRITICAL", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color.White)
                        }
                    }
                    
                    Box(modifier = Modifier.width(3.dp).fillMaxHeight().background(Color.Black))
                    
                    // After
                    Box(modifier = Modifier.weight(1f).fillMaxHeight()) {
                        AsyncImage(
                            model = "https://images.unsplash.com/photo-1583511655857-d19b40a7a54e?auto=format&fit=crop&w=400&q=80",
                            contentDescription = "After",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                        Box(
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(8.dp)
                                .background(Color(0xFFFFE24E))
                                .border(1.dp, Color.Black)
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text("TODAY: HEALED!", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color.Black)
                        }
                    }
                }
                
                // Floating stamp
                Row(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    Box(
                        modifier = Modifier
                            .background(Color(0xFF0F3822))
                            .border(2.dp, Color.Black)
                            .padding(horizontal = 10.dp, vertical = 6.dp)
                    ) {
                        Text("🐾 100% RECOVERED • VET CERTIFIED", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color(0xFFFFE24E))
                    }
                    Box(
                        modifier = Modifier
                            .background(Color.White)
                            .border(2.dp, Color.Black)
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Text("42 DAYS", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color.Black)
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Text(
                "When our night squad spotted Sheru on the median near Kalyan Marg, his hind leg had compound fractures and he was battling severe hypothermia. Thanks to Dr. Ananya's emergency reconstructive surgery and volunteer Aarav's dedicated foster care, Sheru is now sprinting happily.",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF1C1B1B),
                lineHeight = 20.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                "Sheru has completed all vaccinations and is officially ready for adoption into a loving home!",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF002210),
                lineHeight = 20.sp
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // Impact Metrics Box
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFF6F3F2))
                    .border(2.dp, Color.Black)
                    .padding(12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.VolunteerActivism, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text("COMMUNITY MICRO-FUND", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color(0xFF414942))
                        Text("₹4,200 / ₹4,200 (100%)", fontSize = 18.sp, fontWeight = FontWeight.Black, color = Color(0xFF002210))
                    }
                }
                Column(horizontalAlignment = Alignment.End) {
                    Text("CARE TEAM", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color(0xFF414942))
                    Text("3 RESCUERS +", fontSize = 12.sp, fontWeight = FontWeight.Black, color = Color(0xFF1C1B1B))
                    Text("JANPATH BAY", fontSize = 12.sp, fontWeight = FontWeight.Black, color = Color(0xFF1C1B1B))
                }
            }
        }
        
        HorizontalDivider(thickness = 3.dp, color = Color.Black)
        
        // Action Bar
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF4EFE6))
                .padding(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    ActionButton(Icons.Default.FavoriteBorder, "248\nPAWS")
                    ActionButton(Icons.Default.ChatBubbleOutline, "42\nNOTES")
                    ActionButton(Icons.Default.BookmarkBorder, "SAVE")
                }
                ActionButton(Icons.Default.Share, "")
            }
            Spacer(modifier = Modifier.height(12.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(
                    onClick = { /* TODO */ },
                    shape = RoundedCornerShape(2.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFE24E), contentColor = Color(0xFF121212)),
                    modifier = Modifier.weight(1f).border(2.dp, Color.Black, RoundedCornerShape(2.dp)).height(44.dp)
                ) {
                    Icon(Icons.Default.Pets, contentDescription = null, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("ADOPT SHERU", fontSize = 10.sp, fontWeight = FontWeight.Black)
                }
                Button(
                    onClick = { /* TODO */ },
                    shape = RoundedCornerShape(2.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC0EDCD), contentColor = Color(0xFF121212)),
                    modifier = Modifier.weight(1f).border(2.dp, Color.Black, RoundedCornerShape(2.dp)).height(44.dp)
                ) {
                    Icon(Icons.Default.VolunteerActivism, contentDescription = null, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("SPONSOR NEXT", fontSize = 10.sp, fontWeight = FontWeight.Black)
                }
            }
        }
    }
}

@Composable
private fun ActionButton(icon: androidx.compose.ui.graphics.vector.ImageVector, text: String) {
    Row(
        modifier = Modifier
            .background(Color.White)
            .border(2.dp, Color.Black)
            .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(icon, contentDescription = null, modifier = Modifier.size(16.dp))
        if (text.isNotEmpty()) {
            Spacer(modifier = Modifier.width(6.dp))
            Text(text, fontSize = 10.sp, fontWeight = FontWeight.Black, textAlign = TextAlign.Center, lineHeight = 12.sp)
        }
    }
}

@Composable
private fun FeedPostUrgent() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp)
            .shadow(4.dp, RoundedCornerShape(2.dp))
            .background(Color(0xFFFFDAD6), RoundedCornerShape(2.dp))
            .border(3.dp, Color.Black, RoundedCornerShape(2.dp))
    ) {
        // Red Top Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFBA1A1A))
                .padding(horizontal = 14.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.ErrorOutline, contentDescription = null, tint = Color.White, modifier = Modifier.size(14.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("CANINE BLOOD DONOR NEEDED\n(URGENT)", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color.White, lineHeight = 12.sp)
            }
            Box(
                modifier = Modifier
                    .background(Color(0xFF93000A))
                    .border(1.dp, Color.Black)
                    .padding(horizontal = 6.dp, vertical = 4.dp)
            ) {
                Text("ROHINI\nSECTOR 11", fontSize = 8.sp, fontWeight = FontWeight.Black, color = Color(0xFFFFB4AB), textAlign = TextAlign.Right, lineHeight = 10.sp)
            }
        }
        HorizontalDivider(thickness = 3.dp, color = Color.Black)
        
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Row {
                    Box(modifier = Modifier.size(40.dp).background(Color(0xFF1C1B1B)).border(2.dp, Color.Black), contentAlignment = Alignment.Center) {
                        Icon(Icons.Default.Emergency, contentDescription = null, tint = Color(0xFFFFE24E), modifier = Modifier.size(24.dp))
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text("Bezubaan Emergency\nDesk", fontSize = 16.sp, fontWeight = FontWeight.Black, color = Color(0xFF1C1B1B), lineHeight = 18.sp)
                        Text("35 mins ago • Priority Dispatch Alert", fontSize = 10.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF414942))
                    }
                }
                Box(modifier = Modifier.background(Color(0xFFFF5733)).border(2.dp, Color.Black).padding(horizontal = 8.dp, vertical = 4.dp)) {
                    Text("STATUS:\nOPEN", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color.White, textAlign = TextAlign.Center, lineHeight = 12.sp)
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                "DONOR DOG NEEDED FOR TOMMY (PARVO + SEVERE ANAEMIA)",
                fontSize = 20.sp,
                fontWeight = FontWeight.Black,
                color = Color(0xFF93000A),
                lineHeight = 24.sp
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                "Tommy's PCV levels have dropped dangerously below 12%. We immediately require a healthy donor dog to provide 200ml blood transfusion at Janpath Animal Trauma Centre.",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF410002),
                lineHeight = 20.sp
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // Eligibility Box
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .border(2.dp, Color.Black)
                    .padding(12.dp)
            ) {
                Text("ELIGIBILITY CRITERIA:", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color(0xFF1C1B1B))
                Spacer(modifier = Modifier.height(8.dp))
                Row(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.weight(1f)) {
                        CriteriaItem("WEIGHT: 20KG+")
                        Spacer(modifier = Modifier.height(6.dp))
                        CriteriaItem("FULLY VACCINATED")
                    }
                    Column(modifier = Modifier.weight(1f)) {
                        CriteriaItem("AGE: 1.5 TO 7 YEARS")
                        Spacer(modifier = Modifier.height(6.dp))
                        CriteriaItem("NO PAST TICK FEVER")
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
                HorizontalDivider(color = Color.LightGray)
                Spacer(modifier = Modifier.height(8.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Row(modifier = Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.LocalShipping, contentDescription = null, modifier = Modifier.size(12.dp), tint = Color(0xFF93000A))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Bezubaan ambulance can pick up\ndonor & guardian", fontSize = 10.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF1C1B1B))
                    }
                    Text("ETA: < 4\nHours", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color(0xFF1C1B1B), textAlign = TextAlign.Right)
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Button(
                onClick = { /* TODO */ },
                modifier = Modifier.fillMaxWidth().height(48.dp).border(2.dp, Color.Black),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFBA1A1A), contentColor = Color.White),
                shape = RoundedCornerShape(0.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                    Icon(Icons.Default.Phone, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("I HAVE A DONOR DOG (CALL HELPLINE)", fontSize = 14.sp, fontWeight = FontWeight.Black)
                }
            }
        }
        
        HorizontalDivider(thickness = 3.dp, color = Color.Black)
        
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFFFEBEB))
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = { /* TODO */ },
                modifier = Modifier.weight(1f).height(36.dp).border(2.dp, Color.Black),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = Color(0xFF1C1B1B)),
                shape = RoundedCornerShape(0.dp)
            ) {
                Icon(Icons.Default.Share, contentDescription = null, modifier = Modifier.size(14.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("BROADCAST TO WHATSAPP", fontSize = 10.sp, fontWeight = FontWeight.Black)
            }
            Spacer(modifier = Modifier.width(12.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Campaign, contentDescription = null, tint = Color(0xFFBA1A1A), modifier = Modifier.size(14.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("189 AMPLIFIED", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color(0xFFBA1A1A))
            }
        }
    }
}

@Composable
private fun CriteriaItem(text: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(Icons.Default.CheckBox, contentDescription = null, modifier = Modifier.size(12.dp), tint = Color(0xFF10B981))
        Spacer(modifier = Modifier.width(6.dp))
        Text(text, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1C1B1B))
    }
}

@Composable
private fun FeedPostFoster() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp)
            .shadow(4.dp, RoundedCornerShape(2.dp))
            .background(Color.White, RoundedCornerShape(2.dp))
            .border(3.dp, Color.Black, RoundedCornerShape(2.dp))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Row {
                Box(modifier = Modifier.size(40.dp).border(2.dp, Color.Black, RoundedCornerShape(2.dp))) {
                    AsyncImage(
                        model = "https://images.unsplash.com/photo-1559839734-2b71ea197ec2?auto=format&fit=crop&w=100&q=80",
                        contentDescription = "Doctor Profile",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("Dr. Ananya Sha", fontSize = 18.sp, fontWeight = FontWeight.Black, color = Color(0xFF002210))
                        Spacer(modifier = Modifier.width(6.dp))
                        Box(modifier = Modifier.background(Color.Black).padding(horizontal = 4.dp, vertical = 2.dp)) {
                            Text("VET", fontSize = 8.sp, fontWeight = FontWeight.Black, color = Color.White)
                        }
                    }
                    Text("Lead Surgeon • 5h ago • Janpath", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF414942))
                }
            }
            Box(
                modifier = Modifier
                    .background(Color(0xFFFFE24E))
                    .border(2.dp, Color.Black)
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text("FOSTER UPDATE", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color(0xFF211B00))
            }
        }
        HorizontalDivider(thickness = 3.dp, color = Color.Black)
        
        Column(modifier = Modifier.padding(14.dp)) {
            Text(
                "Chhoti & Mittu hit the 500g milestone! Eyes wide open & purring up a storm.",
                fontSize = 22.sp,
                fontWeight = FontWeight.Black,
                color = Color.Black,
                lineHeight = 28.sp
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .border(3.dp, Color.Black)
            ) {
                AsyncImage(
                    model = "https://images.unsplash.com/photo-1514888286974-6c03e2ca1dba?auto=format&fit=crop&w=800&q=80",
                    contentDescription = "Kittens",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(8.dp)
                        .background(Color.Black)
                        .border(1.dp, Color.White)
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text("WEIGHT: 510 GRAMS • BORN: NOV 6", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color.White)
                }
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Text(
                "Rescued during last week's torrential rain drain clog, these two tiny neonates required syringe feeds every 3 hours round the clock. Massive gratitude to volunteer foster parent Meera and our neonatal care squad. Today they graduated to warm starter mousse!",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF1C1B1B),
                lineHeight = 20.sp
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // Foster Badge
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFC0EDCD))
                    .border(2.dp, Color.Black)
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.VerifiedUser, contentDescription = null, modifier = Modifier.size(16.dp), tint = Color(0xFF002210))
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text("FOSTER PARENT OF THE WEEK", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color(0xFF414942))
                    Text("Meera Verma (Hauz Khas Foster Home #11)", fontSize = 12.sp, fontWeight = FontWeight.Black, color = Color(0xFF002210))
                }
            }
        }
        
        HorizontalDivider(thickness = 3.dp, color = Color.Black)
        
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF4EFE6))
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                ActionButton(Icons.Default.FavoriteBorder, "412 PAWS")
                ActionButton(Icons.Default.ChatBubbleOutline, "65 NOTES")
            }
            Button(
                onClick = { /* TODO */ },
                shape = RoundedCornerShape(2.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFE24E), contentColor = Color(0xFF121212)),
                modifier = Modifier.border(2.dp, Color.Black, RoundedCornerShape(2.dp)).height(44.dp)
            ) {
                Icon(Icons.Default.Celebration, contentDescription = null, modifier = Modifier.size(14.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("SEND WISHES", fontSize = 10.sp, fontWeight = FontWeight.Black)
            }
        }
    }
}

@Composable
private fun BottomCallToAction(onShareLogClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 24.dp)
            .background(Color(0xFF1C1B1B), RoundedCornerShape(2.dp))
            .border(3.dp, Color.Black, RoundedCornerShape(2.dp))
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(Icons.Default.Pets, contentDescription = null, tint = Color(0xFFFFE24E), modifier = Modifier.size(32.dp))
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            "THEY HAVE NO VOICE. YOU ARE THEIR ECHO.",
            fontSize = 18.sp,
            fontWeight = FontWeight.Black,
            color = Color.White,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            "Did you help, feed, or rescue a stray animal today? Post your field report to mobilize help and inspire thousands across Delhi NCR.",
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFFC1C8C0),
            textAlign = TextAlign.Center,
            lineHeight = 18.sp
        )
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = onShareLogClick,
            modifier = Modifier.fillMaxWidth().height(48.dp).border(2.dp, Color.Black, RoundedCornerShape(2.dp)),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFE24E), contentColor = Color(0xFF121212)),
            shape = RoundedCornerShape(2.dp)
        ) {
            Icon(Icons.Default.AddBox, contentDescription = null, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text("SHARE A RESCUE LOG", fontSize = 14.sp, fontWeight = FontWeight.Black)
        }
    }
}
