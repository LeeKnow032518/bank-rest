package com.example.bank.rest.dto.auth;

import com.example.bank.rest.util.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {

    private String token;

    private RoleEnum role;
}
