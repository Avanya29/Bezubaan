package com.bezubaan.app.feature.profile.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bezubaan.app.core.data.VolunteerPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SettingsUiState(
    val pushNotificationsEnabled: Boolean = true,
    val locationSharingEnabled: Boolean = true,
    val darkModeEnabled: Boolean = false,
    val isLoading: Boolean = true
)

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val preferences: VolunteerPreferences
) : ViewModel() {

    val uiState: StateFlow<SettingsUiState> = combine(
        preferences.isPushNotificationsEnabled,
        preferences.isLocationSharingEnabled,
        preferences.isDarkModeEnabled
    ) { push, location, dark ->
        SettingsUiState(
            pushNotificationsEnabled = push,
            locationSharingEnabled = location,
            darkModeEnabled = dark,
            isLoading = false
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = SettingsUiState()
    )

    fun togglePushNotifications(enabled: Boolean) {
        viewModelScope.launch { preferences.setPushNotificationsEnabled(enabled) }
    }

    fun toggleLocationSharing(enabled: Boolean) {
        viewModelScope.launch { preferences.setLocationSharingEnabled(enabled) }
    }

    fun toggleDarkMode(enabled: Boolean) {
        viewModelScope.launch { preferences.setDarkModeEnabled(enabled) }
    }
}
