package com.bezubaan.app.feature.rescue.data.remote

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface RescueApi {
    @GET("rescues/nearby")
    suspend fun getNearbyRescues(
        @Query("lat") lat: Double,
        @Query("lng") lng: Double
    ): List<RescueDto>

    @POST("rescues")
    suspend fun reportRescue(
        @Body request: RescueReportRequest
    ): RescueDto
}
