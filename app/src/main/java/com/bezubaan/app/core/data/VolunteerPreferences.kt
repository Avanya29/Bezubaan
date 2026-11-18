package com.bezubaan.app.core.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

enum class AppPersona {
    REPORTER,
    VOLUNTEER
}

@Singleton
class VolunteerPreferences @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    private val volunteerModeKey = booleanPreferencesKey("volunteer_mode_active")
    private val personaKey = stringPreferencesKey("app_persona")
    private val pushNotificationsKey = booleanPreferencesKey("push_notifications_enabled")
    private val locationSharingKey = booleanPreferencesKey("location_sharing_enabled")
    private val darkModeKey = booleanPreferencesKey("dark_mode_enabled")

    val isVolunteerModeActive: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[volunteerModeKey] ?: (preferences[personaKey] == AppPersona.VOLUNTEER.name)
    }

    val selectedPersona: Flow<AppPersona> = dataStore.data.map { preferences ->
        val raw = preferences[personaKey]
        if (raw != null) {
            try {
                AppPersona.valueOf(raw)
            } catch (e: Exception) {
                if (preferences[volunteerModeKey] == true) AppPersona.VOLUNTEER else AppPersona.REPORTER
            }
        } else {
            if (preferences[volunteerModeKey] == true) AppPersona.VOLUNTEER else AppPersona.REPORTER
        }
    }
    
    val isPushNotificationsEnabled: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[pushNotificationsKey] ?: true
    }
    
    val isLocationSharingEnabled: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[locationSharingKey] ?: true
    }
    
    val isDarkModeEnabled: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[darkModeKey] ?: false
    }

    suspend fun setVolunteerModeActive(isActive: Boolean) {
        dataStore.edit { preferences ->
            preferences[volunteerModeKey] = isActive
            preferences[personaKey] = if (isActive) AppPersona.VOLUNTEER.name else AppPersona.REPORTER.name
        }
    }

    suspend fun setPersona(persona: AppPersona) {
        dataStore.edit { preferences ->
            preferences[personaKey] = persona.name
            preferences[volunteerModeKey] = (persona == AppPersona.VOLUNTEER)
        }
    }
    
    suspend fun setPushNotificationsEnabled(isEnabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[pushNotificationsKey] = isEnabled
        }
    }
    
    suspend fun setLocationSharingEnabled(isEnabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[locationSharingKey] = isEnabled
        }
    }
    
    suspend fun setDarkModeEnabled(isEnabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[darkModeKey] = isEnabled
        }
    }
}
