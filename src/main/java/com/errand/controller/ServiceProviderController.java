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

    @Autowired
    public ServiceProviderController(TaskService taskService, ServiceProviderService serviceProviderService, OfferService offerService) {
        this.taskService = taskService;
        this.serviceProviderService = serviceProviderService;
        this.offerService = offerService;
    }

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

       List<OfferDto> offerByServiceProvider= offerService.findOfferByServiceProvider(serviceProviderService.getCurrentServiceProvider());


        model.addAttribute("offerByServiceProvider",offerByServiceProvider);
        model.addAttribute("taskList", pendingTaskDtoList);
        model.addAttribute("spTaskPage", "pending");
        return "serviceprovider-tasks-list";
    }

    @GetMapping("/tasks/ongoing-tasks")
    public String getServiceProviderTasks(Model model) {
        List<TaskDto> taskDtoList = taskService.findTaskByServiceProviderAndStatus(
                serviceProviderService.getCurrentServiceProvider().getId(), "ONGOING");
        setServiceProviderForDisplay(model);
        model.addAttribute("taskList", taskDtoList);
        model.addAttribute("spTaskPage", "myTasks");
        return "serviceprovider-my-task-list";
    }

    @GetMapping("/tasks/completed-tasks")
    public String getServiceProviderCompletedTasks(Model model) {
        List<TaskDto> taskDtoList = taskService.findTaskByServiceProviderAndStatus(
                serviceProviderService.getCurrentServiceProvider().getId(), "COMPLETED");
        setServiceProviderForDisplay(model);
        model.addAttribute("taskList", taskDtoList);
        model.addAttribute("spTaskPage", "myTasks");
        return "serviceprovider-my-task-list";
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

    @GetMapping("tasks/{taskId}/offers")
    public String getPendingTask(@PathVariable("taskId") Long taskId, Model model, OfferDto offerDto) {
        TaskDto taskDto = taskService.findTaskById(taskId);

        setServiceProviderForDisplay(model);
        model.addAttribute("task", taskDto);
        model.addAttribute("offer", offerDto);
        return "serviceprovider-tasks-offer";
    }

    @PostMapping("tasks/{taskId}/offers/create")
    public String createOffer(@PathVariable("taskId") Long taskId, OfferDto offerDto, Model model) {
        try {
            // Set the service provider and task for the offer
            offerDto.setServiceProviderDto(serviceProviderService.getCurrentServiceProvider());
            offerDto.setTaskDto(taskService.findTaskById(taskId));
            offerDto.setStatus("OFFERED");


            Boolean isOfferExist = offerService.isOfferExist(taskId, serviceProviderService.getCurrentServiceProvider().getId());

            if (isOfferExist) {
                 // just update offer
                offerService.updateOffer(offerDto);
            } else {
               // else create a new offer
                boolean isCreated = offerService.createOffer(offerDto);
                if (!isCreated) {
                    //if creation failed
                    return "error";
                }
            }


            setServiceProviderForDisplay(model);
            model.addAttribute("offer", offerDto);
            model.addAttribute("taskId", taskId);
            return "redirect:/serviceProvider/tasks/pending-tasks";

        } catch (EntityNotFoundException e) {

            model.addAttribute("errorMessage", e.getMessage());
            return "error";
        }
    }

    private void setServiceProviderForDisplay(Model model) {
        model.addAttribute("serviceProvider", serviceProviderService.getCurrentServiceProvider());


    }


}


