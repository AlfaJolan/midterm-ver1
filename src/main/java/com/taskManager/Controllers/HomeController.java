package com.taskManager.Controllers;
import com.taskManager.DB.Task;
import com.taskManager.Repositories.TaskRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Controller
public class HomeController {
    private final TaskRepository taskRepository;

    public HomeController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @GetMapping(value = "/")
    public String index(Model model){
        List<Task> tasks = taskRepository.findAllByStatus("active");
        model.addAttribute("tasks", tasks);
        return "index";
    }
    @PostMapping(value = "/addtask")
    public String addTask(@RequestParam(name = "task_name", defaultValue = "Default") String name,
                          @RequestParam(name = "task_start", defaultValue = "0") String start,
                          @RequestParam(name = "task_deadline", defaultValue = "0") String end){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate startDate;
        LocalDate endDate;
        if(start.equals("0"))
        {
            startDate = LocalDate.now();
        }
        else{
           startDate  = LocalDate.parse(start, formatter);

        }
        if(end.equals("0"))
        {
            endDate = LocalDate.now().plusWeeks(1);
        }
        else{
            endDate  = LocalDate.parse(end, formatter);
        }
        Task task = new Task();
        task.setName(name);
        task.setStatus("active");
        task.setStart(startDate);
        task.setDeadline(endDate);
        taskRepository.save(task);
        return "redirect:/";
    }
    @PostMapping(value = "/savetask")
    public String saveTask(@RequestParam(name = "task_name", defaultValue = "Default") String name,
                          @RequestParam(name = "task_start", defaultValue = "0") String start,
                          @RequestParam(name = "task_deadline", defaultValue = "0") String end,
                          @RequestParam(name = "task_description") String description,
                          @RequestParam(name = "task_id", defaultValue ="0") Integer id,
                           @RequestParam(value = "markDone", required = false, defaultValue = "false") Boolean markDone
                         ){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate startDate;
        LocalDate endDate;
        if(start.equals("0"))
        {
            startDate = LocalDate.now();
        }
        else{
            startDate  = LocalDate.parse(start, formatter);

        }
        if(end.equals("0"))
        {
            endDate = LocalDate.now().plusWeeks(1);
        }
        else{
            endDate  = LocalDate.parse(end, formatter);
        }
        Task task = taskRepository.getReferenceById(id);
        task.setName(name);
        task.setStart(startDate);
        task.setDeadline(endDate);
        task.setDescription(description);
        if(markDone){
            task.setStatus("done");
            taskRepository.save(task);
            return "redirect:/";
        }
        else {
            task.setStatus("active");
            taskRepository.save(task);
            return "redirect:/details/" + id;
        }
    }
    @GetMapping(value = "/done/{idshka}")
    public String addItem(Model model, @PathVariable(name = "idshka") Integer id)
    {
        Task task = taskRepository.getReferenceById(id);
        task.setStatus("done");
        taskRepository.save(task);
        return "redirect:/";
    }
    @GetMapping(value = "/details/{idshka}")
    public String details(Model model, @PathVariable(name = "idshka") Integer id)
    {
        Task task = taskRepository.getReferenceById(id);
        model.addAttribute("task",task);
        return "details";
    }

}