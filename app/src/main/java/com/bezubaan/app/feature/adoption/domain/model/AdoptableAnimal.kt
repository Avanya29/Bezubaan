package com.bezubaan.app.feature.adoption.domain.model

data class AdoptableAnimal(
    val id: String,
    val name: String,
    val species: String,
    val breed: String,
    val age: String,
    val description: String,
    val imageUrl: String? = null,
    val location: String,
    val isVaccinated: Boolean = false,
    val isNeutered: Boolean = false
)
