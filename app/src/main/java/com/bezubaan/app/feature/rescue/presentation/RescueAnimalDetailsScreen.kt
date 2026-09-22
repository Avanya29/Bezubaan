package com.bezubaan.app.feature.rescue.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage

@Composable
fun RescueAnimalDetailsScreen(
    onBack: () -> Unit,
    onContinue: () -> Unit
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
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            item { StepTelemetry() }
            item { ScreenHeadline() }
            item { AiVisionSyncBanner() }
            item { AnimalTagField() }
            item { SpeciesClassification() }
            item { BreedOrType() }
            item { SexSelector() }
            item { LifeStageSelector() }
            item { SizeWeightBracket() }
            item { CoatColorPattern() }
            item { DistinctMarksField() }
            item { FieldNotesField() }
            item { DispatchReadySpecs() }
            
            item { Spacer(modifier = Modifier.height(24.dp)) }
        }
        
        BottomNavigationBar(onBack, onContinue)
    }
}

@Composable
private fun TopHeader() {
    Column(modifier = Modifier.fillMaxWidth().background(Color(0xFFFCF9F8))) {
        // App Bar
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
                        .background(Color(0xFFE2F163))
                        .border(2.dp, Color.Black)
                        .padding(4.dp)
                ) {
                    Icon(Icons.Default.LocationOn, contentDescription = null, modifier = Modifier.size(20.dp))
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text("BEZUBAAN", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color.DarkGray)
                    Text("VETVISION AI TRIAGE", fontSize = 16.sp, fontWeight = FontWeight.Black)
                }
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.NotificationsNone, contentDescription = "Alerts", modifier = Modifier.size(24.dp))
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
private fun StepTelemetry() {
    Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Row(
                modifier = Modifier
                    .shadow(2.dp, androidx.compose.foundation.shape.RoundedCornerShape(0.dp))
                    .background(Color(0xFFFFE24E))
                    .border(2.dp, Color.Black)
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.Pets, contentDescription = null, modifier = Modifier.size(12.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("STEP 2 OF 4", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color(0xFF211B00), letterSpacing = 0.5.sp)
            }
            
            Box(
                modifier = Modifier
                    .shadow(1.dp, androidx.compose.foundation.shape.RoundedCornerShape(0.dp))
                    .background(Color(0xFFEBE7E7))
                    .border(1.dp, Color.Black)
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text("CASE #BZ-804 • DRAFT", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color(0xFF414942), letterSpacing = 0.8.sp)
            }
        }
        
        // Progress bar
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            Box(modifier = Modifier.weight(1f).height(8.dp).shadow(1.dp, androidx.compose.foundation.shape.RoundedCornerShape(0.dp)).background(Color(0xFF002210)).border(1.dp, Color.Black))
            Box(modifier = Modifier.weight(1f).height(8.dp).shadow(1.dp, androidx.compose.foundation.shape.RoundedCornerShape(0.dp)).background(Color(0xFFFFE24E)).border(1.dp, Color.Black))
            Box(modifier = Modifier.weight(1f).height(8.dp).background(Color(0xFFEBE7E7)))
            Box(modifier = Modifier.weight(1f).height(8.dp).background(Color(0xFFEBE7E7)))
        }
        
        // Progress labels
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("1. PHOTO ✓", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color(0xFF002210), modifier = Modifier.weight(1f))
            Text("2. DETAILS", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color(0xFF1C1B1B), modifier = Modifier.weight(1f))
            Text("3. TRIAGE", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color(0xFF414942).copy(alpha = 0.5f), modifier = Modifier.weight(1f))
            Text("4. DISPATCH", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color(0xFF414942).copy(alpha = 0.5f), modifier = Modifier.weight(1f))
        }
    }
}

@Composable
private fun ScreenHeadline() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("ANIMAL", fontSize = 28.sp, fontWeight = FontWeight.Black, letterSpacing = (-0.7).sp)
            Spacer(modifier = Modifier.width(8.dp))
            Box(modifier = Modifier.rotate(-1f)) {
                Text(
                    "DETAILS.", 
                    fontSize = 28.sp, 
                    fontWeight = FontWeight.Black, 
                    letterSpacing = (-0.7).sp,
                    color = Color(0xFF211B00),
                    modifier = Modifier
                        .shadow(3.dp, androidx.compose.foundation.shape.RoundedCornerShape(0.dp))
                        .background(Color(0xFFFFE24E))
                        .border(2.dp, Color.Black)
                        .padding(horizontal = 8.dp, vertical = 2.dp)
                )
            }
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            "Accurate physical details help street responders recognize and approach the animal safely.",
            fontSize = 12.sp, 
            fontWeight = FontWeight.SemiBold, 
            color = Color(0xFF414942), 
            lineHeight = 16.sp
        )
    }
}

