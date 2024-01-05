package com.example.taskflow.dtos.request;

import com.example.taskflow.entities.User;
import com.example.taskflow.enums.TaskPriority;
import com.example.taskflow.enums.TaskStatus;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
public class TaskRequestDto {
    @NotNull
    @NotEmpty
    @NotBlank
    String name;

    @NotNull
    @NotEmpty
    @NotBlank
    String description;

    @NotNull
    TaskPriority priority;

    @NotNull
    TaskStatus status;

    @NotNull
    LocalDate debutDate;

    @NotNull
    LocalDate deadline;

    @NotNull
    @Size(min = 2)
    List<String> tags;

    @NotNull
    Long created_by_id;

    Long assigned_to_id;
}
