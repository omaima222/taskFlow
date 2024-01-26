package com.example.taskflow.services.interfaces;

import com.example.taskflow.dtos.UserDto;
import com.example.taskflow.entities.User;

import java.util.List;

public interface UserService {
    List<UserDto> getAll();
    User findById(Long id);
    UserDto save(UserDto userDto);
    void delete(Long id);
}
