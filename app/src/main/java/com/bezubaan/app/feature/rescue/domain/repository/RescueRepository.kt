package com.bezubaan.app.feature.rescue.domain.repository

import com.bezubaan.app.core.common.Resource
import com.bezubaan.app.feature.rescue.domain.model.RescueCase
import kotlinx.coroutines.flow.Flow

interface RescueRepository {
    fun getNearbyRescues(lat: Double, lng: Double): Flow<Resource<List<RescueCase>>>
    fun reportRescue(
        description: String,
        lat: Double,
        lng: Double,
        address: String? = null,
        animalSpecies: String,
        animalBreed: String? = null,
        animalSex: String? = null,
        animalAge: String? = null,
        animalColor: String? = null,
        animalSize: String? = null
    ): Flow<Resource<RescueCase>>
}
