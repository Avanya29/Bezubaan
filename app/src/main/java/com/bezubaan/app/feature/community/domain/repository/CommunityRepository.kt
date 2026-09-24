package com.bezubaan.app.feature.community.domain.repository

import com.bezubaan.app.core.common.Resource
import com.bezubaan.app.feature.community.domain.model.Post
import com.bezubaan.app.feature.community.domain.model.Comment
import kotlinx.coroutines.flow.Flow

interface CommunityRepository {
    fun getFeed(): Flow<Resource<List<Post>>>
    fun createPost(content: String, imageUrl: String?): Flow<Resource<Post>>
    fun likePost(postId: String): Flow<Resource<Unit>>
    fun unlikePost(postId: String): Flow<Resource<Unit>>
    fun getComments(postId: String): Flow<Resource<List<Comment>>>
    fun createComment(postId: String, content: String): Flow<Resource<Comment>>
}
