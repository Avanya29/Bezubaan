package com.bezubaan.app.feature.community.domain.model

data class Post(
    val id: String,
    val authorName: String,
    val content: String,
    val imageUrl: String?,
    val likes: Int,
    val comments: Int
)
