package com.example.taskflow.services.interfaces;

import com.example.taskflow.dtos.request.JetonRequestDto;
import com.example.taskflow.dtos.response.JetonResponseDto;
import com.example.taskflow.entities.Jeton;

import javax.xml.bind.ValidationException;
import java.util.List;

public interface JetonService {
    List<JetonResponseDto> getAll();
    Jeton findById(Long id);
    JetonResponseDto save(JetonRequestDto jetonRequestDto) throws ValidationException;
    void delete(Long id);
}
