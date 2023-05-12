package com.errand.controller;

import com.errand.dto.ClientDto;
import com.errand.dto.RatingDto;
import com.errand.dto.TaskDto;

import com.errand.models.Offer;
import com.errand.models.Task;
import com.errand.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import static com.errand.mapper.TaskMapper.mapToTask;

@Controller
@RequestMapping("/client")
public class ClientController {

    private UserService userService;
    private ClientService clientService;
    private TaskService taskService;
    private LabelService labelService;
    private OfferService offerService;
    private RatingService ratingService;

    @Autowired
    public ClientController(UserService userService, ClientService clientService,
                            TaskService taskService,
                            LabelService labelService,
                            OfferService offerService,
                            RatingService ratingService) {
        this.userService = userService;
        this.clientService = clientService;
        this.taskService = taskService;
        this.labelService = labelService;
        this.offerService = offerService;
        this.ratingService = ratingService;
    }

    @GetMapping("/dashboard")
    public String getClientDashboard(Model model){
        BigDecimal pendingTaskCount = new BigDecimal(taskService.getPendingTaskByClient().size());
        BigDecimal ongoingTaskCount = new BigDecimal(taskService.getOngoingTaskByClient().size());
        BigDecimal completedTaskCount = new BigDecimal(taskService.getCompletedTaskByClient().size());
        BigDecimal cancelledTaskCount = new BigDecimal(taskService.getCancelledTaskByClient().size());
        model.addAttribute("client", clientService.getCurrentClient());
        model.addAttribute("pendingTaskCount", pendingTaskCount);
        model.addAttribute("ongoingTaskCount", ongoingTaskCount);
        model.addAttribute("completedTaskCount", completedTaskCount);
        model.addAttribute("cancelledTaskCount", cancelledTaskCount);
        return "client-dashboard";
    }

    @GetMapping("/profile")
    public String getClientProfile(Model model){
        model.addAttribute("client", clientService.getCurrentClient());
        return "client-profile";
    }

    @GetMapping("/tasks/board")
    public String getClientBoard(Model model){
        model.addAttribute("client", clientService.getCurrentClient());
        model.addAttribute("pendingTasks", taskService.getPendingTaskByClient());
        return "client-tasks-board";
    }

    @GetMapping("/tasks")
    public String getClientTasks(Model model, RatingDto rating){
        List<TaskDto> tasks = taskService.getTasksByClient(clientService.getCurrentClient());
        model.addAttribute("tasks", tasks);
        model.addAttribute("rating", rating);
        model.addAttribute("client", clientService.getCurrentClient());
        return "client-tasks-list";
    }

    @GetMapping("/tasks/new")
    public String createTaskForm(Model model){
        Task task = new Task();
        model.addAttribute("taskLabels", labelService.findAllLabels());
        model.addAttribute("client", clientService.getCurrentClient());
        model.addAttribute("task", task);
        return "client-tasks-create";
    }

    @GetMapping("/tasks/{taskId}/edit")
    public String editTaskForm(@PathVariable("taskId") Long taskId, Model model){
        TaskDto task = taskService.findTaskById(taskId);
        model.addAttribute("taskLabels", labelService.findAllLabels());
        model.addAttribute("client", clientService.getCurrentClient());
        model.addAttribute("task", task);
        return "client-tasks-edit";
    }

    @GetMapping("/tasks/{taskId}/cancel")
    public String cancelTask(@PathVariable("taskId") Long taskId){
        taskService.cancelTask(taskId);
        return "redirect:/client/tasks";
    }

    @GetMapping("/tasks/{taskId}/offers")
    public String getTaskOffers(@PathVariable("taskId") Long taskId, Model model){
        TaskDto taskDto = taskService.findTaskById(taskId);
        Task task = mapToTask(taskDto);
        List <Offer> offers = offerService.findOffersByTask(task);
        model.addAttribute("task", task);
        model.addAttribute("offers", offers);
        model.addAttribute("client", clientService.getCurrentClient());
        return "client-tasks-offers";
    }

    @GetMapping("/tasks/{taskId}/offers/{offerId}/accept")
    public String approveOffer(@PathVariable("taskId") Long taskId,
                             @PathVariable("offerId") Long offerId){
        TaskDto taskDto = taskService.findTaskById(taskId);
        offerService.acceptOffer(offerId, taskDto);
        return "redirect:/client/tasks/{taskId}/offers";
    }

    @GetMapping("/tasks/{taskId}/offers/{offerId}/reject")
    public String rejectOffer(@PathVariable("taskId") Long taskId,
                               @PathVariable("offerId") Long offerId){
        TaskDto taskDto = taskService.findTaskById(taskId);
        offerService.rejectOffer(offerId);
        return "redirect:/client/tasks/{taskId}/offers";
    }

    @PostMapping("/tasks/new")
    public String saveTask(@Valid @ModelAttribute("task") TaskDto taskDto,
                           BindingResult result, Model model){
        if(result.hasErrors()){
            model.addAttribute("client", clientService.getCurrentClient());
            model.addAttribute("task", taskDto);
            return "client-tasks-create";
        }
        taskService.saveTask(taskDto);
        return "redirect:/client/tasks";
    }

    @PostMapping("/tasks/{taskId}/edit")
    public String updateTask(@PathVariable("taskId") Long taskId,
                             @Valid @ModelAttribute("task") TaskDto task,
                             BindingResult result, Model model){
        if(result.hasErrors()){
            model.addAttribute("task", task);
            return "client-tasks-edit";
        }
        task.setId(taskId);
        taskService.updateTask(task, clientService.getCurrentClient());
        return "redirect:/client/tasks";
    }

    @PostMapping("/profile/{clientId}/edit")
    public String updateTask(@PathVariable("clientId") Long clientId,
                             @Valid @ModelAttribute("client") ClientDto client,
                             BindingResult result, Model model){
        if(result.hasErrors()){
            model.addAttribute("client", client);
            return "client-profile";
        }
        clientService.updateClient(client);
        return "redirect:/client/profile";
    }

    @PostMapping("/tasks/{taskId}/rate")
    public String rateServiceProvider(@PathVariable("taskId")Long taskId,
                                      @ModelAttribute("rating") RatingDto rating,
                                      BindingResult result, Model model, Task task){
        if(result.hasErrors()){
            model.addAttribute("rating", rating);
            return "client-tasks-List";
        }
        rating.setTaskDto(taskService.findTaskById(taskId));
        ratingService.saveRateFromClient(rating);
        return "redirect:/client/tasks";
    }

}
