package com.example.taskflow.dtos.request;

import com.example.taskflow.enums.JetonStatus;
import com.example.taskflow.enums.JetonType;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class JetonRequestDto {
    Long id;

    @NotNull
    Long user_id;

    @NotNull
    Long task_id;

    @NotNull
    JetonType type;

    @NotNull
    LocalDate createdAt;
}
