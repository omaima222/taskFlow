package com.example.taskflow.entities;

import com.example.taskflow.enums.Role;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private Long replacementJetonsNum;
    private Role role;

    @OneToMany(mappedBy = "assignedTo")
    private List<Task> assignedTasks;

    @OneToMany(mappedBy = "createdBy")
    private List<Task> createdTasks;

    @OneToMany(mappedBy = "user")
    private List<Jeton> jetons;

}
