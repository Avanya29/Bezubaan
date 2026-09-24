package com.bezubaan.app.feature.community.data.remote

import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface CommunityApi {
    @GET("community/feed")
    suspend fun getFeed(): List<PostDto>

    @POST("community/posts")
    suspend fun createPost(@Body request: CreatePostRequest): PostDto

    @POST("community/posts/{id}/like")
    suspend fun likePost(@Path("id") id: String)

    @DELETE("community/posts/{id}/like")
    suspend fun unlikePost(@Path("id") id: String)

    @GET("community/posts/{id}")
    suspend fun getPostById(@Path("id") id: String): PostDto

    @POST("community/posts/{id}/comments")
    suspend fun createComment(
        @Path("id") id: String,
        @Body request: CreateCommentRequest
    ): CommentDto
}
