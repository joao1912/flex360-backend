package com.flex360.api_flex360.unit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.flex360.api_flex360.models.Cadeira;
import com.flex360.api_flex360.models.Categoria;
import com.flex360.api_flex360.repository.CadeiraRepository;
import com.flex360.api_flex360.repository.CategoriaRepository;
import com.flex360.api_flex360.services.CadeiraService;

import jakarta.validation.ValidationException;

@ExtendWith(MockitoExtension.class)
class CadeiraServiceTest {

    @InjectMocks
    private CadeiraService cadeiraService;

    @Mock
    private CadeiraRepository cadeiraRepository;

    @Mock
    private CategoriaRepository categoriaRepository;

    @Test
    void testCriarCadeira_Sucesso() {
        // Arrange
        Cadeira novaCadeira = new Cadeira();
        novaCadeira.setNome("Cadeira Confortável");
        novaCadeira.setDescricao("Uma cadeira muito confortável");
        novaCadeira.setPreco(299.99f);

        Categoria categoria = new Categoria();
        categoria.setName("Ergonômica");
        novaCadeira.setCategorias(Arrays.asList(categoria));

        when(categoriaRepository.findByName(eq("Ergonômica"))).thenReturn(Optional.of(categoria));
        when(cadeiraRepository.save(any(Cadeira.class))).thenReturn(novaCadeira);

        // Act
        Cadeira cadeiraCriada = cadeiraService.criarCadeira(novaCadeira);

        // Assert
        assertNotNull(cadeiraCriada);
        assertEquals("Cadeira Confortável", cadeiraCriada.getNome());
        verify(cadeiraRepository, times(1)).save(novaCadeira);
    }

    @Test
    void testCriarCadeira_ComNomeInvalido_DeveLancarExcecao() {
        // Arrange
        Cadeira novaCadeira = new Cadeira();
        novaCadeira.setNome(""); // Nome inválido
        novaCadeira.setDescricao("Descrição válida");
        novaCadeira.setPreco(299.99f);
        novaCadeira.setCategorias(new ArrayList<>()); // Initialize categorias

        // Act & Assert
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            cadeiraService.criarCadeira(novaCadeira);
        });

        assertEquals("O nome é obrigatório e não pode exceder 20 caracteres.", exception.getMessage());
    }

}