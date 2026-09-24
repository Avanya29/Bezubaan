package com.bezubaan.app.feature.community.domain.usecase

import com.bezubaan.app.core.common.Resource
import com.bezubaan.app.feature.community.domain.model.Post
import com.bezubaan.app.feature.community.domain.repository.CommunityRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFeedUseCase @Inject constructor(
    private val repository: CommunityRepository
) {
    operator fun invoke(): Flow<Resource<List<Post>>> {
        return repository.getFeed()
    }
}

class CreatePostUseCase @Inject constructor(
    private val repository: CommunityRepository
) {
    operator fun invoke(content: String, imageUrl: String? = null): Flow<Resource<Post>> {
        return repository.createPost(content, imageUrl)
    }
}

class LikePostUseCase @Inject constructor(
    private val repository: CommunityRepository
) {
    operator fun invoke(postId: String): Flow<Resource<Unit>> = repository.likePost(postId)
}

class UnlikePostUseCase @Inject constructor(
    private val repository: CommunityRepository
) {
    operator fun invoke(postId: String): Flow<Resource<Unit>> = repository.unlikePost(postId)
}

class GetCommentsUseCase @Inject constructor(
    private val repository: CommunityRepository
) {
    operator fun invoke(postId: String): Flow<Resource<List<com.bezubaan.app.feature.community.domain.model.Comment>>> = repository.getComments(postId)
}

class CreateCommentUseCase @Inject constructor(
    private val repository: CommunityRepository
) {
    operator fun invoke(postId: String, content: String): Flow<Resource<com.bezubaan.app.feature.community.domain.model.Comment>> = repository.createComment(postId, content)
}

data class CommunityUseCases(
    val getFeed: GetFeedUseCase,
    val createPost: CreatePostUseCase,
    val likePost: LikePostUseCase,
    val unlikePost: UnlikePostUseCase,
    val getComments: GetCommentsUseCase,
    val createComment: CreateCommentUseCase
)
