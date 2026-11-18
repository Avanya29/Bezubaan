package com.bezubaan.app.feature.home.presentation

import android.content.Context
import android.location.Geocoder
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bezubaan.app.core.location.LocationTracker
import com.bezubaan.app.feature.auth.domain.repository.AuthRepository
import com.bezubaan.app.feature.rescue.domain.model.RescueCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Locale
import javax.inject.Inject



@HiltViewModel
class HomeViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val locationTracker: LocationTracker,
    private val rescueRepository: com.bezubaan.app.feature.rescue.domain.repository.RescueRepository,
    private val volunteerRepository: com.bezubaan.app.feature.volunteer.domain.repository.VolunteerRepository,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        fetchUserProfile()
        // Wait for fetchLocation to populate location before loading cases
        // but we'll fetch location immediately on init.
        fetchLocation()
    }

    private fun fetchUserProfile() {
        viewModelScope.launch {
            val resource = authRepository.getProfile()
            if (resource is com.bezubaan.app.core.common.Resource.Success) {
                val user = resource.data
                _uiState.update { it.copy(userName = user?.name ?: "") }
            }
        }
    }

    fun fetchLocation() {
        viewModelScope.launch {
            val location = locationTracker.getCurrentLocation()
            if (location != null) {
                val addressName = reverseGeocode(location.latitude, location.longitude)
                _uiState.update { it.copy(locationName = addressName) }
                loadNearbyCases(location.latitude, location.longitude)
            } else {
                _uiState.update { it.copy(locationName = "LOCATION UNAVAILABLE") }
            }
        }
    }

    fun toggleDutyStatus() {
        val newStatus = !_uiState.value.isOnDuty
        _uiState.update { it.copy(isOnDuty = newStatus) }
        viewModelScope.launch {
            volunteerRepository.updateAvailability(newStatus)
        }
    }

    private suspend fun reverseGeocode(lat: Double, lng: Double): String {
        return withContext(Dispatchers.IO) {
            try {
                val geocoder = Geocoder(context, Locale.getDefault())
                val addresses = geocoder.getFromLocation(lat, lng, 1)
                if (!addresses.isNullOrEmpty()) {
                    val address = addresses[0]
                    val subLocality = address.subLocality
                    val locality = address.locality
                    if (subLocality != null && locality != null) {
                        "$subLocality, $locality".uppercase()
                    } else if (locality != null) {
                        locality.uppercase()
                    } else {
                        "UNKNOWN AREA"
                    }
                } else {
                    "UNKNOWN LOCATION"
                }
            } catch (e: Exception) {
                "UNKNOWN LOCATION"
            }
        }
    }

    private fun loadNearbyCases(lat: Double, lng: Double) {
        viewModelScope.launch {
            rescueRepository.getNearbyRescues(lat, lng).collect { result ->
                when (result) {
                    is com.bezubaan.app.core.common.Resource.Success -> {
                        _uiState.update { it.copy(isLoading = false, nearbyCases = result.data ?: emptyList()) }
                    }
                    is com.bezubaan.app.core.common.Resource.Loading -> {
                        _uiState.update { it.copy(isLoading = true) }
                    }
                    is com.bezubaan.app.core.common.Resource.Error -> {
                        android.util.Log.e("HomeViewModel", "Error loading cases: ${result.message}")
                        _uiState.update { it.copy(isLoading = false, error = result.message) }
                    }
                }
            }
        }
    }
}
