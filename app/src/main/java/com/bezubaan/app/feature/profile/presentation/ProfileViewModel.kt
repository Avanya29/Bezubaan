package com.bezubaan.app.feature.profile.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bezubaan.app.core.common.Resource
import com.bezubaan.app.feature.auth.domain.model.User
import com.bezubaan.app.feature.auth.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ProfileUiState(
    val isLoading: Boolean = false,
    val user: User? = null,
    val stats: com.bezubaan.app.feature.auth.domain.model.UserStats? = null,
    val error: String? = null
)

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    init {
        fetchProfile()
    }

    fun fetchProfile() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            
            val profileResult = authRepository.getProfile()
            val statsResult = authRepository.getStats()
            
            if (profileResult is Resource.Success && statsResult is Resource.Success) {
                _uiState.update { 
                    it.copy(
                        isLoading = false, 
                        user = profileResult.data,
                        stats = statsResult.data
                    ) 
                }
            } else {
                val error = if (profileResult is Resource.Error) profileResult.message else if (statsResult is Resource.Error) statsResult.message else "Unknown Error"
                _uiState.update { it.copy(isLoading = false, error = error) }
            }
        }
    }
}
