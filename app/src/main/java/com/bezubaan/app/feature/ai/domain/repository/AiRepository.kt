package com.bezubaan.app.feature.ai.domain.repository

import com.bezubaan.app.core.common.Resource
import com.bezubaan.app.feature.ai.domain.model.AiAnalysis
import com.bezubaan.app.feature.ai.domain.model.ChatMessage

interface AiRepository {
    suspend fun analyzeImage(imageUrl: String): Resource<AiAnalysis>
    suspend fun sendChatMessage(text: String): Resource<ChatMessage>
}
