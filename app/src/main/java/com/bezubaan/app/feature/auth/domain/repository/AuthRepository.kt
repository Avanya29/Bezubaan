package com.bezubaan.app.feature.auth.domain.repository

import com.bezubaan.app.core.common.Resource
import com.bezubaan.app.feature.auth.data.remote.LoginRequest
import com.bezubaan.app.feature.auth.data.remote.RegisterRequest
import com.bezubaan.app.feature.auth.domain.model.User

interface AuthRepository {
    suspend fun login(request: LoginRequest): Resource<User>
    suspend fun register(request: RegisterRequest): Resource<User>
    suspend fun googleSignIn(idToken: String): Resource<User>
    suspend fun getProfile(): Resource<User>
    suspend fun getStats(): Resource<com.bezubaan.app.feature.auth.domain.model.UserStats>
    suspend fun updateProfile(request: com.bezubaan.app.feature.auth.data.remote.UpdateProfileRequest): Resource<User>
    suspend fun logout()
}
