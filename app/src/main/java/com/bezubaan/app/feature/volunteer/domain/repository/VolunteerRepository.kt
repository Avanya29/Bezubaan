package com.bezubaan.app.feature.volunteer.domain.repository

import com.bezubaan.app.core.common.Resource

interface VolunteerRepository {
    suspend fun updateAvailability(isAvailable: Boolean): Resource<Unit>
    suspend fun updateLocation(latitude: Double, longitude: Double): Resource<Unit>
    suspend fun acceptAssignment(assignmentId: String): Resource<Unit>
    suspend fun rejectAssignment(assignmentId: String, reason: String): Resource<Unit>
}
