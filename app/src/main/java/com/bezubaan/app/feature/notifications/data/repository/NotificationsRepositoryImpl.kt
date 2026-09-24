package com.bezubaan.app.feature.notifications.data.repository

import com.bezubaan.app.core.common.Resource
import com.bezubaan.app.feature.notifications.data.remote.NotificationDto
import com.bezubaan.app.feature.notifications.data.remote.NotificationsApi
import com.bezubaan.app.feature.notifications.domain.model.Notification
import com.bezubaan.app.feature.notifications.domain.model.toNotificationType
import com.bezubaan.app.feature.notifications.domain.repository.NotificationsRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationsRepositoryImpl @Inject constructor(
    private val api: NotificationsApi
) : NotificationsRepository {
    
    override suspend fun getUserNotifications(): Resource<List<Notification>> {
        return try {
            val response = api.getUserNotifications()
            if (response.isSuccessful) {
                val dtoList = response.body() ?: emptyList()
                val notifications = dtoList.map { it.toNotification() }
                Resource.Success(notifications)
            } else {
                Resource.Error("Failed to fetch notifications: ${response.message()}")
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An unknown error occurred")
        }
    }

    override suspend fun markAsRead(id: String): Resource<Unit> {
        return try {
            val response = api.markAsRead(id)
            if (response.isSuccessful) {
                Resource.Success(Unit)
            } else {
                Resource.Error("Failed to mark as read: ${response.message()}")
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An unknown error occurred")
        }
    }

    private fun NotificationDto.toNotification(): Notification {
        return Notification(
            id = id,
            type = type.toNotificationType(),
            title = title,
            message = message,
            data = data,
            isRead = isRead,
            createdAt = createdAt
        )
    }
}
