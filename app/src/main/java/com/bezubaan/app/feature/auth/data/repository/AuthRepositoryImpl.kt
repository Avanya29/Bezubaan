package com.bezubaan.app.feature.auth.data.repository

import com.bezubaan.app.core.common.Resource
import com.bezubaan.app.core.network.TokenManager
import com.bezubaan.app.feature.auth.data.remote.AuthApi
import com.bezubaan.app.feature.auth.data.remote.LoginRequest
import com.bezubaan.app.feature.auth.data.remote.RegisterRequest
import com.bezubaan.app.feature.auth.domain.model.User
import com.bezubaan.app.feature.auth.domain.repository.AuthRepository
import kotlinx.coroutines.delay
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val api: AuthApi,
    private val tokenManager: TokenManager
) : AuthRepository {

    override suspend fun login(request: LoginRequest): Resource<User> {
        // Mock backend implementation
        delay(1500)
        tokenManager.saveToken("mock_token_12345")
        return Resource.Success(
            User(
                id = "1",
                name = "Test User",
                email = request.email
            )
        )
    }

    override suspend fun register(request: RegisterRequest): Resource<User> {
        // Mock backend implementation
        delay(1500)
        tokenManager.saveToken("mock_token_12345")
        return Resource.Success(
            User(
                id = "1",
                name = request.name,
                email = request.email
            )
        )
    }

    override suspend fun logout() {
        tokenManager.clearToken()
    }
}
