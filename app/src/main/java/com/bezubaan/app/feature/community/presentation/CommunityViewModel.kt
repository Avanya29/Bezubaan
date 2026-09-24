package com.bezubaan.app.feature.community.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bezubaan.app.core.common.Resource
import com.bezubaan.app.feature.community.domain.usecase.CommunityUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommunityViewModel @Inject constructor(
    private val useCases: CommunityUseCases
) : ViewModel() {

    private val _uiState = MutableStateFlow(CommunityUiState())
    val uiState: StateFlow<CommunityUiState> = _uiState.asStateFlow()

    init {
        loadPosts()
    }

    fun loadPosts() {
        viewModelScope.launch {
            useCases.getFeed().collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        _uiState.update { it.copy(isLoading = true, error = null) }
                    }
                    is Resource.Success -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                posts = resource.data ?: emptyList()
                            )
                        }
                    }
                    is Resource.Error -> {
                        _uiState.update {
                            it.copy(isLoading = false, error = resource.message)
                        }
                    }
                }
            }
        }
    }

    fun createPost(content: String, imageUrl: String? = null) {
        if (content.isBlank()) return
        viewModelScope.launch {
            useCases.createPost(content, imageUrl).collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        _uiState.update { it.copy(isCreatingPost = true, createPostError = null) }
                    }
                    is Resource.Success -> {
                        val newPost = resource.data
                        _uiState.update { currentState ->
                            currentState.copy(
                                isCreatingPost = false,
                                postCreatedSuccess = true,
                                posts = if (newPost != null) listOf(newPost) + currentState.posts else currentState.posts
                            )
                        }
                    }
                    is Resource.Error -> {
                        _uiState.update {
                            it.copy(
                                isCreatingPost = false,
                                createPostError = resource.message
                            )
                        }
                    }
                }
            }
        }
    }

    fun clearPostCreatedFlag() {
        _uiState.update { it.copy(postCreatedSuccess = false) }
    }

    fun toggleLike(postId: String) {
        val currentPosts = _uiState.value.posts
        val postIndex = currentPosts.indexOfFirst { it.id == postId }
        if (postIndex == -1) return

        val post = currentPosts[postIndex]
        val isLiked = post.isLikedByMe
        val newLikes = if (isLiked) post.likes - 1 else post.likes + 1

        // Optimistic UI update
        val updatedPost = post.copy(
            isLikedByMe = !isLiked,
            likes = newLikes
        )
        val newPosts = currentPosts.toMutableList().apply { set(postIndex, updatedPost) }
        _uiState.update { it.copy(posts = newPosts) }

        viewModelScope.launch {
            if (isLiked) {
                useCases.unlikePost(postId).collect { /* Handle error if needed and rollback */ }
            } else {
                useCases.likePost(postId).collect { /* Handle error if needed and rollback */ }
            }
        }
    }

    fun fetchComments(postId: String) {
        viewModelScope.launch {
            useCases.getComments(postId).collect { resource ->
                when (resource) {
                    is Resource.Loading -> _uiState.update { it.copy(isLoadingComments = true, commentsError = null) }
                    is Resource.Success -> _uiState.update {
                        it.copy(isLoadingComments = false, currentComments = resource.data ?: emptyList())
                    }
                    is Resource.Error -> _uiState.update {
                        it.copy(isLoadingComments = false, commentsError = resource.message)
                    }
                }
            }
        }
    }

    fun createComment(postId: String, content: String) {
        if (content.isBlank()) return
        viewModelScope.launch {
            useCases.createComment(postId, content).collect { resource ->
                when (resource) {
                    is Resource.Loading -> _uiState.update { it.copy(isCreatingComment = true) }
                    is Resource.Success -> {
                        val newComment = resource.data
                        _uiState.update { state ->
                            state.copy(
                                isCreatingComment = false,
                                currentComments = if (newComment != null) listOf(newComment) + state.currentComments else state.currentComments
                            )
                        }
                        // Update comment count in feed
                        val postIndex = _uiState.value.posts.indexOfFirst { it.id == postId }
                        if (postIndex != -1) {
                            val post = _uiState.value.posts[postIndex]
                            val newPosts = _uiState.value.posts.toMutableList().apply {
                                set(postIndex, post.copy(comments = post.comments + 1))
                            }
                            _uiState.update { it.copy(posts = newPosts) }
                        }
                    }
                    is Resource.Error -> _uiState.update { it.copy(isCreatingComment = false, commentsError = resource.message) }
                }
            }
        }
    }
}
