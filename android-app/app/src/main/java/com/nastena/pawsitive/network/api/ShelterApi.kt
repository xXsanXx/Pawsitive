package com.nastena.pawsitive.network.api

import com.nastena.pawsitive.dto.ShelterProfileResponse
import com.nastena.pawsitive.dto.UpdateShelterProfileRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ShelterApi {
    @GET("api/shelter/profile")
    suspend fun getShelterProfile(): Response<ShelterProfileResponse>

    @POST("api/shelter/profile/update")
    suspend fun updateShelterProfile(
        @Body request: UpdateShelterProfileRequest
    ): Response<Unit>

}