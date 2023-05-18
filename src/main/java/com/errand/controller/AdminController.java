package com.errand.controller;

import com.errand.dto.*;

import com.errand.models.Label;
import com.errand.models.Rating;
import com.errand.models.Users;
import com.errand.security.SecurityUtil;
import com.errand.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private UserService userService;
    private ClientService clientService;
    private TaskService taskService;
    private ServiceProviderService serviceProviderService;
    private RatingService ratingService;
    private LabelService labelService;

    @Autowired
    public AdminController(UserService userService, ClientService clientService, TaskService taskService, ServiceProviderService serviceProviderService, RatingService ratingService, LabelService labelService) {
        this.userService = userService;
        this.clientService = clientService;
        this.taskService = taskService;
        this.serviceProviderService = serviceProviderService;
        this.ratingService = ratingService;
        this.labelService = labelService;
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
    @GetMapping("/labels")
    public String getAdminLabels(Model model, LabelDto labelDto){
        Set<Label> labels = labelService.findAllLabels();
        model.addAttribute("user", userService.getCurrentUser());
        model.addAttribute("labels", labels);
        model.addAttribute("labelDto", labelDto);
        return "admin-labels";
    }
    @PostMapping("/labels-save")
    public String addAdminLabels(@ModelAttribute("labelDto")LabelDto labelDto, BindingResult result, Model model){
        if(result.hasErrors()){
            model.addAttribute("labelDto", labelDto);
            return "admin-labels";
        }
        model.addAttribute("labelDto", labelDto);
        model.addAttribute("user", userService.getCurrentUser());
        labelService.saveLabel(labelDto);
        return "redirect:/admin/labels";
    }

    @GetMapping("/labels-edit/{labelId}")
    public  String editAdminLabels(@PathVariable("labelId")Long labelId,
                                   @ModelAttribute("existingLabel")LabelDto existingLabel,
                                   BindingResult result, Model model) {

        if(result.hasErrors()){
            model.addAttribute("existingLabel", existingLabel);
            return "admin-label-edit";
        }
        existingLabel = labelService.findbyId(labelId);
        Set<Label> labels = labelService.findAllLabels();
        model.addAttribute("labels", labels);
        model.addAttribute("user", userService.getCurrentUser());
        model.addAttribute("existingLabel", existingLabel);
        return "admin-label-edit";
    }

    @PostMapping("/labels-edit/{labelId}/update")
    public  String saveEditedAdminLabels(@PathVariable("labelId")Long labelId,
                                   @ModelAttribute("existingLabel")LabelDto existingLabel,
                                   BindingResult result, Model model){
        if(result.hasErrors()){
            model.addAttribute("existingLabel", existingLabel);
            return "admin-label-edit";
        }
        existingLabel.setId(labelId);
        labelService.editLabel(existingLabel);
        return "redirect:/admin/labels";
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

    @GetMapping("/clients/search")
    public String getAdminClientsByName(@RequestParam("name")String name, Model model){
        List<ClientDto> clients = clientService.findByFirstNameIgnoreCase(name);
        if(clients == null || clients.isEmpty()){
            clients = clientService.findByLastNameIgnoreCase(name);
        }
        model.addAttribute("user", userService.getCurrentUser());
        model.addAttribute("clients", clients);

        return "admin-clients";
    }

    @GetMapping("/serviceproviders/search")
    public String getAdminServiceProvidersByName(@RequestParam("name")String name, Model model){
        List<ServiceProviderDto> serviceProviders = serviceProviderService.findByFirstNameIgnoreCase(name);
        if(serviceProviders == null || serviceProviders.isEmpty()){
            serviceProviders = serviceProviderService.findByLastNameIgnoreCase(name);
        }
        model.addAttribute("user", userService.getCurrentUser());
        model.addAttribute("serviceProviders", serviceProviders);

        return "admin-serviceproviders";
    }

    @GetMapping("/serviceProviders/{serviceProviderId}/ratings")
    public String getServiceProviderRatings(@PathVariable("serviceProviderId") Long serviceProviderId,
                                            Model model){
        ServiceProviderDto serviceProvider = serviceProviderService.getServiceProviderById(serviceProviderId);
        List<Rating> ratings = ratingService.getRatingByServiceProvider(serviceProvider);
        model.addAttribute("user", userService.getCurrentUser());
        model.addAttribute("serviceProvider", serviceProvider);
        model.addAttribute("ratings", ratings);
        return "admin-serviceproviders-ratings";
    }

}
