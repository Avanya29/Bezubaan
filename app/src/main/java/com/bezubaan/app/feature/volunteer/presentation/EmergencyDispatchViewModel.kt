package com.bezubaan.app.feature.volunteer.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bezubaan.app.core.common.Resource
import com.bezubaan.app.feature.volunteer.domain.repository.VolunteerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EmergencyDispatchViewModel @Inject constructor(
    private val volunteerRepository: VolunteerRepository
) : ViewModel() {

    fun acceptEmergency(assignmentId: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            val result = volunteerRepository.acceptAssignment(assignmentId)
            if (result is Resource.Success) {
                onSuccess()
            }
        }
    }

    fun declineEmergency(assignmentId: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            val result = volunteerRepository.rejectAssignment(assignmentId, "declined")
            if (result is Resource.Success) {
                onSuccess()
            }
        }
    }
}
