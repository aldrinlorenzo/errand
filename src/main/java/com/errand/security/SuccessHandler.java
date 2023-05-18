package com.errand.security;

import com.errand.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

public class SuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private   UserService userService;
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    SuccessHandler(UserService userService){
        this.userService = userService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        Collection<? extends GrantedAuthority> authoritiesRole = authentication.getAuthorities();
        String authoritiesName = authentication.getName();


        if (authoritiesRole.contains(new SimpleGrantedAuthority("ADMIN"))) {
            redirectStrategy.sendRedirect(request, response, "/admin/dashboard");
        } else if (authoritiesRole.contains(new SimpleGrantedAuthority("CLIENT"))){
            redirectStrategy.sendRedirect(request, response, "/client/dashboard");
        } else if(authoritiesRole.contains(new SimpleGrantedAuthority("SERVICE_PROVIDER"))){
            redirectStrategy.sendRedirect(request, response, "/serviceProvider/dashboard");
        }
    }

}