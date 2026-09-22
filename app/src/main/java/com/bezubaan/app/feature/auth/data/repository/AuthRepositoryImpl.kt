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
        return try {
            val response = api.login(request)
            if (response.isSuccessful && response.body() != null) {
                val token = response.body()!!.access_token
                tokenManager.saveToken(token)
                
                // Now fetch user profile
                val profileResponse = api.getProfile()
                if (profileResponse.isSuccessful && profileResponse.body() != null) {
                    val userDto = profileResponse.body()!!
                    Resource.Success(
                        User(
                            id = userDto.id,
                            name = userDto.name,
                            email = userDto.email
                        )
                    )
                } else {
                    Resource.Error(profileResponse.message() ?: "Failed to fetch user profile")
                }
            } else {
                Resource.Error(response.message() ?: "Login failed")
            }
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "An unexpected error occurred")
        }
    }

    override suspend fun register(request: RegisterRequest): Resource<User> {
        return try {
            val response = api.register(request)
            if (response.isSuccessful && response.body() != null) {
                val userDto = response.body()!!
                Resource.Success(
                    User(
                        id = userDto.id,
                        name = userDto.name,
                        email = userDto.email
                    )
                )
            } else {
                Resource.Error(response.message() ?: "Registration failed")
            }
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "An unexpected error occurred")
        }
    }

    override suspend fun logout() {
        tokenManager.clearToken()
    }
}
