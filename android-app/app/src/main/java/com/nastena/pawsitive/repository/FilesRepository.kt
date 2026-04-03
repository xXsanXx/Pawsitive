package com.nastena.pawsitive.repository

import com.nastena.pawsitive.network.NetworkUtils

class FilesRepository() {
    fun getAbsoluteFileUrl(localFileUrl: String): String = NetworkUtils.getAbsoluteFileUrl(localFileUrl)
}