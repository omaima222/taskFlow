package com.example.taskflow.entities;

import jakarta.persistence.*;
import lombok.*;

import javax.management.ConstructorParameters;
import java.util.List;

@Setter
@Getter
@Entity
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToMany(mappedBy = "tags")
    private List<Task> tasks;
}
