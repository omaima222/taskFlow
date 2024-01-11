package com.example.taskflow.services;

import com.example.taskflow.dtos.request.JetonRequestDto;
import com.example.taskflow.dtos.response.JetonResponseDto;
import com.example.taskflow.entities.Jeton;
import com.example.taskflow.entities.Task;
import com.example.taskflow.entities.User;
import com.example.taskflow.enums.JetonStatus;
import com.example.taskflow.enums.JetonType;
import com.example.taskflow.enums.Role;
import com.example.taskflow.mappers.interfaces.JetonMapper;
import com.example.taskflow.repositories.JetonRepository;
import com.example.taskflow.repositories.TaskRepository;
import com.example.taskflow.services.interfaces.JetonService;
import com.example.taskflow.services.interfaces.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.scheduling.annotation.Scheduled;
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
    UserService userService;
    TaskRepository taskRepository;

    JetonServiceImp(JetonRepository jetonRepository,TaskRepository taskRepository, JetonMapper jetonMapper, UserService userService) {
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
        jeton.setCreatedAt(LocalDate.now());
        User user = this.userService.findById(jetonRequestDto.getUser_id());
        jeton.setUser(user);
        Optional<Task> task = this.taskRepository.findById(jetonRequestDto.getTask_id());
        if(!task.isPresent()) throw new EntityNotFoundException("Task not found !");
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
            if(existingJetons.size()<user.getReplacementJetonsNum()) {
                 jeton.setTask(task.get());
                 this.jetonRepository.save(jeton);
            }
            else throw new ValidationException("You already used your 'replacement jetons' for today!");
        }
        JetonResponseDto jetonResponseDto = jetonMapper.entityToResponseDto(jeton);
        return jetonResponseDto;
    }

    public void delete(Long id){
        Jeton jeton = this.findById(id);
        this.jetonRepository.delete(jeton);
    }

    public void handleDemand(Long id, Long user_id, Long to_user_id, String status) throws ValidationException{
        User user = this.userService.findById(user_id);
        if(!user.getRole().equals(Role.MANAGER)) throw new ValidationException("You do not have the permission to use this method !");
        Jeton jeton = this.findById(id);
        if(!jeton.getType().equals(JetonType.REPLACE)) throw new ValidationException("This 'jeton' isn't of type 'replace' !");
        if(status.equals("accept")) {
            if (to_user_id == null) throw new ValidationException("The new assigned user id is essential!");
            User assignedTo = this.userService.findById(to_user_id);
            jeton.getTask().setAssignedTo(assignedTo);
            jeton.setStatus(JetonStatus.ACCEPTED);
            this.jetonRepository.save(jeton);
        }else if (status.equals("decline")){
            jeton.setStatus(JetonStatus.DECLINED);
            this.jetonRepository.save(jeton);
        }
    }

    @Scheduled(cron = "0 0 0/12 * * ?")
    public void checkForUnansweredReplacementDemands(){
        List<Jeton> jetons = this.jetonRepository.findAll();
        for(Jeton jeton : jetons){
            if(jeton.getType().equals(JetonType.REPLACE)){
                if(jeton.getStatus()==null){
                    jeton.getUser().setReplacementJetonsNum(jeton.getUser().getReplacementJetonsNum()+1);
                    jeton.setStatus(JetonStatus.NEGLECTED);
                }
            }
        }
    }
}
