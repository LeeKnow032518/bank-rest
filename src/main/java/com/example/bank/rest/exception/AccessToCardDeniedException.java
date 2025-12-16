package com.example.bank.rest.exception;

public class AccessToCardDeniedException extends RuntimeException{
    public AccessToCardDeniedException(String message){
        super(message);
    }
}
