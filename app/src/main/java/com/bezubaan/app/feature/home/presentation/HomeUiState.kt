package com.bezubaan.app.feature.home.presentation

import com.bezubaan.app.feature.rescue.domain.model.RescueCase

data class HomeUiState(
    val isLoading: Boolean = false,
    val nearbyCases: List<RescueCase> = emptyList()
)
