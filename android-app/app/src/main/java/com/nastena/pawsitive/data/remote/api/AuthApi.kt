package com.nastena.pawsitive.data.remote.api

import com.nastena.pawsitive.data.remote.dto.LoginRequest
import com.nastena.pawsitive.data.remote.dto.LoginResponse
import com.nastena.pawsitive.data.remote.dto.RegisterRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("api/account/login")
    suspend fun login(
        @Body request: LoginRequest
    ): LoginResponse

    @POST("api/account/register")
    suspend fun register(
        @Body request: RegisterRequest
    ): LoginResponse
}