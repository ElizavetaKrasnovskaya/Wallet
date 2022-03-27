package com.bsuir.data.utils

import com.bsuir.domain.error.ApplicationError
import com.bsuir.domain.model.Result
import retrofit2.HttpException
import java.net.ConnectException
import java.net.HttpURLConnection
import java.net.SocketTimeoutException
import java.net.UnknownHostException

suspend fun <T> safeApiCall(apiCall: suspend () -> T): Result<T> {
    return try {
        Result.Success(apiCall.invoke())
    } catch (throwable: Throwable) {
        when (throwable) {
            is SocketTimeoutException -> Result.Error(ApplicationError.TimeOut)
            is ConnectException -> Result.Error(ApplicationError.NoInternetConnection)
            is UnknownHostException -> Result.Error(ApplicationError.NoInternetConnection)
            is HttpException -> {
                when (throwable.code()) {
                    HttpURLConnection.HTTP_BAD_REQUEST -> Result.Error(ApplicationError.BadRequest)
                    HttpURLConnection.HTTP_UNAUTHORIZED -> Result.Error(ApplicationError.Unauthorized)
                    HttpURLConnection.HTTP_NOT_FOUND -> Result.Error(ApplicationError.NotFound)
                    HttpURLConnection.HTTP_INTERNAL_ERROR -> Result.Error(ApplicationError.Server)
                    else -> Result.Error(ApplicationError.Generic)
                }
            }
            else -> {
                Result.Error(ApplicationError.Generic)
            }
        }
    }
}
