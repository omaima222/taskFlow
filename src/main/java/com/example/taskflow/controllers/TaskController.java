package com.example.taskflow.controllers;

import com.example.taskflow.dtos.IdsRequest;
import com.example.taskflow.dtos.StaticsDto;
import com.example.taskflow.dtos.request.TaskRequestDto;
import com.example.taskflow.dtos.response.TaskResponseDto;
import com.example.taskflow.services.interfaces.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

     @GetMapping("/{id}/my_created_tasks")
     public List<TaskResponseDto> getAllMyCreatedTasks(@PathVariable Long id){
          return taskService.getAllMyCreatedTasks(id);
     }

     @GetMapping("/{id}/my_assigned_tasks")
     public List<TaskResponseDto> getAllMyAssignedTasks(@PathVariable Long id){
          return taskService.getAllMyAssignedTasks(id);
     }

     @PostMapping("")
     public ResponseEntity<String> add(@RequestBody @Valid TaskRequestDto taskRequestDto){
          this.taskService.save(taskRequestDto, null);
          return ResponseEntity.status(HttpStatus.CREATED).body("Task created successfully");
     }

     @PutMapping("/{id}")
     public ResponseEntity<String> update(@RequestBody @Valid  TaskRequestDto taskRequestDto, @PathVariable Long id){
          taskRequestDto.setId(id);
          this.taskService.save(taskRequestDto, id);
          return ResponseEntity.status(HttpStatus.OK).body("Task updated successfully");
     }

     @DeleteMapping("/{id}/{userId}")
     public ResponseEntity<String> delete(@PathVariable Long id,  @PathVariable Long userId){
          this.taskService.delete(id, userId);
          return ResponseEntity.status(HttpStatus.OK).body("Task deleted successfully");
     }

     @PostMapping("replace/{id}")
     public ResponseEntity<String> replace(@PathVariable Long id, @RequestBody @Valid IdsRequest idsRequest){
          this.taskService.replace(id, idsRequest.getUser_id());
          return ResponseEntity.status(HttpStatus.OK).body("Task replaced successfully");
     }

     @PostMapping("assign/{id}")
     public ResponseEntity<String> assign(@PathVariable Long id, @RequestBody @Valid IdsRequest idsRequest){
          this.taskService.assign(id, idsRequest.getUser_id(), idsRequest.getTo_user_id());
          return ResponseEntity.status(HttpStatus.OK).body("Task assigned successfully");
     }

     @GetMapping("/statics/{per}/{value}")
     public StaticsDto statics(@PathVariable String value, @PathVariable String per){
          return this.taskService.statics(per, value);
     }
}
