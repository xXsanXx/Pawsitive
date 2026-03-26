package com.nastena.pawsitive.repository

import com.nastena.pawsitive.dto.AnimalResponse
import com.nastena.pawsitive.dto.ShelterProfileResponse
import com.nastena.pawsitive.network.api.AnimalApi
import com.nastena.pawsitive.repository.utils.handleServerErrorBody
import retrofit2.Response

class AnimalRepository (
    private val _api: AnimalApi
) {
    suspend fun getAnimalsData(): Result<AnimalResponse> = runCatching {
        val response: Response<AnimalResponse> = _api.getShelterAnimals()
        if (response.isSuccessful) {
            return Result.success(response.body()!!)
        } else {
            return handleServerErrorBody(response)
        }
    }

    suspend fun getAnimalData(id: Long): Result<AnimalResponse> = runCatching {
        val response: Response<AnimalResponse> = _api.getAnimal(id)
        if (response.isSuccessful) {
            return Result.success(response.body()!!)
        } else {
            return handleServerErrorBody(response)
        }
    }
}