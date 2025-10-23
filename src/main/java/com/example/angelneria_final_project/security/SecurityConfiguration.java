package com.example.angelneria_final_project.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final UserDetailsImpl userDetailsService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(registry->{
                    registry.requestMatchers("/auth/**").permitAll();
                    registry.requestMatchers("/users/{id}/country").hasAnyRole("USER", "ADMIN");
                    registry.requestMatchers( "/users/**").hasRole("ADMIN");
                    registry.requestMatchers(HttpMethod.GET, "/products/{id}").hasAnyRole("USER", "ADMIN");
                    registry.requestMatchers(HttpMethod.GET, "/products").hasAnyRole("USER", "ADMIN");
                    registry.requestMatchers("/products/**").hasRole("ADMIN");
                    registry.requestMatchers(HttpMethod.GET, "/orders").hasRole("ADMIN");
                    registry.requestMatchers(HttpMethod.PATCH, "/orders").hasRole("ADMIN");
                    registry.requestMatchers(HttpMethod.GET, "/orders/{id}").hasAnyRole("USER", "ADMIN");
                    registry.requestMatchers(HttpMethod.POST, "/orders").hasRole("USER");
                    registry.requestMatchers(HttpMethod.GET, "/countries/**").hasAnyRole("USER", "ADMIN");
                    registry.requestMatchers("/countries/**").hasRole("ADMIN");
                    registry.requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll();
                    registry.anyRequest().permitAll();
                })
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public UserDetailsService userDetailsService(){
        return userDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailsService());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(){
        return new ProviderManager(authenticationProvider());
    }
}
