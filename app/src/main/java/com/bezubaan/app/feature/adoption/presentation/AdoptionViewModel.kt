package com.bezubaan.app.feature.adoption.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bezubaan.app.feature.adoption.domain.model.AdoptableAnimal
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class AdoptionViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(AdoptionUiState())
    val uiState: StateFlow<AdoptionUiState> = _uiState.asStateFlow()

    init {
        loadAnimals()
    }

    private fun loadAnimals() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            delay(1000)

            val mockAnimals = listOf(
                AdoptableAnimal(
                    id = UUID.randomUUID().toString(),
                    name = "Bruno",
                    species = "Dog",
                    breed = "Indie",
                    age = "2 years",
                    description = "Friendly and playful. Rescued from a construction site.",
                    location = "Bangalore",
                    isVaccinated = true,
                    isNeutered = true
                ),
                AdoptableAnimal(
                    id = UUID.randomUUID().toString(),
                    name = "Whiskers",
                    species = "Cat",
                    breed = "Persian Mix",
                    age = "1 year",
                    description = "Calm and loves to be petted. Found abandoned near a park.",
                    location = "Mumbai",
                    isVaccinated = true,
                    isNeutered = false
                ),
                AdoptableAnimal(
                    id = UUID.randomUUID().toString(),
                    name = "Simba",
                    species = "Dog",
                    breed = "Labrador Mix",
                    age = "6 months",
                    description = "Energetic puppy looking for a loving home!",
                    location = "Delhi",
                    isVaccinated = true,
                    isNeutered = false
                )
            )

            _uiState.update {
                it.copy(isLoading = false, animals = mockAnimals)
            }
        }
    }
}
