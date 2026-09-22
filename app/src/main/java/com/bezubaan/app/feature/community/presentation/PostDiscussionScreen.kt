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
import androidx.compose.material.icons.automirrored.filled.Send
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage

@Composable
fun PostDiscussionScreen(
    onBack: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFCF9F8))
    ) {
        DiscussionHeader(onBack)

        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            item {
                PostContextBanner()
            }
            item {
                CommunityNotesSection()
            }
        }

        BottomReplyBar()
    }
}

@Composable
private fun DiscussionHeader(onBack: () -> Unit) {
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
                Column {
                    Text("EMERGENCY TRIAGE", fontSize = 8.sp, fontWeight = FontWeight.Bold, color = Color(0xFF414942))
                    Text("POST DISCUSSION", fontSize = 14.sp, fontWeight = FontWeight.Black, color = Color.Black, lineHeight = 16.sp)
                    Text("CASE BZ 804", fontSize = 14.sp, fontWeight = FontWeight.Black, color = Color.Black, lineHeight = 16.sp)
                }
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
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
                Spacer(modifier = Modifier.width(16.dp))
                IconButton(onClick = onBack, modifier = Modifier.size(24.dp)) {
                    Icon(Icons.Default.Close, contentDescription = "Close", tint = Color.Black)
                }
            }
        }
        HorizontalDivider(thickness = 2.dp, color = Color.Black)
    }
}

@Composable
private fun PostContextBanner() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(4.dp, RoundedCornerShape(2.dp))
            .background(Color.White, RoundedCornerShape(2.dp))
            .border(3.dp, Color.Black, RoundedCornerShape(2.dp))
    ) {
        // Top Banner
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF0F3822))
                .padding(horizontal = 12.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Verified, contentDescription = null, tint = Color(0xFFFFE24E), modifier = Modifier.size(14.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("#BZ-804 CELEBRATION • SQUAD #4", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color(0xFFFFE24E))
            }
            Box(modifier = Modifier.background(Color(0xFFFFE24E)).padding(horizontal = 6.dp, vertical = 2.dp)) {
                Text("TRIAGED SUCCESS", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color(0xFF0F3822))
            }
        }
        
        // Content
        Row(modifier = Modifier.padding(12.dp)) {
            // Image
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .border(2.dp, Color.Black)
            ) {
                AsyncImage(
                    model = "https://images.unsplash.com/photo-1583511655857-d19b40a7a54e?auto=format&fit=crop&w=400&q=80",
                    contentDescription = "Dog",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .background(Color(0xFFFFE24E))
                        .border(1.dp, Color.Black)
                ) {
                    Text("100% HEALED", fontSize = 8.sp, fontWeight = FontWeight.Black, color = Color.Black, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth().padding(vertical = 2.dp))
                }
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    "FROM ABANDONED IN MONSOON RAIN TO RUNNING IN LODHI GARDENS:",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Black,
                    color = Color.Black,
                    lineHeight = 18.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text("Priya Sharma • 2h ago", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF414942))
                Spacer(modifier = Modifier.height(8.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(
                        modifier = Modifier.background(Color(0xFFF6F3F2)).border(2.dp, Color.Black).padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.Favorite, contentDescription = null, tint = Color(0xFFBA1A1A), modifier = Modifier.size(12.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("248 Paws", fontSize = 10.sp, fontWeight = FontWeight.Black)
                    }
                    Row(
                        modifier = Modifier.background(Color(0xFFFFE24E)).border(2.dp, Color.Black).padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.ChatBubble, contentDescription = null, tint = Color.Black, modifier = Modifier.size(12.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("42 Notes", fontSize = 10.sp, fontWeight = FontWeight.Black)
                    }
                }
            }
        }
    }
}

