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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage

@Composable
fun CreatePostScreen(
    onBack: () -> Unit = {},
    onPostCreated: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFCF9F8))
    ) {
        CreatePostHeader(onBack)

        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            item {
                UserProfileSection()
            }
            item {
                PostFormatSelection()
            }
            item {
                LinkedCaseFileSection()
            }
            item {
                VisualEvidenceSection()
            }
            item {
                PostContentSection()
            }
            item {
                PrivacyAudienceSection()
            }
            item {
                SafeguardWarning()
            }
            item {
                ActionButtons(onPostCreated)
            }
        }
    }
}

@Composable
private fun CreatePostHeader(onBack: () -> Unit) {
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
            }
            Text("CREATE POST", fontSize = 16.sp, fontWeight = FontWeight.Black, color = Color.Black)
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onBack, modifier = Modifier.size(24.dp)) {
                    Icon(Icons.Default.Close, contentDescription = "Close", tint = Color.Black)
                }
                Spacer(modifier = Modifier.width(16.dp))
                Box(
                    modifier = Modifier
                        .size(28.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF0F3822))
                        .border(2.dp, Color.Black, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.Person, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                }
            }
        }
        HorizontalDivider(thickness = 2.dp, color = Color.Black)
    }
}

@Composable
private fun UserProfileSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(4.dp, RoundedCornerShape(2.dp))
            .background(Color(0xFFFBF8F1), RoundedCornerShape(2.dp))
            .border(3.dp, Color.Black, RoundedCornerShape(2.dp))
            .padding(12.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(Color(0xFF0F3822), RoundedCornerShape(2.dp))
                    .border(2.dp, Color.Black, RoundedCornerShape(2.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text("AM", color = Color(0xFFC0EDCD), fontWeight = FontWeight.Black, fontSize = 14.sp)
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Aarav Malhotra", fontSize = 16.sp, fontWeight = FontWeight.Black, color = Color(0xFF1C1B1B))
                    Spacer(modifier = Modifier.width(6.dp))
                    Box(modifier = Modifier.background(Color(0xFF0F3822)).padding(horizontal = 4.dp, vertical = 2.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Verified, contentDescription = null, tint = Color.White, modifier = Modifier.size(10.dp))
                            Spacer(modifier = Modifier.width(2.dp))
                            Text("VERIFIED RESCUER", fontSize = 8.sp, fontWeight = FontWeight.Black, color = Color.White)
                        }
                    }
                }
                Text("Volunteer Squad #4 • North Zone", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF414942))
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .border(2.dp, Color.Black)
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Default.Campaign, contentDescription = null, modifier = Modifier.size(14.dp), tint = Color.Black)
            Spacer(modifier = Modifier.width(8.dp))
            Text("POSTING TO DELHI NCR COMMUNITY FEED", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color.Black)
        }
    }
}

@Composable
private fun PostFormatSelection() {
    Column {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("SELECT POST FORMAT", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color(0xFF1C1B1B))
            Text("REQUIRED", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color(0xFF414942))
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            FormatOption(icon = Icons.Default.CameraAlt, text = "PHOTO", isSelected = false, modifier = Modifier.weight(1f))
            FormatOption(icon = Icons.Default.Pets, text = "RESCUE STORY", isSelected = true, modifier = Modifier.weight(1f))
            FormatOption(icon = Icons.Default.Update, text = "UPDATE", isSelected = false, modifier = Modifier.weight(1f))
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.Link, contentDescription = null, modifier = Modifier.size(12.dp), tint = Color(0xFF414942))
            Spacer(modifier = Modifier.width(4.dp))
            Text("Rescue Stories automatically link milestones to patient\nmedical journals.", fontSize = 10.sp, fontWeight = FontWeight.Medium, color = Color(0xFF414942), lineHeight = 12.sp)
        }
    }
}

@Composable
private fun FormatOption(icon: ImageVector, text: String, isSelected: Boolean, modifier: Modifier = Modifier) {
    val bgColor = if (isSelected) Color(0xFFFFE24E) else Color.White
    Box(
        modifier = modifier
            .height(56.dp)
            .shadow(if (isSelected) 4.dp else 2.dp, RoundedCornerShape(2.dp))
            .background(bgColor, RoundedCornerShape(2.dp))
            .border(3.dp, Color.Black, RoundedCornerShape(2.dp)),
        contentAlignment = Alignment.Center
    ) {
        if (isSelected) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .background(Color.Black)
                    .padding(horizontal = 4.dp, vertical = 2.dp)
            ) {
                Text("ACTIVE", fontSize = 6.sp, fontWeight = FontWeight.Black, color = Color.White)
            }
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(icon, contentDescription = null, modifier = Modifier.size(20.dp), tint = Color.Black)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text, fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color.Black)
        }
    }
}

