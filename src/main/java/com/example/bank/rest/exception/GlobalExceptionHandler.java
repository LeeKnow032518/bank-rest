package com.example.bank.rest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.security.NoSuchAlgorithmException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoSuchAlgorithmException.class)
    public String handleNoSuchAlgorithmException(NoSuchAlgorithmException exception){
        return exception.getMessage();
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<String> handleUsernameNotFoundException(UsernameNotFoundException exception){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
