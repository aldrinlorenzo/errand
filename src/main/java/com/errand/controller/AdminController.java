package com.errand.controller;

import com.errand.dto.ClientDto;
import com.errand.models.Users;
import com.errand.security.SecurityUtil;
import com.errand.services.ClientService;
import com.errand.services.ServiceProviderService;
import com.errand.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private UserService userService;
    private ClientService clientService;
//    private ServiceProviderService serviceProviderService;

    @Autowired
    public AdminController(UserService userService, ClientService clientService) {
        this.userService = userService;
        this.clientService = clientService;
//        ServiceProviderService serviceProviderService
//        this.serviceProviderService = serviceProviderService;
    }

    @GetMapping("/dashboard")
    public String getAdminDashboard(Model model){
        Users user = new Users();
        String username = SecurityUtil.getSessionUser();
        if(username != null){
            user = userService.findByUsername(username);
            model.addAttribute("user", user);
        }
        model.addAttribute("user", user);
        return "admin-dashboard";
    }

    @GetMapping("/clients")
    public String getAdminClients(Model model){
        Users user = new Users();
        List<ClientDto> clients = clientService.findAllClients();
        String username = SecurityUtil.getSessionUser();
        if(username != null){
            user = userService.findByUsername(username);
            model.addAttribute("user", user);
        }
        model.addAttribute("user", user);
        model.addAttribute("clients", clients);
        return "admin-clients";
    }

}
