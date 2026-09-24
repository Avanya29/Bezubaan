package com.bezubaan.app.feature.auth.presentation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.bezubaan.app.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onNavigateToNext: () -> Unit,
    onNavigateToHome: () -> Unit,
    viewModel: AuthViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var progress by remember { mutableFloatStateOf(0f) }

    LaunchedEffect(Unit) {
        val startTime = System.currentTimeMillis()
        val duration = 2500L
        while(System.currentTimeMillis() - startTime < duration) {
            val elapsed = System.currentTimeMillis() - startTime
            progress = (elapsed.toFloat() / duration).coerceIn(0f, 1f)
            delay(16)
        }
        progress = 1f
        delay(300)
        
        // Wait until auth check is complete if it hasn't already
        while(!uiState.isAuthCheckComplete) {
            delay(50)
        }
        
        if (uiState.isAuthenticated) {
            onNavigateToHome()
        } else {
            onNavigateToNext()
        }
    }

    val brutalYellow = Color(0xFFFFD54F)
    val brutalRed = Color(0xFFFF5252)
    val brutalDarkGreen = Color(0xFF0A2B10)
    val brutalBg = Color(0xFFFCFCFC)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(brutalBg)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Top row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .rotate(-2f)
                    .background(Color.White)
                    .border(2.dp, Color.Black)
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text("SYS_BOOT // V2.4", fontSize = 10.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
            }

            Box(
                modifier = Modifier
                    .rotate(2f)
                    .background(Color.White)
                    .border(2.dp, Color.Black)
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text("BLR // REGION 080", fontSize = 10.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // Top tag
        Box(
            modifier = Modifier
                .offset(y = 10.dp)
                .rotate(-1f)
                .background(brutalYellow)
                .border(2.dp, Color.Black)
                .padding(horizontal = 12.dp, vertical = 4.dp)
                .zIndex(1f)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(modifier = Modifier.size(8.dp).background(brutalRed, androidx.compose.foundation.shape.CircleShape).border(1.dp, Color.Black, androidx.compose.foundation.shape.CircleShape))
                Spacer(modifier = Modifier.width(6.dp))
                Text("RAPID ANIMAL RESCUE NETWORK", fontSize = 12.sp, fontWeight = FontWeight.Black, fontFamily = FontFamily.Monospace)
            }
        }

        // Logo Box
        Box(contentAlignment = Alignment.Center) {
            // Shadow
            Box(
                modifier = Modifier
                    .offset(x = 6.dp, y = 6.dp)
                    .size(120.dp)
                    .background(Color.Black, RoundedCornerShape(12.dp))
            )
            // Main Box
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .background(Color.Black, RoundedCornerShape(12.dp))
                    .border(3.dp, Color.Black, RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Logo",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = androidx.compose.ui.layout.ContentScale.Crop
                )
            }
            // Tag
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .offset(x = 10.dp, y = 10.dp)
                    .rotate(4f)
                    .background(brutalRed)
                    .border(2.dp, Color.Black)
                    .padding(horizontal = 8.dp, vertical = 2.dp)
            ) {
                Text("24/7 SOS", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold)
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text("BEZUBAAN", fontSize = 48.sp, fontWeight = FontWeight.Black, letterSpacing = 1.sp)
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Box(
            modifier = Modifier
                .background(brutalDarkGreen)
                .border(2.dp, Color.Black)
                .padding(horizontal = 16.dp, vertical = 6.dp)
        ) {
             Text("HELPING HANDS", color = brutalYellow, fontSize = 14.sp, fontWeight = FontWeight.Bold, letterSpacing = 2.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = "For the voiceless, with courage, precision\n& urgent care.",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(24.dp))
        
        // Tags
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            TagPill("GPS DISPATCH", brutalDarkGreen)
            TagPill("AI TRIAGE", brutalYellow)
        }
        Spacer(modifier = Modifier.height(8.dp))
        TagPill("COMMUNITY CARE", brutalRed)

        Spacer(modifier = Modifier.weight(1f))

        // Progress bar
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("INITIALIZING TELEMETRY", fontSize = 10.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace, color = Color.Black)
            Text(if(progress >= 1f) "READY" else "${(progress*100).toInt()}%", fontSize = 10.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace, color = Color.Black)
        }
        Spacer(modifier = Modifier.height(4.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(12.dp)
                .background(Color.White)
                .border(2.dp, Color.Black)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(progress)
                    .background(brutalDarkGreen)
                    .border(1.dp, Color.Black)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Footer
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
            Box(modifier = Modifier.size(6.dp).background(brutalYellow).border(1.dp, Color.Black))
            Spacer(modifier = Modifier.width(6.dp))
            Text("BEZUBAAN FOUNDATION • BENGALURU", fontSize = 9.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace, color = Color.Black)
            Spacer(modifier = Modifier.width(6.dp))
            Box(modifier = Modifier.size(6.dp).background(brutalRed).border(1.dp, Color.Black))
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text("ANDROID ARCHITECTURE • JETPACK COMPOSE NATIVE", fontSize = 8.sp, fontFamily = FontFamily.Monospace, color = Color.LightGray)
    }
}

@Composable
fun TagPill(text: String, dotColor: Color) {
    Box(
        modifier = Modifier
            .background(Color.White)
            .border(2.dp, Color.Black)
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.size(6.dp).background(dotColor, androidx.compose.foundation.shape.CircleShape))
            Spacer(modifier = Modifier.width(6.dp))
            Text(text, fontSize = 10.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
        }
    }
}
