package com.bezubaan.app.feature.rescue.di

import com.bezubaan.app.feature.rescue.data.remote.RescueApi
import com.bezubaan.app.feature.rescue.data.repository.RescueRepositoryImpl
import com.bezubaan.app.feature.rescue.domain.repository.RescueRepository
import com.bezubaan.app.feature.rescue.domain.usecase.GetNearbyRescuesUseCase
import com.bezubaan.app.feature.rescue.domain.usecase.ReportRescueUseCase
import com.bezubaan.app.feature.rescue.domain.usecase.RescueUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RescueModule {

    @Provides
    @Singleton
    fun provideRescueApi(retrofit: Retrofit): RescueApi {
        return retrofit.create(RescueApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRescueRepository(api: RescueApi): RescueRepository {
        return RescueRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideRescueUseCases(repository: RescueRepository): RescueUseCases {
        return RescueUseCases(
            getNearbyRescues = GetNearbyRescuesUseCase(repository),
            reportRescue = ReportRescueUseCase(repository)
        )
    }
}
