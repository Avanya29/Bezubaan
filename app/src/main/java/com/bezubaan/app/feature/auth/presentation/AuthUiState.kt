package com.bezubaan.app.feature.auth.presentation

data class AuthUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val isAuthenticated: Boolean = false,
    val isRegistered: Boolean = false,
    val isAuthCheckComplete: Boolean = false
)
