package com.bezubaan.app.feature.rescue.data.remote

import kotlinx.serialization.Serializable

@Serializable
data class RescueDto(
    val id: String,
    val title: String,
    val description: String,
    val location: String,
    val status: String,
    val urgency: String
)

@Serializable
data class RescueReportRequest(
    val title: String,
    val description: String,
    val location: String,
    val lat: Double,
    val lng: Double,
    val urgency: String
)

fun RescueDto.toDomain(): com.bezubaan.app.feature.rescue.domain.model.RescueCase {
    return com.bezubaan.app.feature.rescue.domain.model.RescueCase(
        id = id,
        title = title,
        description = description,
        location = location,
        status = status,
        urgency = urgency
    )
}
