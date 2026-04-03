package com.nastena.pawsitive.network

import android.content.Context
import coil.ImageLoader
import com.nastena.pawsitive.repository.datastores.AuthDataStore
import okhttp3.OkHttpClient

object NetworkImageLoader {
    fun create(context: Context): ImageLoader = ImageLoader.Builder(context)
            .okHttpClient(
                com.nastena.pawsitive.network.OkHttpClient.get(
                    AuthDataStore(context)
                )
            )
            .build()
}