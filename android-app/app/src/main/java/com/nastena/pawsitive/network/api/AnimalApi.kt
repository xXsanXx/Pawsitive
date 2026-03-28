package com.nastena.pawsitive.network.api

import com.nastena.pawsitive.dto.AnimalResponse
import com.nastena.pawsitive.dto.CreateAnimalRequest
import com.nastena.pawsitive.dto.UpdateAnimalRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AnimalApi {

    @GET("api/animals")
    suspend fun getAnimals(): Response<AnimalResponse>

    @POST("api/animals/create")
    suspend fun createAnimal(
        @Body request: CreateAnimalRequest
    ): Response<Long>

    @POST("api/animals/update")
    suspend fun updateAnimal(
        @Body request: UpdateAnimalRequest
    ): Response<Unit>
}