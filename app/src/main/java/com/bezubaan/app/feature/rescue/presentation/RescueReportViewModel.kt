package com.bezubaan.app.feature.rescue.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RescueReportViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(RescueReportUiState())
    val uiState: StateFlow<RescueReportUiState> = _uiState.asStateFlow()

    fun updateAnimalType(type: String) {
        _uiState.update { it.copy(animalType = type) }
    }

    fun updateDescription(desc: String) {
        _uiState.update { it.copy(description = desc) }
    }

    fun updateLocation(loc: String) {
        _uiState.update { it.copy(location = loc) }
    }

    fun updateUrgency(urgency: String) {
        _uiState.update { it.copy(urgency = urgency) }
    }

    fun submitReport() {
        viewModelScope.launch {
            _uiState.update { it.copy(isSubmitting = true) }
            delay(1500) // Mock network delay
            _uiState.update { it.copy(isSubmitting = false, isSuccess = true) }
        }
    }
}
