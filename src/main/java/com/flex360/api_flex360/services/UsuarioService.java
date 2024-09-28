package com.flex360.api_flex360.services;

import org.springframework.stereotype.Service;

import com.flex360.api_flex360.models.Usuario;
import com.flex360.api_flex360.repository.UsuarioRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public Usuario buscarTodosUsuarios() {

        throw new EntityNotFoundException();

    }

    public Usuario buscarUsuariosPorId() {

        throw new EntityNotFoundException();

    }
    
    public Usuario criarUsuario() {

        throw new EntityNotFoundException();

    }

    public Usuario editarUsuario() {

        throw new EntityNotFoundException();

    }

    public Usuario deletarUsuario() {

        throw new EntityNotFoundException();

    }

}
