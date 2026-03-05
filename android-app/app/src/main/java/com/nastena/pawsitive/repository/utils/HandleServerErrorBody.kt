package com.nastena.pawsitive.repository.utils

import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.nastena.pawsitive.common.ServerParsedException
import com.nastena.pawsitive.common.ServerUnknownErrorCodeException
import com.nastena.pawsitive.dto.ErrorResponse
import retrofit2.Response

inline fun <reified TResponse, reified TResult> handleServerErrorBody(response: Response<TResponse>): Result<TResult> =
    runCatching {
        Log.e("Server", "Handling error.\n" +
                "HTTP code: ${response.code()}\n" +
                "Body: ${response.body()}\n" +
                "Error body: ${response.errorBody()?.string()}")

        val errorBody: String? = response.errorBody()?.string()
        if (errorBody == null || errorBody.isEmpty()) {
            return Result.failure(ServerUnknownErrorCodeException(response.code(), null))
        }

        try {
            val errorResponse: ErrorResponse = Gson().fromJson(errorBody, ErrorResponse::class.java)
            return Result.failure(ServerParsedException(errorResponse.errorCode))
        } catch (e: JsonSyntaxException) {
            Log.e("Server", "Failed to parse error response. Got http code: ${response.code()}. Got error body:\n'$errorBody'")
        }

        return Result.failure(ServerUnknownErrorCodeException(response.code(), errorBody))
    }