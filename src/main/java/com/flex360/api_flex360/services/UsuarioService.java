package com.flex360.api_flex360.services;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.flex360.api_flex360.enums.UserRole;
import com.flex360.api_flex360.models.Usuario;
import com.flex360.api_flex360.repository.UsuarioRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public List<Usuario> buscarTodosUsuarios() {

        List<Usuario> usuarios = usuarioRepository.findAll();
        if (usuarios.isEmpty()) {
            throw new EntityNotFoundException("Nenhum usuário encontrado");
        }

        return usuarios;

    }

    public Usuario buscarUsuarioPorId(UUID id) {

        return usuarioRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado com ID"));

    }
    
    public Usuario criarUsuario(Usuario usuario) {

        if (usuarioRepository.findByEmail(usuario.getEmail()) != null) {
            throw new RuntimeException("Email ja cadastrado");
        }
        
        String encryptedPassword = new BCryptPasswordEncoder().encode(usuario.getSenha());
        Usuario newUser = new Usuario(usuario.getNome(), encryptedPassword, usuario.getEmail(), UserRole.USER);

        try {
            
            Usuario novoUsuario = usuarioRepository.save(newUser);

            return novoUsuario;
            
        } catch (Exception e) {
            
            throw new RuntimeException("Erro ao criar usuário: " + e.getMessage(), e);
        }

    }

    public Usuario editarUsuario(UUID id, Usuario usuarioAtualizado) {

        Optional<Usuario> optionalUsuario = usuarioRepository.findById(id);

        if (!optionalUsuario.isPresent()) {
            throw new RuntimeException("Erro ao buscar o usuário");
        }

        Usuario usuarioExistente = optionalUsuario.get();

        if (usuarioAtualizado.getNome() != null) {
            usuarioExistente.setNome(usuarioAtualizado.getNome());
        }

        if (usuarioAtualizado.getEmail() != null) {
            usuarioExistente.setEmail(usuarioAtualizado.getEmail());
        }

        try {
            
            Usuario usuarioSalvo = usuarioRepository.save(usuarioExistente);

            return usuarioSalvo;
            
        } catch (Exception e) {
            
            throw new RuntimeException("Erro ao editar usuário: " + e.getMessage(), e);
        }

    }

    public boolean deletarUsuario(UUID id) {

        try {
            
            usuarioRepository.deleteById(id);

            return true;
            
        } catch (Exception e) {
            
            throw new RuntimeException("Erro ao deletar usuário: " + e.getMessage(), e);
        }

    }

}
