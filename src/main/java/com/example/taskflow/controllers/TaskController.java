package com.example.taskflow.controllers;

import com.example.taskflow.dtos.IdsRequest;
import com.example.taskflow.dtos.StaticsDto;
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

     @GetMapping("/{id}")
     public Task findById(@PathVariable Long id){
          return this.taskService.findById(id);
     }

     @GetMapping("/{id}/my_created_tasks")
     public List<TaskResponseDto> getAllMyCreatedTasks(@PathVariable Long id){
          return taskService.getAllMyCreatedTasks(id);
     }

     @GetMapping("/{id}/my_assigned_tasks")
     public List<TaskResponseDto> getAllMyAssignedTasks(@PathVariable Long id){
          return taskService.getAllMyAssignedTasks(id);
     }


     @PostMapping("")
     public Task add(@RequestBody @Valid TaskRequestDto taskRequestDto) throws ValidationException{
           return this.taskService.save(taskRequestDto, null);
     }

     @PutMapping("/{id}")
     public Task update(@RequestBody @Valid  TaskRequestDto taskRequestDto, @PathVariable Long id) throws ValidationException {
          taskRequestDto.setId(id);
          return this.taskService.save(taskRequestDto, id);
     }

     @DeleteMapping("/{id}")
     public void delete(@PathVariable Long id, @RequestBody @Valid  IdsRequest idsRequest) throws ValidationException{
          this.taskService.delete(id, idsRequest.getUser_id());
     }

     @PostMapping("replace/{id}")
     public void replace(@PathVariable Long id, @RequestBody @Valid IdsRequest idsRequest) throws ValidationException{
          this.taskService.replace(id, idsRequest.getUser_id());
     }

     @PostMapping("assign/{id}")
     public void assign(@PathVariable Long id, @RequestBody @Valid IdsRequest idsRequest) throws ValidationException{
          this.taskService.assign(id, idsRequest.getUser_id(), idsRequest.getTo_user_id());
     }

     @GetMapping("/statics/{per}/{value}")
     public StaticsDto statics(@PathVariable String value, @PathVariable String per){
          return this.taskService.statics(per, value);
     }
}
