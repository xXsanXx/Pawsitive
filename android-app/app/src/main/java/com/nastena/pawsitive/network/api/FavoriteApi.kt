package com.nastena.pawsitive.network.api

import com.nastena.pawsitive.dto.AnimalsResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface FavoriteApi {

    @POST("api/favorites/add")
    suspend fun add(@Body id: Long): Response<Unit>

    @POST("api/favorites/remove")
    suspend fun remove(@Body id: Long): Response<Unit>

    @GET("api/favorites/get")
    suspend fun get(): Response<AnimalsResponse>


}