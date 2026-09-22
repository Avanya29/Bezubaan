package com.bezubaan.app.feature.volunteer.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bezubaan.app.core.data.VolunteerPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VolunteerSetupViewModel @Inject constructor(
    private val volunteerPreferences: VolunteerPreferences
) : ViewModel() {

    fun activateVolunteerMode() {
        viewModelScope.launch {
            volunteerPreferences.setVolunteerModeActive(true)
        }
    }

    fun deactivateVolunteerMode() {
        viewModelScope.launch {
            volunteerPreferences.setVolunteerModeActive(false)
        }
    }
}
