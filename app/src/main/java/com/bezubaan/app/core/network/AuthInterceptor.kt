package com.bezubaan.app.core.network

import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val tokenManager: TokenManager
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        
        // Blockingly get the token (runBlocking is acceptable in Interceptors on background threads)
        val token = runBlocking { tokenManager.getToken() }
        
        if (!token.isNullOrEmpty()) {
            requestBuilder.addHeader("Authorization", "Bearer $token")
        }
        
        val response = chain.proceed(requestBuilder.build())
        
        if (response.code == 401) {
            runBlocking {
                tokenManager.clearToken()
                tokenManager.triggerSessionExpired()
            }
        }
        
        return response
    }
}
