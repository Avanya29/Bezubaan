package com.bezubaan.app.feature.auth.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class AuthRequestDto(
    val email: String,
    val password: String
)

@Serializable
data class AuthResponseDto(
    val access_token: String,
    val user: UserDto? = null
)

@Serializable
data class UserDto(
    val id: String,
    val email: String,
    val role: String? = null
)
