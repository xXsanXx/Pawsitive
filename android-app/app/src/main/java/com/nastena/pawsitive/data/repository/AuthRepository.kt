package com.nastena.pawsitive.data.repository

import com.nastena.pawsitive.data.datastore.TokenManager
import com.nastena.pawsitive.data.remote.api.AuthApi
import com.nastena.pawsitive.data.remote.dto.LoginRequest
import com.nastena.pawsitive.data.remote.dto.RegisterRequest
import com.nastena.pawsitive.data.remote.dto.Role

class AuthRepository(
    private val api: AuthApi,
    private val tokenManager: TokenManager
) {

    suspend fun register(
        email: String,
        password: String,
        role: Role
    ): Result<Unit> {

        return try {
            val response = api.register(
                RegisterRequest(email, password, role)
            )

            tokenManager.saveToken(response.token)
            tokenManager.saveRole(response.role)
            Result.success(Unit)

        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun login(
        email: String,
        password: String
    ): Result<Unit> {

        return try {
            val response = api.login(LoginRequest(email, password))

            tokenManager.saveToken(response.token)
            tokenManager.saveRole(response.role)

            Result.success(Unit)

        } catch (e: Exception) {
            Result.failure(e)
        }

    }

}