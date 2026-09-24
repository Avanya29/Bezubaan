package com.bezubaan.app.feature.notifications.domain.repository

import com.bezubaan.app.core.common.Resource
import com.bezubaan.app.feature.notifications.domain.model.Notification

interface NotificationsRepository {
    suspend fun getUserNotifications(): Resource<List<Notification>>
    suspend fun markAsRead(id: String): Resource<Unit>
}
