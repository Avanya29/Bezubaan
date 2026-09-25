package com.bezubaan.app.feature.volunteer.data.remote

import retrofit2.http.Body
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

data class AvailabilityRequest(
    val isAvailable: Boolean
)

data class VolunteerLocationRequest(
    val latitude: Double,
    val longitude: Double
)

interface VolunteerApi {
    @PATCH("api/volunteers/me/availability")
    suspend fun updateAvailability(
        @Body request: AvailabilityRequest
    ): retrofit2.Response<Unit>

    @PATCH("api/volunteers/me/location")
    suspend fun updateLocation(
        @Body request: VolunteerLocationRequest
    ): retrofit2.Response<Unit>

    @POST("api/assignments/{id}/accept")
    suspend fun acceptAssignment(
        @Path("id") id: String
    ): retrofit2.Response<Unit>

    @POST("api/assignments/{id}/reject")
    suspend fun rejectAssignment(
        @Path("id") id: String,
        @Body request: Map<String, String> = mapOf("reason" to "declined")
    ): retrofit2.Response<Unit>
}
