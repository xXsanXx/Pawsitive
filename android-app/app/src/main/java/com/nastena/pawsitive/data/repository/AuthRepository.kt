package com.nastena.pawsitive.data.repository

import com.nastena.pawsitive.data.api.AuthApi
import com.nastena.pawsitive.data.api.RetrofitClient
import com.nastena.pawsitive.data.model.LoginRequest

class AuthRepository {
    private val api = RetrofitClient
        .retrofit
        .create(AuthApi::class.java)

    suspend fun login(email: String, password: String) =
        api.login(LoginRequest(email, password))
}