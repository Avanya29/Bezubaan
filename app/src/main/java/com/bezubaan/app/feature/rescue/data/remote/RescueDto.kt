package com.bezubaan.app.feature.rescue.data.remote

import kotlinx.serialization.Serializable
import com.bezubaan.app.feature.rescue.domain.model.RescueCase
import com.bezubaan.app.feature.rescue.domain.model.AnimalDetails

@Serializable
data class AnimalDto(
    val species: String,
    val breed: String? = null,
    val sex: String? = null,
    val approximateAge: String? = null,
    val color: String? = null,
    val size: String? = null,
    val identifyingMarks: String? = null,
    val name: String? = null,
    val description: String? = null
)

@Serializable
data class RescueDto(
    val id: String,
    val reporterId: String? = null,
    val status: String,
    val description: String,
    val latitude: Double,
    val longitude: Double,
    val address: String? = null,
    val createdAt: String? = null,
    val animal: AnimalDto? = null
)

@Serializable
data class CreateRescueRequest(
    val description: String,
    val latitude: Double,
    val longitude: Double,
    val address: String? = null,
    val animal: AnimalDto? = null
)

fun RescueDto.toDomain(): RescueCase {
    return RescueCase(
        id = id,
        reporterId = reporterId,
        status = status,
        description = description,
        latitude = latitude,
        longitude = longitude,
        address = address,
        createdAt = createdAt,
        animal = animal?.let {
            AnimalDetails(
                species = it.species,
                breed = it.breed,
                sex = it.sex,
                approximateAge = it.approximateAge,
                color = it.color,
                size = it.size,
                identifyingMarks = it.identifyingMarks,
                name = it.name,
                description = it.description
            )
        }
    )
}
