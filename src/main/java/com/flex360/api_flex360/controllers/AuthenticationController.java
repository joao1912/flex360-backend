package com.flex360.api_flex360.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flex360.api_flex360.dto.auth.AuthenticationDTO;
import com.flex360.api_flex360.dto.auth.LoginResponseDTO;
import com.flex360.api_flex360.dto.auth.RegisterDTO;
import com.flex360.api_flex360.infra.security.TokenService;
import com.flex360.api_flex360.models.Usuario;
import com.flex360.api_flex360.services.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/auth")
@Tag(name = "Auth")
public class AuthenticationController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    TokenService tokenService;

    @SuppressWarnings("rawtypes")
    @Operation(description = "Vai logar no sistema e receber um token JWT.", responses = {
            @ApiResponse(responseCode = "200"),

            @ApiResponse(responseCode = "404", content = @Content())
    }

    )
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AuthenticationDTO data) {

        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());

        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((Usuario) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));

    }

    @SuppressWarnings("rawtypes")
    @Operation(description = "Vai cadastrar no sistema.", responses = {
            @ApiResponse(responseCode = "200"),

            @ApiResponse(responseCode = "400", content = @Content())
    }

    )
    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterDTO data) {

        Usuario newUsuario = new Usuario();
        newUsuario.setNome(data.nome());
        newUsuario.setEmail(data.email());
        newUsuario.setSenha(data.password());

        usuarioService.criarUsuario(newUsuario);

        return ResponseEntity.ok().build();

    }

}
