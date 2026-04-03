package com.nastena.pawsitive.network

import com.nastena.pawsitive.utils.FileUtils

object NetworkUtils {
    const val BASE_URL = "https://10.0.2.2:8080/"

    fun getAbsoluteFileUrl(localFileUrl: String): String {
        return FileUtils.getAbsoluteFileUrl(BASE_URL, localFileUrl);
    }
}