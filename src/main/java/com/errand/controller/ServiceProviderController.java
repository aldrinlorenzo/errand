package com.errand.controller;

import com.errand.dto.PendingTaskDto;
import com.errand.dto.ServiceProviderForDisplayDto;
import com.errand.dto.ServiceProviderForUpdateDto;
import com.errand.services.ServiceProviderService;
import com.errand.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    @GetMapping("/{serviceProviderId}/dashboard")
    public String getServiceProviderDetails(@PathVariable Long serviceProviderId, Model model) {

        ServiceProviderForDisplayDto serviceProviderForDisplayDto = serviceProviderService.getCurrentServiceProvider();
        model.addAttribute("serviceProvider", serviceProviderForDisplayDto );
//        List<PendingTaskDto> pendingTasks = taskService.getPendingTask();
//        model.addAttribute("pendingTasks", pendingTasks);

        return "serviceprovider-dashboard";
    }
    @PutMapping("/{serviceProviderId}/dashboard/")
    public String  updateServiceProviderDetails(@PathVariable Long serviceProviderId, @RequestBody ServiceProviderForUpdateDto serviceProviderDto, Model model) {
        boolean isUpdated = serviceProviderService.updateServiceProviderDetails(serviceProviderDto,serviceProviderId);

        if (isUpdated) {
            model.addAttribute("serviceProvider", serviceProviderDto);
            model.addAttribute("serviceProviderId", serviceProviderId);
            return "serviceprovider-dashboard";
        } else {
            model.addAttribute("errorMessage", "Failed to update service provider with ID: " + serviceProviderId);
            return "error";
        }

    }

}





