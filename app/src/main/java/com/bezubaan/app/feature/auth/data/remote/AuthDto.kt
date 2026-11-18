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
data class GoogleSignInRequest(
    val idToken: String
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
data class UserProfileDto(
    val id: String,
    val phoneNumber: String?,
    val address: String?
)

@Serializable
data class UserDto(
    val id: String,
    val name: String? = null,
    val email: String,
    val role: String? = null,
    val profile: UserProfileDto? = null
)

@Serializable
data class UserStatsDto(
    val rescues: Int = 0,
    val fostered: Int = 0,
    val funded: Double = 0.0
)

@Serializable
data class UpdateProfileRequest(
    val phoneNumber: String? = null,
    val address: String? = null
)
