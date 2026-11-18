package com.bezubaan.app.feature.ai.domain.repository

import com.bezubaan.app.core.common.Resource
import com.bezubaan.app.feature.ai.domain.model.ChatMessage

interface AiRepository {
    suspend fun analyzeImage(
        imageUrl: String,
        latitude: Double? = null,
        longitude: Double? = null,
        address: String? = null
    ): Resource<ChatMessage>

    suspend fun sendChatMessage(
        text: String,
        latitude: Double? = null,
        longitude: Double? = null,
        address: String? = null
    ): Resource<ChatMessage>
}
