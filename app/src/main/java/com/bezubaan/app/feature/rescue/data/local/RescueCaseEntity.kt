package com.bezubaan.app.feature.rescue.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rescue_cases")
data class RescueCaseEntity(
    @PrimaryKey val id: String,
    val description: String,
    val latitude: Double,
    val longitude: Double,
    val address: String? = null,
    val status: String,
    val isPendingSync: Boolean = false // For offline queue
)

fun RescueCaseEntity.toRescueCase(): com.bezubaan.app.feature.rescue.domain.model.RescueCase {
    return com.bezubaan.app.feature.rescue.domain.model.RescueCase(
        id = id,
        description = description,
        latitude = latitude,
        longitude = longitude,
        address = address,
        status = status
    )
}
