package com.example.taskflow.dtos.response;

import com.example.taskflow.entities.Task;
import com.example.taskflow.entities.User;
import com.example.taskflow.enums.JetonStatus;
import com.example.taskflow.enums.JetonType;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class JetonResponseDto {
    Long id;

    @NotNull
    User user;

    @NotNull
    Task task;

    @NotNull
    JetonType type;

    @NotNull
    JetonStatus status;

    @NotNull
    LocalDate createdAt;
}
