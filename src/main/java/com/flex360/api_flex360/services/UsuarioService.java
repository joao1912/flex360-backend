package com.flex360.api_flex360.services;

import java.util.List;
import java.util.UUID;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.flex360.api_flex360.enums.UserRole;
import com.flex360.api_flex360.models.Carrinho;
import com.flex360.api_flex360.models.Usuario;
import com.flex360.api_flex360.repository.CarrinhoRepository;
import com.flex360.api_flex360.repository.UsuarioRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final CarrinhoRepository carrinhoRepository;
    
    private void validarUsuario(Usuario usuario, boolean checaEmail) {
        if (!StringUtils.hasText(usuario.getNome()) || usuario.getNome().length() > 20) {
            throw new ValidationException("O nome é obrigatório e deve ter no máximo 20 caracteres.");
        }
        if (checaEmail && !StringUtils.hasText(usuario.getEmail()) || !usuario.getEmail().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {

            throw new ValidationException("O e-mail é obrigatório e deve ser válido.");
        }
        if (usuarioRepository.findByEmail(usuario.getEmail()) != null) {
            throw new ValidationException("E-mail já cadastrado.");
        }
        if (!StringUtils.hasText(usuario.getSenha()) || usuario.getSenha().length() < 8) {
            throw new ValidationException("A senha é obrigatória e deve ter no mínimo 8 caracteres.");
        }
    }

    @Transactional(readOnly = true)
    public List<Usuario> buscarTodosUsuarios() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        if (usuarios.isEmpty()) {
            throw new EntityNotFoundException("Nenhum usuário encontrado.");
        }
        return usuarios;
    }

    @Transactional(readOnly = true)
    public Usuario buscarUsuarioPorId(UUID id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado com ID " + id));
    }
    
    @Transactional
    public Usuario criarUsuario(Usuario usuario) {
        validarUsuario(usuario, true);
        String senhaCriptografada = passwordEncoder.encode(usuario.getSenha());

        Carrinho dataCarrinho = new Carrinho();

        try {
            
            Carrinho novoCarrinho = carrinhoRepository.save(dataCarrinho);

            Usuario novoUsuario = new Usuario(usuario.getNome(), senhaCriptografada, usuario.getEmail(), UserRole.USER, novoCarrinho);

                return usuarioRepository.save(novoUsuario);
    } catch (DataAccessException | ConstraintViolationException e) {
        throw new RuntimeException("Erro ao acessar o banco de dados: " + e.getMessage(), e);
    } catch (Exception e) {
        throw new RuntimeException("Erro inesperado ao criar usuário ou carrinho: " + e.getMessage(), e);
    }
    }

    @Transactional
    public Usuario editarUsuario(UUID id, Usuario usuarioAtualizado) {
        Usuario usuarioExistente = buscarUsuarioPorId(id);

        if (StringUtils.hasText(usuarioAtualizado.getNome())) {
            usuarioExistente.setNome(usuarioAtualizado.getNome());
        }
        if (StringUtils.hasText(usuarioAtualizado.getEmail())) {
            if (!usuarioExistente.getEmail().equals(usuarioAtualizado.getEmail()) &&
                    usuarioRepository.findByEmail(usuarioAtualizado.getEmail()) != null) {
                throw new ValidationException("E-mail já cadastrado.");
            }

            validarUsuario(usuarioAtualizado, false);

            usuarioExistente.setEmail(usuarioAtualizado.getEmail());
        }
        if (StringUtils.hasText(usuarioAtualizado.getSenha())) {
            usuarioExistente.setSenha(passwordEncoder.encode(usuarioAtualizado.getSenha()));
        }

        try {
            return usuarioRepository.save(usuarioExistente);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao editar usuário: " + e.getMessage(), e);
        }
    }

    @Transactional
    public void deletarUsuario(UUID id) {
        try {
            usuarioRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException("Usuário com ID " + id + " já foi removido ou não existe.");
        } catch (Exception e) {
            throw new RuntimeException("Erro ao deletar usuário: " + e.getMessage(), e);
        }
    }
}