package com.bezubaan.app.feature.volunteer.di

import com.bezubaan.app.feature.volunteer.data.remote.VolunteerApi
import com.bezubaan.app.feature.volunteer.data.repository.VolunteerRepositoryImpl
import com.bezubaan.app.feature.volunteer.domain.repository.VolunteerRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object VolunteerModule {

    @Provides
    @Singleton
    fun provideVolunteerApi(retrofit: Retrofit): VolunteerApi {
        return retrofit.create(VolunteerApi::class.java)
    }

    @Provides
    @Singleton
    fun provideVolunteerRepository(api: VolunteerApi): VolunteerRepository {
        return VolunteerRepositoryImpl(api)
    }
}
