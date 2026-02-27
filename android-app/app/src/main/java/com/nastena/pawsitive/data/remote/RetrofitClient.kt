package com.nastena.pawsitive.data.remote

import com.nastena.pawsitive.network.AuthInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "http://10.0.2.2:8080/"

    val instance: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

//    val okHttpClient = okHttpClient.Builder()
//        .addInterceptor(AuthInterceptor(tokenManager))
//        .build()
//
//    Retrofit.Builder()
//        .client(okHttpClient)
}