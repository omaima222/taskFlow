package com.example.taskflow.controllers;

import com.example.taskflow.dtos.IdsRequest;
import com.example.taskflow.dtos.UserDto;
import com.example.taskflow.dtos.response.TaskResponseDto;
import com.example.taskflow.entities.User;
import com.example.taskflow.services.interfaces.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    UserService userService;

    UserController(UserService userService){this.userService=userService;}

    @GetMapping("")
    public List<UserDto> getAll(){return  this.userService.getAll();}

    @GetMapping("/{id}")
    public User findById(@PathVariable Long id){return  this.userService.findById(id);}

    @PostMapping("")
    public UserDto add(@RequestBody @Valid UserDto userDto){
        return this.userService.save(userDto);
    }

    @PutMapping("/{id}")
    public UserDto update(@PathVariable Long id, @RequestBody @Valid UserDto userDto){
        userDto.setId(id);
        return this.userService.save(userDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        this.userService.delete(id);
    }

}
