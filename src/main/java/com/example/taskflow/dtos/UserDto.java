package com.example.taskflow.dtos;

import com.example.taskflow.enums.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.*;

@Setter
@Getter
public class UserDto {
    Long id;

    @NotNull
    @NotEmpty
    @NotBlank
    String firstName;

    @NotNull
    @NotEmpty
    @NotBlank
    String lastName;

    @NotNull
    Role role;

    Long replacementJetonsNum = 0L;
}
