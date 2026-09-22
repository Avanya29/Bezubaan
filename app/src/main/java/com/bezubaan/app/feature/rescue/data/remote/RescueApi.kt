package com.bezubaan.app.feature.rescue.data.remote

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface RescueApi {
    @GET("api/rescues")
    suspend fun getNearbyRescues(
        @Query("lat") lat: Double,
        @Query("lng") lng: Double
    ): retrofit2.Response<List<RescueDto>>

    @POST("api/rescues")
    suspend fun reportRescue(
        @Body request: CreateRescueRequest
    ): retrofit2.Response<RescueDto>
}
