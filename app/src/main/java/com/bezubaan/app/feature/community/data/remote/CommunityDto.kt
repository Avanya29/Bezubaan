package com.bezubaan.app.feature.community.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import com.bezubaan.app.feature.community.domain.model.Post

// --- Feed Response DTOs (matches backend's getFeed include shape) ---

@Serializable
data class AuthorProfileDto(
    val phoneNumber: String? = null,
    val address: String? = null
)

@Serializable
data class AuthorDto(
    val id: String,
    val profile: AuthorProfileDto? = null
)

@Serializable
data class CountDto(
    val likes: Int = 0,
    val comments: Int = 0
)

@Serializable
data class PostLikeDto(
    val userId: String,
    val postId: String
)

@Serializable
data class CommentDto(
    val id: String,
    val authorId: String,
    val content: String,
    val author: AuthorDto? = null,
    val createdAt: String = ""
)

fun CommentDto.toComment(): com.bezubaan.app.feature.community.domain.model.Comment {
    val displayName = author?.profile?.phoneNumber ?: author?.id?.take(8) ?: "Anonymous"
    return com.bezubaan.app.feature.community.domain.model.Comment(
        id = id,
        authorId = authorId,
        authorName = displayName,
        content = content,
        createdAt = createdAt
    )
}

@Serializable
data class PostDto(
    val id: String,
    val authorId: String,
    val content: String,
    val type: String = "POST",
    val privacy: String = "PUBLIC",
    val author: AuthorDto? = null,
    val likes: List<PostLikeDto> = emptyList(),
    val comments: List<CommentDto> = emptyList(),
    @SerialName("_count")
    val count: CountDto? = null,
    val createdAt: String = ""
)

fun PostDto.toPost(): Post {
    val displayName = author?.profile?.phoneNumber
        ?: author?.id?.take(8)
        ?: "Anonymous"
    return Post(
        id = id,
        authorId = authorId,
        authorName = displayName,
        content = content,
        type = type,
        privacy = privacy,
        likes = count?.likes ?: 0,
        comments = count?.comments ?: 0,
        isLikedByMe = likes.isNotEmpty(),
        createdAt = createdAt
    )
}

// --- Create Post Request (matches backend's CreatePostDto) ---

@Serializable
data class CreatePostRequest(
    val content: String,
    val type: String = "POST",
    val privacy: String = "PUBLIC"
)

@Serializable
data class CreateCommentRequest(
    val content: String
)
