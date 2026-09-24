package com.bezubaan.app.feature.ai.data.repository

import com.bezubaan.app.core.common.Resource
import com.bezubaan.app.feature.ai.data.remote.AiApi
import com.bezubaan.app.feature.ai.data.remote.ImageAnalysisRequest
import com.bezubaan.app.feature.ai.domain.model.ChatMessage
import com.bezubaan.app.feature.ai.domain.repository.AiRepository
import javax.inject.Inject

class AiRepositoryImpl @Inject constructor(
    private val api: AiApi
) : AiRepository {

    override suspend fun analyzeImage(imageUrl: String): Resource<ChatMessage> {
        return try {
            val response = api.analyzeImage(ImageAnalysisRequest(imageUrl))
            Resource.Success(response.toDomain())
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Failed to analyze image")
        }
    }

    override suspend fun sendChatMessage(text: String): Resource<ChatMessage> {
        return try {
            val request = com.bezubaan.app.feature.ai.data.remote.ChatRequest(text)
            val response = api.sendChatMessage(request)
            Resource.Success(response.toDomain())
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An unknown error occurred")
        }
    }
}