@Composable
private fun AiVisionSyncBanner() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(3.dp, androidx.compose.foundation.shape.RoundedCornerShape(0.dp))
            .background(Color(0xFFC0EDCD))
            .border(2.dp, Color.Black)
            .padding(12.dp),
        verticalAlignment = Alignment.Top
    ) {
        Icon(Icons.Default.AutoAwesome, contentDescription = null, modifier = Modifier.size(18.dp), tint = Color(0xFF002210))
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text("VETVISION VISION SYNC", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color(0xFF002210), letterSpacing = 0.5.sp)
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                "Auto-detected: Medium Adult Female Indie from Step 1 photos. Tap any chip to adjust.",
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF274F37),
                lineHeight = 16.sp
            )
        }
    }
}

@Composable
private fun FormLabel(number: String, title: String, trailingText: String? = null, trailingColor: Color = Color(0xFF414942)) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.Bottom) {
        Text("$number. $title", fontSize = 12.sp, fontWeight = FontWeight.Black, letterSpacing = 0.6.sp, color = Color(0xFF1C1B1B))
        if (trailingText != null) {
            Text(trailingText, fontSize = 10.sp, fontWeight = FontWeight.Black, letterSpacing = 0.8.sp, color = trailingColor)
        }
    }
    Spacer(modifier = Modifier.height(8.dp))
}

@Composable
private fun AnimalTagField() {
    Column(modifier = Modifier.fillMaxWidth()) {
        FormLabel("1", "ANIMAL TAG / NAME", "OPTIONAL")
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(3.dp, androidx.compose.foundation.shape.RoundedCornerShape(0.dp))
                .background(Color.White)
                .border(2.dp, Color.Black)
                .padding(horizontal = 14.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Rusty (Street Tag)", fontSize = 16.sp, fontWeight = FontWeight.Medium, color = Color(0xFF1C1B1B))
            Icon(Icons.Default.Badge, contentDescription = null, modifier = Modifier.size(20.dp), tint = Color.DarkGray)
        }
    }
}

@Composable
private fun SpeciesClassification() {
    Column(modifier = Modifier.fillMaxWidth()) {
        FormLabel("2", "SPECIES CLASSIFICATION *")
        
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            SelectionBox(modifier = Modifier.weight(1f), icon = "🐕", label = "DOG", isSelected = true)
            SelectionBox(modifier = Modifier.weight(1f), icon = "🐈", label = "CAT", isSelected = false)
            SelectionBox(modifier = Modifier.weight(1f), icon = "🐄", label = "CATTLE", isSelected = false)
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            SelectionBox(modifier = Modifier.weight(1f), icon = "🐦", label = "BIRD", isSelected = false)
            SelectionBox(modifier = Modifier.weight(1f), icon = "🐒", label = "MONKEY", isSelected = false)
            SelectionBox(modifier = Modifier.weight(1f), icon = "⋯", label = "OTHER", isSelected = false)
        }
    }
}

@Composable
private fun BreedOrType() {
    Column(modifier = Modifier.fillMaxWidth()) {
        FormLabel("3", "BREED OR TYPE")
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(3.dp, androidx.compose.foundation.shape.RoundedCornerShape(0.dp))
                .background(Color.White)
                .border(2.dp, Color.Black)
                .padding(horizontal = 14.dp, vertical = 12.dp)
        ) {
            Text("Desi / Indian Pariah Dog (Indie)", fontSize = 16.sp, fontWeight = FontWeight.Medium, color = Color(0xFF1C1B1B))
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Chip("⚡ INDIE / PARIAH")
            Chip("⚡ LABRADOR MIX")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row {
            Chip("⚡ GERMAN SHEP CROSS")
        }
    }
}

@Composable
private fun Chip(text: String) {
    Box(
        modifier = Modifier
            .shadow(1.dp, androidx.compose.foundation.shape.RoundedCornerShape(0.dp))
            .background(Color(0xFFEBE7E7))
            .border(1.dp, Color.Black)
            .padding(horizontal = 8.dp, vertical = 6.dp)
    ) {
        Text(text, fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color(0xFF1C1B1B), letterSpacing = 0.8.sp)
    }
}

@Composable
private fun SexSelector() {
    Column(modifier = Modifier.fillMaxWidth()) {
        FormLabel("4", "SEX *")
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            SelectionBox(modifier = Modifier.weight(1f), icon = "♂", label = "MALE", isSelected = false)
            SelectionBox(modifier = Modifier.weight(1f), icon = "♀", label = "FEMALE", isSelected = true, selectedColor = Color(0xFFFFE24E), selectedTextColor = Color(0xFF211B00))
            SelectionBox(modifier = Modifier.weight(1f), icon = "?", label = "UNKNOWN", isSelected = false)
        }
    }
}

