package com.bezubaan.app.feature.auth.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bezubaan.app.ui.components.NeoInput

@Composable
fun RegisterScreen(
    onNavigateToLogin: () -> Unit,
    onRegisterSuccess: () -> Unit,
    viewModel: AuthViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var role by remember { mutableStateOf("CITIZEN") }
    var ethicsAccepted by remember { mutableStateOf(false) }

    LaunchedEffect(uiState.isRegistered) {
        if (uiState.isRegistered) {
            onRegisterSuccess() // Navigates back to login
        }
    }

    val yellow = Color(0xFFF5CE42)
    val darkGreen = Color(0xFF0D3311)
    val lightGreen = Color(0xFFC8E6C9)
    val greenText = Color(0xFF2E7D32)
    val redAlert = Color(0xFFC5221F)
    val bgGray = Color(0xFFF4F4F0)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(bgGray)
            .verticalScroll(rememberScrollState())
            .padding(24.dp)
    ) {
        // Header Bar
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier
                    .background(darkGreen)
                    .border(2.dp, Color.Black)
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(Color.White)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    "AUTH // RESCUER ONBOARDING", 
                    color = Color.White, 
                    fontSize = 10.sp, 
                    fontWeight = FontWeight.Bold,
                    fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace
                )
            }
            Row(
                modifier = Modifier
                    .background(lightGreen)
                    .border(2.dp, Color.Black)
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.Security, contentDescription = null, modifier = Modifier.size(12.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    "VERIFIED REGISTRY", 
                    color = Color.Black, 
                    fontSize = 10.sp, 
                    fontWeight = FontWeight.Bold,
                    fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace
                )
            }
        }
        
        Spacer(modifier = Modifier.height(24.dp))

        // Logo Header
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(yellow)
                    .border(3.dp, Color.Black),
                contentAlignment = Alignment.Center
            ) {
                Text("🐾", fontSize = 24.sp)
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    "BEZUBAAN OPS V2.4 // RECRUIT", 
                    color = Color.Gray, 
                    fontSize = 12.sp, 
                    fontWeight = FontWeight.Bold,
                    fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "JOIN THE ",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Black,
                        color = Color.Black
                    )
                    Box(
                        modifier = Modifier
                            .background(yellow)
                            .border(3.dp, Color.Black)
                            .padding(horizontal = 8.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = "RESCUE FORCE.",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Black,
                            color = Color.Black
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Create your citizen or volunteer profile to report injured animals, join emergency field dispatches & foster lives.",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Black,
            fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace
        )

        if (uiState.error != null) {
            Spacer(modifier = Modifier.height(16.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .border(2.dp, redAlert)
                    .padding(12.dp)
            ) {
                Text(
                    text = "ERROR: ${uiState.error}",
                    color = redAlert,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Full Name Field
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Text("■ FULL LEGAL NAME // CALLSIGN", fontSize = 12.sp, fontWeight = FontWeight.Bold, fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace)
            Box(modifier = Modifier.background(redAlert).padding(horizontal = 4.dp, vertical = 2.dp)) {
                Text("REQUIRED", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold, fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace)
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        NeoInput(
            value = name, 
            onValueChange = { name = it }, 
            label = "Aditi Sharma",
            leadingIcon = { Icon(Icons.Default.Badge, contentDescription = null, tint = Color.Gray) }
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Email Field
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Text("■ RESCUER EMAIL ADDRESS", fontSize = 12.sp, fontWeight = FontWeight.Bold, fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace)
            Box(modifier = Modifier.background(lightGreen).padding(horizontal = 4.dp, vertical = 2.dp).border(1.dp, Color.Black)) {
                Text("VERIFIED DOMAIN", color = greenText, fontSize = 10.sp, fontWeight = FontWeight.Bold, fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace)
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        NeoInput(
            value = email, 
            onValueChange = { email = it }, 
            label = "aditi.rescue@gmail.com",
            leadingIcon = { Icon(Icons.Default.Email, contentDescription = null, tint = Color.Gray) },
            trailingIcon = { 
                if(email.isNotEmpty()) {
                    Box(modifier = Modifier.background(lightGreen).border(1.dp, Color.Black).padding(horizontal = 6.dp, vertical = 2.dp)) {
                        Text("VALID ✓", color = greenText, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Password Field
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Text("■ SECURITY CODE // PASSWORD", fontSize = 12.sp, fontWeight = FontWeight.Bold, fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace)
            Box(modifier = Modifier.background(Color.Black).padding(horizontal = 4.dp, vertical = 2.dp)) {
                Text("MIN 8 CHARS", color = yellow, fontSize = 10.sp, fontWeight = FontWeight.Bold, fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace)
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        NeoInput(
            value = password, 
            onValueChange = { password = it }, 
            label = "••••••••••••",
            leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null, tint = Color.Gray) }
        )

        // Strength Meter
        Spacer(modifier = Modifier.height(8.dp))
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Text("CRYPT-STRENGTH:", fontSize = 10.sp, fontWeight = FontWeight.Bold, fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace)
            Spacer(modifier = Modifier.width(8.dp))
            Box(modifier = Modifier.background(lightGreen).border(1.dp, Color.Black).padding(horizontal = 4.dp, vertical = 2.dp)) {
                Text("HIGH (88%)", color = greenText, fontSize = 10.sp, fontWeight = FontWeight.Bold, fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace)
            }
            Spacer(modifier = Modifier.width(8.dp))
            Row(modifier = Modifier.weight(1f), horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                Box(modifier = Modifier.weight(1f).height(6.dp).background(greenText).border(1.dp, Color.Black))
                Box(modifier = Modifier.weight(1f).height(6.dp).background(greenText).border(1.dp, Color.Black))
                Box(modifier = Modifier.weight(1f).height(6.dp).background(greenText).border(1.dp, Color.Black))
                Box(modifier = Modifier.weight(1f).height(6.dp).background(Color.White).border(1.dp, Color.Black))
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Confirm Password
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Text("■ CONFIRM SECURITY CODE", fontSize = 12.sp, fontWeight = FontWeight.Bold, fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace)
        }
        Spacer(modifier = Modifier.height(8.dp))
        NeoInput(value = confirmPassword, onValueChange = { confirmPassword = it }, label = "••••••••••••")

        // Validation Badges
        Spacer(modifier = Modifier.height(16.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
            ValidationBadge("✔ 8+ CHARACTERS", true)
            ValidationBadge("✔ MATCH", confirmPassword.isNotEmpty() && password == confirmPassword)
            ValidationBadge("✔ 1 NUM/SYM", true)
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Warning Box
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(yellow)
                .border(3.dp, Color.Black)
                .padding(12.dp)
        ) {
            Text(
                "! DISPATCH SECURITY PROTOCOL: Emergency response credentials must remain private. Do not share your access keys.",
                fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Role Selection
        Text("■ SELECT PRIMARY OPERATIONAL ROLE", fontSize = 12.sp, fontWeight = FontWeight.Bold, fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace)
        Spacer(modifier = Modifier.height(12.dp))
        RoleCard(
            title = "CITIZEN REPORTER",
            desc = "Report injured strays & ping local ambulances",
            isSelected = role == "CITIZEN",
            onClick = { role = "CITIZEN" }
        )
        Spacer(modifier = Modifier.height(8.dp))
        RoleCard(
            title = "FIELD RESCUER / VOLUNTEER",
            desc = "Join emergency extraction units & physical rescue",
            isSelected = role == "VOLUNTEER",
            onClick = { role = "VOLUNTEER" }
        )
        Spacer(modifier = Modifier.height(8.dp))
        RoleCard(
            title = "MEDICAL / FOSTER CARE",
            desc = "Vet care, first-aid triage, or temporary home sheltering",
            isSelected = role == "MEDICAL",
            onClick = { role = "MEDICAL" }
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Ethics Agreement
        Row(verticalAlignment = Alignment.Top, modifier = Modifier.fillMaxWidth().clickable { ethicsAccepted = !ethicsAccepted }) {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .background(if (ethicsAccepted) darkGreen else Color.White)
                    .border(2.dp, Color.Black),
                contentAlignment = Alignment.Center
            ) {
                if (ethicsAccepted) {
                    Icon(Icons.Default.Check, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                }
            }
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                "I declare that all rescue alerts submitted will be genuine and accept the Bezubaan Code of Ethics and volunteer liability protocol.",
                fontSize = 12.sp,
                fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace,
                lineHeight = 16.sp
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Primary CTA
        Button(
            onClick = { viewModel.register(name, email, password, role) },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .border(3.dp, Color.Black),
            colors = ButtonDefaults.buttonColors(
                containerColor = yellow,
                contentColor = Color.Black,
                disabledContainerColor = Color.LightGray
            ),
            shape = RoundedCornerShape(0.dp),
            enabled = ethicsAccepted
        ) {
            Text(
                text = if (uiState.isLoading) "TRANSMITTING..." else "CREATE ACCOUNT →",
                fontWeight = FontWeight.Black,
                fontSize = 16.sp
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
        
        Text(
            text = "Already registered? [LOG IN HERE →]",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .clickable { onNavigateToLogin() }
                .padding(8.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Emergency SOS
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(redAlert.copy(alpha = 0.1f))
                .border(2.dp, redAlert, shape = RoundedCornerShape(0.dp))
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Warning, contentDescription = null, tint = redAlert, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    "⚡ IMMEDIATE EMERGENCY SOS?",
                    fontWeight = FontWeight.Black,
                    color = redAlert,
                    fontSize = 12.sp,
                    fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace
                )
            }
        }
        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
fun ValidationBadge(text: String, isValid: Boolean) {
    val lightGreen = Color(0xFFC8E6C9)
    val greenText = Color(0xFF2E7D32)
    Box(
        modifier = Modifier
            .background(if (isValid) lightGreen else Color.LightGray)
            .border(1.dp, Color.Black)
            .padding(horizontal = 6.dp, vertical = 4.dp)
    ) {
        Text(
            text, 
            color = if (isValid) greenText else Color.DarkGray, 
            fontSize = 9.sp, 
            fontWeight = FontWeight.Bold, 
            fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace
        )
    }
}

@Composable
fun RoleCard(title: String, desc: String, isSelected: Boolean, onClick: () -> Unit) {
    val darkGreen = Color(0xFF0D3311)
    
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(if (isSelected) darkGreen else Color.White)
            .border(3.dp, Color.Black)
            .clickable { onClick() }
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    title,
                    fontWeight = FontWeight.Black,
                    color = if (isSelected) Color.White else Color.Black,
                    fontSize = 14.sp,
                    fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    desc,
                    color = if (isSelected) Color.LightGray else Color.DarkGray,
                    fontSize = 12.sp,
                    fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace,
                    lineHeight = 16.sp
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .background(Color.White)
                    .border(2.dp, Color.Black),
                contentAlignment = Alignment.Center
            ) {
                if (isSelected) {
                    Text("X", fontWeight = FontWeight.Black, fontSize = 14.sp)
                }
            }
        }
    }
}
