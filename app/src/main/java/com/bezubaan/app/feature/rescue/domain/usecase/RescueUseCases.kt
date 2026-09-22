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
        title: String,
        description: String,
        location: String,
        lat: Double,
        lng: Double,
        urgency: String
    ): Flow<Resource<RescueCase>> {
        return repository.reportRescue(title, description, location, lat, lng, urgency)
    }
}

data class RescueUseCases(
    val getNearbyRescues: GetNearbyRescuesUseCase,
    val reportRescue: ReportRescueUseCase
)
