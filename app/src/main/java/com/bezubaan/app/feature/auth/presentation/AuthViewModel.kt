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
                val activity = findActivity(context) ?: (context as? android.app.Activity)
                if (activity == null) {
                    _uiState.update { it.copy(isLoading = false, error = "Activity context is required for Google Sign-In") }
                    return@launch
                }

                android.widget.Toast.makeText(activity, "Requesting Google Sign-In...", android.widget.Toast.LENGTH_SHORT).show()

                val credentialManager = androidx.credentials.CredentialManager.create(activity)
                
                // 1. Primary: GetSignInWithGoogleOption specifically designed for explicit button clicks
                // This triggers the interactive Google Account Picker dialog (bottom sheet)
                val signInWithGoogleOption = com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption.Builder(
                    serverClientId = com.bezubaan.app.core.common.Constants.GOOGLE_WEB_CLIENT_ID
                ).build()

                val request = androidx.credentials.GetCredentialRequest.Builder()
                    .addCredentialOption(signInWithGoogleOption)
                    .build()

                val result = credentialManager.getCredential(activity, request)
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
                            android.widget.Toast.makeText(activity, "Google Sign-In successful!", android.widget.Toast.LENGTH_SHORT).show()
                        }
                        is Resource.Error -> {
                            _uiState.update { it.copy(isLoading = false, error = authResult.message) }
                            android.widget.Toast.makeText(activity, "Auth Error: ${authResult.message}", android.widget.Toast.LENGTH_LONG).show()
                        }
                        is Resource.Loading -> {}
                    }
                } else {
                    val err = "Unexpected credential format returned: ${credential.type}"
                    _uiState.update { it.copy(isLoading = false, error = err) }
                    android.widget.Toast.makeText(activity, err, android.widget.Toast.LENGTH_LONG).show()
                }
            } catch (e: androidx.credentials.exceptions.GetCredentialCancellationException) {
                android.util.Log.w("AuthViewModel", "Google Sign-In dismissed: ${e.message}", e)
                val dismissMsg = "Google Sign-In sheet closed (${e.message ?: "dismissed"}). If it closed instantly without showing accounts, Google Play Services rejected the credentials or is in cooldown."
                _uiState.update { it.copy(isLoading = false, error = dismissMsg) }
                val act = findActivity(context) ?: (context as? android.app.Activity)
                act?.let { android.widget.Toast.makeText(it, dismissMsg, android.widget.Toast.LENGTH_LONG).show() }
            } catch (e: androidx.credentials.exceptions.GetCredentialCustomException) {
                android.util.Log.e("AuthViewModel", "Google Sign-In custom error (${e.type}): ${e.message}", e)
                val hint = when {
                    e.message?.contains("Developer console is not properly configured") == true || e.type.contains("16") || e.message?.contains("16") == true ->
                        "Google Play Services configuration error (16): Google Cloud is still propagating your SHA-1 or package name. Wait 5 mins or verify SHA-1."
                    else -> e.message ?: "Google Sign-In failed (${e.type})"
                }
                _uiState.update { it.copy(isLoading = false, error = hint) }
                val act = findActivity(context) ?: (context as? android.app.Activity)
                act?.let { android.widget.Toast.makeText(it, hint, android.widget.Toast.LENGTH_LONG).show() }
            } catch (e: androidx.credentials.exceptions.NoCredentialException) {
                android.util.Log.e("AuthViewModel", "NoCredentialException: Play Services blocked popup or no authorized account matching client ID", e)
                val hint = "No Google accounts available or Google Cloud OAuth is still propagating. Wait 5 mins or check device Google accounts."
                _uiState.update { it.copy(isLoading = false, error = hint) }
                val act = findActivity(context) ?: (context as? android.app.Activity)
                act?.let { android.widget.Toast.makeText(it, hint, android.widget.Toast.LENGTH_LONG).show() }
            } catch (e: Exception) {
                android.util.Log.e("AuthViewModel", "Google Sign-In exception", e)
                val msg = e.localizedMessage ?: "Google Sign-In failed: ${e::class.java.simpleName}"
                _uiState.update { it.copy(isLoading = false, error = msg) }
                val act = findActivity(context) ?: (context as? android.app.Activity)
                act?.let { android.widget.Toast.makeText(it, msg, android.widget.Toast.LENGTH_LONG).show() }
            }
        }
    }

    private fun findActivity(context: android.content.Context): android.app.Activity? {
        var ctx = context
        while (ctx is android.content.ContextWrapper) {
            if (ctx is android.app.Activity) return ctx
            ctx = ctx.baseContext
        }
        return null
    }

    fun authenticateWithGoogleIdToken(idToken: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
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
        }
    }

    fun setAuthError(errorMessage: String) {
        _uiState.update { it.copy(isLoading = false, error = errorMessage) }
    }

    fun logout() {
        viewModelScope.launch {
            authRepository.logout()
            _uiState.update { it.copy(isAuthenticated = false) }
        }
    }
}
