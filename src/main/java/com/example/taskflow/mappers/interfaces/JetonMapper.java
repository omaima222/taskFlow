package com.example.taskflow.mappers.interfaces;

import com.example.taskflow.dtos.request.JetonRequestDto;
import com.example.taskflow.dtos.response.JetonResponseDto;
import com.example.taskflow.entities.Jeton;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface JetonMapper {
    @Mapping(target = "type", source = "type" )
    Jeton requestDtoToEntity(JetonRequestDto jetonRequestDto);

    @Mapping(target = "user", source = "user")
    @Mapping(target = "task", source = "task")
    JetonResponseDto entityToResponseDto(Jeton jeton);
}
