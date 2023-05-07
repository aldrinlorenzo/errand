package com.errand.controller;

import com.errand.models.Client;
import com.errand.models.Users;
import com.errand.security.SecurityUtil;
import com.errand.services.ClientService;
import com.errand.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/client")
public class ClientController {

    private UserService userService;
    private ClientService clientService;

    @Autowired
    public ClientController(UserService userService, ClientService clientService) {
        this.userService = userService;
        this.clientService = clientService;
    }

    @GetMapping("/dashboard")
    public String getClientDashboard(Model model){
        Users user;
        Client client;
        Optional<Client> optionalClient;

        String username = SecurityUtil.getSessionUser();
        if(username != null){
            user = userService.findByUsername(username);
            optionalClient = clientService.findById(user.getId());
            client = optionalClient.get();
            model.addAttribute("user", user);
            model.addAttribute("client", client);
        }

        return "client-dashboard";
    }

    @GetMapping("/tasks")
    public String getClientTasks(Model model){
        Users user;
        Client client;
        Optional<Client> optionalClient;

        String username = SecurityUtil.getSessionUser();
        if(username != null){
            user = userService.findByUsername(username);
            optionalClient = clientService.findById(user.getId());
            client = optionalClient.get();
            model.addAttribute("user", user);
            model.addAttribute("client", client);
        }

        return "client-tasks";
    }

}
