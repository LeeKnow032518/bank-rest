package com.example.bank.rest.dto.auth;

import lombok.Data;

@Data
public class LoginRequest {

    private String username;

    private String password;
}
