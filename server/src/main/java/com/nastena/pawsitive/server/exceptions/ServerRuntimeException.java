package com.nastena.pawsitive.server.exceptions;

import com.nastena.pawsitive.dto.ErrorCode;

public class ServerRuntimeException extends RuntimeException {
    public final ErrorCode errorCode;

    public ServerRuntimeException(ErrorCode errorCode) {
        super("");
        this.errorCode = errorCode;
    }

    public ServerRuntimeException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

}
