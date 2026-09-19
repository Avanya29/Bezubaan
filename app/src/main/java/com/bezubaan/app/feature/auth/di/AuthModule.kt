package com.bezubaan.app.feature.auth.di

import com.bezubaan.app.core.network.TokenManager
import com.bezubaan.app.feature.auth.data.remote.AuthApi
import com.bezubaan.app.feature.auth.data.repository.AuthRepositoryImpl
import com.bezubaan.app.feature.auth.domain.repository.AuthRepository
import com.bezubaan.app.feature.auth.domain.usecase.AuthUseCases
import com.bezubaan.app.feature.auth.domain.usecase.LoginUseCase
import com.bezubaan.app.feature.auth.domain.usecase.LogoutUseCase
import com.bezubaan.app.feature.auth.domain.usecase.RegisterUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    @Singleton
    fun provideAuthApi(retrofit: Retrofit): AuthApi {
        return retrofit.create(AuthApi::class.java)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(
        api: AuthApi,
        tokenManager: TokenManager
    ): AuthRepository {
        return AuthRepositoryImpl(api, tokenManager)
    }

    @Provides
    @Singleton
    fun provideAuthUseCases(repository: AuthRepository): AuthUseCases {
        return AuthUseCases(
            login = LoginUseCase(repository),
            register = RegisterUseCase(repository),
            logout = LogoutUseCase(repository)
        )
    }
}
