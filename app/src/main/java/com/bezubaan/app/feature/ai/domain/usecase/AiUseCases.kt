package com.bezubaan.app.feature.ai.domain.usecase

import com.bezubaan.app.core.common.Resource
import com.bezubaan.app.feature.ai.domain.model.AiAnalysis
import com.bezubaan.app.feature.ai.domain.model.ChatMessage
import com.bezubaan.app.feature.ai.domain.repository.AiRepository
import javax.inject.Inject

class AnalyzeImageUseCase @Inject constructor(
    private val repository: AiRepository
) {
    suspend operator fun invoke(
        imageUrl: String,
        latitude: Double? = null,
        longitude: Double? = null,
        address: String? = null
    ): Resource<ChatMessage> {
        return repository.analyzeImage(imageUrl, latitude, longitude, address)
    }
}

class SendChatMessageUseCase @Inject constructor(
    private val repository: AiRepository
) {
    suspend operator fun invoke(
        text: String,
        latitude: Double? = null,
        longitude: Double? = null,
        address: String? = null
    ): Resource<ChatMessage> {
        return repository.sendChatMessage(text, latitude, longitude, address)
    }
}

data class AiUseCases(
    val analyzeImage: AnalyzeImageUseCase,
    val sendChatMessage: SendChatMessageUseCase
)
