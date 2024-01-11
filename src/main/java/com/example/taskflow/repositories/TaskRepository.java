package com.example.taskflow.repositories;

import com.example.taskflow.entities.Tag;
import com.example.taskflow.entities.Task;
import com.example.taskflow.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    @Query("SELECT t.status, COUNT(t) as average FROM Task t where :tag in elements(t.tags)  GROUP BY t.status ")
    public  List<Object[]>  taskCountByStatusByTag(Tag tag);

    @Query("SELECT t.status, COUNT(t) as average FROM Task t WHERE " +
            "(:value = 'week' AND week(t.deadline) = week(current_date)) OR " +
            "(:value = 'month' AND month(t.deadline) = month(current_date)) OR " +
            "(:value = 'year' AND year(t.deadline) = year(current_date)) " +
            "GROUP BY t.status")
    public List<Object[]>  taskCountByStatusByTime(String value);
    public List<Task>  getTasksByAssignedTo(User user);
    public List<Task>  getTasksByCreatedBy(User user);
}
