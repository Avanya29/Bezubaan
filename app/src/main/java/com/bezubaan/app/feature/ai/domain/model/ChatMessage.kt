package com.bezubaan.app.feature.ai.domain.model

data class ChatMessage(
    val id: String,
    val text: String,
    val isUser: Boolean,
    val timestamp: Long
)
