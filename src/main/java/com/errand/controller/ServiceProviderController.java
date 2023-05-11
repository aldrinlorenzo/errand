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
    @GetMapping("/task")
    public String  getAllTask( Model model) {
        List<PendingTaskDto> pendingTaskDtoList = taskService.getPendingTask();
        model.addAttribute("serviceProvider",pendingTaskDtoList );
        return "serviceprovider-task";

    }

}





