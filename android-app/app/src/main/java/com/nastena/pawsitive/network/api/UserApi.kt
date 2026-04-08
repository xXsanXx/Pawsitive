package com.nastena.pawsitive.network.api

import com.nastena.pawsitive.dto.UserProfileResponse
import retrofit2.Response
import retrofit2.http.GET

interface UserApi {
    @GET("api/user/profile")
    suspend fun getUserProfile(): Response<UserProfileResponse>


}