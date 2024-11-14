package com.flex360.api_flex360.unit;

import java.util.Arrays;
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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;

import com.flex360.api_flex360.exceptions.ResourceNotFoundException;
import com.flex360.api_flex360.models.Acessorio;
import com.flex360.api_flex360.repository.AcessorioRepository;
import com.flex360.api_flex360.services.AcessorioService;

@ExtendWith(MockitoExtension.class)
public class AcessorioServiceTest {

    @Mock
    private AcessorioRepository acessorioRepository;

    @InjectMocks
    private AcessorioService acessorioService;

    @Test
    public void buscarTodosAcessorios_DeveRetornarAcessorios() {

        Acessorio acessorio1 = new Acessorio();
        acessorio1.setNome("Mouse");
        acessorio1.setPreco(100.0f);

        Acessorio acessorio2 = new Acessorio();
        acessorio2.setNome("Teclado");
        acessorio2.setPreco(150.0f);

        when(acessorioRepository.findAll()).thenReturn(Arrays.asList(acessorio1, acessorio2));
       
        List<Acessorio> acessorios = acessorioService.buscarTodosAcessorios();

        assertNotNull(acessorios);
        assertEquals(2, acessorios.size());
        assertEquals("Mouse", acessorios.get(0).getNome());
        assertEquals(100.0f, acessorios.get(0).getPreco());
        assertEquals("Teclado", acessorios.get(1).getNome());
    }

    @Test
    public void buscarTodosAcessorios_DeveLancarExcecaoQuandoVazio() {
       
        when(acessorioRepository.findAll()).thenReturn(Arrays.asList());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            acessorioService.buscarTodosAcessorios();
        });

        assertEquals("Nenhum acessório encontrado", exception.getMessage());
    }

    @Test
    public void buscarAcessorioPorId_DeveRetornarAcessorio() {
       
        UUID id = UUID.randomUUID();
        Acessorio acessorio = new Acessorio();
        acessorio.setNome("Mouse");
        acessorio.setPreco(100.0f);

        when(acessorioRepository.findById(id)).thenReturn(Optional.of(acessorio));

        Acessorio resultado = acessorioService.buscarAcessorioPorId(id);

        assertNotNull(resultado);
        assertEquals("Mouse", resultado.getNome());
        assertEquals(100.0f, resultado.getPreco());
    }

    @Test
    public void buscarAcessorioPorId_DeveLancarExcecaoQuandoNaoEncontrado() {
        UUID id = UUID.randomUUID();

        when(acessorioRepository.findById(id)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            acessorioService.buscarAcessorioPorId(id);
        });

        assertTrue(exception.getMessage().contains("Acessório não encontrado com ID"));
    }

    @Test
    public void criarAcessorio_DeveRetornarAcessorioCriado() {
        Acessorio acessorio = new Acessorio();
        acessorio.setNome("Mouse");
        acessorio.setPreco(100.0f);
        acessorio.setFoto("https://bucket/foto");

        when(acessorioRepository.save(acessorio)).thenReturn(acessorio);

        Acessorio resultado = acessorioService.criarAcessorio(acessorio);

        assertNotNull(resultado);
        assertEquals("Mouse", resultado.getNome());
        assertEquals(100.0f, resultado.getPreco());
    }

    @Test
    public void criarAcessorio_DeveLancarExcecaoQuandoErroAoCriar() {
        Acessorio acessorio = new Acessorio();
        acessorio.setNome("Mouse");
        acessorio.setPreco(100.0f);
        acessorio.setFoto("https://bucket/foto");

        when(acessorioRepository.save(acessorio)).thenThrow(new RuntimeException("Erro ao salvar no banco"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            acessorioService.criarAcessorio(acessorio);
        });

        assertEquals("Erro ao criar o acessório: Erro ao salvar no banco", exception.getMessage());
    }

    @Test
    public void editarAcessorio_DeveRetornarAcessorioEditado() {
        UUID id = UUID.randomUUID();
        Acessorio acessorioExistente = new Acessorio();
        acessorioExistente.setNome("Mouse");
        acessorioExistente.setPreco(100.0f);
        acessorioExistente.setFoto("https://bucket/foto");

        Acessorio acessorioAtualizado = new Acessorio();
        acessorioAtualizado.setNome("Teclado");
        acessorioAtualizado.setPreco(150.0f);
        acessorioAtualizado.setFoto("https://bucket/foto1");

        when(acessorioRepository.findById(id)).thenReturn(Optional.of(acessorioExistente));
        when(acessorioRepository.save(acessorioExistente)).thenReturn(acessorioExistente);

        Acessorio resultado = acessorioService.editarAcessorio(id, acessorioAtualizado);

        assertNotNull(resultado);
        assertEquals("Teclado", resultado.getNome());
        assertEquals(150.0f, resultado.getPreco());
        assertEquals("https://bucket/foto1", resultado.getFoto());
    }

    @Test
    public void editarAcessorio_DeveLancarExcecaoQuandoErroAoEditar() {
        UUID id = UUID.randomUUID();
        Acessorio acessorioExistente = new Acessorio();
        acessorioExistente.setNome("Mouse");
        acessorioExistente.setPreco(100.0f);
        acessorioExistente.setFoto("https://bucket/foto");

        Acessorio acessorioAtualizado = new Acessorio();
        acessorioAtualizado.setNome("Teclado");
        acessorioAtualizado.setPreco(150.0f);
        acessorioAtualizado.setFoto("https://bucket/foto1");

        when(acessorioRepository.findById(id)).thenReturn(Optional.of(acessorioExistente));
        when(acessorioRepository.save(acessorioExistente)).thenThrow(new RuntimeException("Erro ao salvar no banco"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            acessorioService.editarAcessorio(id, acessorioAtualizado);
        });

        assertEquals("Erro ao editar o acessório: Erro ao salvar no banco", exception.getMessage());
    }

     @Test
    public void deletarAcessorio_DeveExcluirAcessorio() {
        UUID id = UUID.randomUUID();
        Acessorio acessorio = new Acessorio();
        acessorio.setNome("Mouse");

        when(acessorioRepository.findById(id)).thenReturn(Optional.of(acessorio));

        assertDoesNotThrow(() -> acessorioService.deletarAcessorio(id));
        verify(acessorioRepository, times(1)).delete(acessorio);
    }

    @Test
    public void deletarAcessorio_DeveLancarExcecaoQuandoNaoEncontrado() {
        UUID id = UUID.randomUUID();

        when(acessorioRepository.findById(id)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            acessorioService.deletarAcessorio(id);
        });

        assertEquals("Não foi possível deletar. Acessório não encontrado com ID: " + id, exception.getMessage());
    }

    @Test
    public void deletarAcessorio_DeveLancarExcecaoQuandoErroAoDeletar() {
        UUID id = UUID.randomUUID();
        Acessorio acessorio = new Acessorio();
        acessorio.setNome("Mouse");

        when(acessorioRepository.findById(id)).thenReturn(Optional.of(acessorio));
        doThrow(new EmptyResultDataAccessException(1)).when(acessorioRepository).delete(acessorio);

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            acessorioService.deletarAcessorio(id);
        });

        assertEquals("Acessório com ID " + id + " já foi removido ou não existe.", exception.getMessage());
    }

}
