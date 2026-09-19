package com.bezubaan.app.feature.adoption.presentation

import com.bezubaan.app.feature.adoption.domain.model.AdoptableAnimal

data class AdoptionUiState(
    val isLoading: Boolean = false,
    val animals: List<AdoptableAnimal> = emptyList(),
    val error: String? = null
)
