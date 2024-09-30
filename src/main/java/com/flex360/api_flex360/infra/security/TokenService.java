package com.flex360.api_flex360.infra.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import java.lang.RuntimeException;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.flex360.api_flex360.models.Usuario;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    public String generateToken(Usuario user) {

        try {

            Algorithm algorithm = Algorithm.HMAC256(secret);
            String token = JWT.create()
                            .withIssuer("flex360-api")
                            .withSubject(user.getEmail())
                            .withExpiresAt(generateExpirationDate())
                            .sign(algorithm);
            
            return token;

        } catch (JWTCreationException e) {
            throw new RuntimeException("Ocorreu um erro em tentar gerar um token", e);
        }

    }

    public String validateToken(String token) {

        try {

            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                .withIssuer("flex360-api")
                .build()
                .verify(token)
                .getSubject();
            
        } catch (JWTVerificationException e) {

            return "";
            
        }

    }

    private Instant generateExpirationDate() {

        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));

    }
    
}
