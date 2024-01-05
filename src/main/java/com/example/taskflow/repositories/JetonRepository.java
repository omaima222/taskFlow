package com.example.taskflow.repositories;

import com.example.taskflow.entities.Jeton;
import com.example.taskflow.entities.User;
import com.example.taskflow.enums.JetonType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface JetonRepository extends JpaRepository<Jeton, Long> {
    List<Jeton> getJetonsByUserAndType(User user, JetonType type);
    List<Jeton> getJetonsByUserAndTypeAndCreatedAt(User user, JetonType type, LocalDate createdAt);
}
