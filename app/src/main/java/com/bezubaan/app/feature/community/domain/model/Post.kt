package com.bezubaan.app.feature.community.domain.model

data class Post(
    val id: String,
    val authorId: String,
    val authorName: String,
    val content: String,
    val type: String,
    val privacy: String,
    val likes: Int,
    val comments: Int,
    val isLikedByMe: Boolean,
    val createdAt: String
)

data class Comment(
    val id: String,
    val authorId: String,
    val authorName: String,
    val content: String,
    val createdAt: String
)
