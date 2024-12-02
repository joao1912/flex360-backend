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

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Adicione o cabeçalho CORS se necessário
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");

        String errorMessage = "Você não está autenticado ou não possui autorização para acessar este recurso.";

        if (authException instanceof BadCredentialsException) {
            errorMessage = "Senha inválida.";
        } else if (authException instanceof UsernameNotFoundException) {
            errorMessage = "Usuário não encontrado.";
        } else {
            Throwable cause = authException.getCause();
            if (cause instanceof AccountExpiredException) {
                errorMessage = "Conta expirada.";
            }

        }

        response.getWriter().write("{\"message\": \"" + errorMessage + "\"}");
    }
}