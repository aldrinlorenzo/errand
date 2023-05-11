package com.errand.controller;

import com.errand.dto.OfferDto;
import com.errand.dto.PendingTaskDto;
import com.errand.dto.ServiceProviderDto;
import com.errand.dto.TaskDto;
import com.errand.services.OfferService;
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

    @Autowired
    private TaskService taskService;
    @Autowired
    private OfferService offerService;
    @Autowired
    private ServiceProviderService serviceProviderService;


    @GetMapping("/dashboard")
    public String getServiceProviderDetails(Model model) {
        setServiceProviderForDisplay(model);
        return "serviceprovider-dashboard";
    }

    @GetMapping("/profile")
    public String createUpdateProfileForm(Model model) {
        setServiceProviderForDisplay(model);
        return "serviceprovider-profile-edit";

    }

    @GetMapping("/tasks/pending-tasks")
    public String getAllTask(Model model) {
        List<PendingTaskDto> pendingTaskDtoList = taskService.getPendingTask();
        setServiceProviderForDisplay(model);
        model.addAttribute("taskList", pendingTaskDtoList);
        return "serviceprovider-tasks-list";
    }

    @GetMapping("/tasks")
    public String getServiceProviderTasks(Model model) {
        List<TaskDto> taskDtoList = taskService.findTaskByServiceProvider(
                serviceProviderService.getCurrentServiceProvider().getId());
        setServiceProviderForDisplay(model);
        model.addAttribute("taskList", taskDtoList);
        return "serviceprovider-tasks-list";
    }

    @PostMapping("/profile/save")
    public String updateServiceProviderDetails(ServiceProviderDto serviceProviderDto, Model model) {
        try {
            boolean isUpdated = serviceProviderService.updateServiceProviderDetails(serviceProviderDto, serviceProviderService.getCurrentServiceProvider().getId());

            if (isUpdated) {
                model.addAttribute("serviceProvider", serviceProviderDto);
                model.addAttribute("serviceProviderId", serviceProviderService.getCurrentServiceProvider().getId());
                return "serviceprovider-dashboard";
            } else {
                return "error";
            }
        } catch (EntityNotFoundException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "error";
        }
    }
//    @GetMapping("/tasks")
//    public String  getAllTask(Model model) {
//        ServiceProviderDto serviceProviderForDisplayDto = serviceProviderService.getCurrentServiceProvider();
//        List<PendingTaskDto> pendingTaskDtoList = taskService.getPendingTask();
//
//        model.addAttribute("serviceProvider", serviceProviderService.getCurrentServiceProvider());
//        model.addAttribute("pendingTaskDtoList",pendingTaskDtoList );
//        return "serviceprovider-task";
//
//    }

    @GetMapping("tasks/{taskId}/offers")
    public String getPendingTask(@PathVariable("taskId")Long taskId, Model model, OfferDto offerDto){
        TaskDto taskDto = taskService.findTaskById(taskId);

        model.addAttribute("task" ,taskDto);
        model.addAttribute("offer", offerDto);
        model.addAttribute("serviceProvider", serviceProviderService.getCurrentServiceProvider());
        return "serviceprovider-tasks-offer";
    }
    @PostMapping("tasks/{taskId}/offers/create")
    public String createOffer(@PathVariable("taskId")Long taskId, OfferDto offerDto, Model model){
        try {
            offerDto.setServiceProviderDto(serviceProviderService.getCurrentServiceProvider());
            offerDto.setTaskDto(taskService.findTaskById(taskId));
            offerDto.setStatus("OFFERED");
            boolean isCreated = offerService.createOffer(offerDto);

            if (isCreated) {
                model.addAttribute("offer" ,offerDto);
                model.addAttribute("taskId",taskId);
                model.addAttribute("serviceProvider",  serviceProviderService.getCurrentServiceProvider());
                return "serviceprovider-tasks-offer";
            } else {
                return "error";
            }
        } catch (EntityNotFoundException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "error";
        }

    }


    private void setServiceProviderForDisplay(Model model) {
        model.addAttribute("serviceProvider", serviceProviderService.getCurrentServiceProvider());


    }


}


