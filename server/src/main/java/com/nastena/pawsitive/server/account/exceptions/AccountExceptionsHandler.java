package com.nastena.pawsitive.server.account.exceptions;

import com.nastena.pawsitive.server.account.dto.AccountErrorCode;
import com.nastena.pawsitive.server.account.dto.AccountErrorResponse;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AccountExceptionsHandler {

    @ExceptionHandler(UserWithEmailAlreadyExistsException.class)
    public ResponseEntity<AccountErrorResponse> handleUserAlreadyExists() {
        return ResponseEntity.status(HttpStatusCode.valueOf(401)).body(new AccountErrorResponse(AccountErrorCode.USER_WITH_EMAIL_EXISTS));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<AccountErrorResponse> handlerBadCredentials() {
        return ResponseEntity.status(HttpStatusCode.valueOf(401)).body(new AccountErrorResponse(AccountErrorCode.INVALID_LOGIN_CREDENTIALS));
    }
}
