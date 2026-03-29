package com.nastena.pawsitive.repository

import com.nastena.pawsitive.dto.AnimalResponse
import com.nastena.pawsitive.dto.AnimalsResponse
import com.nastena.pawsitive.network.api.AnimalApi
import com.nastena.pawsitive.repository.utils.handleServerErrorBody
import retrofit2.Response

class AnimalRepository (
    private val _api: AnimalApi
) {
    suspend fun getAnimalsData(): Result<AnimalsResponse> = runCatching {
        val response: Response<AnimalsResponse> = _api.getAnimals()
        if (response.isSuccessful) {
            return Result.success(response.body()!!)
        } else {
            return handleServerErrorBody(response)
        }
    }
}