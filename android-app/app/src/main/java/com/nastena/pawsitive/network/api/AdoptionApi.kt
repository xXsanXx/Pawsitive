package com.nastena.pawsitive.network.api

import com.nastena.pawsitive.dto.UserAdoptionsResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AdoptionApi {
    @POST("api/adoption/form/create")
    suspend fun createForm(@Body id: Long): Response<Unit>

    @GET("api/adoption/requests")
    suspend fun getUserRequests(): Response<UserAdoptionsResponse>

    @POST("api/adoption/cancel")
    suspend fun cancelAdoptionRequest(@Body animalId: Long): Response<Unit>

    @POST("api/adoption/shelter/hide")
    suspend fun hideShelterAdoptionRequest(@Body requestId: Long): Response<Unit>

    @POST("api/adoption/user/hide")
    suspend fun hideUserAdoptionRequest(@Body requestId: Long): Response<Unit>

}
