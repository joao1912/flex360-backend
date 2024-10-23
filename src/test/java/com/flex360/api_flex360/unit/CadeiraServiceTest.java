package com.flex360.api_flex360.unit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

import com.flex360.api_flex360.dto.cadeira.RequestCadeiraDTO;
import com.flex360.api_flex360.models.Cadeira;
import com.flex360.api_flex360.models.Categoria;
import com.flex360.api_flex360.models.Cor;
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

        Categoria categoria = new Categoria();
        categoria.setName("Ergonômica");
        List<Categoria> categorias = new ArrayList<>();
        categorias.add(categoria);

        List<Cor> cores = new ArrayList<>();
        
        Cor novaCor = new Cor();
        novaCor.setCodigo("#ffffff");
        novaCor.setFoto_cadeira("http://site.com/foto.png");
        novaCor.setId(UUID.randomUUID());
        novaCor.setName("Preto");
        RequestCadeiraDTO cadeiraDTO = new RequestCadeiraDTO("Nome da cadeira", "Descrição da cadeira", "Informações", 5, 10000,  "Dimensões", "http://site.com/foto_dimensao.png", "Desc do encosto", "Desc do apoio", "Desc da rodinha", "Desc da rodinha", "Desc do revestimento", categorias, cores);

        Cadeira novaCadeira = new Cadeira();
        novaCadeira.setNome(cadeiraDTO.nome());
        novaCadeira.setDescricao(cadeiraDTO.descricao());
        novaCadeira.setInformacoes(cadeiraDTO.informacoes());
        novaCadeira.setTemp_garantia(cadeiraDTO.temp_garantia());
        novaCadeira.setPreco(cadeiraDTO.preco());
        novaCadeira.setDimensoes(cadeiraDTO.dimencoes());
        novaCadeira.setFoto_dimensoes(cadeiraDTO.foto_dimencoes());
        novaCadeira.setDesc_encosto(cadeiraDTO.desc_encosto());
        novaCadeira.setDesc_apoio(cadeiraDTO.desc_apoio());
        novaCadeira.setDesc_rodinha(cadeiraDTO.desc_rodinha());
        novaCadeira.setDesc_ajuste_altura(cadeiraDTO.desc_ajuste_altura());
        novaCadeira.setDesc_revestimento(cadeiraDTO.desc_revestimento());

        when(categoriaRepository.findByName(eq("Ergonômica"))).thenReturn(Optional.of(categoria));
        when(cadeiraRepository.save(any(Cadeira.class))).thenReturn(novaCadeira);

        // Act
        Cadeira cadeiraCriada = cadeiraService.criarCadeira(cadeiraDTO);

        // Assert
        assertNotNull(cadeiraCriada);
        assertEquals("Nome da cadeira", cadeiraCriada.getNome());
        verify(cadeiraRepository, times(1)).save(novaCadeira);
    }

    @Test
    void testCriarCadeira_ComNomeInvalido_DeveLancarExcecao() {
        // Arrange
        Categoria categoria = new Categoria();
        categoria.setName("Ergonômica");
        List<Categoria> categorias = new ArrayList<>();
        categorias.add(categoria);

        List<Cor> cores = new ArrayList<>();
        
        Cor novaCor = new Cor();
        novaCor.setCodigo("#ffffff");
        novaCor.setFoto_cadeira("http://site.com/foto.png");
        novaCor.setId(UUID.randomUUID());
        novaCor.setName("Preto");
        RequestCadeiraDTO novaCadeiraDTO = new RequestCadeiraDTO("", "Uma cadeira muito confortáve", "Não sei o que colocar", 5, 10000, "200 x 200", "http://site.com/foto.png", "uma cadeira", "cadeira também", "mais uma", "vich", "Revestimento", categorias, cores);

        Cadeira novaCadeira = new Cadeira(UUID.randomUUID(), "Uma cadeira muito confortáve", "Não sei o que colocar", 5, "200 x 200", "http://site.com/foto.png", "uma cadeira", "cadeira também", "mais uma", "vich", "Revestimento", "http://site.com/foto.jpeg", cores, categorias);
        novaCadeira.setNome("");
        novaCadeira.setPreco(10000);

        // Act & Assert
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            cadeiraService.criarCadeira(novaCadeiraDTO);
        });

        assertEquals("O nome é obrigatório e não pode exceder 20 caracteres.", exception.getMessage());
    }

}