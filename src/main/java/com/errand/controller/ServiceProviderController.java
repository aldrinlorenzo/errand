package com.errand.controller;

import com.errand.dto.*;
import com.errand.models.Client;
import com.errand.models.Rating;
import com.errand.models.ServiceProvider;
import com.errand.models.Task;
import com.errand.services.OfferService;
import com.errand.services.RatingService;
import com.errand.services.ServiceProviderService;
import com.errand.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import static com.errand.mapper.ServiceProviderMapper.toServiceProviderDto;
import static com.errand.mapper.TaskMapper.mapToTask;

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
    RatingService ratingService;

    @Autowired
    public ServiceProviderController(TaskService taskService,
                                     ServiceProviderService serviceProviderService,
                                     OfferService offerService,
                                     RatingService ratingService) {
        this.taskService = taskService;
        this.serviceProviderService = serviceProviderService;
        this.offerService = offerService;
        this.ratingService = ratingService;
    }

    @GetMapping("/dashboard")
    public String getServiceProviderDetails(Model model) {
        long serviceProviderId = serviceProviderService.getCurrentServiceProvider().getId();
        List<TaskDto> completedTaskDto = taskService.findTaskByServiceProviderAndStatus(
                serviceProviderId, "COMPLETED");
        int pendingTaskCount = taskService.findTaskByServiceProviderAndStatus(
                serviceProviderId, "PENDING").size();
        int ongoingTaskCount = taskService.findTaskByServiceProviderAndStatus(
                serviceProviderId, "ONGOING").size();
        int completedTaskCount = completedTaskDto.size();
        int ongoingPercentage = ongoingTaskCount + completedTaskCount > 0 ?
                (ongoingTaskCount * 100) / (completedTaskCount + ongoingTaskCount) : 0;
        int completedPercentage = ongoingTaskCount + completedTaskCount > 0 ?
                (completedTaskCount * 100) / (completedTaskCount + ongoingTaskCount) : 0;
        List<Long> taskIds = completedTaskDto.stream().map(TaskDto::getId).collect(Collectors.toList());
        BigDecimal totalEarnings = taskIds.stream().map((task) -> offerService.findOfferByTaskIdAndServiceProviderId(
                task, serviceProviderId).getPrice()).reduce(new BigDecimal(0), BigDecimal::add);
        setServiceProviderForDisplay(model);
        List<Rating> ratingList = ratingService
                .getRatingByServiceProvider(serviceProviderService.getCurrentServiceProvider());
        Float currentRating = ratingList.size() > 0 ?
                ratingList
                        .stream()
                        .map(Rating::getServiceProviderRating)
                        .reduce(0f, Float::sum) / ratingList.size() : 0;
        model.addAttribute("totalEarnings", totalEarnings);
        model.addAttribute("pendingTaskCount", pendingTaskCount);
        model.addAttribute("ongoingTaskCount", ongoingTaskCount);
        model.addAttribute("completedTaskCount", completedTaskCount);
        model.addAttribute("ongoingPercentage", ongoingPercentage);
        model.addAttribute("completedPercentage", completedPercentage);
        model.addAttribute("currentRating", currentRating);
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
    public String getServiceProviderTasks(Model model, RatingDto rating) {
        List<TaskDto> taskDtoList = taskService.findTaskByServiceProviderAndStatus(
                serviceProviderService.getCurrentServiceProvider().getId(), "ONGOING");
        setServiceProviderForDisplay(model);
        model.addAttribute("taskList", taskDtoList);
        model.addAttribute("rating", rating);
        model.addAttribute("spTaskPage", "myTasks");
        return "serviceprovider-my-task-list";
    }

    @GetMapping("/tasks/completed-tasks")
    public String getServiceProviderCompletedTasks(Model model, RatingDto rating) {
        List<TaskDto> taskDtoList = taskService.findTaskByServiceProviderAndStatus(
                serviceProviderService.getCurrentServiceProvider().getId(), "COMPLETED");
        setServiceProviderForDisplay(model);
        model.addAttribute("taskList", taskDtoList);
        model.addAttribute("rating", rating);
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

    @PostMapping("/tasks/{taskId}/rate")
    public String rateServiceProvider(@PathVariable("taskId")Long taskId,
                                      @ModelAttribute("rating") RatingDto ratingDto,
                                      BindingResult result, Model model){
        if(result.hasErrors()){
            model.addAttribute("rating", ratingDto);
            return "serviceprovider-my-task-list";
        }
        Task task = mapToTask(taskService.findTaskById(taskId));
        Rating rating  = ratingService.getRatingByTask(task);
        if( rating != null){
            ratingDto.setServiceProviderDto(toServiceProviderDto(serviceProviderService.getLoggedInServiceProvider()));
            ratingService.updateRatingFromServiceProvider(rating, ratingDto);
        }else{
            ratingDto.setServiceProviderDto(serviceProviderService.getCurrentServiceProvider());
            ratingDto.setTaskDto(taskService.findTaskById(taskId));
            ratingService.saveRateFromServiceProvider(ratingDto);
        }
        return "redirect:/serviceProvider/tasks/completed-tasks";
    }


    private void setServiceProviderForDisplay(Model model) {
        model.addAttribute("serviceProvider", serviceProviderService.getCurrentServiceProvider());
    }

    @GetMapping("/viewMyRatings")
    public String getRatingsOfCurrentClient(Model model, ServiceProvider serviceProvider, Task task){
        serviceProvider = serviceProviderService.getLoggedInServiceProvider();
        List<RatingDto> ratings = ratingService.getRatingsByServiceProvider(serviceProvider);
        model.addAttribute("ratings", ratings);
        model.addAttribute("serviceProvider", serviceProvider);
        return "serviceprovider-ratings";
    }

}


