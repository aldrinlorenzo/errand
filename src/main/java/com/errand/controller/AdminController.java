package com.errand.controller;

import com.errand.dto.ClientDto;
import com.errand.dto.TaskDto;
import com.errand.models.Task;
import com.errand.models.Users;
import com.errand.security.SecurityUtil;
import com.errand.services.ClientService;
import com.errand.services.ServiceProviderService;
import com.errand.services.TaskService;
import com.errand.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private UserService userService;
    private ClientService clientService;
    private TaskService taskService;
    private ServiceProviderService serviceProviderService;

    @Autowired
    public AdminController(UserService userService, ClientService clientService, TaskService taskService, ServiceProviderService serviceProviderService) {
        this.userService = userService;
        this.clientService = clientService;
        this.taskService = taskService;
        this.serviceProviderService = serviceProviderService;
    }

    @GetMapping("/dashboard")
    public String getAdminDashboard(Model model){
        model.addAttribute("user", userService.getCurrentUser());
        return "admin-dashboard";
    }

    @GetMapping("/clients")
    public String getAdminClients(Model model){

        List<ClientDto> clients = clientService.findAllClients();
        model.addAttribute("user", userService.getCurrentUser());
        model.addAttribute("clients", clients);

        return "admin-clients";

    }

    @GetMapping("/tasks")
    public String getAllTasks(Model model){

        List<TaskDto> tasks = taskService.findAllTask();
        model.addAttribute("user", userService.getCurrentUser());
        model.addAttribute("tasks", tasks);

        return "admin-tasks";
    }

}
