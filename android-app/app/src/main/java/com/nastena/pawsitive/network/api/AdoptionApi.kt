package com.nastena.pawsitive.network.api

import com.nastena.pawsitive.dto.AnimalResponse
import com.nastena.pawsitive.dto.FormRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface AdoptionApi {

    @POST("api/adoption/form")
    suspend fun sendForm(@Path("id") id: Long, @Body request: FormRequest): Response<AnimalResponse>
}
