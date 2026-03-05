package com.nastena.pawsitive.network.api

import com.nastena.pawsitive.legacy_data.remote.dto.AnimalResponse
import retrofit2.http.GET

interface AnimalApi {
    @GET("api/animals")
    suspend fun getAnimals(): List<AnimalResponse>

    @GET("api/animals/my")
    suspend fun getMyAnimals(): List<AnimalResponse>
}