@Composable
private fun LifeStageSelector() {
    Column(modifier = Modifier.fillMaxWidth()) {
        FormLabel("5", "APPROXIMATE LIFE STAGE")
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            LifeStageBox(modifier = Modifier.weight(1f), title = "PUPPY / KITTEN", subtitle = "< 6 months", isSelected = false)
            LifeStageBox(modifier = Modifier.weight(1f), title = "YOUNG ANIMAL", subtitle = "6m - 2 years", isSelected = false)
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            LifeStageBox(modifier = Modifier.weight(1f), title = "ADULT DOG", subtitle = "2 - 7 years", isSelected = true)
            LifeStageBox(modifier = Modifier.weight(1f), title = "SENIOR STRAY", subtitle = "7+ years", isSelected = false)
        }
    }
}

@Composable
private fun SizeWeightBracket() {
    Column(modifier = Modifier.fillMaxWidth()) {
        FormLabel("6", "SIZE & WEIGHT BRACKET *", "NEEDED FOR CRATE", Color(0xFFC5AB00))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            SizeBox(modifier = Modifier.weight(1f), icon = Icons.Default.Pets, title = "SMALL", subtitle = "< 10 kg", isSelected = false)
            SizeBox(modifier = Modifier.weight(1f), icon = Icons.Default.LocalShipping, title = "MEDIUM", subtitle = "10 - 25 kg", isSelected = true)
            SizeBox(modifier = Modifier.weight(1f), icon = Icons.Default.Inventory, title = "LARGE", subtitle = "25+ kg", isSelected = false)
        }
    }
}

@Composable
private fun CoatColorPattern() {
    Column(modifier = Modifier.fillMaxWidth()) {
        FormLabel("7", "COAT COLOR & PATTERN")
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            ColorBox(modifier = Modifier.weight(1f), color = Color(0xFF8B4513), label = "BROWN / TAN", isSelected = true)
            ColorBox(modifier = Modifier.weight(1f), color = Color.White, label = "PURE WHITE", isSelected = false)
            ColorBox(modifier = Modifier.weight(1f), color = Color.Black, label = "BLACK", isSelected = false)
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            ColorBox(modifier = Modifier.weight(1f), color = Color(0xFFD2B48C), label = "FAWN / GOLD", isSelected = false)
            ColorBox(modifier = Modifier.weight(1.5f), color = Color.DarkGray, label = "BRINDLE / MULTI-PATCH", isSelected = false, showPattern = true)
        }
    }
}

@Composable
private fun DistinctMarksField() {
    Column(modifier = Modifier.fillMaxWidth()) {
        FormLabel("8", "DISTINCT MARKS / NOTCHES", "CRITICAL", Color(0xFFB01212))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(2.dp, androidx.compose.foundation.shape.RoundedCornerShape(0.dp))
                .background(Color.White)
                .border(2.dp, Color.Black)
                .padding(horizontal = 14.dp, vertical = 12.dp)
        ) {
            Text("Right ear V-notch (sterilized), white patch ac", fontSize = 16.sp, fontWeight = FontWeight.Medium, color = Color(0xFF1C1B1B))
        }
    }
}

@Composable
private fun FieldNotesField() {
    Column(modifier = Modifier.fillMaxWidth()) {
        FormLabel("9", "FIELD NOTES & DEMEANOR")
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(2.dp, androidx.compose.foundation.shape.RoundedCornerShape(0.dp))
                .background(Color.White)
                .border(2.dp, Color.Black)
                .padding(horizontal = 14.dp, vertical = 12.dp)
        ) {
            Text(
                "Frightened and shivering, huddled behind the Sharma Tea Stall. Not aggressive, accepts biscuit crumbs cautiously.", 
                fontSize = 16.sp, 
                fontWeight = FontWeight.Medium, 
                color = Color(0xFF1C1B1B),
                lineHeight = 22.sp
            )
        }
    }
}

@Composable
private fun DispatchReadySpecs() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(2.dp, androidx.compose.foundation.shape.RoundedCornerShape(0.dp))
            .background(Color(0xFFF0EDEC))
            .border(2.dp, Color.Black)
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .background(Color(0xFFC0EDCD))
                    .border(2.dp, Color.Black)
                    .padding(4.dp)
            ) {
                Icon(Icons.Default.Verified, contentDescription = null, modifier = Modifier.size(16.dp))
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text("DISPATCH READY SPECS", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color.Black)
                Text("Net & Medium crate allocated", fontSize = 12.sp, fontWeight = FontWeight.Medium, color = Color(0xFF414942))
            }
        }
        
        Box(
            modifier = Modifier
                .background(Color(0xFFFFE24E))
                .border(1.dp, Color.Black)
                .padding(horizontal = 6.dp, vertical = 4.dp)
        ) {
            Text("VALIDATED", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color.Black)
        }
    }
}