@Composable
private fun CommunityNotesSection() {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Pets, contentDescription = null, tint = Color(0xFF0F3822), modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("COMMUNITY NOTES (42)", fontSize = 16.sp, fontWeight = FontWeight.Black, color = Color(0xFF1C1B1B))
            }
            Box(modifier = Modifier.background(Color(0xFFE8E9E7)).border(2.dp, Color.Black).padding(horizontal = 8.dp, vertical = 4.dp)) {
                Text("LIVE FEED", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color(0xFF414942))
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            FilterTab("🔥 TOP NOTES", true)
            FilterTab("NEWEST", false)
            FilterTab("👥 FROM SQUAD", false)
        }
        Spacer(modifier = Modifier.height(20.dp))
        
        // Notes
        NoteItem(
            name = "Meera Verma",
            role = "CERTIFIED FOSTER",
            roleColor = Color(0xFF0F3822),
            avatarText = "MV",
            avatarBg = Color(0xFF0F3822),
            timeLoc = "1h ago • Hauz Khas",
            text = "Seeing Sheru sprint like that brings tears to my eyes! Remember when he could barely bear weight on his left hind leg during Week 2? Hats off to Dr. Ananya and the Janpath trauma team! 🙌🐶",
            paws = "18 Paws"
        )
        
        NoteItem(
            name = "Aarav Malhotra (You)",
            role = "YOU • RESCUER",
            roleColor = Color(0xFFE8B600), // Darker yellow for text pill background to maintain contrast
            roleTextColor = Color.Black,
            hasAvatarImage = true,
            timeLoc = "45m ago • Edited",
            text = "Special shoutout to volunteer Rohit for securing the perimeter and keeping him calm during the initial ambulance dispatch on Kalyan Marg. True team victory!",
            paws = "12 Paws",
            isOwnNote = true
        )
        
        // Nested Reply
        Row(modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Min)) {
            // Indentation line
            Box(
                modifier = Modifier
                    .width(32.dp)
                    .fillMaxHeight()
                    .drawBehind {
                        val strokeWidth = 2.dp.toPx()
                        // Vertical line
                        drawLine(
                            color = Color.Black,
                            start = Offset(16.dp.toPx(), 0f),
                            end = Offset(16.dp.toPx(), size.height - 24.dp.toPx()),
                            strokeWidth = strokeWidth
                        )
                        // Horizontal connector
                        drawLine(
                            color = Color.Black,
                            start = Offset(16.dp.toPx(), size.height - 24.dp.toPx()),
                            end = Offset(32.dp.toPx(), size.height - 24.dp.toPx()),
                            strokeWidth = strokeWidth
                        )
                    }
            )
            Box(modifier = Modifier.weight(1f).padding(bottom = 20.dp)) {
                NoteItem(
                    name = "Dr. Ananya Sharma",
                    role = "VET SURGEON",
                    roleColor = Color(0xFF0F3822),
                    hasAvatarImage = true,
                    isVet = true,
                    timeLoc = "22m ago",
                    text = "[@Aarav Malhotra] His tibial plate checkup next Tuesday is confirmed. Will do a final gait telemetry scan!",
                    paws = "8 Paws",
                    isReply = true
                )
            }
        }
        
        NoteItem(
            name = "Kabir Anand",
            role = "CITIZEN",
            roleColor = Color(0xFFE8E9E7),
            roleTextColor = Color.Black,
            avatarText = "KA",
            avatarBg = Color(0xFFE8E9E7),
            timeLoc = "10m ago • Saket",
            text = "Has the adoption application window closed for Sheru? My family has an independent house with a fenced garden in Saket.",
            paws = "4 Paws"
        )
    }
}

@Composable
private fun FilterTab(text: String, isActive: Boolean) {
    val bgColor = if (isActive) Color(0xFFFFE24E) else Color.White
    Box(
        modifier = Modifier
            .background(bgColor)
            .border(2.dp, Color.Black)
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Text(text, fontSize = 12.sp, fontWeight = FontWeight.Black, color = Color.Black)
    }
}

