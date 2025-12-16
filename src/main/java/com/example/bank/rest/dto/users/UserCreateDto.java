package com.example.bank.rest.dto.users;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class UserCreateDto {

    private String login;

    private String password;

    private String username;
}
