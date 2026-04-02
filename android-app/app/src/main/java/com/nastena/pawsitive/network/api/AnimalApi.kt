package com.nastena.pawsitive.network.api

import com.nastena.pawsitive.dto.ShelterAnimalsResponse
import com.nastena.pawsitive.dto.UpdateAnimalRequest
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Part
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST

interface AnimalApi {

    @GET("api/animals/shelters")
    suspend fun getShelterAnimals(): Response<ShelterAnimalsResponse>

    @Multipart
    @POST("api/animals/create")
    suspend fun createAnimal(
        @Part("data") data: RequestBody,
        @Part photos: List<MultipartBody.Part>?,
        @Part vetPassports: List<MultipartBody.Part>?
    ): Response<Long>

    @POST("api/animals/update")
    suspend fun updateAnimal(
        @Body request: UpdateAnimalRequest
    ): Response<Unit>

    @POST("api/animals/remove")
    suspend fun removeAnimal(
        @Body id: Long
    ): Response<Unit>
}
