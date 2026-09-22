package com.bezubaan.app.feature.ai.data.remote

import com.bezubaan.app.feature.ai.domain.model.AiAnalysis
import com.bezubaan.app.feature.ai.domain.model.ChatMessage
import kotlinx.serialization.Serializable

@Serializable
data class ImageAnalysisRequest(
    val imageUrl: String
)

@Serializable
data class AiAnalysisDto(
    val breed: String,
    val urgency: String,
    val firstAid: String
) {
    fun toDomain(): AiAnalysis {
        return AiAnalysis(
            breed = breed,
            urgency = urgency,
            firstAid = firstAid
        )
    }
}

@Serializable
data class ChatRequest(
    val text: String
)

@Serializable
data class ChatMessageDto(
    val id: String,
    val text: String,
    val isUser: Boolean,
    val timestamp: Long
) {
    fun toDomain(): ChatMessage {
        return ChatMessage(
            id = id,
            text = text,
            isUser = isUser,
            timestamp = timestamp
        )
    }
}
