package com.example.taskflow.services;

import com.example.taskflow.dtos.UserDto;
import com.example.taskflow.entities.User;
import com.example.taskflow.mappers.interfaces.UserMapper;
import com.example.taskflow.repositories.UserRepository;
import com.example.taskflow.services.interfaces.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImp implements UserService {
    UserRepository userRepository;
    UserMapper userMapper;

    UserServiceImp(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository=userRepository;
        this.userMapper=userMapper;
    }

    public List<UserDto> getAll(){
        List<User> users = this.userRepository.findAll();
        if(!users.isEmpty()){
            List<UserDto> userDtos = users.stream().map(x-> userMapper.entityToDto(x)).collect(Collectors.toList());
            return userDtos;
        }
        return null;
    }

    public User findById(Long id){
        Optional<User> user = this.userRepository.findById(id);
        if(user.isPresent()) return user.get();
        else throw new EntityNotFoundException("User not found !");
    }

    public UserDto save(UserDto userDto){
        User user = userMapper.dtoToEntity(userDto);
        return userMapper.entityToDto(this.userRepository.save(user));
    }

    public void delete(Long id){
        User user = this.findById(id);
        this.userRepository.delete(user);
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void updateReplacementJetonNum(){
        List<User> users = this.userRepository.findAll();
        users.stream().forEach(u->u.setReplacementJetonsNum(2L));
    }

}