@Composable
private fun LinkedCaseFileSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(4.dp, RoundedCornerShape(2.dp))
            .background(Color(0xFFE2F3E8), RoundedCornerShape(2.dp))
            .border(3.dp, Color.Black, RoundedCornerShape(2.dp))
            .padding(12.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("LINKED CASE FILE (AUTO-SYNCED)", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color(0xFF0F3822))
            Text("CHANGE", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color(0xFF0F3822))
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFC0EDCD))
                .border(2.dp, Color.Black)
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .background(Color(0xFF0F3822), RoundedCornerShape(2.dp))
                    .border(2.dp, Color.Black, RoundedCornerShape(2.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.MedicalServices, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("#BZ-804 • SHERU", fontSize = 12.sp, fontWeight = FontWeight.Black, color = Color(0xFF002210))
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(Icons.Default.CheckCircle, contentDescription = null, tint = Color(0xFF10B981), modifier = Modifier.size(12.dp))
                }
                Text("Reconstructive Surgery • 100% Healed", fontSize = 10.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF0F3822))
            }
            Icon(Icons.Default.Close, contentDescription = "Remove", tint = Color.Black, modifier = Modifier.size(16.dp))
        }
    }
}

@Composable
private fun VisualEvidenceSection() {
    Column {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("VISUAL EVIDENCE & MEDIA *", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color(0xFF1C1B1B))
            Box(modifier = Modifier.background(Color(0xFFFFE24E)).border(1.dp, Color.Black).padding(horizontal = 4.dp, vertical = 2.dp)) {
                Text("1 / 4 Added", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color.Black)
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(4.dp, RoundedCornerShape(2.dp))
                .background(Color.White, RoundedCornerShape(2.dp))
                .border(3.dp, Color.Black, RoundedCornerShape(2.dp))
                .padding(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .border(2.dp, Color.Black)
            ) {
                AsyncImage(
                    model = "https://images.unsplash.com/photo-1583511655857-d19b40a7a54e?auto=format&fit=crop&w=800&q=80",
                    contentDescription = "Cover Photo",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                Box(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(8.dp)
                        .background(Color(0xFFFFE24E))
                        .border(2.dp, Color.Black)
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Star, contentDescription = null, modifier = Modifier.size(12.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("COVER PHOTO (PRIMARY)", fontSize = 10.sp, fontWeight = FontWeight.Black)
                    }
                }
                Row(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .background(Color.White)
                            .border(2.dp, Color.Black)
                            .padding(horizontal = 12.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.Sync, contentDescription = null, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("REPLACE", fontSize = 10.sp, fontWeight = FontWeight.Black)
                    }
                    Box(
                        modifier = Modifier
                            .background(Color(0xFFFFDAD6))
                            .border(2.dp, Color.Black)
                            .padding(8.dp)
                    ) {
                        Icon(Icons.Default.DeleteOutline, contentDescription = "Delete", tint = Color(0xFF93000A), modifier = Modifier.size(14.dp))
                    }
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    DashedPlaceholder("+ BEFORE", Icons.Default.CameraAlt)
                    DashedPlaceholder("+ CLINIC", Icons.Default.CameraAlt)
                    DashedPlaceholder("+ MORE", Icons.Default.Add)
                }
                Column(horizontalAlignment = Alignment.End) {
                    Text("PRO-TIP", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color(0xFF1C1B1B))
                    Text("Clear smiling photos\nboost adoptions by\n3.8x.", fontSize = 10.sp, fontWeight = FontWeight.Medium, color = Color(0xFF414942), textAlign = TextAlign.Right, lineHeight = 12.sp)
                }
            }
        }
    }
}

@Composable
private fun DashedPlaceholder(text: String, icon: ImageVector) {
    val stroke = androidx.compose.ui.graphics.drawscope.Stroke(width = 4f, pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f))
    Box(
        modifier = Modifier
            .size(56.dp)
            .background(Color.Transparent)
            .border(androidx.compose.foundation.BorderStroke(2.dp, Color.Black), shape = RoundedCornerShape(2.dp)), // Approximation for dashed border
        contentAlignment = Alignment.Center
    ) {
        // We use a regular solid border above as a fallback, but a custom drawn one is better. 
        // For simplicity in standard modifiers, standard border is used.
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(icon, contentDescription = null, modifier = Modifier.size(14.dp), tint = Color.Black)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text, fontSize = 8.sp, fontWeight = FontWeight.Black, color = Color.Black)
        }
    }
}

