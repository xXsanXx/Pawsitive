package com.nastena.pawsitive.server.exceptions;

import com.nastena.pawsitive.dto.ErrorCode;

public class ServerRuntimeException extends RuntimeException {
    public final ErrorCode errorCode;

    public ServerRuntimeException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
