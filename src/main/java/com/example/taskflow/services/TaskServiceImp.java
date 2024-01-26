package com.example.taskflow.services;

import com.example.taskflow.dtos.StaticsDto;
import com.example.taskflow.dtos.TagDto;
import com.example.taskflow.dtos.request.JetonRequestDto;
import com.example.taskflow.dtos.request.TaskRequestDto;
import com.example.taskflow.dtos.response.TaskResponseDto;
import com.example.taskflow.entities.Tag;
import com.example.taskflow.entities.Task;
import com.example.taskflow.entities.User;
import com.example.taskflow.enums.JetonType;
import com.example.taskflow.enums.Role;
import com.example.taskflow.enums.TaskStatus;
import com.example.taskflow.mappers.interfaces.TagMapper;
import com.example.taskflow.mappers.interfaces.TaskMapper;
import com.example.taskflow.repositories.TaskRepository;
import com.example.taskflow.services.interfaces.JetonService;
import com.example.taskflow.services.interfaces.TagService;
import com.example.taskflow.services.interfaces.TaskService;
import com.example.taskflow.services.interfaces.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import jakarta.validation.ValidationException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskServiceImp implements TaskService {
    TaskRepository taskRepository;
    UserService userService;
    TaskMapper taskMapper;
    TagMapper tagMapper;
    TagService tagService;
    JetonService jetonService;

    TaskServiceImp(TaskRepository taskRepository, TaskMapper taskMapper, TagMapper tagMapper, UserService userService, JetonService jetonService, TagService tagService) {
        this.taskRepository=taskRepository;
        this.taskMapper=taskMapper;
        this.tagMapper=tagMapper;
        this.userService=userService;
        this.jetonService=jetonService;
        this.tagService= tagService;
    }

    public List<TaskResponseDto> getAll(){
        List<Task> tasks = this.taskRepository.findAll();
        return tasksToDto(tasks);
    }

    public Task findById(Long id){
        Optional<Task> task = this.taskRepository.findById(id);
        if(task.isPresent()) return task.get();
        else throw new EntityNotFoundException("Task not found !");
    }

    public Task save(TaskRequestDto taskRequestDto, Long id) throws ValidationException{
        Task task = taskMapper.requestDtoToEntity(taskRequestDto);
        LocalDate today = LocalDate.now();
        if(task.getDebutDate().isBefore(today) || task.getDebutDate().isAfter(today.plusDays(3))){
            throw new ValidationException("Debut date should be max 3 days from now !");
        }
        if(task.getDeadline().isBefore(task.getDebutDate())){
            throw new ValidationException("Deadline must be after the debut date !");
        }
        if(taskRequestDto.getAssigned_to_id()!=null){
            User assignedUser = this.userService.findById(taskRequestDto.getAssigned_to_id());
            task.setAssignedTo(assignedUser);
        }
        User creator = this.userService.findById(taskRequestDto.getCreated_by_id());
        task.setCreatedBy(creator);
        List<Tag> tagList = taskRequestDto.getTags().stream().map(t -> {
            Tag tag = this.tagService.findByName(t);
            if(tag==null){
                tag = new Tag();
                tag.setName(t);
                TagDto tagDto = this.tagService.save(tagMapper.EntityToDto(tag));
                tag = tagMapper.dtoToEntity(tagDto);
            }return tag;}).collect(Collectors.toList());
        task.setTags(tagList);
        if(LocalDate.now().isAfter(task.getDeadline())){
            task.setStatus(TaskStatus.NEGLECTED);
        }
        if(id!=null) task.setId(id);
        return this.taskRepository.save(task);
    }

    public void delete(Long task_id, Long user_id) throws ValidationException{
        Task task = this.findById(task_id);
        User user = this.userService.findById(user_id);
        if(task.getCreatedBy()==user) this.taskRepository.delete(task);
        else{
            JetonRequestDto jetonRequestDto = new JetonRequestDto();
            jetonRequestDto.setTask_id(task.getId());
            jetonRequestDto.setType(JetonType.DELETE);
            jetonRequestDto.setUser_id(user_id);
            this.jetonService.save(jetonRequestDto) ;
        }
    }

    public void replace(Long task_id, Long user_id) throws ValidationException{
        JetonRequestDto jetonRequestDto = new JetonRequestDto();
        jetonRequestDto.setTask_id(task_id);
        jetonRequestDto.setType(JetonType.REPLACE);
        jetonRequestDto.setUser_id(user_id);
        this.jetonService.save(jetonRequestDto);
    }

    public void assign(Long task_id, Long user_id, Long to_user_id) throws ValidationException{
        Task task = this.findById(task_id);
        User user = this.userService.findById(user_id);
        User toUser = this.userService.findById(to_user_id);
        if( user.getRole().equals(Role.MANAGER) || user==toUser) {
            task.setAssignedTo(toUser);
            this.taskRepository.save(task);
        }else{
            throw new ValidationException("You don't have the permission for this method !");
        }
    }

    public StaticsDto statics(String  per, String value){
        List<Object[]> result = null;
        StaticsDto staticsDto = new StaticsDto();
        if(per.equals("tag")){
            Tag tag = this.tagService.findByName(value);
            result = this.taskRepository.taskCountByStatusByTag(tag);
        }else if(per.equals("time")) {
            result = this.taskRepository.taskCountByStatusByTime(value);
        }
        if(result!=null){
            Long allCount = result.stream().mapToLong(o->(Long) o[1]).sum();
            result.stream().forEach(o -> {
                TaskStatus status = (TaskStatus) o[0];
                Long count = (Long) o[1];
                Long percentage = count * 100 / allCount;
                if (status.equals(TaskStatus.DONE)) staticsDto.setDone_tasks(percentage + "%");
                else if (status.equals(TaskStatus.TODO)) staticsDto.setTo_do_tasks(percentage + "%");
                else if (status.equals(TaskStatus.INPROGRESS)) staticsDto.setIn_progress_tasks(percentage + "%");
                else if (status.equals(TaskStatus.NEGLECTED)) staticsDto.setNeglected_tasks(percentage + "%");
            });
        }
        return staticsDto;
    }

    public List<TaskResponseDto> getAllMyCreatedTasks(Long user_id){
        User user = this.userService.findById(user_id);
        List<Task> tasks = this.taskRepository.getTasksByCreatedBy(user);
        return tasksToDto(tasks);
    }

    public List<TaskResponseDto> getAllMyAssignedTasks(Long user_id){
        User user = this.userService.findById(user_id);
        List<Task> tasks = this.taskRepository.getTasksByAssignedTo(user);
        return tasksToDto(tasks);
    }

    public List<TaskResponseDto> tasksToDto(List<Task> tasks){
        List<TaskResponseDto> taskDtos = tasks.stream().map(t-> taskMapper.entityToResponseDto(t)).collect(Collectors.toList());
        return taskDtos;
    }

    @Scheduled(cron= "0 0 0 * * ?")
    public void checkForNeglectedTasks(){
        List<Task> tasks = this.taskRepository.findAll();
        tasks.stream().forEach(t->{
            if(!t.getStatus().equals(TaskStatus.DONE)){
                if(LocalDate.now().isAfter(t.getDeadline())){

                    t.setStatus(TaskStatus.NEGLECTED);
                    this.taskRepository.save(t);
                }
            }
        });
    }

}
