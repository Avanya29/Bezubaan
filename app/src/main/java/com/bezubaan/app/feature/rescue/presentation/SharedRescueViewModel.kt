package com.bezubaan.app.feature.rescue.presentation

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bezubaan.app.core.common.Resource
import com.bezubaan.app.feature.rescue.domain.model.RescueCase
import com.bezubaan.app.feature.rescue.domain.repository.RescueRepository
import com.bezubaan.app.core.location.LocationTracker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SharedRescueState(
    val photoUri: Uri? = null,
    val species: String = "DOG",
    val sex: String = "UNKNOWN",
    val lifeStage: String = "ADULT",
    val size: String = "MEDIUM",
    val coatColor: String = "",
    val marks: String = "",
    val notes: String = "",
    
    val latitude: Double? = null,
    val longitude: Double? = null,
    val address: String? = null,
    
    val isLoading: Boolean = false,
    val error: String? = null,
    val successCase: RescueCase? = null
)

@HiltViewModel
class SharedRescueViewModel @Inject constructor(
    private val repository: RescueRepository,
    private val locationTracker: LocationTracker
) : ViewModel() {

    private val _state = MutableStateFlow(SharedRescueState())
    val state: StateFlow<SharedRescueState> = _state.asStateFlow()

    fun updatePhoto(uri: Uri?) {
        _state.update { it.copy(photoUri = uri) }
    }

    fun updateSpecies(species: String) {
        _state.update { it.copy(species = species) }
    }

    fun updateSex(sex: String) {
        _state.update { it.copy(sex = sex) }
    }
    
    fun updateLifeStage(stage: String) {
        _state.update { it.copy(lifeStage = stage) }
    }
    
    fun updateSize(size: String) {
        _state.update { it.copy(size = size) }
    }

    fun updateCoatColor(color: String) {
        _state.update { it.copy(coatColor = color) }
    }

    fun updateMarks(marks: String) {
        _state.update { it.copy(marks = marks) }
    }

    fun updateNotes(notes: String) {
        _state.update { it.copy(notes = notes) }
    }

    fun updateLocation(lat: Double, lng: Double, address: String) {
        _state.update { it.copy(latitude = lat, longitude = lng, address = address) }
    }
    
    fun fetchCurrentLocation() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            val location = locationTracker.getCurrentLocation()
            if (location != null) {
                // For a real app, geocoding would happen here. For now, we'll set a default address based on coordinates
                val address = "GPS Synced: ${String.format("%.4f", location.latitude)}, ${String.format("%.4f", location.longitude)}"
                _state.update { it.copy(
                    latitude = location.latitude,
                    longitude = location.longitude,
                    address = address,
                    isLoading = false
                ) }
            } else {
                _state.update { it.copy(
                    error = "Could not fetch current location. Ensure GPS is enabled.",
                    isLoading = false
                ) }
            }
        }
    }

    fun submitRescue() {
        val currentState = _state.value
        
        val lat = currentState.latitude
        val lng = currentState.longitude
        
        if (lat == null || lng == null) {
            _state.update { it.copy(error = "Location is required") }
            return
        }
        
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            
            // Construct a detailed description from the form fields
            val description = buildString {
                append("Sex: ${currentState.sex}, Age: ${currentState.lifeStage}, Size: ${currentState.size}. ")
                if (currentState.coatColor.isNotBlank()) append("Coat: ${currentState.coatColor}. ")
                if (currentState.marks.isNotBlank()) append("Marks: ${currentState.marks}. ")
                if (currentState.notes.isNotBlank()) append("Notes: ${currentState.notes}")
            }
            
            repository.reportRescue(
                description = description,
                lat = lat,
                lng = lng,
                address = currentState.address,
                animalSpecies = currentState.species,
                animalSex = currentState.sex,
                animalAge = currentState.lifeStage,
                animalColor = currentState.coatColor,
                animalSize = currentState.size
            ).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        _state.update { it.copy(isLoading = false, successCase = result.data) }
                    }
                    is Resource.Error -> {
                        _state.update { it.copy(isLoading = false, error = result.message ?: "Failed to report rescue") }
                    }
                    is Resource.Loading -> {
                        _state.update { it.copy(isLoading = true) }
                    }
                }
            }
        }
    }
}
