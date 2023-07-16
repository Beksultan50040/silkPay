package com.example.silkpaytask.mapper;

import com.example.silkpaytask.dto.SignUpDto;
import com.example.silkpaytask.dto.UserDto;
import com.example.silkpaytask.entities.UserCredentials;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDto toUserDto(UserCredentials userCredentials);
    @Mapping(target = "password", ignore = true)
    UserCredentials signUpToUser(SignUpDto signUpDto);




}