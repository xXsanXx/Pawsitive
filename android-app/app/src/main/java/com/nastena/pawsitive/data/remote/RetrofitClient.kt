package com.nastena.pawsitive.data.remote

import com.nastena.pawsitive.data.datastore.TokenManager
import com.nastena.pawsitive.network.AuthInterceptor
import retrofit2.Retrofit
import okhttp3.OkHttpClient
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "http://127.0.0.1:8080/"

    fun create(tokenManager: TokenManager): Retrofit {

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(tokenManager))
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
