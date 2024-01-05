package com.example.taskflow.controllers;

import com.example.taskflow.dtos.request.TaskRequestDto;
import com.example.taskflow.dtos.response.TaskResponseDto;
import com.example.taskflow.entities.Task;
import com.example.taskflow.services.interfaces.TaskService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.ValidationException;
import java.util.List;

@RestController
@RequestMapping("api/task")
public class TaskController {
     TaskService taskService;
     TaskController(TaskService taskService){this.taskService=taskService;}

     @GetMapping("")
     public List<TaskResponseDto> getAll(){
          return this.taskService.getAll();
     }

     @PostMapping("")
     public Task add(@RequestBody TaskRequestDto taskRequestDto) throws ValidationException{
           return this.taskService.save(taskRequestDto, null);
     }

     @PutMapping("/{id}")
     public Task update(@RequestBody TaskRequestDto taskRequestDto, @PathVariable Long id) throws ValidationException {
          return this.taskService.save(taskRequestDto, id);
     }

     @DeleteMapping("/{id}")
     public void delete(@PathVariable Long id, @RequestBody @Valid long user_id) throws ValidationException{
          this.taskService.delete(id, user_id);
     }

     @PostMapping("replace/{id}")
     public void replace(@PathVariable Long id, @RequestBody @Valid long user_id) throws ValidationException{
          this.taskService.replace(id, user_id);
     }

     @PostMapping("assign/{id}")
     public void assign(@PathVariable Long id, @RequestBody long user_id, @RequestBody Long to_user_id) throws ValidationException{
          this.taskService.assign(id, user_id, to_user_id);
     }

}
