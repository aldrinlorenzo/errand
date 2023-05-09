package com.errand.controller;

import com.errand.dto.TaskDto;
import com.errand.models.Client;
import com.errand.models.Task;
import com.errand.models.Users;
import com.errand.security.SecurityUtil;
import com.errand.services.ClientService;
import com.errand.services.TaskService;
import com.errand.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/client")
public class ClientController {

    private UserService userService;
    private ClientService clientService;
    private TaskService taskService;

    @Autowired
    public ClientController(UserService userService, ClientService clientService, TaskService taskService) {
        this.userService = userService;
        this.clientService = clientService;
        this.taskService = taskService;
    }

    @GetMapping("/dashboard")
    public String getClientDashboard(Model model){
        model.addAttribute("client", clientService.getCurrentClient());
        return "client-dashboard";
    }

    @GetMapping("/tasks")
    public String getClientTasks(Model model){
        List<TaskDto> tasks = taskService.getTasksByClient(clientService.getCurrentClient());
        model.addAttribute("tasks", tasks);
        model.addAttribute("client", clientService.getCurrentClient());
        return "client-tasks-list";
    }

    @GetMapping("/tasks/new")
    public String createTaskForm(Model model){
        Task task = new Task();
        model.addAttribute("client", clientService.getCurrentClient());
        model.addAttribute("task", task);
        return "client-tasks-create";
    }

    @GetMapping("/tasks/{taskId}/edit")
    public String editTaskForm(@PathVariable("taskId") Long taskId, Model model){
        TaskDto task = taskService.findTaskById(taskId);
        model.addAttribute("client", clientService.getCurrentClient());
        model.addAttribute("task", task);
        return "client-tasks-edit";
    }

    @GetMapping("/tasks/{taskId}/cancel")
    public String cancelTask(@PathVariable("taskId") Long taskId){
        taskService.cancelTask(taskId);
        return "redirect:/client/tasks";
    }

    @PostMapping("/tasks/new")
    public String saveTask(@Valid @ModelAttribute("task") TaskDto taskDto,
                           BindingResult result, Model model){
        if(result.hasErrors()){
            model.addAttribute("client", clientService.getCurrentClient());
            model.addAttribute("task", taskDto);
            return "client-tasks-create";
        }
        taskService.saveTask(taskDto);
        return "redirect:/client/tasks";
    }

    @PostMapping("/tasks/{taskId}/edit")
    public String updateTask(@PathVariable("taskId") Long taskId,
                             @Valid @ModelAttribute("task") TaskDto task,
                             BindingResult result, Model model){
        if(result.hasErrors()){
            model.addAttribute("task", task);
            return "client-tasks-edit";
        }
        task.setId(taskId);
        taskService.updateTask(task, clientService.getCurrentClient());
        return "redirect:/client/tasks";
    }

}
