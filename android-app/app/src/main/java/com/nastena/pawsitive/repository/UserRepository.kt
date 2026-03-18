package com.nastena.pawsitive.repository

import android.util.Log
import com.nastena.pawsitive.dto.UserProfileResponse
import com.nastena.pawsitive.network.api.UserApi
import com.nastena.pawsitive.repository.utils.handleServerErrorBody
import retrofit2.Response

class UserRepository(
    private val _api: UserApi
) {
    suspend fun getUserProfileData(): Result<UserProfileResponse> = runCatching {
        val response: Response<UserProfileResponse> =_api.getUserProfile()
        if (response.isSuccessful) {
            return Result.success(response.body()!!)
        } else {
            return handleServerErrorBody(response)
        }
    }
}