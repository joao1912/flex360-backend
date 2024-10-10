
package com.flex360.api_flex360.unit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.NoSuchElementException;

import com.flex360.api_flex360.models.Cadeira;
import com.flex360.api_flex360.repository.CadeiraRepository;
import com.flex360.api_flex360.services.CadeiraService;

import jakarta.persistence.EntityNotFoundException;

class CadeiraServiceTest {

    @Mock
    private CadeiraRepository cadeiraRepository;

    @InjectMocks
    private CadeiraService cadeiraService;

    private UUID idCadeira;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        idCadeira = UUID.randomUUID();  // Gera um ID aleatório para os testes
    }

    @Test
    void testBuscarTodasCadeiras() {
        // Cenário: Repositório retorna uma lista de cadeiras
        List<Cadeira> cadeirasMock = List.of(new Cadeira(), new Cadeira());
        when(cadeiraRepository.findAll()).thenReturn(cadeirasMock);

        List<Cadeira> result = cadeiraService.buscarTodasCadeiras();
        assertEquals(2, result.size());  // Verifica se a lista retornada contém 2 cadeiras

        // Cenário: Repositório retorna uma lista vazia
        when(cadeiraRepository.findAll()).thenReturn(List.of());

        assertThrows(EntityNotFoundException.class, () -> cadeiraService.buscarTodasCadeiras(),
            "Nenhuma cadeira encontrada.");
    }

    @Test
    void testBuscarCadeiraPorId_CadeiraExistente() {
        // Cenário: Cadeira com o ID fornecido existe no banco de dados
        Cadeira cadeiraMock = new Cadeira();
        when(cadeiraRepository.findById(idCadeira)).thenReturn(Optional.of(cadeiraMock));

        Cadeira result = cadeiraService.buscarCadeiraPorId(idCadeira);
        assertNotNull(result);  // Verifica se o resultado não é nulo
    }

    @Test
    void testBuscarCadeiraPorId_CadeiraNaoExistente() {
        // Cenário: Cadeira com o ID fornecido não existe no banco de dados
        when(cadeiraRepository.findById(idCadeira)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> cadeiraService.buscarCadeiraPorId(idCadeira),
            "Cadeira com ID " + idCadeira + " não encontrada.");
    }

    @Test
    void testCriarCadeira() {
        // Cenário: Criar uma nova cadeira
        Cadeira novaCadeira = new Cadeira();
        when(cadeiraRepository.save(novaCadeira)).thenReturn(novaCadeira);

        Cadeira result = cadeiraService.criarCadeira(novaCadeira);
        assertNotNull(result);  // Verifica se a cadeira foi salva corretamente
    }

    @Test
    void testEditarCadeira() {
        // Cenário: Cadeira com o ID fornecido existe no banco de dados
        Cadeira cadeiraExistente = new Cadeira();
        Cadeira cadeiraAtualizada = new Cadeira();

        when(cadeiraRepository.findById(idCadeira)).thenReturn(Optional.of(cadeiraExistente));
        when(cadeiraRepository.save(cadeiraExistente)).thenReturn(cadeiraExistente);

        Cadeira result = cadeiraService.editarCadeira(idCadeira, cadeiraAtualizada);
        assertNotNull(result);  // Verifica se a edição foi bem-sucedida
    }

    @Test
    void testDeletarCadeira_CadeiraExistente() {
        // Cenário: Cadeira com o ID fornecido existe no banco de dados
        Cadeira cadeiraExistente = new Cadeira();
        when(cadeiraRepository.findById(idCadeira)).thenReturn(Optional.of(cadeiraExistente));

        cadeiraService.deletarCadeira(idCadeira);
        verify(cadeiraRepository, times(1)).delete(cadeiraExistente);  // Verifica se o método delete foi chamado
    }

    @Test
    void testDeletarCadeira_CadeiraNaoExistente() {
        // Cenário: Cadeira com o ID fornecido não existe no banco de dados
        when(cadeiraRepository.findById(idCadeira)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> cadeiraService.deletarCadeira(idCadeira));
    }
}
