package com.bezubaan.app.feature.notifications.domain.model

data class Notification(
    val id: String,
    val type: NotificationType,
    val title: String,
    val message: String,
    val data: Map<String, String>?,
    val isRead: Boolean,
    val createdAt: String
)

enum class NotificationType {
    RESCUE_ALERT,
    MEDICAL_UPDATE,
    ADOPTION_MATCH,
    FOSTER_CARE,
    COMMUNITY_DISCUSSION,
    DONATION,
    SYSTEM_UPDATE,
    UNKNOWN
}

fun String.toNotificationType(): NotificationType {
    return try {
        NotificationType.valueOf(this.uppercase())
    } catch (e: Exception) {
        NotificationType.UNKNOWN
    }
}
