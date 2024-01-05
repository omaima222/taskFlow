package com.example.taskflow.repositories;

import com.example.taskflow.entities.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    public Tag findTagByName(String name);
}
