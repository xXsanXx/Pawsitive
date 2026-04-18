package com.nastena.pawsitive.network.api

import com.nastena.pawsitive.dto.UserFormResponse
import com.nastena.pawsitive.dto.UserFormUpdateRequest
import com.nastena.pawsitive.dto.UserProfileResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UserApi {
    @GET("api/user/profile")
    suspend fun getUserProfile(): Response<UserProfileResponse>

    @POST("/api/user/form/update")
    suspend fun updateUserForm(
        @Body userFormUpdateRequest: UserFormUpdateRequest
    ): Response<Unit>

    @POST("/api/user/form/get")
    suspend fun getUserFormForAnimal(@Body animalId: Long): Response<UserFormResponse>

}