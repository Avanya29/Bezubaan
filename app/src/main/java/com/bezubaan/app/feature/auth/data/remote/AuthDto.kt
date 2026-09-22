package com.bezubaan.app.feature.auth.data.remote

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val email: String,
    val password: String
)

@Serializable
data class RegisterRequest(
    val name: String,
    val email: String,
    val password: String
)

@Serializable
data class ForgotPasswordRequest(
    val email: String
)

@Serializable
data class LoginResponse(
    val access_token: String
)

@Serializable
data class UserDto(
    val id: String,
    val name: String,
    val email: String,
    val role: String? = null
)
