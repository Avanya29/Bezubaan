package com.bezubaan.app.feature.rescue.domain.repository

import com.bezubaan.app.core.common.Resource
import com.bezubaan.app.feature.rescue.domain.model.RescueCase
import kotlinx.coroutines.flow.Flow

interface RescueRepository {
    fun getNearbyRescues(lat: Double, lng: Double): Flow<Resource<List<RescueCase>>>
    fun reportRescue(
        title: String,
        description: String,
        location: String,
        lat: Double,
        lng: Double,
        urgency: String
    ): Flow<Resource<RescueCase>>
}
