package com.example.bank.rest.utils;

import com.example.bank.rest.dto.users.UserDto;
import com.example.bank.rest.util.RoleEnum;

import java.util.List;

public class UserDtoUtil {

    public static List<UserDto> getAllUserDto(){
        UserDto first = UserDto.builder()
            .username("IVAN IVANOV")
            .role(RoleEnum.USER.getValue())
            .build();
        UserDto second = UserDto.builder()
            .username("OLEG IVANOV")
            .role(RoleEnum.USER.getValue())
            .build();

        return List.of(first, second);
    }
}
