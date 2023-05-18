package com.errand.controller;

import com.errand.dto.BaseRegistrationDTO;
import com.errand.models.Client;
import com.errand.models.ServiceProvider;
import com.errand.models.Users;
import com.errand.services.ClientService;
import com.errand.services.ServiceProviderService;
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
    private ClientService clientService;
    private ServiceProviderService serviceProviderService;

    public AuthController(UserService userservice, ClientService clientService, ServiceProviderService serviceProviderService) {
        this.userservice = userservice;
        this.clientService = clientService;
        this.serviceProviderService = serviceProviderService;
    }

    @GetMapping("/")
    public String loginPage(){
        return "login";
    }

    @GetMapping("/register/client")
    public String getClientRegisterForm(Model model){
        BaseRegistrationDTO user = new BaseRegistrationDTO();
        model.addAttribute("user", user);
        return "register-client";
    }

    @GetMapping("/register/serviceprovider")
    public String getServiceProviderRegisterForm(Model model){
        BaseRegistrationDTO user = new BaseRegistrationDTO();
        model.addAttribute("user", user);
        return "register-serviceprovider";
    }

    @PostMapping("/register/save/client")
    public String register(@Valid @ModelAttribute("user")BaseRegistrationDTO user,
                           BindingResult result,
                           Model model){
        Client existingUserEmail = clientService.findByEmail(user.getEmail());
        if(existingUserEmail != null && existingUserEmail.getEmail() != null && !existingUserEmail.getEmail().isEmpty()){
            return "redirect:/register/client?fail";
        }
        Users existingUserUsername = userservice.findByUsername(user.getUsername());
        if(existingUserUsername != null && existingUserUsername.getUsername() != null && !existingUserUsername.getUsername().isEmpty()){
            return "redirect:/register/client?fail";
        }
        if(result.hasErrors()){
            model.addAttribute("user",user);
            return "register-client";
        }
        userservice.saveUserClient(user);
        return "redirect:/?success=true";
    }

    @PostMapping("/register/save/serviceprovider")
    public String registerServiceProvider(@Valid @ModelAttribute("user")BaseRegistrationDTO user,
                                          BindingResult result,
                                          Model model){

        ServiceProvider existingUserEmail = serviceProviderService.findByEmail(user.getEmail());
        if(existingUserEmail != null && existingUserEmail.getEmail() != null && !existingUserEmail.getEmail().isEmpty()){
            return "redirect:/register/serviceprovider?fail";
        }

        Users existingUserUsername = userservice.findByUsername(user.getUsername());
        if(existingUserUsername != null && existingUserUsername.getUsername() != null && !existingUserUsername.getUsername().isEmpty()){
            return "redirect:/register/serviceprovider?fail";
        }
        if(result.hasErrors()){
            model.addAttribute("user",user);
            return "register-serviceprovider";
        }
        userservice.saveUserServiceProvider(user);
        return "redirect:/?success=true";
    }

}
