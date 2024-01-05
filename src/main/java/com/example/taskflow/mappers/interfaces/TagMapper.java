package com.example.taskflow.mappers.interfaces;

import com.example.taskflow.dtos.TagDto;
import com.example.taskflow.entities.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TagMapper {
    @Mapping(target = "name", source = "name")
    @Mapping(target = "id", source = "id")
    Tag dtoToEntity(TagDto tagDto);

    @Mapping(target = "name", source = "name")
    @Mapping(target = "id", source = "id")
    TagDto EntityToDto(Tag tag);
}
