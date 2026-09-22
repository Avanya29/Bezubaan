package com.bezubaan.app.feature.rescue.domain.model

data class AnimalDetails(
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

data class RescueCase(
    val id: String,
    val reporterId: String? = null,
    val description: String,
    val latitude: Double,
    val longitude: Double,
    val address: String? = null,
    val status: String,
    val createdAt: String? = null,
    val imageUrl: String? = null,
    val animal: AnimalDetails? = null
)
