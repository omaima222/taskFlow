package com.example.taskflow.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TagDto {
    Long id;

    @NotNull
    @NotEmpty
    @NotBlank
    String name;
}
