package com.bezubaan.app.feature.ai.presentation

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import com.bezubaan.app.feature.ai.domain.model.ChatMessage
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class AiViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(AiUiState())
    val uiState: StateFlow<AiUiState> = _uiState.asStateFlow()

    fun analyzeImage(uri: Uri?) {
        if (uri == null) return
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, result = null) }
            // Mock delay
            delay(2000)
            _uiState.update {
                it.copy(
                    isLoading = false,
                    result = "Analysis Complete:\n- Breed: Indie Dog\n- Urgency: Low\n- Suggestion: Needs food and water."
                )
            }
        }
    }

    fun sendMessage(text: String) {
        if (text.isBlank()) return
        
        val userMessage = ChatMessage(
            id = UUID.randomUUID().toString(),
            text = text,
            isUser = true,
            timestamp = System.currentTimeMillis()
        )
        
        _uiState.update { state ->
            state.copy(
                chatMessages = state.chatMessages + userMessage,
                isLoading = true
            )
        }
        
        viewModelScope.launch {
            // Mock delay for AI response
            delay(1500)
            val aiReply = ChatMessage(
                id = UUID.randomUUID().toString(),
                text = "This is a simulated AI response to: \"$text\"",
                isUser = false,
                timestamp = System.currentTimeMillis()
            )
            _uiState.update { state ->
                state.copy(
                    chatMessages = state.chatMessages + aiReply,
                    isLoading = false
                )
            }
        }
    }
}
