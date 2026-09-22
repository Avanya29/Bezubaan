package com.bezubaan.app.feature.auth.data.remote

import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): retrofit2.Response<LoginResponse>

    @POST("auth/register")
    suspend fun register(@Body request: RegisterRequest): retrofit2.Response<UserDto>

    @POST("auth/forgot-password")
    suspend fun forgotPassword(@Body request: ForgotPasswordRequest): retrofit2.Response<Unit>

    @retrofit2.http.GET("users/me")
    suspend fun getProfile(): retrofit2.Response<UserDto>
}
