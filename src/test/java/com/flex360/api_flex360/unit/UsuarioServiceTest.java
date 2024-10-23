package com.flex360.api_flex360.unit;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.flex360.api_flex360.enums.UserRole;
import com.flex360.api_flex360.models.Carrinho;
import com.flex360.api_flex360.models.Usuario;
import com.flex360.api_flex360.repository.CarrinhoRepository;
import com.flex360.api_flex360.repository.UsuarioRepository;
import com.flex360.api_flex360.services.UsuarioService;

import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private CarrinhoRepository carrinhoRepository;

    @InjectMocks
    UsuarioService usuarioService;

    @Test
    void buscarTodosUsuarios_deveRetornarListaDeUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        usuarios.add(new Usuario("Nome", "Senha", "email@test.com", UserRole.USER, new Carrinho()));
        usuarios.add(new Usuario("Nome2", "Senha2", "email2@test.com", UserRole.USER, new Carrinho()));

        when(usuarioRepository.findAll()).thenReturn(usuarios);

        List<Usuario> result = usuarioService.buscarTodosUsuarios();

        assertEquals(2, result.size());
    }

    @Test
    void buscarTodosUsuarios_deveLancarExcecaoSeNaoExistirUsuarios() {
        when(usuarioRepository.findAll()).thenReturn(new ArrayList<>());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> usuarioService.buscarTodosUsuarios());

        assertEquals("Nenhum usuário encontrado.", exception.getMessage());
    }

    @Test
    void buscarUsuarioPorId_deveRetornarUsuarioSeExistir() {
        UUID id = UUID.randomUUID();
        Usuario usuario = new Usuario("Nome", "Senha", "email@test.com", UserRole.USER, new Carrinho());

        when(usuarioRepository.findById(id)).thenReturn(Optional.of(usuario));

        Usuario result = usuarioService.buscarUsuarioPorId(id);

        assertEquals("Nome", result.getNome());
    }

    @Test
    void buscarUsuarioPorId_deveLancarExcecaoSeNaoExistir() {
        UUID id = UUID.randomUUID();
        when(usuarioRepository.findById(id)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> usuarioService.buscarUsuarioPorId(id));

        assertTrue(exception.getMessage().contains("Usuário não encontrado com ID"));
    }

    @Test
    void criarUsuario_deveSalvarNovoUsuario() {
        Usuario usuario = new Usuario("Nome", "UmaSenha123", "email@test.com", UserRole.USER, new Carrinho());

        when(usuarioRepository.findByEmail(usuario.getEmail())).thenReturn(null);
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        Usuario result = usuarioService.criarUsuario(usuario);

        assertEquals(usuario.getNome(), result.getNome());
        assertNotNull(result.getSenha());
    }

    @Test
    void criarUsuario_deveLancarExcecaoSeEmailJaCadastrado() {
        Usuario usuario = new Usuario("Nome", "123456", "email@test.com", UserRole.USER, new Carrinho());

        when(usuarioRepository.findByEmail(usuario.getEmail())).thenReturn(usuario);

        Exception e = assertThrows(RuntimeException.class, () -> usuarioService.criarUsuario(usuario));
        assertEquals("E-mail já cadastrado.", e.getMessage());
    }

    @Test
    void editarUsuario_deveAtualizarDadosDoUsuario() {
        UUID id = UUID.randomUUID();

        Usuario usuarioExistente = new Usuario("Nome", "Senha123@@ss", "email@test.com", UserRole.USER, new Carrinho());
        Usuario usuarioAtualizado = new Usuario("Novo Nome", "Senha123@@ss", "novoemail@test.com", UserRole.USER,
                new Carrinho());

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

        assertDoesNotThrow(() -> usuarioService.deletarUsuario(id));

    }

    @Test
    void deletarUsuario_deveLancarExcecaoSeFalharAoDeletar() {
        UUID id = UUID.randomUUID();

        doThrow(new RuntimeException("Erro ao deletar")).when(usuarioRepository).deleteById(id);

        Exception e = assertThrows(RuntimeException.class, () -> usuarioService.deletarUsuario(id));
        assertTrue(e.getMessage().contains("Erro ao deletar"));
    }

}
