package com.example.taskflow.mappers.interfaces;

import com.example.taskflow.dtos.request.TaskRequestDto;
import com.example.taskflow.dtos.response.TaskResponseDto;
import com.example.taskflow.entities.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    @Mapping(target = "tags", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "assingedTo", ignore = true)
    Task requestDtoToEntity(TaskRequestDto taskRequestDto);

    TaskResponseDto entityToResponseDto(Task task);
}
