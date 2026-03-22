package com.nastena.pawsitive.repository

import com.nastena.pawsitive.dto.ShelterProfileResponse
import com.nastena.pawsitive.network.api.ShelterApi
import com.nastena.pawsitive.repository.utils.handleServerErrorBody
import retrofit2.Response

class ShelterRepository(
    private val _api: ShelterApi
) {
    suspend fun getShelterProfileData(): Result<ShelterProfileResponse> = runCatching {
        val response: Response<ShelterProfileResponse> =_api.getShelterProfile()
        if (response.isSuccessful) {
            return Result.success(response.body()!!)
        } else {
            return handleServerErrorBody(response)
        }
    }
}