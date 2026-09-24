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
    val avatarUrl: String? = null,
    val address: String? = null
)

fun com.bezubaan.app.feature.auth.data.remote.UserDto.toUser(): User {
    return User(
        id = id,
        name = name,
        email = email,
        role = role ?: "CITIZEN",
        phone = profile?.phoneNumber,
        address = profile?.address
    )
}

data class UserStats(
    val rescues: Int,
    val fostered: Int,
    val funded: Double
)

fun com.bezubaan.app.feature.auth.data.remote.UserStatsDto.toUserStats(): UserStats {
    return UserStats(
        rescues = rescues,
        fostered = fostered,
        funded = funded
    )
}
