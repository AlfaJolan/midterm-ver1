package com.taskManager.Controllers;
import com.taskManager.DB.Task;
import com.taskManager.Repositories.TaskRepository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("api/v1/tasks")
public class APIController {
    private final TaskRepository taskRepository;

    public APIController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @GetMapping(value = "/active")
    public List<Task> getActiveTasks(){
        return taskRepository.findAllByStatus("active");
    }
    @GetMapping(value = "/done")
    public List<Task> getDoneTasks(){
        return taskRepository.findAllByStatus("done");
    }
    @GetMapping(value = "/all")
    public List<Task> getTasks(){
        return taskRepository.findAll();
    }
    record NewTaskRequest(
            String name,
            LocalDate start,
            LocalDate deadline,
            String description
    ) {

    }
    @PostMapping
    public void addTask(@RequestBody NewTaskRequest request) {
        Task task = new Task();
        task.setName(request.name);
        task.setStart(request.start);
        task.setDeadline(request.deadline);
        task.setStatus("active");
        task.setDescription(request.description);
        taskRepository.save(task);
    }

}
