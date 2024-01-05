package com.example.taskflow.services;

import com.example.taskflow.dtos.request.JetonRequestDto;
import com.example.taskflow.dtos.response.JetonResponseDto;
import com.example.taskflow.entities.Jeton;
import com.example.taskflow.entities.Task;
import com.example.taskflow.entities.User;
import com.example.taskflow.enums.JetonType;
import com.example.taskflow.mappers.interfaces.JetonMapper;
import com.example.taskflow.repositories.JetonRepository;
import com.example.taskflow.repositories.TaskRepository;
import com.example.taskflow.services.interfaces.JetonService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import javax.xml.bind.ValidationException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JetonServiceImp implements JetonService {
    JetonRepository jetonRepository;
    JetonMapper jetonMapper;
    UserServiceImp userService;
    TaskRepository taskRepository;

    JetonServiceImp(JetonRepository jetonRepository,TaskRepository taskRepository, JetonMapper jetonMapper, UserServiceImp userService) {
        this.userService=userService;
        this.jetonMapper=jetonMapper;
        this.jetonRepository=jetonRepository;
        this.taskRepository=taskRepository;
    }

    public List<JetonResponseDto> getAll(){
        List<Jeton> jetons = this.jetonRepository.findAll();
        List<JetonResponseDto> jetonResponseDtoList = jetons.stream().map(jetonMapper::entityToResponseDto).collect(Collectors.toList());
        return jetonResponseDtoList;
    }

    public Jeton findById(Long id){
        Optional<Jeton> jeton = this.jetonRepository.findById(id);
        if(jeton.isPresent()) return jeton.get();
        else throw new EntityNotFoundException("Jeton not found !");
    }

    public JetonResponseDto save(JetonRequestDto jetonRequestDto) throws ValidationException{
        Jeton jeton = jetonMapper.requestDtoToEntity(jetonRequestDto);
        User user = this.userService.findById(jetonRequestDto.getUser_id());
        Optional<Task> task = this.taskRepository.findById(jetonRequestDto.getTask_id());
        if(task.isPresent()) jeton.setTask(task.get());
        else throw new EntityNotFoundException("Task not found !");
        JetonType type = jetonRequestDto.getType();
        if(type.equals(JetonType.DELETE)){
            List<Jeton> existingJetons = this.jetonRepository.getJetonsByUserAndType(user, type).stream().filter(t->t.getCreatedAt().getMonth() == LocalDate.now().getMonth()).collect(Collectors.toList());
            if(existingJetons.size()<1) {
                this.jetonRepository.save(jeton);
                this.taskRepository.delete(task.get());
            }
            else throw new ValidationException("You already used your '1 delete jeton' for this month!");
        }else if(type.equals(JetonType.REPLACE)){
            List<Jeton> existingJetons = this.jetonRepository.getJetonsByUserAndTypeAndCreatedAt(user, type, LocalDate.now());
            if(existingJetons.size()<2) this.jetonRepository.save(jeton);
            else throw new ValidationException("You already used your '2 replacement jeton' for today!");
        }
        JetonResponseDto jetonResponseDto = jetonMapper.entityToResponseDto(jeton);
        return jetonResponseDto;
    }

    public void delete(Long id){
        Jeton jeton = this.findById(id);
        this.jetonRepository.delete(jeton);
    }
}
