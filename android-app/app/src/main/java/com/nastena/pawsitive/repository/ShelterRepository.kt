package com.nastena.pawsitive.repository

import com.nastena.pawsitive.dto.ShelterAnimalsResponse
import com.nastena.pawsitive.dto.ShelterProfileResponse
import com.nastena.pawsitive.dto.UpdateShelterProfileRequest
import com.nastena.pawsitive.network.api.AnimalApi
import com.nastena.pawsitive.network.api.ShelterApi
import com.nastena.pawsitive.repository.utils.handleServerErrorBody
import retrofit2.Response

class ShelterRepository(
    private val _api: ShelterApi,
    private val _animalsApi: AnimalApi
) {
    suspend fun getShelterProfileData(): Result<ShelterProfileResponse> = runCatching {
        val response: Response<ShelterProfileResponse> = _api.getShelterProfile()
        if (response.isSuccessful) {
            return Result.success(response.body()!!)
        } else {
            return handleServerErrorBody(response)
        }
    }

    suspend fun updateShelterProfileData(
        phone: String,
        address: String,
        info: String
    ): Result<Unit> = runCatching {
        val response: Response<Unit> =
            _api.updateShelterProfile(UpdateShelterProfileRequest(phone, address, info))
        if (response.isSuccessful) {
            return Result.success(Unit)
        } else {
            return handleServerErrorBody(response)
        }
    }

    suspend fun getShelterAnimalsData(): Result<ShelterAnimalsResponse> = runCatching {
        val response: Response<ShelterAnimalsResponse> = _animalsApi.getShelterAnimals()
        if (response.isSuccessful) {
            return Result.success(response.body()!!)
        } else {
            return handleServerErrorBody(response)
        }
    }
}