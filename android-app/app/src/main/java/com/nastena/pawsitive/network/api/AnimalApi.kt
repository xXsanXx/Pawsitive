package com.nastena.pawsitive.network.api

import com.nastena.pawsitive.dto.AnimalResponse
import com.nastena.pawsitive.dto.AnimalsResponse
import com.nastena.pawsitive.dto.ShelterAnimalResponse
import com.nastena.pawsitive.dto.ShelterAnimalsResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface AnimalApi {

    @GET("api/animals/shelters")
    suspend fun getShelterAnimals(): Response<ShelterAnimalsResponse>

    @POST("api/animals/shelters/id")
    suspend fun getShelterAnimal(@Body id: Long): Response<ShelterAnimalResponse>

    @Multipart
    @POST("api/animals/create")
    suspend fun createAnimal(
        @Part("data") data: RequestBody,
        @Part photos: List<MultipartBody.Part>?,
        @Part vetPassports: List<MultipartBody.Part>?
    ): Response<Long>

    @Multipart
    @POST("api/animals/update")
    suspend fun updateAnimal(
        @Part("data") data: RequestBody,
        @Part newPhotos: List<MultipartBody.Part>?,
        @Part newPassportPhotos: List<MultipartBody.Part>?
    ): Response<Unit>

    @POST("api/animals/remove")
    suspend fun removeAnimal(
        @Body id: Long
    ): Response<Unit>

    @POST("api/animals/user/random")
    suspend fun getRandomUserAnimalsRatio(): Response<AnimalsResponse>


    // сделать отдельный api
    @POST("api/animals/user/favorite/add")
    suspend fun addToFavorite(@Body id: Long): Response<Unit>

    @POST("api/animals/users/id")
    suspend fun getAnimalDetails(@Body id: Long): Response<AnimalResponse>
}
