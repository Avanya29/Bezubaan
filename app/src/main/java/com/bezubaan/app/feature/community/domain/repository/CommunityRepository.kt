package com.bezubaan.app.feature.community.domain.repository

import com.bezubaan.app.core.common.Resource
import com.bezubaan.app.feature.community.domain.model.Post
import kotlinx.coroutines.flow.Flow

interface CommunityRepository {
    fun getPosts(): Flow<Resource<List<Post>>>
    fun createPost(content: String, imageUrl: String?): Flow<Resource<Post>>
}
