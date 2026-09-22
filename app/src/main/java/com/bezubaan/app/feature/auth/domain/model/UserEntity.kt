package com.bezubaan.app.feature.auth.domain.model

/**
 * Domain model for a User. This is distinct from the Room entity
 * (feature.auth.data.local.UserEntity) — the domain model represents
 * the user in the domain/presentation layers while the Room entity
 * is strictly for local caching.
 */
data class User(
    val id: String,
    val name: String,
    val email: String,
    val role: String = "CITIZEN",
    val phone: String? = null,
    val avatarUrl: String? = null
)

fun com.bezubaan.app.feature.auth.data.remote.UserDto.toUser(): User {
    return User(
        id = id,
        name = name,
        email = email
    )
}
