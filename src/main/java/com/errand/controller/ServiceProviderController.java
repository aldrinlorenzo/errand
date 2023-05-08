package com.errand.controller;

import com.errand.dto.PendingTaskDto;
import com.errand.dto.ServiceProviderForUpdateDto;
import com.errand.services.ServiceProviderService;
import com.errand.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/serviceProvider")
public class ServiceProviderController {
    @Autowired
    private TaskService taskService;

    @Autowired
    private ServiceProviderService serviceProviderService;

    @GetMapping("/{serviceProviderId}/dashboard")
    public String getServiceProviderDetails(@PathVariable Long serviceProviderId, Model model) {
        // Get service provider by ID

        ServiceProviderForUpdateDto serviceProviderDto = serviceProviderService.getServiceProviderById(serviceProviderId).orElseThrow(() -> new IllegalArgumentException("Invalid service provider ID: " + serviceProviderId));
        model.addAttribute("serviceProvider", serviceProviderDto);

        // Get pending tasks for service provider
        List<PendingTaskDto> pendingTasks = taskService.getPendingTask();
        model.addAttribute("pendingTasks", pendingTasks);

        return "serviceProviderDetails";
    }
}

//    @GetMapping("/{serviceProviderId}/task")
//
//    public String getPendingTask(Model model){
//        List<PendingTaskDto> tasks = taskService.getPendingTask();
//        model.addAttribute("tasks", tasks);
//        return "pending-tasks";
//
//    }