@Composable
private fun PostContentSection() {
    Column {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("POST TITLE *", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color(0xFF1C1B1B))
            Text("78 / 120", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color(0xFF414942))
        }
        Spacer(modifier = Modifier.height(8.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(2.dp, RoundedCornerShape(2.dp))
                .background(Color.White, RoundedCornerShape(2.dp))
                .border(3.dp, Color.Black, RoundedCornerShape(2.dp))
                .padding(12.dp)
        ) {
            Text(
                "From Abandoned in Monsoon Rain to Running in l",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )
        }
        
        Spacer(modifier = Modifier.height(20.dp))
        
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("FULL RESCUE NARRATIVE *", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color(0xFF1C1B1B))
            Text("MARKDOWN SUPPORTED", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color(0xFF414942))
        }
        Spacer(modifier = Modifier.height(8.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(4.dp, RoundedCornerShape(2.dp))
                .background(Color.White, RoundedCornerShape(2.dp))
                .border(3.dp, Color.Black, RoundedCornerShape(2.dp))
                .padding(12.dp)
        ) {
            Text(
                "When our night squad spotted Sheru on the concrete median near Kalyan Marg, his hind leg had compound fractures. Today after 6 weeks of dedicated clinical care and loving home foster, he has made a 100% recovery and is officially ready for his forever family! Huge",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF1C1B1B),
                lineHeight = 20.sp
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        // Tags
        Row(
            modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            TagPill("#RescueMilestone", Color(0xFFE2F3E8), Color(0xFF0F3822))
            TagPill("#AdoptionReady", Color(0xFFC0EDCD), Color(0xFF0F3822))
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            TagPill("#BeforeAndAfter", Color(0xFFF6F3F2), Color(0xFF1C1B1B))
            TagPill("#VolunteerSquad4", Color(0xFFF6F3F2), Color(0xFF1C1B1B))
        }
    }
}

@Composable
private fun TagPill(text: String, bgColor: Color, contentColor: Color) {
    Box(
        modifier = Modifier
            .background(bgColor)
            .border(2.dp, Color.Black)
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(text, fontSize = 10.sp, fontWeight = FontWeight.Black, color = contentColor)
    }
}

@Composable
private fun PrivacyAudienceSection() {
    Column {
        Text("POST PRIVACY & AUDIENCE *", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color(0xFF1C1B1B))
        Spacer(modifier = Modifier.height(8.dp))
        
        // Public Option
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(4.dp, RoundedCornerShape(2.dp))
                .background(Color(0xFFFFE24E), RoundedCornerShape(2.dp))
                .border(3.dp, Color.Black, RoundedCornerShape(2.dp))
                .padding(16.dp),
            verticalAlignment = Alignment.Top
        ) {
            RadioButton(
                selected = true,
                onClick = null,
                colors = RadioButtonDefaults.colors(selectedColor = Color.Black)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("PUBLIC (COMMUNITY FEED)", fontSize = 14.sp, fontWeight = FontWeight.Black, color = Color.Black)
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(Icons.Default.Public, contentDescription = null, modifier = Modifier.size(16.dp), tint = Color.Black)
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    "Broadcast to 2,400+ volunteers, vetted adopters, and animal lovers across Delhi NCR. Eligible for verified home amplification.",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF211B00),
                    lineHeight = 16.sp
                )
            }
        }
        
        Spacer(modifier = Modifier.height(12.dp))
        
        // Private Option
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(2.dp, RoundedCornerShape(2.dp))
                .background(Color.White, RoundedCornerShape(2.dp))
                .border(3.dp, Color.Black, RoundedCornerShape(2.dp))
                .padding(16.dp),
            verticalAlignment = Alignment.Top
        ) {
            RadioButton(
                selected = false,
                onClick = null,
                colors = RadioButtonDefaults.colors(unselectedColor = Color.Black)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Text("PRIVATE (TRIAGE & VOLUNTEERS ONLY)", fontSize = 14.sp, fontWeight = FontWeight.Black, color = Color(0xFF414942), modifier = Modifier.weight(1f))
                    Icon(Icons.Default.Lock, contentDescription = null, modifier = Modifier.size(16.dp), tint = Color(0xFF414942))
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    "Internal documentation visible only to registered Bezubaan rescuers, field drivers, and shelter medics.",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF414942),
                    lineHeight = 16.sp
                )
            }
        }
    }
}

@Composable
private fun SafeguardWarning() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFE8E9E7))
            .border(2.dp, Color.Black)
            .padding(12.dp),
        verticalAlignment = Alignment.Top
    ) {
        Icon(Icons.Default.Shield, contentDescription = null, tint = Color(0xFFBA1A1A), modifier = Modifier.size(16.dp))
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            "Community Safeguard: Please ensure graphic surgical wounds include content warnings. Bezubaan strictly vets all adoption applicants.",
            fontSize = 10.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF1C1B1B),
            lineHeight = 14.sp
        )
    }
}

@Composable
private fun ActionButtons(onPostCreated: () -> Unit) {
    Column {
        Button(
            onClick = onPostCreated,
            modifier = Modifier.fillMaxWidth().height(56.dp).border(3.dp, Color.Black, RoundedCornerShape(2.dp)).shadow(4.dp, RoundedCornerShape(2.dp)),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFE24E), contentColor = Color.Black),
            shape = RoundedCornerShape(2.dp)
        ) {
            Icon(Icons.Default.Pets, contentDescription = null, modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text("PUBLISH RESCUE STORY", fontSize = 14.sp, fontWeight = FontWeight.Black)
        }
        Spacer(modifier = Modifier.height(12.dp))
        Button(
            onClick = onPostCreated,
            modifier = Modifier.fillMaxWidth().height(56.dp).border(3.dp, Color.Black, RoundedCornerShape(2.dp)).shadow(2.dp, RoundedCornerShape(2.dp)),
            colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = Color.Black),
            shape = RoundedCornerShape(2.dp)
        ) {
            Icon(Icons.Default.Save, contentDescription = null, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text("SAVE AS DRAFT", fontSize = 14.sp, fontWeight = FontWeight.Black)
        }
    }
}
