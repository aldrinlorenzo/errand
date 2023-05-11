package com.errand.controller;

import com.errand.dto.PendingTaskDto;
import com.errand.dto.ServiceProviderDto;
import com.errand.dto.ServiceProviderForUpdateDto;
import com.errand.dto.TaskDto;
import com.errand.services.ServiceProviderService;
import com.errand.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Controller
@RequestMapping("/serviceProvider")
public class ServiceProviderController {

    private TaskService taskService;
    private ServiceProviderService serviceProviderService;

    @Autowired
    public ServiceProviderController(TaskService taskService, ServiceProviderService serviceProviderService) {
        this.taskService = taskService;
        this.serviceProviderService = serviceProviderService;
    }

    @GetMapping("/dashboard")
    public String getServiceProviderDetails( Model model) {

        ServiceProviderDto serviceProviderForDisplayDto = serviceProviderService.getCurrentServiceProvider();
        model.addAttribute("serviceProvider", serviceProviderForDisplayDto );
        return "serviceprovider-dashboard";
    }
    @GetMapping("/profile")
    public String  createUpdateProfileForm( Model model) {
        ServiceProviderDto serviceProviderForDisplayDto = serviceProviderService.getCurrentServiceProvider();
        model.addAttribute("serviceProvider",serviceProviderForDisplayDto );
        return "serviceprovider-profile-edit";

    }
    @GetMapping("/tasks/pending-tasks")
    public String  getAllTask(Model model) {
        ServiceProviderDto serviceProviderForDisplayDto = serviceProviderService.getCurrentServiceProvider();
        List<PendingTaskDto> pendingTaskDtoList = taskService.getPendingTask();
        model.addAttribute("serviceProvider", serviceProviderForDisplayDto);
        model.addAttribute("taskList",pendingTaskDtoList);
        return "serviceprovider-tasks-list";
    }

    @GetMapping("/tasks")
    public String getServiceProviderTasks(Model model) {
        ServiceProviderDto serviceProviderForDisplayDto = serviceProviderService.getCurrentServiceProvider();
        List<TaskDto> taskDtoList = taskService.findTaskByServiceProvider(
                serviceProviderService.getCurrentServiceProvider().getId());
        model.addAttribute("serviceProvider", serviceProviderForDisplayDto);
        model.addAttribute("taskList", taskDtoList);
        return "serviceprovider-tasks-list";
    }

    @PostMapping("/profile/save")
    public String updateServiceProviderDetails( ServiceProviderDto serviceProviderDto, Model model) {
        try {
            boolean isUpdated = serviceProviderService.updateServiceProviderDetails(serviceProviderDto, serviceProviderService.getCurrentServiceProvider().getId());

            if (isUpdated) {
                model.addAttribute("serviceProvider", serviceProviderDto);
                model.addAttribute("serviceProviderId",  serviceProviderService.getCurrentServiceProvider().getId());
                return "serviceprovider-dashboard";
            } else {
                return "error";
            }
        } catch (EntityNotFoundException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "error";
        }
    }

}





