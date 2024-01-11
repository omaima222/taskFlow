package com.example.taskflow.services;

import com.example.taskflow.entities.*;
import com.example.taskflow.enums.Role;
import com.example.taskflow.repositories.TaskRepository;
import com.example.taskflow.services.interfaces.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;


import javax.xml.bind.ValidationException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TaskServiceImpTest {
    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private TaskServiceImp taskService;


    @Test
    public void testAssign_ManagerAssignsTask_Successfully() throws ValidationException {
        Long taskId = 1L;
        Long userId = 1L;
        Long toUserId = 2L;

        Task task = new Task();
        task.setId(taskId);

        User user = new User();
        user.setId(userId);
        user.setRole(Role.MANAGER);

        User toUser = new User();
        toUser.setId(toUserId);

        when(userService.findById(userId)).thenReturn(user);
        when(userService.findById(toUserId)).thenReturn(toUser);
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));

        taskService.assign(taskId, userId, toUserId);

        assertEquals(toUser, task.getAssignedTo());
        verify(taskRepository).save(task);
    }

    @Test
    public void testAssign_UserAssignsTaskToHimself_Successfully() throws ValidationException {
        Long taskId = 1L;
        Long userId = 1L;

        Task task = new Task();
        task.setId(taskId);

        User user = new User();
        user.setRole(Role.EMPLOYEE);
        user.setId(userId);

        User toUser = user;

        when(userService.findById(userId)).thenReturn(user);
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));

        taskService.assign(taskId, userId, toUser.getId());
        assertEquals(toUser, user);
        assertEquals(toUser, task.getAssignedTo());
        verify(taskRepository).save(task);
    }

    @Test
    public void testAssign_EmployeeAssignsTaskToSomeoneElse() throws ValidationException {
        Long taskId = 1L;
        Long userId = 1L;
        Long toUserId = 2L;

        Task task = new Task();
        task.setId(taskId);

        User user = new User();
        user.setId(userId);
        user.setRole(Role.EMPLOYEE);

        User toUser = new User();
        toUser.setId(toUserId);

        when(userService.findById(userId)).thenReturn(user);
        when(userService.findById(toUserId)).thenReturn(toUser);
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));

        assertThrows(ValidationException.class, () -> taskService.assign(taskId, userId, toUserId));

    }


}