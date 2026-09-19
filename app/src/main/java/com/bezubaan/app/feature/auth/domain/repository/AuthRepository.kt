package com.bezubaan.app.feature.auth.domain.repository

import com.bezubaan.app.core.common.Resource
import com.bezubaan.app.feature.auth.data.remote.LoginRequest
import com.bezubaan.app.feature.auth.data.remote.RegisterRequest
import com.bezubaan.app.feature.auth.domain.model.User

interface AuthRepository {
    suspend fun login(request: LoginRequest): Resource<User>
    suspend fun register(request: RegisterRequest): Resource<User>
    suspend fun logout()
}
