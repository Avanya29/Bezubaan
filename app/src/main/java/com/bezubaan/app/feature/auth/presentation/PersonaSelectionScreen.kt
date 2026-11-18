package com.bezubaan.app.feature.auth.presentation

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Emergency
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material.icons.filled.Radar
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bezubaan.app.core.data.AppPersona

@Composable
fun PersonaSelectionScreen(
    initialPersona: AppPersona = AppPersona.REPORTER,
    onContinue: (AppPersona) -> Unit,
    onLoginClick: () -> Unit = {}
) {
    var selectedPersona by remember { mutableStateOf(initialPersona) }

    val brutalYellow = Color(0xFFFFD54F)
    val brutalMint = Color(0xFFC4F7D4)
    val brutalRed = Color(0xFFFF5252)
    val brutalDarkGreen = Color(0xFF0A2B10)
    val brutalBg = Color(0xFFFCFCFC)

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
                    .rotate(-1.5f)
                    .background(Color.White)
                    .border(2.dp, Color.Black)
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    "SYS_BOOT // SELECT ROLE",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace
                )
            }

            Box(
                modifier = Modifier
                    .rotate(1.5f)
                    .background(brutalYellow)
                    .border(2.dp, Color.Black)
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    "DUAL PERSONA ACTIVE",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Black,
                    fontFamily = FontFamily.Monospace
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Title Section
        Text(
            "CHOOSE YOUR",
            fontSize = 32.sp,
            fontWeight = FontWeight.Black,
            letterSpacing = 1.sp
        )
        Box(
            modifier = Modifier
                .background(brutalYellow)
                .border(4.dp, Color.Black)
                .padding(horizontal = 8.dp, vertical = 2.dp)
        ) {
            Text(
                "PRIMARY ROLE.",
                fontSize = 32.sp,
                fontWeight = FontWeight.Black,
                letterSpacing = 1.sp
            )
        }

        Spacer(modifier = Modifier.height(12.dp))
        Text(
            "Select how you want to interact with Bezubaan. You can switch between roles anytime inside the app with zero disruption.",
            fontSize = 13.sp,
            lineHeight = 18.sp,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Persona Card 1: Animal Reporter / Citizen
        PersonaCard(
            title = "ANIMAL REPORTER",
            tag = "CITIZEN TRIAGE // 24/7 SOS",
            description = "For compassionate citizens and caretakers who want to report animals in distress and track rescue operations.",
            icon = Icons.Default.Pets,
            isSelected = selectedPersona == AppPersona.REPORTER,
            accentColor = brutalYellow,
            features = listOf(
                "Snap photos & report street injuries with 1-tap GPS",
                "Instant AI VetVision triage & emergency advice",
                "Live tracking of ambulance & assigned volunteers",
                "Community care squad, foster & pet adoption"
            ),
            onClick = { selectedPersona = AppPersona.REPORTER }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Persona Card 2: Volunteer / Field Responder
        PersonaCard(
            title = "FIELD VOLUNTEER",
            tag = "RESPONDER // RADAR DISPATCH",
            description = "For trained rescuers, paramedical volunteers, and foster parents ready to respond to live emergencies.",
            icon = Icons.Default.MedicalServices,
            isSelected = selectedPersona == AppPersona.VOLUNTEER,
            accentColor = brutalMint,
            features = listOf(
                "Real-time 3.5 km proximity radar & emergency pings",
                "Accept / decline on-duty street rescue dispatches",
                "Tactical first-aid protocol & live tracking beacon",
                "Direct emergency vet clinic & transit hotline"
            ),
            onClick = { selectedPersona = AppPersona.VOLUNTEER }
        )

        Spacer(modifier = Modifier.height(28.dp))

        // Continue Button with Brutalist Shadow
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onContinue(selectedPersona) }
        ) {
            Box(
                modifier = Modifier
                    .offset(x = 4.dp, y = 4.dp)
                    .fillMaxWidth()
                    .height(56.dp)
                    .background(Color.Black)
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .background(if (selectedPersona == AppPersona.VOLUNTEER) brutalMint else brutalYellow)
                    .border(3.dp, Color.Black),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "CONTINUE AS ${if (selectedPersona == AppPersona.VOLUNTEER) "VOLUNTEER" else "REPORTER"}",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Black,
                        fontFamily = FontFamily.Monospace
                    )
                    Text("➔", fontSize = 18.sp, fontWeight = FontWeight.Black)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Bottom notice
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF0F0EE))
                .border(2.dp, Color.Black)
                .padding(12.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .background(brutalDarkGreen, CircleShape)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    "Zero-Disruption Guarantee: One account holds both profiles. Toggle modes at any time without logging out.",
                    fontSize = 11.sp,
                    color = Color.Black,
                    lineHeight = 15.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
private fun PersonaCard(
    title: String,
    tag: String,
    description: String,
    icon: ImageVector,
    isSelected: Boolean,
    accentColor: Color,
    features: List<String>,
    onClick: () -> Unit
) {
    val cardBg by animateColorAsState(
        targetValue = if (isSelected) Color.White else Color(0xFFFAFAFA),
        label = "cardBg"
    )
    val borderColor = if (isSelected) Color.Black else Color.DarkGray
    val borderWidth = if (isSelected) 3.dp else 2.dp

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        if (isSelected) {
            Box(
                modifier = Modifier
                    .offset(x = 4.dp, y = 4.dp)
                    .matchParentSize()
                    .background(Color.Black)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(cardBg)
                .border(borderWidth, borderColor)
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .background(if (isSelected) accentColor else Color.LightGray)
                        .border(1.5.dp, Color.Black)
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                ) {
                    Text(
                        text = tag,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Black,
                        fontFamily = FontFamily.Monospace,
                        color = Color.Black
                    )
                }

                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .background(if (isSelected) Color.Black else Color.White)
                        .border(2.dp, Color.Black),
                    contentAlignment = Alignment.Center
                ) {
                    if (isSelected) {
                        Icon(
                            Icons.Default.Check,
                            contentDescription = "Selected",
                            tint = accentColor,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(if (isSelected) accentColor else Color(0xFFEEEEEE))
                        .border(2.dp, Color.Black),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        icon,
                        contentDescription = null,
                        modifier = Modifier.size(22.dp),
                        tint = Color.Black
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = title,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Black,
                        fontFamily = FontFamily.Monospace
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = description,
                fontSize = 12.sp,
                lineHeight = 16.sp,
                color = Color.DarkGray
            )

            Spacer(modifier = Modifier.height(12.dp))
            HorizontalDivider(thickness = 1.dp, color = Color.LightGray)
            Spacer(modifier = Modifier.height(10.dp))

            features.forEach { feature ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 3.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("✓", fontSize = 12.sp, fontWeight = FontWeight.Black, color = Color(0xFF0A2B10))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(feature, fontSize = 11.sp, color = Color.Black, lineHeight = 15.sp)
                }
            }
        }
    }
}

