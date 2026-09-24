package com.bezubaan.app.core.network

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.bezubaan.app.core.common.Constants
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenManager @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    private val tokenKey = stringPreferencesKey(Constants.JWT_TOKEN_KEY)

    suspend fun saveToken(token: String) {
        dataStore.edit { preferences ->
            preferences[tokenKey] = token
        }
    }

    suspend fun getToken(): String? {
        return dataStore.data.map { preferences ->
            preferences[tokenKey]
        }.first()
    }

    suspend fun clearToken() {
        dataStore.edit { preferences ->
            preferences.remove(tokenKey)
        }
    }

    private val _sessionExpiredFlow = kotlinx.coroutines.flow.MutableSharedFlow<Unit>()
    val sessionExpiredFlow = _sessionExpiredFlow.asSharedFlow()

    suspend fun triggerSessionExpired() {
        _sessionExpiredFlow.emit(Unit)
    }
}
