package com.errand.controller;

import com.errand.dto.*;
import com.errand.models.Offer;
import com.errand.models.Rating;
import com.errand.models.ServiceProvider;
import com.errand.models.Task;
import com.errand.services.*;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import static com.errand.mapper.ClientMapper.mapToClientDto;
import static com.errand.mapper.OfferMapper.mapToOffer;
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
    LabelService labelService;

    private FileStorageService fileStorageService;

    @Autowired
    public ServiceProviderController(TaskService taskService,
                                     ServiceProviderService serviceProviderService,
                                     OfferService offerService,
                                     RatingService ratingService, LabelService labelService,
                                     FileStorageService fileStorageService) {
        this.taskService = taskService;
        this.serviceProviderService = serviceProviderService;
        this.offerService = offerService;
        this.ratingService = ratingService;
        this.labelService = labelService;
        this.fileStorageService = fileStorageService;
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
        model.addAttribute("totalEarnings", totalEarnings);
        model.addAttribute("pendingTaskCount", pendingTaskCount);
        model.addAttribute("ongoingTaskCount", ongoingTaskCount);
        model.addAttribute("completedTaskCount", completedTaskCount);
        model.addAttribute("ongoingPercentage", ongoingPercentage);
        model.addAttribute("completedPercentage", completedPercentage);
        return "serviceprovider-dashboard";
    }

    @GetMapping("/profile")
    public String createUpdateProfileForm(Model model) {
        setServiceProviderForDisplay(model);
        return "serviceprovider-profile-edit";

    }

    @PostMapping("/profile/upload")
    public String uploadImage(Model model, @RequestParam("file") MultipartFile file) {

        try {
            fileStorageService.saveServiceProviderImage(file, serviceProviderService.getCurrentServiceProvider());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:/serviceProvider/profile";
    }

    @GetMapping("/tasks/pending-tasks")
    public String getAllTask(Model model) {
        List<PendingTaskDto> pendingTaskDtoList = taskService.getPendingTask();
        setServiceProviderForDisplay(model);

        List<OfferDto> offerByServiceProvider = offerService.findOfferByServiceProvider(serviceProviderService.getCurrentServiceProvider());
        OfferStatisticDto offerStatisticDto = offerService.getOfferStatistic(serviceProviderService.getCurrentServiceProvider());

        model.addAttribute("offerStatisticDto",offerStatisticDto);

        model.addAttribute("offerByServiceProvider", offerByServiceProvider);
        model.addAttribute("taskList", pendingTaskDtoList);
        model.addAttribute("spTaskPage", "pending");
        return "serviceprovider-tasks-list";
    }

    @GetMapping("/tasks/ongoing-tasks")

    public String getServiceProviderTasks(Model model, RatingDto ratingDto) {
        List<TaskDto> taskDtoList = taskService.findTaskByServiceProviderAndStatus(
                serviceProviderService.getCurrentServiceProvider().getId(), "ONGOING");
        setServiceProviderForDisplay(model);

        model.addAttribute("taskList", taskDtoList);
        model.addAttribute("rating", ratingDto);
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
    public String updateServiceProviderDetails(@Valid ServiceProviderDto serviceProviderDto,
                                               BindingResult result,
                                               Model model) {
        if (result.hasErrors()) {
            setServiceProviderForDisplay(model);
            return "serviceprovider-profile-edit";
        }
        try {
            boolean isUpdated = serviceProviderService.updateServiceProviderDetails(serviceProviderDto, serviceProviderService.getCurrentServiceProvider().getId());

            if (isUpdated) {
                model.addAttribute("serviceProvider", serviceProviderDto);
                model.addAttribute("serviceProviderId", serviceProviderService.getCurrentServiceProvider().getId());
                return "redirect:/serviceProvider/profile";
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

    @GetMapping("tasks/{taskId}/offers/{offerId}/delete")

    public String deletePendingTask(@PathVariable("taskId") Long taskId, @PathVariable("offerId") Long offerId) throws Exception {

        boolean deleted = offerService.deleteOffer(offerId);
        if (deleted) {
            return "redirect:/serviceProvider/tasks/pending-tasks";
        } else {
            throw new Exception("Failed to delete offer");
        }


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
    public String saveRateServiceProvider(@PathVariable("taskId") Long taskId,
                                      @ModelAttribute("rating") RatingDto ratingDto,
                                      BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("rating", ratingDto);
            return "serviceprovider-my-task-list";
        }
        Task task = mapToTask(taskService.findTaskById(taskId));
        Rating rating = ratingService.getRatingByTask(task);
        if (rating != null) {
            ratingDto.setServiceProviderDto(toServiceProviderDto(serviceProviderService.getLoggedInServiceProvider()));
            ratingService.updateRatingFromServiceProvider(rating, ratingDto);
        } else {
            Offer offer  = mapToOffer(offerService.findOfferById(task.getOfferId()));
            ratingDto.setClientDto(mapToClientDto(task.getClient()));
            ratingDto.setServiceProviderDto(serviceProviderService.getCurrentServiceProvider());
            ratingDto.setTaskDto(taskService.findTaskById(taskId));
            ratingService.saveRateFromServiceProvider(ratingDto);
        }
        return "redirect:/serviceProvider/tasks/completed-tasks";
    }

    @GetMapping("/tasks/{taskId}/rate")
    public String rateClient(@PathVariable("taskId") Long taskId,
                             @ModelAttribute("rating") RatingDto ratingDto,
                             BindingResult result, Model model){

        TaskDto task = taskService.findTaskById(taskId);
        model.addAttribute("taskLabels", labelService.findAllLabels());
        model.addAttribute("serviceProvider", serviceProviderService.getLoggedInServiceProvider());
        model.addAttribute("task", task);

        return "rating-from-serviceprovider";
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
        model.addAttribute("task",task);
        return "serviceprovider-ratings";
    }

}


