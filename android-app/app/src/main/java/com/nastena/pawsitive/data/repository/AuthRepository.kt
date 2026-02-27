package com.nastena.pawsitive.data.repository

import com.nastena.pawsitive.data.remote.api.AuthApi
import com.nastena.pawsitive.data.remote.dto.LoginRequest

class AuthRepository(
    private val api: AuthApi
) {
    suspend fun login(email: String, password: String): Result<String> {
        return try {
            val response = api.login(LoginRequest(email, password))

            if (response.isSuccessful) {
                val token = response.body()?.token

                if (token != null) {
                    Result.success(token)
                } else {
                    Result.failure(Exception("Token is null"))
                }
            } else {
                Result.failure(Exception("Ошибка: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}