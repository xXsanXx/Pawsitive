package com.nastena.pawsitive.network

import com.nastena.pawsitive.BuildConfig
import com.nastena.pawsitive.repository.datastores.AuthDataStore
import okhttp3.OkHttpClient

object OkHttpClient {
    fun get(authDataStore: AuthDataStore): OkHttpClient {
        var builder: OkHttpClient.Builder = OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(authDataStore))

        if (BuildConfig.DEBUG) {
            builder = builder.hostnameVerifier { _, _ -> true }
        }

        return builder.build()
    }
}