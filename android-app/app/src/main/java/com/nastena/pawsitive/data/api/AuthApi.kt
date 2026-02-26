package com.nastena.pawsitive.data.api

import com.nastena.pawsitive.data.model.LoginRequest
import com.nastena.pawsitive.data.model.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("api/account/login")
    suspend fun login(
        @Body request: LoginRequest
    ): LoginResponse
}