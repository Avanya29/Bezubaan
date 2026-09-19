package com.bezubaan.app.feature.community.di

import com.bezubaan.app.feature.community.data.remote.CommunityApi
import com.bezubaan.app.feature.community.data.repository.CommunityRepositoryImpl
import com.bezubaan.app.feature.community.domain.repository.CommunityRepository
import com.bezubaan.app.feature.community.domain.usecase.CreatePostUseCase
import com.bezubaan.app.feature.community.domain.usecase.GetPostsUseCase
import com.bezubaan.app.feature.community.domain.usecase.CommunityUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CommunityModule {

    @Provides
    @Singleton
    fun provideCommunityApi(retrofit: Retrofit): CommunityApi {
        return retrofit.create(CommunityApi::class.java)
    }

    @Provides
    @Singleton
    fun provideCommunityRepository(api: CommunityApi): CommunityRepository {
        return CommunityRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideCommunityUseCases(repository: CommunityRepository): CommunityUseCases {
        return CommunityUseCases(
            getPosts = GetPostsUseCase(repository),
            createPost = CreatePostUseCase(repository)
        )
    }
}
