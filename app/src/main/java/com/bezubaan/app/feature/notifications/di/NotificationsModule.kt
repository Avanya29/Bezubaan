package com.bezubaan.app.feature.notifications.di

import com.bezubaan.app.feature.notifications.data.remote.NotificationsApi
import com.bezubaan.app.feature.notifications.data.repository.NotificationsRepositoryImpl
import com.bezubaan.app.feature.notifications.domain.repository.NotificationsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NotificationsModule {

    @Provides
    @Singleton
    fun provideNotificationsApi(retrofit: Retrofit): NotificationsApi {
        return retrofit.create(NotificationsApi::class.java)
    }

    @Provides
    @Singleton
    fun provideNotificationsRepository(api: NotificationsApi): NotificationsRepository {
        return NotificationsRepositoryImpl(api)
    }
}
