package com.nastena.pawsitive.server.exceptions;

import com.nastena.pawsitive.dto.ErrorResponse;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler(ServerRuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(ServerRuntimeException exception) {
        return ResponseEntity.status(HttpStatusCode.valueOf(500)).body(new ErrorResponse(exception.errorCode));
    }
}