@Composable
private fun NoteItem(
    name: String,
    role: String,
    roleColor: Color,
    roleTextColor: Color = Color.White,
    avatarText: String? = null,
    avatarBg: Color = Color.White,
    hasAvatarImage: Boolean = false,
    isVet: Boolean = false,
    timeLoc: String,
    text: String,
    paws: String,
    isOwnNote: Boolean = false,
    isReply: Boolean = false
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = if (isReply) 0.dp else 20.dp)
            .shadow(4.dp, RoundedCornerShape(2.dp))
            .background(Color.White, RoundedCornerShape(2.dp))
            .border(3.dp, Color.Black, RoundedCornerShape(2.dp))
    ) {
        if (isOwnNote) {
            // Diagonal Your Note banner (simplified as a top-right badge for standard compose)
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .background(Color(0xFFFFE24E))
                    .border(2.dp, Color.Black)
                    .padding(horizontal = 24.dp, vertical = 4.dp)
            ) {
                Text("YOUR NOTE", fontSize = 8.sp, fontWeight = FontWeight.Black, color = Color.Black)
            }
        }
        
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.Top) {
                // Avatar
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(avatarBg)
                        .border(2.dp, Color.Black, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    if (hasAvatarImage) {
                        val url = if (isVet) "https://images.unsplash.com/photo-1559839734-2b71ea197ec2?auto=format&fit=crop&w=100&q=80" 
                                  else "https://images.unsplash.com/photo-1534528741775-53994a69daeb?auto=format&fit=crop&w=100&q=80"
                        AsyncImage(
                            model = url,
                            contentDescription = "Avatar",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    } else if (avatarText != null) {
                        Text(avatarText, color = if (avatarBg == Color(0xFF0F3822)) Color(0xFFFFE24E) else Color.Black, fontWeight = FontWeight.Black, fontSize = 12.sp)
                    }
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(name, fontSize = 14.sp, fontWeight = FontWeight.Black, color = Color.Black)
                        Spacer(modifier = Modifier.width(8.dp))
                        Box(modifier = Modifier.background(roleColor).padding(horizontal = 4.dp, vertical = 2.dp)) {
                            Text(role, fontSize = 8.sp, fontWeight = FontWeight.Black, color = roleTextColor)
                        }
                    }
                    Text(timeLoc, fontSize = 10.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF414942))
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            
            // Format text with mentions if needed
            if (text.contains("[@")) {
                val parts = text.split("]")
                if (parts.size > 1) {
                    val mention = parts[0].replace("[", "")
                    val rest = parts[1]
                    Text(
                        text = androidx.compose.ui.text.buildAnnotatedString {
                            withStyle(androidx.compose.ui.text.SpanStyle(color = Color(0xFF0F3822), background = Color(0xFFC0EDCD))) {
                                append(mention)
                            }
                            append(rest)
                        },
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF1C1B1B),
                        lineHeight = 20.sp
                    )
                } else {
                    Text(text, fontSize = 14.sp, fontWeight = FontWeight.Medium, color = Color(0xFF1C1B1B), lineHeight = 20.sp)
                }
            } else {
                Text(text, fontSize = 14.sp, fontWeight = FontWeight.Medium, color = Color(0xFF1C1B1B), lineHeight = 20.sp)
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
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
                        Icon(Icons.Default.Pets, contentDescription = null, modifier = Modifier.size(12.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(paws, fontSize = 10.sp, fontWeight = FontWeight.Black)
                    }
                    Row(
                        modifier = Modifier.background(Color.White).border(2.dp, Color.Black).padding(horizontal = 8.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null, modifier = Modifier.size(12.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("REPLY", fontSize = 10.sp, fontWeight = FontWeight.Black)
                    }
                    
                    if (isOwnNote) {
                        Row(
                            modifier = Modifier.background(Color.White).border(2.dp, Color.Black).padding(horizontal = 8.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Default.Edit, contentDescription = null, modifier = Modifier.size(12.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("EDIT", fontSize = 10.sp, fontWeight = FontWeight.Black)
                        }
                        Row(
                            modifier = Modifier.background(Color(0xFFFFEBEB)).border(2.dp, Color.Black).padding(horizontal = 8.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Default.DeleteOutline, contentDescription = null, tint = Color(0xFF93000A), modifier = Modifier.size(12.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("DELETE", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color(0xFF93000A))
                        }
                    }
                }
                
                if (!isOwnNote) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Flag, contentDescription = null, tint = Color(0xFF747775), modifier = Modifier.size(12.dp))
                        if (!isReply) {
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("REPORT", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color(0xFF747775))
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun BottomReplyBar() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .drawBehind {
                val strokeWidth = 3.dp.toPx()
                drawLine(
                    color = Color.Black,
                    start = Offset(0f, strokeWidth / 2),
                    end = Offset(size.width, strokeWidth / 2),
                    strokeWidth = strokeWidth
                )
            }
            .padding(12.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.horizontalScroll(rememberScrollState())) {
            Text("QUICK\nTAG:", fontSize = 8.sp, fontWeight = FontWeight.Black, color = Color(0xFF414942), lineHeight = 10.sp)
            Spacer(modifier = Modifier.width(8.dp))
            QuickTagPill(Icons.Default.FavoriteBorder, "HEALTH UPDATE")
            Spacer(modifier = Modifier.width(8.dp))
            QuickTagPill(Icons.Default.Home, "FOSTER OFFER")
            Spacer(modifier = Modifier.width(8.dp))
            QuickTagPill(Icons.Default.Favorite, "WORDS OF JOY", tint = Color(0xFFBA1A1A))
        }
        Spacer(modifier = Modifier.height(12.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(40.dp)
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
            Spacer(modifier = Modifier.width(8.dp))
            Row(
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp)
                    .background(Color.White)
                    .border(2.dp, Color.Black)
                    .padding(horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Write a supportive...", fontSize = 14.sp, color = Color(0xFF747775), modifier = Modifier.weight(1f))
                Icon(Icons.Default.CameraAlt, contentDescription = null, tint = Color.Black, modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(12.dp))
                Icon(Icons.Default.EmojiEmotions, contentDescription = null, tint = Color.Black, modifier = Modifier.size(20.dp))
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = { /* TODO */ },
                modifier = Modifier.height(48.dp).border(2.dp, Color.Black),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFE24E), contentColor = Color.Black),
                shape = RoundedCornerShape(0.dp),
                contentPadding = PaddingValues(horizontal = 12.dp)
            ) {
                Text("SEND", fontSize = 14.sp, fontWeight = FontWeight.Black)
                Spacer(modifier = Modifier.width(4.dp))
                Icon(Icons.AutoMirrored.Filled.Send, contentDescription = null, modifier = Modifier.size(16.dp))
            }
        }
    }
}

@Composable
private fun QuickTagPill(icon: androidx.compose.ui.graphics.vector.ImageVector, text: String, tint: Color = Color(0xFF414942)) {
    Row(
        modifier = Modifier
            .background(Color(0xFFE8E9E7))
            .border(1.dp, Color.Black)
            .padding(horizontal = 8.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null, tint = tint, modifier = Modifier.size(10.dp))
        Spacer(modifier = Modifier.width(4.dp))
        Text(text, fontSize = 8.sp, fontWeight = FontWeight.Black, color = Color(0xFF1C1B1B))
    }
}
