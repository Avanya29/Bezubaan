package com.bezubaan.app.feature.ai.data.repository

import com.bezubaan.app.core.common.Resource
import com.bezubaan.app.feature.ai.data.remote.AiApi
import com.bezubaan.app.feature.ai.domain.model.AiAnalysis
import com.bezubaan.app.feature.ai.domain.model.ChatMessage
import com.bezubaan.app.feature.ai.domain.repository.AiRepository
import kotlinx.coroutines.delay
import javax.inject.Inject
import java.util.UUID

class AiRepositoryImpl @Inject constructor(
    private val api: AiApi
) : AiRepository {
    override suspend fun analyzeImage(imageUrl: String): Resource<AiAnalysis> {
        return try {
            // Mock backend delay
            delay(1500)
            
            // Return mock successful response for now
            val mockAnalysis = AiAnalysis(
                breed = "Unknown (Mock)",
                urgency = "Low",
                firstAid = "Keep the animal warm and quiet."
            )
            Resource.Success(mockAnalysis)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An unknown error occurred")
        }
    }

    override suspend fun sendChatMessage(text: String): Resource<ChatMessage> {
        return try {
            // Mock backend delay
            delay(1000)
            
            // Return mock successful response for now
            val mockResponse = ChatMessage(
                id = UUID.randomUUID().toString(),
                text = "This is a mock response from the AI backend.",
                isUser = false,
                timestamp = System.currentTimeMillis()
            )
            Resource.Success(mockResponse)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An unknown error occurred")
        }
    }
}
