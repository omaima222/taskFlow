package com.example.taskflow.mappers.interfaces;

import com.example.taskflow.dtos.UserDto;
import com.example.taskflow.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User dtoToEntity(UserDto userDto);

    UserDto entityToDto(User user);
}
