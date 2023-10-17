package com.taskManager.Controllers;
import com.taskManager.DB.Task;
import com.taskManager.Repositories.TaskRepository;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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
    record UpdateTaskRequest(
            String name,
            String status,
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
    @DeleteMapping("{taskId}")
    public void deleteTask(@PathVariable("taskId") Integer id) {
        taskRepository.deleteById(id);
    }
    @PutMapping("{customerId}")
    public void updateCustomer(@PathVariable("customerId") Integer id,
                               @RequestBody UpdateTaskRequest request){
        Task task = taskRepository.getReferenceById(id);
        if(request.name != null) task.setName(request.name);
        if(request.start != null) task.setStart(request.start);
        if(request.deadline != null) task.setDeadline(request.deadline);
        if(request.description != null) task.setDescription(request.description);
        if(request.status != null) task.setStatus(request.status);
        taskRepository.save(task);
    }
}
