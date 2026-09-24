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
import com.bezubaan.app.feature.ai.domain.repository.AiRepository
import com.bezubaan.app.core.common.Resource

@HiltViewModel
class AiViewModel @Inject constructor(
    private val repository: AiRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AiUiState())
    val uiState: StateFlow<AiUiState> = _uiState.asStateFlow()

    fun analyzeImage(localUri: String, base64Data: String) {
        val userMessage = ChatMessage(
            id = UUID.randomUUID().toString(),
            text = "Please analyze this image.",
            isUser = true,
            timestamp = System.currentTimeMillis(),
            imageUrl = localUri
        )
        
        _uiState.update { state ->
            state.copy(
                chatMessages = state.chatMessages + userMessage,
                isLoading = true,
                result = null
            )
        }
        
        viewModelScope.launch {
            val result = repository.analyzeImage(base64Data)
            when (result) {
                is Resource.Success -> {
                    val aiReply = result.data
                    if (aiReply != null) {
                        _uiState.update { state ->
                            state.copy(
                                chatMessages = state.chatMessages + aiReply,
                                isLoading = false
                            )
                        }
                    }
                }
                is Resource.Error -> {
                    val errorReply = ChatMessage(
                        id = UUID.randomUUID().toString(),
                        text = "Error: ${result.message}",
                        isUser = false,
                        timestamp = System.currentTimeMillis()
                    )
                    _uiState.update { state ->
                        state.copy(
                            chatMessages = state.chatMessages + errorReply,
                            isLoading = false
                        )
                    }
                }
                is Resource.Loading -> {}
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
            val result = repository.sendChatMessage(text)
            when (result) {
                is Resource.Success -> {
                    val aiReply = result.data
                    if (aiReply != null) {
                        _uiState.update { state ->
                            state.copy(
                                chatMessages = state.chatMessages + aiReply,
                                isLoading = false
                            )
                        }
                    }
                }
                is Resource.Error -> {
                    val errorReply = ChatMessage(
                        id = UUID.randomUUID().toString(),
                        text = "Error: ${result.message}",
                        isUser = false,
                        timestamp = System.currentTimeMillis()
                    )
                    _uiState.update { state ->
                        state.copy(
                            chatMessages = state.chatMessages + errorReply,
                            isLoading = false
                        )
                    }
                }
                is Resource.Loading -> {}
            }
        }
    }
}
