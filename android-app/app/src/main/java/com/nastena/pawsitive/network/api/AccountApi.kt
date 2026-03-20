package com.nastena.pawsitive.network.api

import com.nastena.pawsitive.dto.LoginRequest
import com.nastena.pawsitive.dto.LoginResponse
import com.nastena.pawsitive.dto.MeResponse
import com.nastena.pawsitive.dto.RegisterRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AccountApi {
    @POST("api/account/login")
    suspend fun login(
        @Body request: LoginRequest
    ): Response<LoginResponse>

    @POST("api/account/register")
    suspend fun register(
        @Body request: RegisterRequest
    ): Response<Unit>

    @GET("api/account/me")
    suspend fun me(): Response<MeResponse>
}