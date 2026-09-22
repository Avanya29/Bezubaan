package com.bezubaan.app.feature.rescue.domain.usecase

import com.bezubaan.app.core.common.Resource
import com.bezubaan.app.feature.rescue.domain.model.RescueCase
import com.bezubaan.app.feature.rescue.domain.repository.RescueRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNearbyRescuesUseCase @Inject constructor(
    private val repository: RescueRepository
) {
    operator fun invoke(lat: Double, lng: Double): Flow<Resource<List<RescueCase>>> {
        return repository.getNearbyRescues(lat, lng)
    }
}

class ReportRescueUseCase @Inject constructor(
    private val repository: RescueRepository
) {
    operator fun invoke(
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
    ): Flow<Resource<RescueCase>> {
        return repository.reportRescue(
            description = description,
            lat = lat,
            lng = lng,
            address = address,
            animalSpecies = animalSpecies,
            animalBreed = animalBreed,
            animalSex = animalSex,
            animalAge = animalAge,
            animalColor = animalColor,
            animalSize = animalSize
        )
    }
}

data class RescueUseCases(
    val getNearbyRescues: GetNearbyRescuesUseCase,
    val reportRescue: ReportRescueUseCase
)
