package com.nastena.pawsitive.server.exceptions;

import com.nastena.pawsitive.dto.ErrorCode;
import com.nastena.pawsitive.dto.ErrorResponse;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;

@RestControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler(ServerRuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(ServerRuntimeException exception) {
        return ResponseEntity.status(HttpStatusCode.valueOf(500)).body(
                new ErrorResponse(exception.getMessage(), exception.errorCode)
        );
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleMessageNotReadable(HttpMessageNotReadableException exception) {
        return ResponseEntity.status(HttpStatusCode.valueOf(500)).body(
                new ErrorResponse(null, ErrorCode.INVALID_REQUEST_BODY)
        );
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<ErrorResponse> handleSqlException(SQLException exception) {
        return ResponseEntity.status(HttpStatusCode.valueOf(500)).body(
                new ErrorResponse("Internal server error", ErrorCode.INTERNAL_SERVER_ERROR)
        );
    }
}
