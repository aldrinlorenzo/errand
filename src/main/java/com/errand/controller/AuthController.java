package com.errand.controller;

import com.errand.dto.ClientRegistrationDto;
import com.errand.dto.ServiceProviderRegistrationDto;
import com.errand.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {
    private UserService userservice;

    public AuthController(UserService userservice) {
        this.userservice = userservice;
    }

    @GetMapping("/")
    public String loginPage(){
        return "login";
    }

    @GetMapping("/register/client")
    public String getClientRegisterForm(Model model){
        ClientRegistrationDto user = new ClientRegistrationDto();
        model.addAttribute("user", user);
        return "register-client";
    }

    @GetMapping("/register/serviceprovider")
    public String getServiceProviderRegisterForm(Model model){
        ServiceProviderRegistrationDto user = new ServiceProviderRegistrationDto();
        model.addAttribute("user", user);
        return "register-serviceprovider";
    }

}
