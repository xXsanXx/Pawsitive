package com.nastena.pawsitive.common

import com.nastena.pawsitive.dto.ErrorCode

class ServerParsedException(val errorCode: ErrorCode) :
    Throwable("Received server error code $errorCode") {
}