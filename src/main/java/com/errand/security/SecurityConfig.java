package com.errand.security;

import com.errand.repository.UserRepository;
import com.errand.services.UserService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AndRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private CustomUserDetailsService userDetailsService;
    private UserService userService;

    @Autowired
    public SecurityConfig(CustomUserDetailsService userDetailsService , UserService userService) {
        this.userDetailsService = userDetailsService;
        this.userService = userService;
    }

    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.csrf().disable()
                .authorizeRequests()
                    .antMatchers("/login", "/register/client", "/register/serviceprovider", "/css/**", "/js/**", "/vendor/**")
                    .permitAll()
                    .antMatchers("/admin/**")
                    .hasAuthority("ADMIN")
                    .antMatchers("/client/**")
                    .hasAuthority("CLIENT")
                    .antMatchers("/serviceProvider/**")
                    .hasAuthority("SERVICE_PROVIDER")
                .and()
                .formLogin(form -> form
                        .loginPage("/")
                        .loginProcessingUrl("/login")
                        .successHandler(new SuccessHandler(userService))
                        .failureUrl("/?error=true")
                        .permitAll()
                )
                .logout(
                        logout -> logout
                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout")).permitAll()
                );
        return http.build();
    }

    public void configure(AuthenticationManagerBuilder builder) throws Exception{
        builder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

}
