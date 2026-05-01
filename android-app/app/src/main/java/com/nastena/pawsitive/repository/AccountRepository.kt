package com.nastena.pawsitive.repository

import android.util.Log
import com.nastena.pawsitive.dto.AccountRole
import com.nastena.pawsitive.dto.LoginRequest
import com.nastena.pawsitive.dto.LoginResponse
import com.nastena.pawsitive.dto.MeResponse
import com.nastena.pawsitive.dto.RegisterRequest
import com.nastena.pawsitive.network.api.AccountApi
import com.nastena.pawsitive.repository.datastores.AuthDataStore
import com.nastena.pawsitive.repository.utils.handleServerErrorBody
import retrofit2.Response

class AccountRepository(
    private val _api: AccountApi,
    private val _authDataStore: AuthDataStore
) {

    suspend fun register(
        name: String,
        email: String,
        password: String,
        role: AccountRole
    ): Result<Unit> = runCatching {
        Log.i(
            "Account Repository",
            "[register] name: $name, email: $email, password: $password, role: $role"
        )

        val response: Response<Unit> = _api.register(RegisterRequest(name, email, password, role))
        if (response.isSuccessful) {
            return Result.success(Unit)
        } else {
            return handleServerErrorBody(response)
        }
    }

    suspend fun login(
        email: String,
        password: String
    ): Result<AccountRole> = runCatching {
        Log.i("Account Repository", "[login] email: $email, password: $password")

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

    suspend fun getAuthorizedRole(): Result<AccountRole?> = runCatching {
        val token = _authDataStore.getToken();

        Log.i("Account Repository", "[getAuthorizedRole] current token: $token")

        if (token == null) {
            return Result.success(null)
        }

        val response: Response<MeResponse> = _api.me()

        if (!response.isSuccessful) {
            return handleServerErrorBody(response)
        }

        val authorizedResponse: MeResponse = response.body()!!
        return Result.success(authorizedResponse.role)
    }

    suspend fun logout(): Result<Unit> = runCatching {
        Log.i("Account repository", "Logout is successful")

        _authDataStore.clearAll()
        return Result.success(Unit)
    }
}