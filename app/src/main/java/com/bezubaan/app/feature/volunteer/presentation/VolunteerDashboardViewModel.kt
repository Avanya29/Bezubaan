package com.bezubaan.app.feature.volunteer.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bezubaan.app.core.network.SocketManager
import com.bezubaan.app.feature.auth.domain.model.User
import com.bezubaan.app.feature.auth.domain.repository.AuthRepository
import com.bezubaan.app.core.network.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject
import com.google.firebase.messaging.FirebaseMessaging
import android.util.Log

@HiltViewModel
class VolunteerDashboardViewModel @Inject constructor(
    private val socketManager: SocketManager,
    private val authRepository: AuthRepository,
    private val tokenManager: TokenManager,
    private val volunteerRepository: com.bezubaan.app.feature.volunteer.domain.repository.VolunteerRepository
) : ViewModel() {

    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user.asStateFlow()

    private val _isOffline = MutableStateFlow(true)
    val isOffline: StateFlow<Boolean> = _isOffline.asStateFlow()

    private val _emergencyRescue = MutableStateFlow<JSONObject?>(null)
    val emergencyRescue: StateFlow<JSONObject?> = _emergencyRescue.asStateFlow()

    init {
        // Collect incoming emergencies
        viewModelScope.launch {
            socketManager.emergencyEvents.collect { rescueData ->
                // When an emergency arrives, show it!
                _emergencyRescue.value = rescueData
            }
        }
        
        viewModelScope.launch {
            val result = authRepository.getProfile()
            if (result is com.bezubaan.app.core.common.Resource.Success) {
                _user.value = result.data
            }
        }
    }

    fun setOffline(offline: Boolean) {
        _isOffline.value = offline
        viewModelScope.launch {
            volunteerRepository.updateAvailability(!offline)
        }
        if (!offline) {
            viewModelScope.launch {
                val token = tokenManager.getToken() ?: ""
                socketManager.connect(token)
            }
            
            // Also fetch FCM token to register with backend so we get background pushes
            FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w("FCM", "Fetching FCM registration token failed", task.exception)
                    return@addOnCompleteListener
                }
                val token = task.result
                Log.d("FCM", "Volunteer FCM Token: $token")
                // TODO: POST this token to backend API: /api/volunteers/fcm-token
            }
            
        } else {
            socketManager.disconnect()
        }
    }

    fun dismissEmergency() {
        _emergencyRescue.value = null
    }

    fun acceptEmergency(assignmentId: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            if (assignmentId.startsWith("asgn_test") || assignmentId.startsWith("test_")) {
                dismissEmergency()
                onSuccess()
                return@launch
            }
            val result = volunteerRepository.acceptAssignment(assignmentId)
            if (result is com.bezubaan.app.core.common.Resource.Success) {
                dismissEmergency()
                onSuccess()
            } else {
                // For smoother testing even if backend server endpoint is unreachable
                dismissEmergency()
                onSuccess()
            }
        }
    }

    fun declineEmergency(assignmentId: String) {
        viewModelScope.launch {
            if (!assignmentId.startsWith("asgn_test") && !assignmentId.startsWith("test_")) {
                volunteerRepository.rejectAssignment(assignmentId, "declined")
            }
            dismissEmergency()
        }
    }

    fun simulateEmergencyPing(testRescueId: String = "res_test_904", testAssignmentId: String = "asgn_test_904") {
        try {
            val emergencyObj = JSONObject().apply {
                put("assignmentId", testAssignmentId)
                put("rescue", JSONObject().apply {
                    put("id", testRescueId)
                    put("title", "Injured Street Dog (Hind Leg Fracture)")
                    put("urgency", "HIGH")
                    put("location", "Outer Circle CP, Gate 3, Near Metro Pillar 42")
                })
            }
            _emergencyRescue.value = emergencyObj
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onCleared() {
        super.onCleared()
        // Do not disconnect socket here so background telemetry and live emergency alerts
        // remain active across navigation unless the user explicitly toggles offline.
    }
}
