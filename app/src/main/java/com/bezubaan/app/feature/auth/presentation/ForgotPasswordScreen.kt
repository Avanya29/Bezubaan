package com.bezubaan.app.feature.auth.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Timer
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
fun ForgotPasswordScreen(
    onBackToLogin: () -> Unit,
    viewModel: AuthViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var email by remember { mutableStateOf("") }

    val yellow = Color(0xFFF5CE42)
    val darkGreen = Color(0xFF0D3311)
    val lightGreen = Color(0xFFC8E6C9)
    val redAlert = Color(0xFFC5221F)
    val bgGray = Color(0xFFF4F4F0)
    val darkRed = Color(0xFF8B0000)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(bgGray)
            .verticalScroll(rememberScrollState())
            .padding(24.dp)
    ) {
        // Header Nav
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable { onBackToLogin() }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    "BACK TO LOGIN",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace
                )
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Box(modifier = Modifier.background(Color.Black).padding(horizontal = 6.dp, vertical = 2.dp)) {
                    Text("AUTH // PROTOCOL", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold, fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace)
                }
                Box(modifier = Modifier.background(lightGreen).padding(horizontal = 6.dp, vertical = 2.dp).border(1.dp, Color.Black)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Lock, contentDescription = null, modifier = Modifier.size(10.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("SECURE", color = Color.Black, fontSize = 10.sp, fontWeight = FontWeight.Bold, fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace)
                    }
                }
            }
        }
        
        Spacer(modifier = Modifier.height(32.dp))

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
                    "BEZUBAAN KEY-VAULT", 
                    color = Color.Black, 
                    fontSize = 14.sp, 
                    fontWeight = FontWeight.Black,
                    fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace
                )
                Text(
                    "DISPATCH RESCUER ID // V2.4 KEY RECOVERY", 
                    color = Color.Black, 
                    fontSize = 10.sp, 
                    fontWeight = FontWeight.Bold,
                    fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Title
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "RESET YOUR ",
                style = MaterialTheme.typography.headlineLarge,
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
                    text = "PASSWORD.",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Black,
                    color = Color.Black
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Description
        Text(
            text = "Enter your registered rescuer email or field callsign. We will transmit a cryptographically signed bypass token to your inbox.",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Black,
            fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace
        )

        Spacer(modifier = Modifier.height(32.dp))

        // State Inspector Tabs
        Row(modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .background(darkGreen)
                    .border(2.dp, Color.Black)
                    .padding(vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text("1. FORM", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold, fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace)
            }
            Box(
                modifier = Modifier
                    .weight(1f)
                    .background(Color.White)
                    .border(2.dp, Color.Black)
                    .padding(vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text("2. SENT", color = Color.Black, fontSize = 10.sp, fontWeight = FontWeight.Bold, fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace)
            }
            Box(
                modifier = Modifier
                    .weight(1f)
                    .background(Color.White)
                    .border(2.dp, Color.Black)
                    .padding(vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text("3. ERROR", color = Color.Black, fontSize = 10.sp, fontWeight = FontWeight.Bold, fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace)
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Input Field
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Text("■ RESCUER EMAIL // CALLSIGN", fontSize = 12.sp, fontWeight = FontWeight.Bold, fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace)
            Box(modifier = Modifier.background(redAlert).padding(horizontal = 4.dp, vertical = 2.dp)) {
                Text("REQUIRED", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold, fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace)
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        NeoInput(
            value = email, 
            onValueChange = { email = it }, 
            label = "rescuer@bezubaan.org",
            leadingIcon = { Icon(Icons.Default.Email, contentDescription = null, tint = Color.Black) },
            trailingIcon = { 
                if(email.isNotEmpty()) {
                    Box(modifier = Modifier.background(lightGreen).border(1.dp, Color.Black).padding(horizontal = 6.dp, vertical = 2.dp)) {
                        Text("VALID ✓", color = Color(0xFF2E7D32), fontSize = 10.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Domain Status
        Text(
            "✔ VERIFIED BEZUBAAN RESCUE DOMAIN v",
            fontSize = 10.sp,
            color = Color(0xFF2E7D32),
            fontWeight = FontWeight.Bold,
            fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Timer Info Box
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Black)
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Default.Timer, contentDescription = null, tint = yellow, modifier = Modifier.size(24.dp))
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                "Reset links self-terminate in exactly 15 minutes. Dispatch field stations require continuous email network ping.",
                color = yellow,
                fontSize = 10.sp,
                fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace,
                lineHeight = 14.sp
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Primary Action
        Button(
            onClick = { viewModel.forgotPassword(email) },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .border(3.dp, Color.Black),
            colors = ButtonDefaults.buttonColors(
                containerColor = yellow,
                contentColor = Color.Black,
                disabledContainerColor = Color.LightGray
            ),
            shape = RoundedCornerShape(0.dp)
        ) {
            Text(
                text = if (uiState.isLoading) "TRANSMITTING..." else "TRANSMIT RESET LINK →",
                fontWeight = FontWeight.Black,
                fontSize = 16.sp
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Remember Password Box
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .border(3.dp, Color.Black)
                .padding(16.dp)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                Text(
                    "REMEMBER PASSWORD?",
                    fontWeight = FontWeight.Black,
                    fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace,
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    "Return to standard operational login.",
                    color = Color.Black,
                    fontSize = 12.sp,
                    fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = onBackToLogin,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .border(2.dp, Color.Black),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = darkGreen,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(0.dp)
                ) {
                    Text(
                        text = "SIGN IN |",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Critical Distress Bypass
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(redAlert.copy(alpha = 0.1f))
                .border(2.dp, redAlert, shape = RoundedCornerShape(0.dp))
                .padding(16.dp)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Warning, contentDescription = null, tint = redAlert, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        "CRITICAL DISTRESS BYPASS ●",
                        fontWeight = FontWeight.Black,
                        color = redAlert,
                        fontSize = 12.sp,
                        fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    "Reporting an animal trauma?",
                    color = redAlert,
                    fontSize = 12.sp,
                    fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { /* TODO */ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .border(2.dp, Color.Black),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = darkRed,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(0.dp)
                ) {
                    Text(
                        text = "SOS ALERT",
                        fontWeight = FontWeight.Black,
                        fontSize = 16.sp,
                        fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace
                    )
                }
            }
        }
        
        Spacer(modifier = Modifier.height(32.dp))
    }
}
