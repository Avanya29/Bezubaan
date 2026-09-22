package com.bezubaan.app.feature.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bezubaan.app.feature.rescue.domain.model.RescueCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadNearbyCases()
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
