package com.bezubaan.app.feature.community.presentation

import com.bezubaan.app.feature.community.domain.model.Post

data class CommunityUiState(
    val isLoading: Boolean = false,
    val posts: List<Post> = emptyList(),
    val error: String? = null,
    val isCreatingPost: Boolean = false,
    val postCreatedSuccess: Boolean = false,
    val createPostError: String? = null,
    val isLoadingComments: Boolean = false,
    val currentComments: List<com.bezubaan.app.feature.community.domain.model.Comment> = emptyList(),
    val commentsError: String? = null,
    val isCreatingComment: Boolean = false
)
