package com.example.smartwaiter.repository

import hr.foi.air.webservice.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

abstract class BaseRepository {

    suspend fun <T> safeApiCall(
        apiCall: suspend () -> T
    ): Resource<T> {
        return withContext(Dispatchers.IO) {
            try {
                Resource.Success(apiCall.invoke())
            } catch (throwable: Throwable) {
                when (throwable) {
                    is IOException ->
                        Resource.Failure(true, throwable.cause.toString())
                    else ->
                        Resource.Failure(false, null)
                }
            }
        }
    }
}