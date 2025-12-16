package com.example.bank.rest.utils;

import com.example.bank.rest.entity.UserDetails;
import com.example.bank.rest.util.RoleEnum;

import java.util.List;

public class UserDetailsUtil {

    public static List<UserDetails> getAllUserDetails(){
        UserDetails userOne = UserDetails.builder()
            .id(1)
            .username("IVAN IVANOV")
            .login("userIvan")
            .password("passwordIvan")
            .role(RoleEnum.USER.getValue())
            .build();

        UserDetails userTwo = UserDetails.builder()
            .id(2)
            .username("OLEG IVANOV")
            .login("userOleg")
            .password("passwordOleg")
            .role(RoleEnum.USER.getValue())
            .build();

        return List.of(userOne, userTwo);
    }
}
