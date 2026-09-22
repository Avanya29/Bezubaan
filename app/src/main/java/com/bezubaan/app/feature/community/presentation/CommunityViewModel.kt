package com.bezubaan.app.feature.community.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bezubaan.app.feature.community.domain.model.Post
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class CommunityViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(CommunityUiState())
    val uiState: StateFlow<CommunityUiState> = _uiState.asStateFlow()

    init {
        loadPosts()
    }

    private fun loadPosts() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            delay(1000)

            val mockPosts = listOf(
                Post(
                    id = UUID.randomUUID().toString(),
                    authorName = "Priya Sharma",
                    content = "Just rescued a puppy near MG Road! He's safe now at the shelter. 🐾",
                    imageUrl = null,
                    likes = 24,
                    comments = 7
                ),
                Post(
                    id = UUID.randomUUID().toString(),
                    authorName = "Arjun Patel",
                    content = "Found an injured cat near Koramangala. Called the vet — she's getting treatment now. Please share if you know anyone who can foster.",
                    imageUrl = "placeholder_url",
                    likes = 18,
                    comments = 12
                ),
                Post(
                    id = UUID.randomUUID().toString(),
                    authorName = "Riya Mehta",
                    content = "Our community drive fed 50 stray dogs today! Thank you to all the volunteers who showed up! 🙏",
                    imageUrl = "placeholder_url",
                    likes = 45,
                    comments = 15
                )
            )

            _uiState.update {
                it.copy(
                    isLoading = false,
                    posts = mockPosts
                )
            }
        }
    }

    fun createPost(content: String, imageUrl: String? = null) {
        viewModelScope.launch {
            val newPost = Post(
                id = UUID.randomUUID().toString(),
                authorName = "You",
                content = content,
                imageUrl = imageUrl,
                likes = 0,
                comments = 0
            )

            _uiState.update { currentState ->
                currentState.copy(
                    posts = listOf(newPost) + currentState.posts
                )
            }
        }
    }
}
