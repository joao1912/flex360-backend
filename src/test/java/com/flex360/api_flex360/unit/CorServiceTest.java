package com.flex360.api_flex360.unit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;
import java.util.UUID;
import java.util.List;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.flex360.api_flex360.models.Cor;
import com.flex360.api_flex360.repository.CorRepository;
import com.flex360.api_flex360.services.CorService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.Id;

@SpringBootTest
public class CorServiceTest {

    @Mock
    private CorRepository corRepository;

    @InjectMocks
    private CorService corService;

    // Teste para buscarTodasCores()
    @Test
    public void testBuscarTodasCores_success() {
        // Mockando o comportamento do repository
        List<Cor> cores = new ArrayList<>();

        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();

        Cor cor1 = new Cor();
        cor1.setId(id1);
        cor1.setCodigo("#00BFFF");
        cor1.setName("azul claro");

        Cor cor2 = new Cor();
        cor2.setId(id2);
        cor2.setCodigo("#00008B");
        cor2.setName("azul escuro");

        cores.add(cor1);
        cores.add(cor2);
        when(corRepository.findAll()).thenReturn(cores);

        // Testando o método buscarTodasCores
        List<Cor> result = corService.buscarTodasCores();

        // Verificando se o resultado é o esperado
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(2, result.size());
        assertEquals("azul claro", result.get(0).getName());
    }

    @Test
    public void testBuscarTodasCores_emptyList() {
        // Mockando um retorno de lista vazia
        when(corRepository.findAll()).thenReturn(new ArrayList<>());

        // Verificando se a exceção correta é lançada
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> corService.buscarTodasCores());
        assertEquals("Nenhuma cor encontrada.", exception.getMessage());
    }

    // Teste para buscarCorPorId()
    @Test
    public void testBuscarCorPorId_success() {
        // Mockando o comportamento do repository
        UUID id = UUID.randomUUID();
        Cor cor = new Cor();
        cor.setId(id);
        cor.setCodigo("#00BFFF");
        cor.setName("azul claro");
        when(corRepository.findById(id)).thenReturn(Optional.of(cor));

        // Testando o método buscarCorPorId
        Cor result = corService.buscarCorPorId(id);

        // Verificando se o resultado é o esperado
        assertNotNull(result);
        assertEquals("azul claro", result.getName());
        assertEquals("#00BFFF", result.getCodigo());
    }

    @Test
    public void testBuscarCorPorId_notFound() {
        // Mockando um cenário onde a cor não é encontrada
        UUID id = UUID.randomUUID();
        when(corRepository.findById(id)).thenReturn(Optional.empty());

        // Verificando se a exceção correta é lançada
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> corService.buscarCorPorId(id));
        assertEquals("Cor com ID " + id + " não encontrada.", exception.getMessage());
    }

    // Teste para criarCor()
    @Test
    public void testCriarCor_success() {
        // Mockando o comportamento do repository
        Cor novaCor = new Cor();
        novaCor.setCodigo("#00BFFF");
        novaCor.setName("azul escuro");
        Cor corSalva = new Cor();
        corSalva.setCodigo("#00BFFF");
        corSalva.setName("azul escuro");
        when(corRepository.save(novaCor)).thenReturn(corSalva);

        // Testando o método criarCor
        Cor result = corService.criarCor(novaCor);

        // Verificando se o resultado é o esperado
        assertNotNull(result);
        assertEquals("azul escuro", result.getName());
        assertEquals("#00BFFF", result.getCodigo());
    }

    // Teste para editarCor()
    @Test
    public void testEditarCor_success() {
        // Mockando o comportamento do repository
        UUID id = UUID.randomUUID();
        Cor corExistente = new Cor();
        corExistente.setCodigo("#00BFFF");
        corExistente.setId(id);
        corExistente.setName("azul escuro");

        Cor corAtualizada = new Cor();
        corAtualizada.setCodigo("#4B0082");
        corAtualizada.setId(id);
        corAtualizada.setName("indigo");

        when(corRepository.findById(id)).thenReturn(Optional.of(corExistente));
        when(corRepository.save(corExistente)).thenReturn(corAtualizada);

        // Testando o método editarCor
        Cor result = corService.editarCor(id, corAtualizada);

        // Verificando se o resultado é o esperado
        assertNotNull(result);
        assertEquals("indigo", result.getName());
        assertEquals("#4B0082", result.getCodigo());
    }

    @Test
    public void testEditarCor_notFound() {
        // Mockando um cenário onde a cor não é encontrada
        UUID id = UUID.randomUUID();
        Cor corAtualizada = new Cor();
        corAtualizada.setCodigo("#8A2BE2");
        corAtualizada.setId(id);
        corAtualizada.setName("violetBlue");
        when(corRepository.findById(id)).thenReturn(Optional.empty());

        // Verificando se a exceção correta é lançada
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> corService.editarCor(id, corAtualizada));
        assertEquals("Cor com ID " + id + " não encontrada.", exception.getMessage());
    }

    // Teste para deletarCor()
    @Test
    public void testDeletarCor_success() {

      // Define um UUID para o teste
    UUID id = UUID.randomUUID();

    // Simula a entidade que será retornada pelo repositório
    Cor cor = new Cor();
    cor.setCodigo("#00BFFF");
    cor.setName("azul claro");
    cor.setId(id);

    // Configura o comportamento do mock para retornar a entidade
    Mockito.when(corRepository.findById(id)).thenReturn(Optional.of(cor));

    // Chama o método que estamos testando
    corService.deletarCor(id);

    // Verifica se o método delete foi chamado com a entidade correta
    Mockito.verify(corRepository).delete(cor);
    }

    @Test
    public void testDeletarCor_notFound() {
         // Define um UUID para o teste
    UUID id = UUID.randomUUID();

    // Configura o mock para retornar vazio (entidade não encontrada)
    Mockito.when(corRepository.findById(id)).thenReturn(Optional.empty());

    // Verifica se a exceção EntityNotFoundException é lançada
    assertThrows(EntityNotFoundException.class, () -> {
        corService.deletarCor(id);
    });

    // Verifica se o método delete nunca foi chamado
    Mockito.verify(corRepository, Mockito.never()).delete(Mockito.any());
    }
}
