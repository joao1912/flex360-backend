package com.flex360.api_flex360.infra.security;

import java.io.IOException;

import javax.security.auth.login.AccountExpiredException;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException)
            throws IOException, ServletException {

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Adicione o cabeçalho CORS se necessário
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");

        String errorMessage = "Você não está autenticado ou não possui autorização para acessar o recurso solicitado.";

        if (authException instanceof UsernameNotFoundException) {
            errorMessage = "E-mail ou senha inválidos.";
        } else if (authException instanceof BadCredentialsException) {
            errorMessage = "E-mail ou senha inválidos.";
        } else {
            Throwable cause = authException.getCause();
            if (cause instanceof AccountExpiredException) {
                errorMessage = "Conta expirada.";
            } else {
                String exceptionName = authException.getClass().getSimpleName();
                errorMessage = "Erro de autenticação: " + exceptionName;
        }
        }

        response.getWriter().write("{\"message\": \"" + errorMessage + "\"}");
    }
}