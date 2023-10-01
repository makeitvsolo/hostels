package com.makeitvsolo.hostels.controller;

import com.makeitvsolo.hostels.dto.ErrorMessage;
import com.makeitvsolo.hostels.exception.HostelsException;
import com.makeitvsolo.hostels.exception.MemberAlreadyExistsException;
import com.makeitvsolo.hostels.exception.MemberNotFoundException;
import com.makeitvsolo.hostels.exception.PasswordsAreNotMatchesException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionMapper extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {MemberAlreadyExistsException.class})
    public ResponseEntity<ErrorMessage> handleMemberAlreadyExists(
            HostelsException ex
    ) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                       .body(new ErrorMessage(ex.getMessage()));
    }

    @ExceptionHandler(value = {MemberNotFoundException.class, PasswordsAreNotMatchesException.class})
    public ResponseEntity<ErrorMessage> handleAuthenticationException(
            HostelsException ex
    ) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                       .body(new ErrorMessage(ex.getMessage()));
    }
}
