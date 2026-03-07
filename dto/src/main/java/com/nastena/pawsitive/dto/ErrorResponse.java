package com.nastena.pawsitive.dto;

public class ErrorResponse {
    private String message;
    private ErrorCode errorCode;

    public ErrorResponse() {
    }

    public ErrorResponse(String message, ErrorCode errorCode)
    {
        this.message = message;
        this.errorCode = errorCode;
    }

    public String getMessage() { return message; }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
