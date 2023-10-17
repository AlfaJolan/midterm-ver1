package com.taskManager.Repositories;

import com.taskManager.DB.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Integer> {
    List<Task> findAllByStatus(String string);
}
