package com.bezubaan.app.feature.community.domain.usecase

import com.bezubaan.app.core.common.Resource
import com.bezubaan.app.feature.community.domain.model.Post
import com.bezubaan.app.feature.community.domain.repository.CommunityRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPostsUseCase @Inject constructor(
    private val repository: CommunityRepository
) {
    operator fun invoke(): Flow<Resource<List<Post>>> {
        return repository.getPosts()
    }
}

class CreatePostUseCase @Inject constructor(
    private val repository: CommunityRepository
) {
    operator fun invoke(content: String, imageUrl: String? = null): Flow<Resource<Post>> {
        return repository.createPost(content, imageUrl)
    }
}

data class CommunityUseCases(
    val getPosts: GetPostsUseCase,
    val createPost: CreatePostUseCase
)
