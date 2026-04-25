package com.nastena.pawsitive.network.api

import com.nastena.pawsitive.dto.ShelterFormDetailsResponse
import com.nastena.pawsitive.dto.ShelterFormsResponse
import com.nastena.pawsitive.dto.ShelterInfoResponse
import com.nastena.pawsitive.dto.ShelterProfileResponse
import com.nastena.pawsitive.dto.ShelterUpdateStatusRequest
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

    @POST("api/shelter/info")
    suspend fun getShelterInfo(
        @Body id: Long
    ): Response<ShelterInfoResponse>

    @GET("api/shelter/forms")
    suspend fun getShelterForms(): Response<ShelterFormsResponse>

    @POST("api/shelter/forms/details")
    suspend fun getShelterDetailsForm(@Body requestId: Long): Response<ShelterFormDetailsResponse>

    @POST("api/shelter/forms/update")
    suspend fun updateRequestStatus(
        @Body shelterUpdateStatusRequest: ShelterUpdateStatusRequest
    ): Response<Unit>

}