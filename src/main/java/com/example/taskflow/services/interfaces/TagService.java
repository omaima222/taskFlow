package com.example.taskflow.services.interfaces;

import com.example.taskflow.dtos.TagDto;
import com.example.taskflow.entities.Tag;

import javax.xml.bind.ValidationException;
import java.util.List;

public interface TagService {
    List<TagDto> getAll();
    Tag findById(Long id);
    Tag findByName(String name);
    TagDto save(TagDto tagDto);
    void delete(Long id);
}
