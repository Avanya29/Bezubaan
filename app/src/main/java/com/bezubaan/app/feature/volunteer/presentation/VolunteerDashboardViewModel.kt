package com.bezubaan.app.feature.volunteer.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bezubaan.app.core.network.SocketManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject
import com.google.firebase.messaging.FirebaseMessaging
import android.util.Log

@HiltViewModel
class VolunteerDashboardViewModel @Inject constructor(
    private val socketManager: SocketManager
) : ViewModel() {

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
    }

    // In a real app, JWT token is fetched from AuthManager/DataStore.
    // For demo, we can pass a dummy token or a real one if available.
    fun setOffline(offline: Boolean, jwtToken: String = "dummy-token-for-now") {
        _isOffline.value = offline
        if (!offline) {
            socketManager.connect(jwtToken)
            
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

    override fun onCleared() {
        super.onCleared()
        socketManager.disconnect()
    }
}
