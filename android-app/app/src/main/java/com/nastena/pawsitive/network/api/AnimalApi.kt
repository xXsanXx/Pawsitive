package com.nastena.pawsitive.network.api

import com.nastena.pawsitive.dto.AnimalResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface AnimalApi {

    @GET("api/animals")
    suspend fun getShelterAnimals(): Response<AnimalResponse>

    @GET("api/animal/{id}")
    suspend fun getAnimal(@Path("id") id: Long): Response<AnimalResponse>
}