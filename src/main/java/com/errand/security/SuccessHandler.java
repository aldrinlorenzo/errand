package com.errand.security;

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

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        if (authorities.contains(new SimpleGrantedAuthority("ADMIN"))) {
            redirectStrategy.sendRedirect(request, response, "/admin/dashboard");
        } else if (authorities.contains(new SimpleGrantedAuthority("CLIENT"))){
            redirectStrategy.sendRedirect(request, response, "/client/dashboard");
        } else if(authorities.contains(new SimpleGrantedAuthority("SERVICE_PROVIDER"))){
            redirectStrategy.sendRedirect(request, response, "/serviceProvider/dashboard");
        }
    }

}