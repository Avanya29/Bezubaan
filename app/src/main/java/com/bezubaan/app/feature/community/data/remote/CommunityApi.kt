package com.bezubaan.app.feature.community.data.remote

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface CommunityApi {
    @GET("community/posts")
    suspend fun getPosts(): List<PostDto>

    @POST("community/posts")
    suspend fun createPost(@Body request: CreatePostRequest): PostDto
}
