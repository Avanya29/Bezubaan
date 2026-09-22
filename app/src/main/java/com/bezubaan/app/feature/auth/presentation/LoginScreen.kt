package com.bezubaan.app.feature.auth.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
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
import com.bezubaan.app.ui.components.NeoButton
import com.bezubaan.app.ui.components.NeoInput
import com.bezubaan.app.ui.components.NeoOutlinedButton

@Composable
fun LoginScreen(
    onNavigateToRegister: () -> Unit,
    onNavigateToForgot: () -> Unit,
    onLoginSuccess: () -> Unit,
    viewModel: AuthViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var keepSessionActive by remember { mutableStateOf(true) }

    LaunchedEffect(uiState.isAuthenticated) {
        if (uiState.isAuthenticated) {
            onLoginSuccess()
        }
    }

    val yellow = Color(0xFFF5CE42)
    val darkGreen = Color(0xFF0D3311)
    val lightGreen = Color(0xFFC8E6C9)
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
                    "AUTH // ACCESS PORTAL", 
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
                    "256-BIT ENCRYPTED", 
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
            Box(
                modifier = Modifier
                    .background(redAlert)
                    .border(2.dp, Color.Black)
                    .padding(horizontal = 6.dp, vertical = 2.dp)
            ) {
                Text(
                    "V2.4", 
                    color = Color.White, 
                    fontSize = 12.sp, 
                    fontWeight = FontWeight.Bold,
                    fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Title
        Text(
            text = "SIGN IN TO",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Black,
            color = Color.Black
        )
        Box(
            modifier = Modifier
                .background(yellow)
                .border(3.dp, Color.Black)
                .padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
            Text(
                text = "BEZUBAAN.",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Black,
                color = Color.Black
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Description
        Text(
            text = "Enter your rescuer credentials to coordinate emergency dispatches, manage active field rescues & track sheltered cases.",
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

        // Email Field
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "■ EMAIL // RESCUER ID",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace,
                color = Color.Black
            )
            Box(modifier = Modifier.background(Color.Black).padding(horizontal = 4.dp, vertical = 2.dp)) {
                Text("SYS_AUTH", color = yellow, fontSize = 10.sp, fontWeight = FontWeight.Bold, fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace)
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        NeoInput(
            value = email,
            onValueChange = { email = it },
            label = "rescuer@bezubaan.org"
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Password Field
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "■ SECURITY CODE // PASSWORD",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace,
                color = Color.Black
            )
            Text(
                "FORGOT?",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = redAlert,
                modifier = Modifier.clickable { onNavigateToForgot() },
                fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        NeoInput(
            value = password,
            onValueChange = { password = it },
            label = "••••••••••••"
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Keep Session Active
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable { keepSessionActive = !keepSessionActive }) {
                Box(
                    modifier = Modifier
                        .size(20.dp)
                        .background(if (keepSessionActive) yellow else Color.White)
                        .border(2.dp, Color.Black),
                    contentAlignment = Alignment.Center
                ) {
                    if (keepSessionActive) {
                        Text("X", fontWeight = FontWeight.Black, fontSize = 12.sp)
                    }
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text("Keep dispatch session active", fontSize = 14.sp, fontWeight = FontWeight.Medium, color = Color.Black)
            }
            Text("NODE: MH-02", fontSize = 10.sp, fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace, color = Color.DarkGray)
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Primary Action
        Button(
            onClick = { viewModel.login(email, password) },
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
                text = if (uiState.isLoading) "AUTHENTICATING..." else "ACCESS RESCUE NETWORK →",
                fontWeight = FontWeight.Black,
                fontSize = 16.sp
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Divider
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
            HorizontalDivider(modifier = Modifier.weight(1f), color = Color.Black, thickness = 2.dp)
            Text(
                " OR AUTHENTICATE VIA ",
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace,
                color = Color.Black
            )
            HorizontalDivider(modifier = Modifier.weight(1f), color = Color.Black, thickness = 2.dp)
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Secondary Action (Google)
        Button(
            onClick = { /* TODO */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .border(3.dp, Color.Black),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White,
                contentColor = Color.Black
            ),
            shape = RoundedCornerShape(0.dp)
        ) {
            Text(
                text = "G CONTINUE WITH GOOGLE SECURE",
                fontWeight = FontWeight.Black,
                fontSize = 14.sp
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // New Volunteer Box
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .border(3.dp, Color.Black)
                .padding(16.dp)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                Text(
                    "NEW CITIZEN OR VOLUNTEER?",
                    fontWeight = FontWeight.Black,
                    fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace,
                    fontSize = 14.sp,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(12.dp))
                Button(
                    onClick = onNavigateToRegister,
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
                        text = "🛈 REGISTER AS RESCUER / VOLUNTEER",
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Emergency Bypass Box
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
                    "NO LOGIN REQUIRED FOR EMERGENCY DISPATCH",
                    fontWeight = FontWeight.Black,
                    color = redAlert,
                    fontSize = 10.sp,
                    fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace
                )
            }
        }
        
        Spacer(modifier = Modifier.height(32.dp))
    }
}
