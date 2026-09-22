package com.bezubaan.app.feature.rescue.data.repository

import com.bezubaan.app.core.common.Resource
import com.bezubaan.app.feature.rescue.data.remote.RescueApi
import com.bezubaan.app.feature.rescue.domain.model.RescueCase
import com.bezubaan.app.feature.rescue.domain.repository.RescueRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RescueRepositoryImpl @Inject constructor(
    private val api: RescueApi
) : RescueRepository {

    override fun getNearbyRescues(lat: Double, lng: Double): Flow<Resource<List<RescueCase>>> = flow {
        emit(Resource.Loading())
        delay(1000)
        val mockData = listOf(
            RescueCase(
                id = "1",
                title = "Injured Dog",
                description = "Dog hit by a car, needs immediate help.",
                location = "Street 1",
                status = "Pending",
                urgency = "High"
            ),
            RescueCase(
                id = "2",
                title = "Stray Cat",
                description = "Cat with broken leg.",
                location = "Park",
                status = "In Progress",
                urgency = "Medium"
            )
        )
        emit(Resource.Success(mockData))
    }

    override fun reportRescue(
        title: String,
        description: String,
        location: String,
        lat: Double,
        lng: Double,
        urgency: String
    ): Flow<Resource<RescueCase>> = flow {
        emit(Resource.Loading())
        delay(1000)
        val mockData = RescueCase(
            id = "3",
            title = title,
            description = description,
            location = location,
            status = "Pending",
            urgency = urgency
        )
        emit(Resource.Success(mockData))
    }
}
