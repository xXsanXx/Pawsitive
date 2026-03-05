package com.nastena.pawsitive.dto;

public class ErrorResponse {
    private ErrorCode errorCode;

    public ErrorResponse() {
    }

    public ErrorResponse(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
