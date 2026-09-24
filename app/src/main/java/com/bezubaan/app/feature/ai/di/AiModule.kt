package com.bezubaan.app.feature.ai.di

import com.bezubaan.app.feature.ai.data.remote.AiApi
import com.bezubaan.app.feature.ai.data.repository.AiRepositoryImpl
import com.bezubaan.app.feature.ai.domain.repository.AiRepository
import com.bezubaan.app.feature.ai.domain.usecase.AiUseCases
import com.bezubaan.app.feature.ai.domain.usecase.AnalyzeImageUseCase
import com.bezubaan.app.feature.ai.domain.usecase.SendChatMessageUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import okhttp3.OkHttpClient
import javax.inject.Named
import javax.inject.Singleton
import com.bezubaan.app.BuildConfig

@Module
@InstallIn(SingletonComponent::class)
object AiModule {

    @Provides
    @Singleton
    fun provideAiApi(retrofit: Retrofit): AiApi {
        return retrofit.create(AiApi::class.java)
    }

    @Provides
    @Singleton
    fun provideAiRepository(api: AiApi): AiRepository {
        return AiRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideAiUseCases(repository: AiRepository): AiUseCases {
        return AiUseCases(
            analyzeImage = AnalyzeImageUseCase(repository),
            sendChatMessage = SendChatMessageUseCase(repository)
        )
    }
}
