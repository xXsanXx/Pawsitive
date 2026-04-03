package com.nastena.pawsitive.network

import okhttp3.OkHttpClient
import com.nastena.pawsitive.repository.datastores.AuthDataStore

object OkHttpClient {
    private var _instance: OkHttpClient? = null

    fun get(authDataStore: AuthDataStore): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(authDataStore))
            .build()
}