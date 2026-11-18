package com.bezubaan.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bezubaan.app.core.data.AppPersona
import com.bezubaan.app.core.data.VolunteerPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

import com.bezubaan.app.core.network.TokenManager
import kotlinx.coroutines.flow.SharedFlow

@HiltViewModel
class MainViewModel @Inject constructor(
    private val volunteerPreferences: VolunteerPreferences,
    private val tokenManager: TokenManager,
    private val socketManager: com.bezubaan.app.core.network.SocketManager
) : ViewModel() {

    init {
        viewModelScope.launch {
            kotlinx.coroutines.flow.combine(
                volunteerPreferences.isVolunteerModeActive,
                tokenManager.tokenFlow
            ) { isVolunteer, token ->
                if (isVolunteer && !token.isNullOrBlank()) {
                    socketManager.connect(token)
                }
            }.collect {}
        }
    }

    val isVolunteerModeActive: StateFlow<Boolean> = volunteerPreferences.isVolunteerModeActive
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = false
        )

    val selectedPersona: StateFlow<AppPersona> = volunteerPreferences.selectedPersona
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = AppPersona.REPORTER
        )
        
    val sessionExpiredFlow: SharedFlow<Unit> = tokenManager.sessionExpiredFlow

    fun setPersona(persona: AppPersona) {
        viewModelScope.launch {
            volunteerPreferences.setPersona(persona)
        }
    }

    fun togglePersona() {
        viewModelScope.launch {
            val current = isVolunteerModeActive.value
            volunteerPreferences.setVolunteerModeActive(!current)
        }
    }
}
