package com.nastena.pawsitive.data.repository

import com.nastena.pawsitive.data.remote.api.AuthApi
import com.nastena.pawsitive.data.remote.RetrofitClient
import com.nastena.pawsitive.data.remote.dto.LoginRequest

class AuthRepository {
    private val api = RetrofitClient
        .retrofit
        .create(AuthApi::class.java)

    suspend fun login(email: String, password: String) =
        api.login(LoginRequest(email, password))
}