package com.bezubaan.app.feature.auth.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val id: String,
    val name: String,
    val email: String,
    val role: String,
    val phone: String? = null,
    val avatarUrl: String? = null
)
