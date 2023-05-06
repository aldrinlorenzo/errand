package com.errand.controller;

import com.errand.dto.ClientRegistrationDto;
import com.errand.dto.ServiceProviderRegistrationDto;
import com.errand.models.Users;
import com.errand.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

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

    @PostMapping("/register/save/client")
    public String register(@Valid @ModelAttribute("user") ClientRegistrationDto user,
                           BindingResult result,
                           Model model){

        Users existingUserUsername = userservice.findByUsername(user.getUsername());
        if(existingUserUsername != null && existingUserUsername.getUsername() != null && !existingUserUsername.getUsername().isEmpty()){
            return "redirect:/register?fail";
        }
        if(result.hasErrors()){
            model.addAttribute("user",user);
            return "register";
        }
        userservice.saveUserClient(user);
        return "redirect:/tasks?success";
    }

    @PostMapping("/register/save/serviceprovider")
    public String registerServiceProvider(@Valid @ModelAttribute("user") ServiceProviderRegistrationDto user,
                                          BindingResult result,
                                          Model model){

        Users existingUserUsername = userservice.findByUsername(user.getUsername());
        if(existingUserUsername != null && existingUserUsername.getUsername() != null && !existingUserUsername.getUsername().isEmpty()){
            return "redirect:/register?fail";
        }
        if(result.hasErrors()){
            model.addAttribute("user",user);
            return "register-serviceprovider";
        }
        userservice.saveUserServiceProvider(user);
        return "redirect:/tasks?success";
    }

}
