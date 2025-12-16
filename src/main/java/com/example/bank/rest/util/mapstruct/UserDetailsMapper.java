package com.example.bank.rest.util.mapstruct;

import com.example.bank.rest.dto.users.UserDto;
import com.example.bank.rest.entity.UserDetails;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserDetailsMapper {

    UserDetailsMapper INSTANCE = Mappers.getMapper(UserDetailsMapper.class);

    UserDto UserDetailsToUserDto(UserDetails details);
    UserDetails UserDtoToUserDetails(UserDto userDto);
}
