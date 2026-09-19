package com.bezubaan.app.core.network

import com.bezubaan.app.core.common.Resource
import kotlinx.serialization.SerializationException
import retrofit2.HttpException
import java.io.IOException

object ApiErrorHandler {
    fun <T> handleError(exception: Exception): Resource.Error {
        val errorMessage = when (exception) {
            is IOException -> "Network error. Please check your internet connection."
            is HttpException -> {
                when (exception.code()) {
                    401 -> "Session expired. Please log in again."
                    403 -> "You don't have permission to perform this action."
                    404 -> "Resource not found."
                    500 -> "Server error. We're working on fixing it."
                    else -> "An unexpected error occurred (${exception.code()})."
                }
            }
            is SerializationException -> "Data parsing error. Please try again."
            else -> exception.localizedMessage ?: "An unknown error occurred."
        }
        return Resource.Error(errorMessage, exception)
    }
}
