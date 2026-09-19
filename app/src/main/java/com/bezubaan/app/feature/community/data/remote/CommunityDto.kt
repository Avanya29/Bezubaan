package com.bezubaan.app.feature.community.data.remote

import kotlinx.serialization.Serializable
import com.bezubaan.app.feature.community.domain.model.Post

@Serializable
data class PostDto(
    val id: String,
    val authorName: String,
    val content: String,
    val imageUrl: String? = null,
    val likes: Int,
    val comments: Int
)

fun PostDto.toPost(): Post {
    return Post(
        id = id,
        authorName = authorName,
        content = content,
        imageUrl = imageUrl,
        likes = likes,
        comments = comments
    )
}

@Serializable
data class CreatePostRequest(
    val content: String,
    val imageUrl: String? = null
)
