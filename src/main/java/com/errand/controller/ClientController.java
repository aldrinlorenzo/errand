package com.errand.controller;

import com.errand.dto.PendingTaskDto;
import com.errand.dto.TaskDto;

import com.errand.models.Offer;
import com.errand.models.Task;
import com.errand.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
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

    @Autowired
    public ClientController(UserService userService, ClientService clientService,
                            TaskService taskService,
                            LabelService labelService,
                            OfferService offerService) {
        this.userService = userService;
        this.clientService = clientService;
        this.taskService = taskService;
        this.labelService = labelService;
        this.offerService = offerService;
    }

    @GetMapping("/dashboard")
    public String getClientDashboard(Model model){
        BigDecimal pendingTaskCount = new BigDecimal(taskService.getPendingTaskByClient().size());
        model.addAttribute("client", clientService.getCurrentClient());
        model.addAttribute("pendingTaskCount", pendingTaskCount);
        return "client-dashboard";
    }

    @GetMapping("/tasks")
    public String getClientTasks(Model model){
        List<TaskDto> tasks = taskService.getTasksByClient(clientService.getCurrentClient());
        model.addAttribute("tasks", tasks);
        model.addAttribute("client", clientService.getCurrentClient());
        return "client-tasks-list";
    }

    @GetMapping("/tasks/new")
    public String createTaskForm(Model model){
        Task task = new Task();
        model.addAttribute("taskLabels", labelService.findAllTasks());
        model.addAttribute("client", clientService.getCurrentClient());
        model.addAttribute("task", task);
        return "client-tasks-create";
    }

    @GetMapping("/tasks/{taskId}/edit")
    public String editTaskForm(@PathVariable("taskId") Long taskId, Model model){
        TaskDto task = taskService.findTaskById(taskId);
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

}
