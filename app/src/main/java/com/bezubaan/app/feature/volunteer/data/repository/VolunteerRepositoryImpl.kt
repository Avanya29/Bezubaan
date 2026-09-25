package com.bezubaan.app.feature.volunteer.data.repository

import com.bezubaan.app.core.common.Resource
import com.bezubaan.app.feature.volunteer.data.remote.AvailabilityRequest
import com.bezubaan.app.feature.volunteer.data.remote.VolunteerApi
import com.bezubaan.app.feature.volunteer.data.remote.VolunteerLocationRequest
import com.bezubaan.app.feature.volunteer.domain.repository.VolunteerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class VolunteerRepositoryImpl @Inject constructor(
    private val api: VolunteerApi
) : VolunteerRepository {
    override suspend fun updateAvailability(isAvailable: Boolean): Resource<Unit> {
        return withContext(Dispatchers.IO) {
            try {
                val response = api.updateAvailability(AvailabilityRequest(isAvailable))
                if (response.isSuccessful) {
                    Resource.Success(Unit)
                } else {
                    Resource.Error(response.message())
                }
            } catch (e: Exception) {
                Resource.Error(e.localizedMessage ?: "An unexpected error occurred")
            }
        }
    }

    override suspend fun updateLocation(latitude: Double, longitude: Double): Resource<Unit> {
        return withContext(Dispatchers.IO) {
            try {
                val response = api.updateLocation(VolunteerLocationRequest(latitude, longitude))
                if (response.isSuccessful) {
                    Resource.Success(Unit)
                } else {
                    Resource.Error(response.message())
                }
            } catch (e: Exception) {
                Resource.Error(e.localizedMessage ?: "An unexpected error occurred")
            }
        }
    }

    override suspend fun acceptAssignment(assignmentId: String): Resource<Unit> {
        return withContext(Dispatchers.IO) {
            try {
                val response = api.acceptAssignment(assignmentId)
                if (response.isSuccessful) {
                    Resource.Success(Unit)
                } else {
                    Resource.Error(response.message())
                }
            } catch (e: Exception) {
                Resource.Error(e.localizedMessage ?: "An unexpected error occurred")
            }
        }
    }

    override suspend fun rejectAssignment(assignmentId: String, reason: String): Resource<Unit> {
        return withContext(Dispatchers.IO) {
            try {
                val response = api.rejectAssignment(assignmentId, mapOf("reason" to reason))
                if (response.isSuccessful) {
                    Resource.Success(Unit)
                } else {
                    Resource.Error(response.message())
                }
            } catch (e: Exception) {
                Resource.Error(e.localizedMessage ?: "An unexpected error occurred")
            }
        }
    }
}
