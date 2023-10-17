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
            LocalDate end
    ) {

    }
    @PostMapping
    public void addCustomer(@RequestBody NewTaskRequest request) {
        Task task = new Task();
        task.setName(request.name);
        task.setStart(request.start);
        task.setDeadline(request.end);
        task.setStatus("active");
        taskRepository.save(task);
    }
    /*
    @GetMapping(value = "/")
    public String index(Model model){
        ArrayList<Items> items = DBmanager.getItems();
        model.addAttribute("tovary",items);
        return "index";
    }
    @GetMapping(value = "/about")
    public String about(){
        return "about";
    }
    @PostMapping(value = "/additem")
    public String addItem(@RequestParam(name = "item_name", defaultValue = "No Item") String name,
                          @RequestParam(name = "item_price", defaultValue = "0") int price){
        DBmanager.addItems(new Items(null,name,price));
        return "redirect:/";
    }
    @GetMapping(value = "/details/{idshka}")
    public String details(Model model, @PathVariable(name = "idshka") Long id){
        Items item = DBmanager.getItem(id);
        model.addAttribute("item",item);
        return "details";
    }
     */
}
