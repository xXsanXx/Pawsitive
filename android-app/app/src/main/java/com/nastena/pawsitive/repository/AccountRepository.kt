package com.nastena.pawsitive.repository

import android.util.Log
import com.nastena.pawsitive.common.ServerParsedException
import com.nastena.pawsitive.dto.AccountRole
import com.nastena.pawsitive.dto.ErrorCode
import com.nastena.pawsitive.dto.LoginRequest
import com.nastena.pawsitive.dto.LoginResponse
import com.nastena.pawsitive.dto.MeResponse
import com.nastena.pawsitive.network.api.AccountApi
import com.nastena.pawsitive.repository.datastores.AuthDataStore
import com.nastena.pawsitive.repository.utils.handleServerErrorBody
import retrofit2.Response

class AccountRepository(
    private val _api: AccountApi,
    private val _authDataStore: AuthDataStore
) {

    suspend fun register(
        email: String,
        password: String,
        role: AccountRole
    ): Result<Unit> {
        return Result.failure(NotImplementedError())
//
//        return try {
//            val response = _api.register(
//                RegisterRequest(email, password, role)
//            )
//
//            _authDataStore.saveToken(response.token)
//            _authDataStore.saveRole(response.role)
//            Result.success(Unit)
//
//        } catch (e: Exception) {
//            Result.failure(e)
//        }
    }

    suspend fun login(
        email: String,
        password: String
    ): Result<AccountRole> = runCatching {
        val response: Response<LoginResponse> = _api.login(LoginRequest(email, password))

        if (response.isSuccessful) {
            val loginResponse: LoginResponse = response.body()!!

            _authDataStore.saveToken(loginResponse.token)
            _authDataStore.saveRole(loginResponse.role)

            return Result.success(loginResponse.role)

        } else {
            return handleServerErrorBody(response)
        }
    }

    suspend fun getAuthorizedRole(): Result<AccountRole> = runCatching {
        val token = _authDataStore.getToken();
        Log.e("test", "save token: $token")
        if (token == null) {
            return Result.failure(ServerParsedException(ErrorCode.UNAUTHORIZED))
        }

        val response: Response<MeResponse> = _api.me()

        if (!response.isSuccessful) {
            return handleServerErrorBody(response)
        }

        val authorizedResponse: MeResponse = response.body()!!
        return Result.success(authorizedResponse.role)
    }
}