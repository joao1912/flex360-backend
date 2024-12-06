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

    @Autowired
    CustomAccessDeniedHandler accessDeniedHandler;

    @Autowired
    CustomAuthenticationEntryPoint authenticationEntryPoint;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .accessDeniedHandler(accessDeniedHandler) // Handler para quando o usuário está autenticado, mas
                                                                  // não autorizado
                        .authenticationEntryPoint(authenticationEntryPoint) // Handler para erros de autenticação
                )
                .authorizeHttpRequests(authorize -> authorize

                        .requestMatchers(HttpMethod.GET, "/usuario/buscarTodos").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/usuario/buscarPorId").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/usuario/buscarPerfil").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/usuario/editar").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/usuario/deletar").authenticated()

                        .requestMatchers(HttpMethod.GET, "/cor/buscarTodas").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/cor/buscarPorId/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/cor/criar").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/cor/editar/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/cor/deletar/{id}").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.POST, "/acessorio/criar").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/acessorio/editar/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/acessorio/deletar/{id}").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.POST, "/cadeira/criar").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/cadeira/editar/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/cadeira/deletar/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/cadeira/sugestaoErgonomica").authenticated()

                        .requestMatchers(HttpMethod.GET, "/carrinho/buscar").authenticated()
                        .requestMatchers(HttpMethod.POST, "/carrinho/adiciona").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/carrinho/remove").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/carrinho/deleta/produto/{id}").authenticated()

                        .anyRequest().permitAll())
                .addFilterBefore((Filter) securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();

    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {

        return authenticationConfiguration.getAuthenticationManager();

    }

    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();

    }

}
