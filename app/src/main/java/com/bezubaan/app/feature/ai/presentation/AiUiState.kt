package com.bezubaan.app.feature.ai.presentation

import com.bezubaan.app.feature.ai.domain.model.ChatMessage

data class AiUiState(
    val isLoading: Boolean = false,
    val result: String? = null,
    val chatMessages: List<ChatMessage> = emptyList()
)
