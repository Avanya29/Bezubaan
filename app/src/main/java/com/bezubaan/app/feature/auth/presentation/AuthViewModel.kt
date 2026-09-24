package com.bezubaan.app.feature.auth.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import com.bezubaan.app.feature.auth.domain.repository.AuthRepository
import com.bezubaan.app.feature.auth.data.remote.LoginRequest
import com.bezubaan.app.feature.auth.data.remote.RegisterRequest
import com.bezubaan.app.core.common.Resource
import com.bezubaan.app.core.network.TokenManager
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val tokenManager: TokenManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    init {
        checkAuthStatus()
    }

    fun checkAuthStatus() {
        viewModelScope.launch {
            val token = tokenManager.getToken()
            _uiState.update { 
                it.copy(
                    isAuthenticated = !token.isNullOrBlank(),
                    isAuthCheckComplete = true
                ) 
            }
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            val result = authRepository.login(LoginRequest(email, password))
            when (result) {
                is Resource.Success -> {
                    _uiState.update { it.copy(isLoading = false, isAuthenticated = true) }
                }
                is Resource.Error -> {
                    _uiState.update { it.copy(isLoading = false, error = result.message) }
                }
                is Resource.Loading -> {}
            }
        }
    }

    fun register(name: String, email: String, password: String, role: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            val result = authRepository.register(RegisterRequest(name, email, password))
            when (result) {
                is Resource.Success -> {
                    _uiState.update { it.copy(isLoading = false, isRegistered = true) }
                }
                is Resource.Error -> {
                    _uiState.update { it.copy(isLoading = false, error = result.message) }
                }
                is Resource.Loading -> {}
            }
        }
    }

    fun forgotPassword(email: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            delay(1500)
            _uiState.update { it.copy(isLoading = false) }
        }
    }

    fun googleSignIn(context: android.content.Context) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val credentialManager = androidx.credentials.CredentialManager.create(context)
                val googleIdOption = com.google.android.libraries.identity.googleid.GetGoogleIdOption.Builder()
                    .setFilterByAuthorizedAccounts(false)
                    .setServerClientId(com.bezubaan.app.core.common.Constants.GOOGLE_WEB_CLIENT_ID)
                    .setAutoSelectEnabled(true)
                    .build()

                val request = androidx.credentials.GetCredentialRequest.Builder()
                    .addCredentialOption(googleIdOption)
                    .build()

                val result = credentialManager.getCredential(context, request)
                val credential = result.credential

                if (credential is androidx.credentials.CustomCredential &&
                    credential.type == com.google.android.libraries.identity.googleid.GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
                ) {
                    val googleIdTokenCredential = com.google.android.libraries.identity.googleid.GoogleIdTokenCredential.createFrom(credential.data)
                    val idToken = googleIdTokenCredential.idToken

                    val authResult = authRepository.googleSignIn(idToken)
                    when (authResult) {
                        is Resource.Success -> {
                            _uiState.update { it.copy(isLoading = false, isAuthenticated = true) }
                        }
                        is Resource.Error -> {
                            _uiState.update { it.copy(isLoading = false, error = authResult.message) }
                        }
                        is Resource.Loading -> {}
                    }
                } else {
                    _uiState.update { it.copy(isLoading = false, error = "Unexpected credential type") }
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, error = e.localizedMessage ?: "Google Sign-In failed") }
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            authRepository.logout()
            _uiState.update { it.copy(isAuthenticated = false) }
        }
    }
}
