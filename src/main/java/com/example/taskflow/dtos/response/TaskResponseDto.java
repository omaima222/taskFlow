package com.example.taskflow.dtos.response;

import com.example.taskflow.dtos.TagDto;
import com.example.taskflow.dtos.UserDto;
import com.example.taskflow.entities.Tag;
import com.example.taskflow.enums.TaskPriority;
import com.example.taskflow.enums.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
public class TaskResponseDto {
    Long id;

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
    List<TagDto> tags;

    @NotNull
    UserDto createdBy;

    UserDto assignedTo;
}
