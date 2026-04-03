package com.nastena.pawsitive

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import com.nastena.pawsitive.network.NetworkImageLoader

class MainApplication : Application(), ImageLoaderFactory {
    override fun newImageLoader(): ImageLoader {
        return NetworkImageLoader.create(applicationContext)
    }
}