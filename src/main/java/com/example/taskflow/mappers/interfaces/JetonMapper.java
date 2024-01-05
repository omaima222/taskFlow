package com.example.taskflow.mappers.interfaces;

import com.example.taskflow.dtos.request.JetonRequestDto;
import com.example.taskflow.dtos.response.JetonResponseDto;
import com.example.taskflow.entities.Jeton;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface JetonMapper {
    Jeton requestDtoToEntity(JetonRequestDto jetonRequestDto);
    JetonResponseDto entityToResponseDto(Jeton jeton);
}
