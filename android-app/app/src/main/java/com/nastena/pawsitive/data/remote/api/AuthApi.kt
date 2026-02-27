package com.nastena.pawsitive.data.remote.api

import com.nastena.pawsitive.data.remote.dto.LoginRequest
import com.nastena.pawsitive.data.remote.dto.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("api/account/login")
    suspend fun login(
        @Body request: LoginRequest
    ): Response<LoginResponse>
}