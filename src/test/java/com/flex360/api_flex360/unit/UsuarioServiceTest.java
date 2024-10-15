package com.flex360.api_flex360.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.flex360.api_flex360.enums.UserRole;
import com.flex360.api_flex360.models.Usuario;
import com.flex360.api_flex360.repository.UsuarioRepository;
import com.flex360.api_flex360.services.UsuarioService;

import jakarta.persistence.EntityNotFoundException;

@SpringBootTest
public class UsuarioServiceTest {

    @Mock
    UsuarioRepository usuarioRepository;

    @InjectMocks
    UsuarioService usuarioService;

    @Test
    void buscarTodosUsuarios_deveRetornarListaDeUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        usuarios.add(new Usuario("Nome", "Senha", "email@test.com", UserRole.USER));
        usuarios.add(new Usuario("Nome2", "Senha2", "email2@test.com", UserRole.USER));

        when(usuarioRepository.findAll()).thenReturn(usuarios);

        List<Usuario> result = usuarioService.buscarTodosUsuarios();

        assertEquals(2, result.size());
    }

    @Test
    void buscarTodosUsuarios_deveLancarExcecaoSeNaoExistirUsuarios() {
        when(usuarioRepository.findAll()).thenReturn(new ArrayList<>());

        assertThrows(EntityNotFoundException.class, () -> usuarioService.buscarTodosUsuarios());
    }

    @Test
    void buscarUsuarioPorId_deveRetornarUsuarioSeExistir() {
        UUID id = UUID.randomUUID();
        Usuario usuario = new Usuario("Nome", "Senha", "email@test.com", UserRole.USER);

        when(usuarioRepository.findById(id)).thenReturn(Optional.of(usuario));

        Usuario result = usuarioService.buscarUsuarioPorId(id);

        assertEquals("Nome", result.getNome());
    }

    @Test
    void buscarUsuarioPorId_deveLancarExcecaoSeNaoExistir() {
        UUID id = UUID.randomUUID();
        when(usuarioRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> usuarioService.buscarUsuarioPorId(id));
    }

    @Test
    void criarUsuario_deveSalvarNovoUsuario() {
        Usuario usuario = new Usuario("Nome", "123456", "email@test.com", UserRole.USER);

        when(usuarioRepository.findByEmail(usuario.getEmail())).thenReturn(null);
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        Usuario result = usuarioService.criarUsuario(usuario);

        assertEquals(usuario.getNome(), result.getNome());
        assertNotNull(result.getSenha());
    }

    @Test
    void criarUsuario_deveLancarExcecaoSeEmailJaCadastrado() {
        Usuario usuario = new Usuario("Nome", "123456", "email@test.com", UserRole.USER);

        when(usuarioRepository.findByEmail(usuario.getEmail())).thenReturn(usuario);

        assertThrows(RuntimeException.class, () -> usuarioService.criarUsuario(usuario));
    }

    @Test
    void editarUsuario_deveAtualizarDadosDoUsuario() {
        UUID id = UUID.randomUUID();
        Usuario usuarioExistente = new Usuario("Nome", "Senha", "email@test.com", UserRole.USER);
        Usuario usuarioAtualizado = new Usuario("Novo Nome", "Senha", "novoemail@test.com", UserRole.USER);

        when(usuarioRepository.findById(id)).thenReturn(Optional.of(usuarioExistente));
        when(usuarioRepository.save(usuarioExistente)).thenReturn(usuarioExistente);

        Usuario result = usuarioService.editarUsuario(id, usuarioAtualizado);

        assertEquals("Novo Nome", result.getNome());
        assertEquals("novoemail@test.com", result.getEmail());
    }

    @Test
    void deletarUsuario_deveDeletarUsuarioComSucesso() {
        UUID id = UUID.randomUUID();

        doNothing().when(usuarioRepository).deleteById(id);

        boolean result = usuarioService.deletarUsuario(id);

        assertTrue(result);
    }

    @Test
    void deletarUsuario_deveLancarExcecaoSeFalharAoDeletar() {
        UUID id = UUID.randomUUID();

        doThrow(new RuntimeException("Erro ao deletar")).when(usuarioRepository).deleteById(id);

        assertThrows(RuntimeException.class, () -> usuarioService.deletarUsuario(id));
    }

}
