package com.nastena.pawsitive.network.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AdoptionApi {
    @POST("api/form/create")
    suspend fun createForm(@Body id: Long): Response<Unit>
}
