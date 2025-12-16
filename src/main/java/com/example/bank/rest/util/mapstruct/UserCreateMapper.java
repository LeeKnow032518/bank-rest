package com.example.bank.rest.util.mapstruct;

import com.example.bank.rest.dto.users.UserCreateDto;
import com.example.bank.rest.entity.UserDetails;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserCreateMapper {
    UserCreateMapper INSTANCE = Mappers.getMapper(UserCreateMapper.class);

    UserCreateDto UserDetailsToUserCreateDto(UserDetails user);
    UserDetails UserCreateDtoToUserDetails(UserCreateDto userDto);
}
