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
import com.bezubaan.app.core.location.LocationTracker
import com.bezubaan.app.feature.rescue.domain.repository.RescueRepository
import com.bezubaan.app.core.common.Resource
import javax.inject.Inject

@HiltViewModel
class RescueReportViewModel @Inject constructor(
    private val repository: RescueRepository,
    private val locationTracker: LocationTracker
) : ViewModel() {

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

    // Removed updateUrgency since the backend does not accept it.

    fun submitReport() {
        viewModelScope.launch {
            _uiState.update { it.copy(isSubmitting = true) }
            val currentState = _uiState.value
            
            val deviceLocation = locationTracker.getCurrentLocation()
            val finalLat = deviceLocation?.latitude ?: 0.0
            val finalLng = deviceLocation?.longitude ?: 0.0
            
            repository.reportRescue(
                description = currentState.description,
                lat = finalLat,
                lng = finalLng,
                address = currentState.location,
                animalSpecies = currentState.animalType
            ).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        _uiState.update { it.copy(isSubmitting = false, isSuccess = true) }
                    }
                    is Resource.Error -> {
                        _uiState.update { it.copy(isSubmitting = false, error = result.message) }
                    }
                    is Resource.Loading -> {
                        _uiState.update { it.copy(isSubmitting = true) }
                    }
                }
            }
        }
    }
}
