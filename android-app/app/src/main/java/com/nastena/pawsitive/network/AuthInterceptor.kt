package com.nastena.pawsitive.network

import android.util.Log
import com.nastena.pawsitive.repository.datastores.AuthDataStore
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(
    private val authDataStore: AuthDataStore
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val token = runBlocking {
            authDataStore.getToken()
        }

        Log.d("AUTH", "Token in interceptor: $token")

        val request = chain.request().newBuilder()

        token?.let {
            request.addHeader("Authorization", "Bearer $it")
        }

        return chain.proceed(request.build())
    }
}