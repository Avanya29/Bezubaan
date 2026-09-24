package com.bezubaan.app.feature.notifications.data.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path

interface NotificationsApi {
    @GET("notifications")
    suspend fun getUserNotifications(): Response<List<NotificationDto>>

    @PATCH("notifications/{id}/read")
    suspend fun markAsRead(@Path("id") id: String): Response<Unit>
}
