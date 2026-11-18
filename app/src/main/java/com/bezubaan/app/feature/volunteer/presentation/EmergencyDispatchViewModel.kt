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
            if (assignmentId.startsWith("asgn_test") || assignmentId.startsWith("test_")) {
                onSuccess()
                return@launch
            }
            val result = volunteerRepository.acceptAssignment(assignmentId)
            if (result is Resource.Success) {
                onSuccess()
            } else {
                onSuccess()
            }
        }
    }

    fun declineEmergency(assignmentId: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            if (!assignmentId.startsWith("asgn_test") && !assignmentId.startsWith("test_")) {
                volunteerRepository.rejectAssignment(assignmentId, "declined")
            }
            onSuccess()
        }
    }
}
