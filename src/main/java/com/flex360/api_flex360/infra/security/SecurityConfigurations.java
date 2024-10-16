package com.flex360.api_flex360.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import jakarta.servlet.Filter;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {

    @Autowired
    SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        return httpSecurity
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers(HttpMethod.GET, "/usuario/buscarTodos").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/usuario/buscarPorId").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/usuario/buscarPerfil").authenticated()
                .requestMatchers(HttpMethod.GET, "/usuario/editar").authenticated()
                .requestMatchers(HttpMethod.GET, "/usuario/deletar").authenticated()
                .requestMatchers(HttpMethod.GET, "/cor/buscarTodas").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/cor/buscarPorId").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/cor/criar").hasRole("ADMIN")

                
                .anyRequest().permitAll()
            )
            .addFilterBefore((Filter) securityFilter, UsernamePasswordAuthenticationFilter.class)
            .build();

    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {

        return authenticationConfiguration.getAuthenticationManager();

    }

    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();

    }
    
}
