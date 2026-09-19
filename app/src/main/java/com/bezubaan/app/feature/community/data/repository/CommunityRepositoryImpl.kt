package com.bezubaan.app.feature.community.data.repository

import com.bezubaan.app.core.common.Resource
import com.bezubaan.app.feature.community.data.remote.CommunityApi
import com.bezubaan.app.feature.community.domain.model.Post
import com.bezubaan.app.feature.community.domain.repository.CommunityRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CommunityRepositoryImpl @Inject constructor(
    private val api: CommunityApi
) : CommunityRepository {

    override fun getPosts(): Flow<Resource<List<Post>>> = flow {
        emit(Resource.Loading())
        delay(1000)
        // Mocking logic
        val mockPosts = listOf(
            Post("1", "Alice", "Hello World!", null, 10, 2),
            Post("2", "Bob", "This is Bezubaan.", null, 25, 5)
        )
        emit(Resource.Success(mockPosts))
    }

    override fun createPost(content: String, imageUrl: String?): Flow<Resource<Post>> = flow {
        emit(Resource.Loading())
        delay(1000)
        val newPost = Post(
            id = System.currentTimeMillis().toString(),
            authorName = "Current User",
            content = content,
            imageUrl = imageUrl,
            likes = 0,
            comments = 0
        )
        emit(Resource.Success(newPost))
    }
}
