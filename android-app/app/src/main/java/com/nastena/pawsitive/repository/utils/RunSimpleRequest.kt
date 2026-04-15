package com.nastena.pawsitive.repository.utils

import retrofit2.Response

suspend inline fun <reified TResult> runSimpleRequest(
    request: suspend () -> Response<TResult>
): Result<TResult> = runCatching {
    val response: Response<TResult> = request()
    return if (response.isSuccessful) {
        Result.success(response.body()!!)
    } else {
        handleServerErrorBody(response)
    }
}