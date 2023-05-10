package com.errand.controller;

import com.errand.dto.ClientDto;
import com.errand.dto.PendingTaskDto;
import com.errand.dto.ServiceProviderDto;
import com.errand.dto.TaskDto;
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
        int clientCount = clientService.findAllClients().size();
        int serviceProviderCount = serviceProviderService.getAllServiceProvider().size();
        int pendingTaskCount = taskService.getPendingTask().size();
        int ongoingTaskCount = taskService.getOngoingTask().size();
        int completedTaskCount = taskService.getCompletedTaskOnAdmin().size();
        int cancelledTaskCount = taskService.getCancelledTaskOnAdmin().size();

        model.addAttribute("user", userService.getCurrentUser());
        model.addAttribute("clientCount", clientCount);
        model.addAttribute("serviceProviderCount", serviceProviderCount);
        model.addAttribute("pendingTaskCount", pendingTaskCount);
        model.addAttribute("ongoingTaskCount", ongoingTaskCount);
        model.addAttribute("completedTaskCount", completedTaskCount);
        model.addAttribute("cancelledTaskCount", cancelledTaskCount);

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

    @GetMapping("/tasks/pending-tasks")
    public String getPendingTasks(Model model){
        List<PendingTaskDto> tasks = taskService.getPendingTask();
        model.addAttribute("user", userService.getCurrentUser());
        model.addAttribute("tasks", tasks);
        return "admin-tasks";
    }

    @GetMapping("/tasks/ongoing-tasks")
    public String getOngoingTasks(Model model){
        List<TaskDto> tasks = taskService.getOngoingTask();
        model.addAttribute("user", userService.getCurrentUser());
        model.addAttribute("tasks", tasks);
        return "admin-tasks";
    }

    @GetMapping("/tasks/completed-tasks")
    public String getCompletedTasksOnAdmin(Model model){
        List<TaskDto> tasks = taskService.getCompletedTaskOnAdmin();
        model.addAttribute("user", userService.getCurrentUser());
        model.addAttribute("tasks", tasks);
        return "admin-tasks";
    }

    @GetMapping("/tasks/cancelled-tasks")
    public String getCancelledTasks(Model model){
        List<TaskDto> tasks = taskService.getCancelledTaskOnAdmin();
        model.addAttribute("user", userService.getCurrentUser());
        model.addAttribute("tasks", tasks);
        return "admin-tasks";
    }

    @GetMapping("/serviceProviders")
    public String getAllServiceProviders(Model model){
        Users user = new Users();
        List<ServiceProviderDto> serviceProviders = serviceProviderService.getAllServiceProvider();
        String username = SecurityUtil.getSessionUser();
        if(username != null){
            user = userService.findByUsername(username);
            model.addAttribute("user", user);
        }
        model.addAttribute("user", user);
        model.addAttribute("serviceProviders", serviceProviders);
        return "admin-serviceproviders";
    }

}
