package com.example.bank.rest.exception;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(Integer id){
        super("Пользователь " + id + " не найден");
    }
}
