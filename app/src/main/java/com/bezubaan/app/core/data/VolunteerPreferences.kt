package com.bezubaan.app.core.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VolunteerPreferences @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    private val volunteerModeKey = booleanPreferencesKey("volunteer_mode_active")

    val isVolunteerModeActive: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[volunteerModeKey] ?: false
    }

    suspend fun setVolunteerModeActive(isActive: Boolean) {
        dataStore.edit { preferences ->
            preferences[volunteerModeKey] = isActive
        }
    }
}
