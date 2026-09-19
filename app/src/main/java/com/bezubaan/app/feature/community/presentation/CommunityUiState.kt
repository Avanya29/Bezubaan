package com.bezubaan.app.feature.community.presentation

import com.bezubaan.app.feature.community.domain.model.Post

data class CommunityUiState(
    val isLoading: Boolean = false,
    val posts: List<Post> = emptyList(),
    val error: String? = null
)
