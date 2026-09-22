package com.bezubaan.app.feature.rescue.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rescue_cases")
data class RescueCaseEntity(
    @PrimaryKey val id: String,
    val title: String,
    val description: String,
    val location: String,
    val status: String,
    val urgency: String,
    val isPendingSync: Boolean = false // For offline queue
)

fun RescueCaseEntity.toRescueCase(): com.bezubaan.app.feature.rescue.domain.model.RescueCase {
    return com.bezubaan.app.feature.rescue.domain.model.RescueCase(
        id = id,
        title = title,
        description = description,
        location = location,
        status = status,
        urgency = urgency
    )
}
