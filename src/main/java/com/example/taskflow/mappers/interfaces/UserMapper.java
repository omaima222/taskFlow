package com.example.taskflow.mappers.interfaces;

import com.example.taskflow.dtos.UserDto;
import com.example.taskflow.entities.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User dtoToEntity(UserDto userDto);
    UserDto entityToDto(User user);
}
