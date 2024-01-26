package com.example.taskflow.controllers;

import com.example.taskflow.dtos.IdsRequest;
import com.example.taskflow.dtos.UserDto;
import com.example.taskflow.dtos.response.TaskResponseDto;
import com.example.taskflow.entities.User;
import com.example.taskflow.services.interfaces.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    UserService userService;

    UserController(UserService userService){this.userService=userService;}

    @GetMapping("")
    public List<UserDto> getAll(){return  this.userService.getAll();}

    @PostMapping("")
    public ResponseEntity<UserDto> add(@RequestBody @Valid UserDto userDto){
        UserDto user = this.userService.save(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> update(@PathVariable Long id, @RequestBody @Valid UserDto userDto){
        userDto.setId(id);
        UserDto user = this.userService.save(userDto);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        this.userService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body("User successfully deleted !");
    }

}
