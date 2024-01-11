package com.example.taskflow.mappers.interfaces;

import com.example.taskflow.dtos.UserDto;
import com.example.taskflow.dtos.request.TaskRequestDto;
import com.example.taskflow.dtos.response.TaskResponseDto;
import com.example.taskflow.entities.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    @Mapping(target = "tags", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "assignedTo", ignore = true)
    Task requestDtoToEntity(TaskRequestDto taskRequestDto);

    @Mapping(target = "createdBy", source = "createdBy")
    @Mapping(target = "assignedTo", source = "assignedTo")
    @Mapping(target = "tags", source = "tags")
    TaskResponseDto entityToResponseDto(Task task);
}
