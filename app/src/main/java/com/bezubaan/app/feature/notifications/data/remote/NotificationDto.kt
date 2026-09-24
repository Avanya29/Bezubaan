package com.bezubaan.app.feature.notifications.data.remote

import com.google.gson.annotations.SerializedName

data class NotificationDto(
    val id: String,
    val type: String,
    val title: String,
    val message: String,
    val data: Map<String, String>?,
    val isRead: Boolean,
    val createdAt: String
)

data class NotificationResponse(
    val notifications: List<NotificationDto>
)