@Composable
private fun BottomNavigationBar(onBack: () -> Unit, onContinue: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .border(2.dp, Color.Black)
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Button(
            onClick = onBack,
            modifier = Modifier
                .weight(0.3f)
                .height(56.dp)
                .shadow(4.dp, androidx.compose.foundation.shape.RoundedCornerShape(0.dp))
                .border(3.dp, Color.Black),
            colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = Color.Black),
            shape = androidx.compose.foundation.shape.RoundedCornerShape(0.dp)
        ) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null, modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.width(4.dp))
            Text("BACK", fontSize = 14.sp, fontWeight = FontWeight.Black)
        }
        
        Button(
            onClick = onContinue,
            modifier = Modifier
                .weight(0.7f)
                .height(56.dp)
                .shadow(4.dp, androidx.compose.foundation.shape.RoundedCornerShape(0.dp))
                .border(3.dp, Color.Black),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFE24E), contentColor = Color(0xFF211B00)),
            shape = androidx.compose.foundation.shape.RoundedCornerShape(0.dp)
        ) {
            Text("CONTINUE TO LOCATION", fontSize = 14.sp, fontWeight = FontWeight.Black)
            Spacer(modifier = Modifier.width(8.dp))
            Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, modifier = Modifier.size(20.dp))
        }
    }
}


@Composable
private fun SelectionBox(modifier: Modifier = Modifier, icon: String, label: String, isSelected: Boolean, selectedColor: Color = Color(0xFF002210), selectedTextColor: Color = Color.White) {
    Column(
        modifier = modifier
            .shadow(if (isSelected) 3.dp else 2.dp, androidx.compose.foundation.shape.RoundedCornerShape(0.dp))
            .background(if (isSelected) selectedColor else Color.White)
            .border(2.dp, Color.Black)
            .height(48.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(icon, fontSize = 16.sp, color = if (isSelected) selectedTextColor else Color.Black)
            Spacer(modifier = Modifier.width(6.dp))
            Text(label, fontSize = 10.sp, fontWeight = FontWeight.Black, color = if (isSelected) selectedTextColor else Color.Black, letterSpacing = 0.8.sp)
        }
    }
}

@Composable
private fun LifeStageBox(modifier: Modifier = Modifier, title: String, subtitle: String, isSelected: Boolean) {
    Column(
        modifier = modifier
            .shadow(if (isSelected) 3.dp else 2.dp, androidx.compose.foundation.shape.RoundedCornerShape(0.dp))
            .background(if (isSelected) Color(0xFF002210) else Color.White)
            .border(2.dp, Color.Black)
            .height(48.dp)
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(title, fontSize = 10.sp, fontWeight = FontWeight.Black, color = if (isSelected) Color.White else Color.Black, letterSpacing = 0.8.sp)
        Text(subtitle, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = if (isSelected) Color(0xFF78A285) else Color(0xFF414942))
    }
}

@Composable
private fun SizeBox(modifier: Modifier = Modifier, icon: androidx.compose.ui.graphics.vector.ImageVector, title: String, subtitle: String, isSelected: Boolean) {
    Column(
        modifier = modifier
            .shadow(if (isSelected) 3.dp else 2.dp, androidx.compose.foundation.shape.RoundedCornerShape(0.dp))
            .background(if (isSelected) Color(0xFFFFE24E) else Color.White)
            .border(2.dp, Color.Black)
            .height(64.dp)
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(icon, contentDescription = null, modifier = Modifier.size(18.dp), tint = Color.Black)
        Spacer(modifier = Modifier.height(2.dp))
        Text(title, fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color.Black, letterSpacing = 0.8.sp)
        Text(subtitle, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color.DarkGray)
    }
}

@Composable
private fun ColorBox(modifier: Modifier = Modifier, color: Color, label: String, isSelected: Boolean, showPattern: Boolean = false) {
    Row(
        modifier = modifier
            .shadow(if (isSelected) 3.dp else 2.dp, androidx.compose.foundation.shape.RoundedCornerShape(0.dp))
            .background(if (isSelected) Color(0xFF002210) else Color.White)
            .border(2.dp, Color.Black)
            .height(40.dp)
            .padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        if (showPattern) {
            Icon(Icons.Default.Gradient, contentDescription = null, modifier = Modifier.size(12.dp), tint = if (isSelected) Color.White else Color.Black)
        } else {
            Box(modifier = Modifier.size(12.dp).background(color, CircleShape).border(1.dp, if (color == Color.Black) Color.Gray else Color.Black, CircleShape))
        }
        Spacer(modifier = Modifier.width(6.dp))
        Text(label, fontSize = 9.sp, fontWeight = FontWeight.Black, color = if (isSelected) Color.White else Color.Black, letterSpacing = 0.5.sp)
    }
}
