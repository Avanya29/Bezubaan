package com.bezubaan.app.feature.volunteer.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.bezubaan.app.feature.rescue.presentation.RescueTrackingViewModel
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RescueTrackingScreen(
    rescueId: String,
    viewModel: RescueTrackingViewModel = hiltViewModel(),
    onCallVolunteer: () -> Unit,
    onChat: () -> Unit,
    onBack: () -> Unit
) {
    val rescueCase by viewModel.rescueCase.collectAsState()

    DisposableEffect(rescueId) {
        viewModel.startTracking(rescueId)
        onDispose { viewModel.stopTracking() }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF7F7F7))
    ) {
        // Top Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = onBack, modifier = Modifier.size(24.dp)) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("RESCUE TRACKING", fontSize = 16.sp, fontWeight = FontWeight.Black)
            }
            Box(modifier = Modifier.background(Color.Black).padding(horizontal = 6.dp, vertical = 2.dp)) {
                Text(rescueId.takeLast(6).uppercase(), color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold)
            }
        }
        HorizontalDivider(thickness = 3.dp, color = Color.Black)

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth().background(Color(0xFFE8F5E9)).border(1.dp, Color(0xFF2E7D32)).padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.LocalShipping, tint = Color(0xFF2E7D32), contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("VOLUNTEER ON THE WAY", fontSize = 10.sp, color = Color(0xFF2E7D32), fontWeight = FontWeight.Black)
                    }
                    Text("ETA ~3 MIN", fontSize = 10.sp, color = Color(0xFF2E7D32), fontWeight = FontWeight.Black)
                }
            }

            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                        .background(Color.LightGray)
                        .border(3.dp, Color.Black)
                ) {
                    val lat = rescueCase?.latitude ?: 28.6139
                    val lng = rescueCase?.longitude ?: 77.2090
                    val location = com.google.android.gms.maps.model.LatLng(lat, lng)
                    val cameraPositionState = com.google.maps.android.compose.rememberCameraPositionState {
                        position = com.google.android.gms.maps.model.CameraPosition.fromLatLngZoom(location, 15f)
                    }

                    androidx.compose.runtime.LaunchedEffect(lat, lng) {
                        cameraPositionState.position = com.google.android.gms.maps.model.CameraPosition.fromLatLngZoom(location, 15f)
                    }

                    com.google.maps.android.compose.GoogleMap(
                        modifier = Modifier.fillMaxSize(),
                        cameraPositionState = cameraPositionState
                    ) {
                        com.google.maps.android.compose.Marker(
                            state = com.google.maps.android.compose.MarkerState(position = location),
                            title = "Rescue Location"
                        )
                        rescueCase?.volunteerLocation?.let { vol ->
                            com.google.maps.android.compose.Marker(
                                state = com.google.maps.android.compose.MarkerState(position = com.google.android.gms.maps.model.LatLng(vol.latitude, vol.longitude)),
                                title = "Volunteer",
                                icon = com.google.android.gms.maps.model.BitmapDescriptorFactory.defaultMarker(com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_GREEN)
                            )
                        }
                    }
                }
            }

            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .border(3.dp, Color.Black)
                        .padding(16.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(modifier = Modifier.size(40.dp).background(Color.LightGray, RoundedCornerShape(20.dp)))
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                val volunteerStatus = if (rescueCase?.volunteerLocation != null) "Assigned Volunteer" else "Awaiting Volunteer"
                                Text(volunteerStatus, fontSize = 16.sp, fontWeight = FontWeight.Black)
                                if (rescueCase?.volunteerLocation != null) {
                                    Text("CERTIFIED RESPONDER", fontSize = 10.sp, color = Color.Black, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                        Box(modifier = Modifier.background(yellowBtn).padding(horizontal = 8.dp, vertical = 4.dp).border(1.dp, Color.Black)) {
                            val distance = rescueCase?.distanceToRescue ?: 0.0
                            val displayDistance = if (distance > 0) "${distance.toInt()}M AWAY" else "UNKNOWN"
                            Text(displayDistance, fontSize = 10.sp, fontWeight = FontWeight.Black)
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                        Button(
                            onClick = onCallVolunteer,
                            modifier = Modifier.weight(1f).height(48.dp).border(2.dp, Color.Black),
                            colors = ButtonDefaults.buttonColors(containerColor = darkGreen, contentColor = Color.White),
                            shape = RoundedCornerShape(0.dp),
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Text("CALL", fontSize = 12.sp, fontWeight = FontWeight.Black)
                        }
                        Button(
                            onClick = onChat,
                            modifier = Modifier.weight(1f).height(48.dp).border(2.dp, Color.Black),
                            colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = Color.Black),
                            shape = RoundedCornerShape(0.dp),
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Text("CHAT (1)", fontSize = 12.sp, fontWeight = FontWeight.Black)
                        }
                    }
                }
            }

            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .border(3.dp, Color.Black)
                        .padding(16.dp)
                ) {
                    Text("DISPATCH TIMELINE", fontSize = 12.sp, fontWeight = FontWeight.Black)
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    TimelineItem("Reported (You)", rescueCase?.createdAt?.take(10) ?: "Just now", true)
                    TimelineItem("Verified & Logged", "", rescueCase != null)
                    TimelineItem("Assigned", "", rescueCase?.volunteerLocation != null)
                    TimelineItem("Volunteer En Route", "", rescueCase?.volunteerLocation != null, isLast = true)
                }
            }
            
            item { Spacer(modifier = Modifier.height(40.dp)) }
        }
    }
}

@Composable
private fun TimelineItem(title: String, time: String, isComplete: Boolean, isLast: Boolean = false) {
    Row(modifier = Modifier.fillMaxWidth().padding(bottom = if (isLast) 0.dp else 16.dp)) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                modifier = Modifier
                    .size(16.dp)
                    .background(if (isComplete) darkGreen else Color.White)
                    .border(2.dp, Color.Black)
            )
            if (!isLast) {
                Box(modifier = Modifier.width(2.dp).height(24.dp).background(Color.Black))
            }
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(title, fontSize = 14.sp, fontWeight = FontWeight.Black)
            Text(time, fontSize = 10.sp, color = Color.Black, fontWeight = FontWeight.Bold)
        }
    }
}
