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
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadNearbyCases()
        fetchUserProfile()
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
            } else {
                _uiState.update { it.copy(locationName = "LOCATION UNAVAILABLE") }
            }
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

    private fun loadNearbyCases() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            delay(1000)
            val cases = listOf(
                RescueCase(
                    id = "1",
                    description = "Dog found injured near road, needs medical attention.",
                    address = "MG Road, Bangalore",
                    latitude = 12.9716,
                    longitude = 77.5946,
                    status = "Reported",
                    imageUrl = "https://images.unsplash.com/photo-1543466835-00a7907e9de1?auto=format&fit=crop&w=600&q=80",
                    animal = com.bezubaan.app.feature.rescue.domain.model.AnimalDetails(species = "Dog")
                ),
                RescueCase(
                    id = "2",
                    description = "Cat has been stuck in a tree for hours, unable to come down.",
                    address = "Indiranagar, Bangalore",
                    latitude = 12.9784,
                    longitude = 77.6408,
                    status = "Rescued",
                    imageUrl = "https://images.unsplash.com/photo-1514888286974-6c03e2ca1dba?auto=format&fit=crop&w=600&q=80",
                    animal = com.bezubaan.app.feature.rescue.domain.model.AnimalDetails(species = "Cat")
                )
            )
            _uiState.update { it.copy(isLoading = false, nearbyCases = cases) }
        }
    }
}
