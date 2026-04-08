package com.nastena.pawsitive.repository

import com.nastena.pawsitive.dto.AnimalsResponse
import com.nastena.pawsitive.dto.UserProfileResponse
import com.nastena.pawsitive.network.api.AnimalApi
import com.nastena.pawsitive.network.api.UserApi
import com.nastena.pawsitive.repository.utils.handleServerErrorBody
import retrofit2.Response

class UserRepository(
    private val _api: UserApi,
    private val _animalApi: AnimalApi
) {
    suspend fun getUserProfileData(): Result<UserProfileResponse> = runCatching {
        val response: Response<UserProfileResponse> = _api.getUserProfile()
        if (response.isSuccessful) {
            return Result.success(response.body()!!)
        } else {
            return handleServerErrorBody(response)
        }
    }

    suspend fun getRandomAnimalsRatio(): Result<AnimalsResponse> = runCatching {
        val response: Response<AnimalsResponse> = _animalApi.getRandomUserAnimalsRatio()
        if (response.isSuccessful) {
            return Result.success(response.body()!!)
        } else {
            return handleServerErrorBody(response)
        }
    }
}