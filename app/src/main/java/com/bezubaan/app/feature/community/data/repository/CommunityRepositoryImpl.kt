package com.bezubaan.app.feature.community.data.repository

import com.bezubaan.app.core.common.Resource
import com.bezubaan.app.feature.community.data.remote.CommunityApi
import com.bezubaan.app.feature.community.data.remote.CreatePostRequest
import com.bezubaan.app.feature.community.data.remote.toPost
import com.bezubaan.app.feature.community.data.remote.toComment
import com.bezubaan.app.feature.community.domain.model.Post
import com.bezubaan.app.feature.community.domain.repository.CommunityRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CommunityRepositoryImpl @Inject constructor(
    private val api: CommunityApi
) : CommunityRepository {

    override fun getFeed(): Flow<Resource<List<Post>>> = flow {
        emit(Resource.Loading)
        try {
            val response = api.getFeed()
            emit(Resource.Success(response.map { it.toPost() }))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Failed to load feed"))
        }
    }

    override fun createPost(content: String, imageUrl: String?): Flow<Resource<Post>> = flow {
        emit(Resource.Loading)
        try {
            val request = CreatePostRequest(
                content = content,
                type = "POST",
                privacy = "PUBLIC"
            )
            val response = api.createPost(request)
            emit(Resource.Success(response.toPost()))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Failed to create post"))
        }
    }

    override fun likePost(postId: String): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading)
        try {
            api.likePost(postId)
            emit(Resource.Success(Unit))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Failed to like post"))
        }
    }

    override fun unlikePost(postId: String): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading)
        try {
            api.unlikePost(postId)
            emit(Resource.Success(Unit))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Failed to unlike post"))
        }
    }

    override fun getComments(postId: String): Flow<Resource<List<com.bezubaan.app.feature.community.domain.model.Comment>>> = flow {
        emit(Resource.Loading)
        try {
            val response = api.getPostById(postId)
            emit(Resource.Success(response.comments.map { it.toComment() }))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Failed to fetch comments"))
        }
    }

    override fun createComment(postId: String, content: String): Flow<Resource<com.bezubaan.app.feature.community.domain.model.Comment>> = flow {
        emit(Resource.Loading)
        try {
            val request = com.bezubaan.app.feature.community.data.remote.CreateCommentRequest(content)
            val response = api.createComment(postId, request)
            emit(Resource.Success(response.toComment()))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Failed to add comment"))
        }
    }
}
