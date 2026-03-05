package com.nastena.pawsitive.network

import com.nastena.pawsitive.repository.datastores.AuthDataStore
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://10.0.2.2:8080/"

    fun create(authDataStore: AuthDataStore): Retrofit {

        val okHttpClient = OkHttpClient.Builder()
//            .hostnameVerifier { _, _ -> true }  // TODO: fix with normal certificate for SSL
            .addInterceptor(AuthInterceptor(authDataStore))
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}