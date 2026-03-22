package com.nastena.pawsitive.network.api

import com.nastena.pawsitive.dto.ShelterProfileResponse
import com.nastena.pawsitive.dto.UserProfileResponse
import retrofit2.Response
import retrofit2.http.GET

interface ShelterApi {
    @GET("api/shelter/profile")
    suspend fun getShelterProfile(): Response<ShelterProfileResponse>

}