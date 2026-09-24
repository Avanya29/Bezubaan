package com.bezubaan.app.feature.ai.data.remote

import retrofit2.http.Body
import retrofit2.http.POST

interface AiApi {
    @POST("ai/analyze")
    suspend fun analyzeImage(@Body request: ImageAnalysisRequest): ChatMessageDto

    @POST("ai/chat")
    suspend fun sendChatMessage(@Body request: ChatRequest): ChatMessageDto
}
