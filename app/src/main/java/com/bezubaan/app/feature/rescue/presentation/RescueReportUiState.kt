package com.bezubaan.app.feature.rescue.presentation

data class RescueReportUiState(
    val animalType: String = "",
    val description: String = "",
    val location: String = "",
    val isSubmitting: Boolean = false,
    val isSuccess: Boolean = false,
    val error: String? = null
)
