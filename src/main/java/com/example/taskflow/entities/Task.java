package com.example.taskflow.entities;
import com.example.taskflow.enums.TaskPriority;
import com.example.taskflow.enums.TaskStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private TaskPriority priority;
    private TaskStatus status;
    private LocalDate debutDate;
    private LocalDate deadline;


    @ManyToMany
    @JoinTable(
        name = "task_tags",
        joinColumns = @JoinColumn(name = "task_id"),
        inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<Tag> tags;

    @ManyToOne
    @JoinColumn(name = "assigned_user_id")
    private User assingedTo;

    @ManyToOne
    @JoinColumn(name = "creator_id")
    private User createdBy;

    @OneToMany(mappedBy = "task")
    private List<Jeton> jetons;
}
