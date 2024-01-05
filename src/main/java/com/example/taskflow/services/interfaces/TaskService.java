package com.example.taskflow.services.interfaces;

import com.example.taskflow.dtos.request.TaskRequestDto;
import com.example.taskflow.dtos.response.TaskResponseDto;
import com.example.taskflow.entities.Task;

import javax.xml.bind.ValidationException;
import java.util.List;

public interface TaskService {
    List<TaskResponseDto> getAll();
    Task findById(Long id);
    Task save(TaskRequestDto taskRequestDto, Long id)  throws ValidationException;
    void delete(Long task_id, Long user_id) throws ValidationException;
    void assign(Long task_id, Long user_id, Long to_user_id) throws ValidationException;
    void replace(Long task_id, Long user_id) throws ValidationException;
}
