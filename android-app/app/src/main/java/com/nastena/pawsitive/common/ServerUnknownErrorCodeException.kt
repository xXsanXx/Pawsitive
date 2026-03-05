package com.nastena.pawsitive.common

class ServerUnknownErrorCodeException(val httpCode: Int, val body: String?) :
    Throwable("Received unknown server code exception with http code $httpCode. Body: $body") {
}