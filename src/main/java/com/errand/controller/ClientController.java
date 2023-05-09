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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
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


}
