package com.errand.controller;

import com.errand.dto.ServiceProviderDto;
import com.errand.dto.ServiceProviderForUpdateDto;
import com.errand.services.ServiceProviderService;
import com.errand.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;

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

        ServiceProviderDto serviceProviderForDisplayDto = serviceProviderService.getCurrentServiceProvider();
        model.addAttribute("serviceProvider", serviceProviderForDisplayDto );
        return "serviceprovider-dashboard";
    }
    @GetMapping("/{serviceProviderId}/profile")
    public String  createUpdateProfileForm(@PathVariable Long serviceProviderId, Model model) {
        ServiceProviderDto serviceProviderForDisplayDto = serviceProviderService.getCurrentServiceProvider();
        serviceProviderId = serviceProviderForDisplayDto.getId();
        model.addAttribute("serviceProvider",serviceProviderForDisplayDto );
        return "serviceprovider-profile-edit";

    }
    @PostMapping("/{serviceProviderId}/profile/save")
    public String updateServiceProviderDetails(@PathVariable Long serviceProviderId, ServiceProviderDto serviceProviderDto, Model model) {
        try {
            boolean isUpdated = serviceProviderService.updateServiceProviderDetails(serviceProviderDto, serviceProviderId);

            if (isUpdated) {
                model.addAttribute("serviceProvider", serviceProviderDto);
                model.addAttribute("serviceProviderId", serviceProviderId);
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





