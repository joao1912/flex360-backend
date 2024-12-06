package com.flex360.api_flex360.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.flex360.api_flex360.models.Usuario;
import com.flex360.api_flex360.repository.UsuarioRepository;

@Service
public class AuthorizationService implements UserDetailsService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        try {
        Usuario usuario = usuarioRepository.findByEmail(username);

        if(usuario == null) {
            throw new UsernameNotFoundException("E-mail ou senha inválidos.");
        }

        return usuario;
        } catch(Exception e) {
            throw new UsernameNotFoundException("Erro ao validar usuário.", e);
        }
    }
    
}