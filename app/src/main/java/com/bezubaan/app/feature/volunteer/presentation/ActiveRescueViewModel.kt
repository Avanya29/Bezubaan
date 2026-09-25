package com.bezubaan.app.feature.volunteer.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bezubaan.app.core.location.LocationTracker
import com.bezubaan.app.feature.volunteer.domain.repository.VolunteerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActiveRescueViewModel @Inject constructor(
    val locationTracker: LocationTracker,
    val volunteerRepository: VolunteerRepository
) : ViewModel() {

    private var trackingJob: Job? = null
    var currentAssignmentId: String? = null
    var currentRescueId: String? = null

    fun startLiveTracking(assignmentId: String, rescueId: String) {
        currentAssignmentId = assignmentId
        currentRescueId = rescueId

        if (trackingJob?.isActive == true) return

        trackingJob = viewModelScope.launch {
            while (true) {
                val location = locationTracker.getCurrentLocation()
                if (location != null) {
                    // Send location to backend
                    volunteerRepository.updateLocation(location.latitude, location.longitude)
                }
                // Ping every 10 seconds
                delay(10000)
            }
        }
    }

    fun stopLiveTracking() {
        trackingJob?.cancel()
        trackingJob = null
    }

    override fun onCleared() {
        super.onCleared()
        stopLiveTracking()
    }
}
