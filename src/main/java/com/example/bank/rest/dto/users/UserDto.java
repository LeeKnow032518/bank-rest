package com.example.bank.rest.dto.users;

import lombok.Builder;
import lombok.Data;


@Data
@Builder(toBuilder = true)
public class UserDto {

    private String username;

    private String role;
}
