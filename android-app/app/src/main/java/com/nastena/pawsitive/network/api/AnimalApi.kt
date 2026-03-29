package com.nastena.pawsitive.network.api

import com.nastena.pawsitive.dto.CreateAnimalRequest
import com.nastena.pawsitive.dto.ShelterAnimalsResponse
import com.nastena.pawsitive.dto.UpdateAnimalRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AnimalApi {

    @GET("api/animals/shelters")
    suspend fun getShelterAnimals(): Response<ShelterAnimalsResponse>

    @POST("api/animals/create")
    suspend fun createAnimal(
        @Body request: CreateAnimalRequest
    ): Response<Long>

    @POST("api/animals/update")
    suspend fun updateAnimal(
        @Body request: UpdateAnimalRequest
    ): Response<Unit>

    @POST("api/animals/remove")
    suspend fun removeAnimal(
        id: Long
    ): Response<Unit>
}
