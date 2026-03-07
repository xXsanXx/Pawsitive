package com.nastena.pawsitive.common

import com.nastena.pawsitive.dto.ErrorCode

class ServerParsedException(message: String, val errorCode: ErrorCode) :
    Throwable("Received server error code $errorCode. Message: $message") {
}