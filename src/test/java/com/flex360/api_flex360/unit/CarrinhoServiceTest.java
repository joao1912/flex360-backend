package com.flex360.api_flex360.unit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.flex360.api_flex360.dto.carrinho.AcessorioDTO;
import com.flex360.api_flex360.dto.carrinho.CadeiraDTO;
import com.flex360.api_flex360.dto.carrinho.ModificaCarrinhoDTO;
import com.flex360.api_flex360.dto.carrinho.ProdutosDTO;
import com.flex360.api_flex360.exceptions.ErroAoSalvarException;
import com.flex360.api_flex360.models.Acessorio;
import com.flex360.api_flex360.models.Cadeira;
import com.flex360.api_flex360.models.Carrinho;
import com.flex360.api_flex360.models.Produto;
import com.flex360.api_flex360.models.ProdutoCarrinho;
import com.flex360.api_flex360.repository.CarrinhoRepository;
import com.flex360.api_flex360.repository.ProdutoCarrinhoRepository;
import com.flex360.api_flex360.repository.ProdutoRepository;
import com.flex360.api_flex360.services.CarrinhoService;

import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
public class CarrinhoServiceTest {

    @InjectMocks
    private CarrinhoService carrinhoService;

    @Mock
    private CarrinhoRepository carrinhoRepository;

    @Mock
    private ProdutoCarrinhoRepository produtoCarrinhoRepository;

    @Mock
    private ProdutoRepository produtoRepository;

    // Teste para buscarCarrinhoPorId quando o carrinho é encontrado
    @Test
    public void testBuscarCarrinhoPorId_CarrinhoFound() {
        // Arrange
        UUID id = UUID.randomUUID();
        Carrinho carrinho = new Carrinho();
        carrinho.setId(id);

        when(carrinhoRepository.findById(id)).thenReturn(Optional.of(carrinho));

        // Act
        Carrinho result = carrinhoService.buscarCarrinhoPorId(id);

        // Assert
        assertNotNull(result);
        assertEquals(id, result.getId());
        verify(carrinhoRepository, times(1)).findById(id);
    }

    // Teste para buscarCarrinhoPorId quando o carrinho não é encontrado
    @Test
    public void testBuscarCarrinhoPorId_CarrinhoNotFound() {
        // Arrange
        UUID id = UUID.randomUUID();

        when(carrinhoRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            carrinhoService.buscarCarrinhoPorId(id);
        });

        assertEquals("Erro ao encontrar carrinho com esse id", exception.getMessage());
        verify(carrinhoRepository, times(1)).findById(id);
    }

    @Test
    public void testEditarQuantidadeProduto_ThrowsErroAoSalvarException() {
        // Arrange
        UUID carrinhoId = UUID.randomUUID();
        UUID produtoId = UUID.randomUUID();
        ModificaCarrinhoDTO modificaCarrinhoDTO = new ModificaCarrinhoDTO(produtoId, 1, null);

        Carrinho carrinho = new Carrinho();
        carrinho.setId(carrinhoId);

        Produto produto = new Produto();
        produto.setId(produtoId);

        when(carrinhoRepository.findById(carrinhoId)).thenReturn(Optional.of(carrinho));
        when(produtoRepository.findById(produtoId)).thenReturn(Optional.of(produto));
        when(produtoCarrinhoRepository.findByCarrinhoId(carrinhoId)).thenReturn(new ArrayList<>());

        // Simula uma exceção ao salvar
        doThrow(new RuntimeException("Erro no banco de dados"))
                .when(produtoCarrinhoRepository).save(any(ProdutoCarrinho.class));

        // Act & Assert
        ErroAoSalvarException exception = assertThrows(ErroAoSalvarException.class, () -> {
            carrinhoService.editarQuantidadeProduto(carrinhoId, modificaCarrinhoDTO, false);
        });

        assertEquals("Erro ao salvar informações na tabela do carrinho", exception.getMessage());

        // Verifica se o método save foi chamado
        verify(produtoCarrinhoRepository).save(any(ProdutoCarrinho.class));
    }

    // Teste para deletarCarrinho quando o carrinho é encontrado
    @Test
    public void testDeletarCarrinho_CarrinhoFound() {
        // Arrange
        UUID id = UUID.randomUUID();
        Carrinho carrinho = new Carrinho();
        carrinho.setId(id);

        when(carrinhoRepository.findById(id)).thenReturn(Optional.of(carrinho));

        // Act
        carrinhoService.deletarCarrinho(id);

        // Assert
        verify(carrinhoRepository, times(1)).findById(id);
        verify(carrinhoRepository, times(1)).delete(carrinho);
    }

    // Teste para deletarCarrinho quando o carrinho não é encontrado
    @Test
    public void testDeletarCarrinho_CarrinhoNotFound() {
        // Arrange
        UUID id = UUID.randomUUID();

        when(carrinhoRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            carrinhoService.deletarCarrinho(id);
        });

        assertEquals("Carrinho não encontrado para deleção.", exception.getMessage());
        verify(carrinhoRepository, times(1)).findById(id);
    }

    // Teste para buscarProdutosDoCarrinho quando o carrinho não é encontrado
    @Test
    public void testBuscarProdutosDoCarrinho_CarrinhoNotFound() {
        // Arrange
        UUID id = UUID.randomUUID();

        when(carrinhoRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            carrinhoService.buscarProdutosDoCarrinho(id);
        });

        assertEquals("Carrinho não encontrado.", exception.getMessage());
        verify(carrinhoRepository, times(1)).findById(id);
    }

    // Teste para buscarProdutosDoCarrinho quando o carrinho é encontrado
    @Test
    public void testBuscarProdutosDoCarrinho_CarrinhoFound() {
        // Arrange
        UUID id = UUID.randomUUID();

        when(carrinhoRepository.findById(id)).thenReturn(Optional.of(new Carrinho()));

        // Criando objetos de exemplo
        Acessorio acessorio = new Acessorio();
        acessorio.setId(UUID.randomUUID());
        acessorio.setNome("Acessorio 1");
        acessorio.setPreco(100);
        acessorio.setFoto("foto_acessorio_1.jpg");

        Cadeira cadeira = new Cadeira();
        cadeira.setId(UUID.randomUUID());
        cadeira.setNome("Cadeira 1");
        cadeira.setPreco(500);
        cadeira.setFoto("foto_cadeira_1.jpg");

        ProdutoCarrinho pcAcessorio = new ProdutoCarrinho();
        pcAcessorio.setProduto(acessorio);
        pcAcessorio.setQuantidade(2);

        ProdutoCarrinho pcCadeira = new ProdutoCarrinho();
        pcCadeira.setProduto(cadeira);
        pcCadeira.setQuantidade(1);

        List<ProdutoCarrinho> produtos = Arrays.asList(pcAcessorio, pcCadeira);

        when(produtoCarrinhoRepository.findByCarrinhoId(id)).thenReturn(produtos);

        // Act
        ProdutosDTO result = carrinhoService.buscarProdutosDoCarrinho(id);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.acessorios().size());
        assertEquals(1, result.cadeiras().size());

        AcessorioDTO acessorioDTO = result.acessorios().get(0);
        assertEquals(acessorio.getId(), acessorioDTO.id());
        assertEquals(acessorio.getNome(), acessorioDTO.nome());
        assertEquals(acessorio.getPreco(), acessorioDTO.preco());
        assertEquals(pcAcessorio.getQuantidade(), acessorioDTO.quantidade());
        assertEquals(acessorio.getFoto(), acessorioDTO.foto());

        CadeiraDTO cadeiraDTO = result.cadeiras().get(0);
        assertEquals(cadeira.getId(), cadeiraDTO.id());
        assertEquals(cadeira.getNome(), cadeiraDTO.nome());
        assertEquals(cadeira.getPreco(), cadeiraDTO.preco());
        assertEquals(pcCadeira.getQuantidade(), cadeiraDTO.quantidade());

        verify(carrinhoRepository, times(1)).findById(id);
        verify(produtoCarrinhoRepository, times(1)).findByCarrinhoId(id);
    }

    // Teste para editarQuantidadeProduto quando o carrinho não é encontrado
    @Test
    public void testEditarQuantidadeProduto_CarrinhoNotFound() {
        // Arrange
        UUID carrinhoId = UUID.randomUUID();
        UUID produtoId = UUID.randomUUID();
        ModificaCarrinhoDTO modificaCarrinhoDTO = new ModificaCarrinhoDTO(produtoId, 1, null);

        when(carrinhoRepository.findById(carrinhoId)).thenReturn(Optional.empty());

        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            carrinhoService.editarQuantidadeProduto(carrinhoId, modificaCarrinhoDTO, false);
        });

        assertEquals("Carrinho não encontrado.", exception.getMessage());
        verify(carrinhoRepository, times(1)).findById(carrinhoId);
    }

    // Teste para editarQuantidadeProduto quando o produto não é encontrado
    @Test
    public void testEditarQuantidadeProduto_ProdutoNotFound() {
        // Arrange
        UUID carrinhoId = UUID.randomUUID();
        UUID produtoId = UUID.randomUUID();
        ModificaCarrinhoDTO modificaCarrinhoDTO = new ModificaCarrinhoDTO(produtoId, 1, null);

        when(carrinhoRepository.findById(carrinhoId)).thenReturn(Optional.of(new Carrinho()));
        when(produtoRepository.findById(produtoId)).thenReturn(Optional.empty());

        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            carrinhoService.editarQuantidadeProduto(carrinhoId, modificaCarrinhoDTO, false);
        });

        assertEquals("Produto não encontrado.", exception.getMessage());
        verify(carrinhoRepository, times(1)).findById(carrinhoId);
        verify(produtoRepository, times(1)).findById(produtoId);
    }

    // Teste para editarQuantidadeProduto quando remove quantidade e deleta o
    // ProdutoCarrinho
    @Test
    public void testEditarQuantidadeProduto_RemoveQuantidade_DeleteProdutoCarrinho() {
        // Arrange
        UUID carrinhoId = UUID.randomUUID();
        UUID produtoId = UUID.randomUUID();
        int quantidadeToRemove = 2;
        ModificaCarrinhoDTO modificaCarrinhoDTO = new ModificaCarrinhoDTO(produtoId, quantidadeToRemove, null);

        Carrinho carrinho = new Carrinho();
        carrinho.setId(carrinhoId);
        Produto produto = new Acessorio();
        produto.setId(produtoId);

        when(carrinhoRepository.findById(carrinhoId)).thenReturn(Optional.of(carrinho));
        when(produtoRepository.findById(produtoId)).thenReturn(Optional.of(produto));

        ProdutoCarrinho produtoCarrinho = new ProdutoCarrinho();
        produtoCarrinho.setCarrinho(carrinho);
        produtoCarrinho.setProduto(produto);
        produtoCarrinho.setQuantidade(2);

        // Lista em memória para simular o repositório
        List<ProdutoCarrinho> produtosInRepository = new ArrayList<>();
        produtosInRepository.add(produtoCarrinho);

        // Configura o mock para retornar o estado atual da lista
        when(produtoCarrinhoRepository.findByCarrinhoId(carrinhoId))
                .thenAnswer(invocation -> new ArrayList<>(produtosInRepository));

        // Simula o comportamento do método delete
        doAnswer(invocation -> {
            produtosInRepository.remove(produtoCarrinho);
            return null;
        }).when(produtoCarrinhoRepository).delete(produtoCarrinho);

        // Act
        ProdutosDTO result = carrinhoService.editarQuantidadeProduto(carrinhoId, modificaCarrinhoDTO, true);

        // Assert
        verify(produtoCarrinhoRepository).delete(produtoCarrinho);
        verify(carrinhoRepository, times(2)).findById(carrinhoId);
        verify(produtoCarrinhoRepository, times(2)).findByCarrinhoId(carrinhoId);

        assertNotNull(result);
        assertTrue(result.acessorios().isEmpty());
        assertTrue(result.cadeiras().isEmpty());
    }

    // Teste para editarQuantidadeProduto quando remove quantidade e atualiza o
    // ProdutoCarrinho
    @Test
    public void testEditarQuantidadeProduto_RemoveQuantidade_SubtractQuantity() {
        // Arrange
        UUID carrinhoId = UUID.randomUUID();
        UUID produtoId = UUID.randomUUID();
        int quantidadeToRemove = 1;
        ModificaCarrinhoDTO modificaCarrinhoDTO = new ModificaCarrinhoDTO(produtoId, quantidadeToRemove, null);

        Carrinho carrinho = new Carrinho();
        carrinho.setId(carrinhoId); // Definindo o ID do carrinho

        Produto produto = new Acessorio();
        produto.setId(produtoId);

        when(carrinhoRepository.findById(carrinhoId)).thenReturn(Optional.of(carrinho));
        when(produtoRepository.findById(produtoId)).thenReturn(Optional.of(produto));

        ProdutoCarrinho produtoCarrinho = new ProdutoCarrinho();
        produtoCarrinho.setCarrinho(carrinho);
        produtoCarrinho.setProduto(produto);
        produtoCarrinho.setQuantidade(2);

        // Lista em memória para simular o repositório
        List<ProdutoCarrinho> produtosInRepository = new ArrayList<>();
        produtosInRepository.add(produtoCarrinho);

        // Configura o mock para retornar o estado atual da lista
        when(produtoCarrinhoRepository.findByCarrinhoId(carrinhoId))
                .thenAnswer(invocation -> new ArrayList<>(produtosInRepository));

        // Simula o comportamento do método save
        doAnswer(invocation -> {
            ProdutoCarrinho pc = invocation.getArgument(0);
            // Atualiza a quantidade no objeto existente
            produtosInRepository
                    .removeIf(existingPc -> existingPc.getProduto().getId().equals(pc.getProduto().getId()));
            produtosInRepository.add(pc);
            return null;
        }).when(produtoCarrinhoRepository).save(any(ProdutoCarrinho.class));

        // Act
        carrinhoService.editarQuantidadeProduto(carrinhoId, modificaCarrinhoDTO, true);

        // Assert
        // Verifica se a quantidade foi atualizada corretamente
        ProdutoCarrinho updatedProdutoCarrinho = produtosInRepository.stream()
                .filter(pc -> pc.getProduto().getId().equals(produtoId))
                .findFirst()
                .orElse(null);

        assertNotNull(updatedProdutoCarrinho);
        assertEquals(1, updatedProdutoCarrinho.getQuantidade());

        verify(produtoCarrinhoRepository, times(1)).save(any(ProdutoCarrinho.class));
        verify(produtoCarrinhoRepository, never()).delete(any());

        verify(carrinhoRepository, times(2)).findById(carrinhoId);
        verify(produtoCarrinhoRepository, times(2)).findByCarrinhoId(carrinhoId);
    }

    // Teste para editarQuantidadeProduto quando adiciona quantidade ao
    // ProdutoCarrinho existente
    @Test
    public void testEditarQuantidadeProduto_AddQuantidade() {
        // Arrange
        UUID carrinhoId = UUID.randomUUID();
        UUID produtoId = UUID.randomUUID();
        int quantidadeToAdd = 3;
        ModificaCarrinhoDTO modificaCarrinhoDTO = new ModificaCarrinhoDTO(produtoId, quantidadeToAdd, null);

        Carrinho carrinho = new Carrinho();
        carrinho.setId(carrinhoId); // Definindo o ID do carrinho

        Produto produto = new Acessorio();
        produto.setId(produtoId);

        when(carrinhoRepository.findById(carrinhoId)).thenReturn(Optional.of(carrinho));
        when(produtoRepository.findById(produtoId)).thenReturn(Optional.of(produto));

        ProdutoCarrinho produtoCarrinho = new ProdutoCarrinho();
        produtoCarrinho.setCarrinho(carrinho);
        produtoCarrinho.setProduto(produto);
        produtoCarrinho.setQuantidade(2);

        // Lista em memória para simular o repositório
        List<ProdutoCarrinho> produtosInRepository = new ArrayList<>();
        produtosInRepository.add(produtoCarrinho);

        // Configura o mock para retornar o estado atual da lista
        when(produtoCarrinhoRepository.findByCarrinhoId(carrinhoId))
                .thenAnswer(invocation -> new ArrayList<>(produtosInRepository));

        // Simula o comportamento do método save
        doAnswer(invocation -> {
            ProdutoCarrinho pc = invocation.getArgument(0);
            // Atualiza a quantidade no objeto existente
            produtosInRepository
                    .removeIf(existingPc -> existingPc.getProduto().getId().equals(pc.getProduto().getId()));
            produtosInRepository.add(pc);
            return null;
        }).when(produtoCarrinhoRepository).save(any(ProdutoCarrinho.class));

        // Act
        carrinhoService.editarQuantidadeProduto(carrinhoId, modificaCarrinhoDTO, false);

        // Assert
        // Verifica se a quantidade foi atualizada corretamente
        ProdutoCarrinho updatedProdutoCarrinho = produtosInRepository.stream()
                .filter(pc -> pc.getProduto().getId().equals(produtoId))
                .findFirst()
                .orElse(null);

        assertNotNull(updatedProdutoCarrinho);
        assertEquals(5, updatedProdutoCarrinho.getQuantidade());

        verify(produtoCarrinhoRepository, times(1)).save(any(ProdutoCarrinho.class));
        verify(produtoCarrinhoRepository, never()).delete(any());

        verify(carrinhoRepository, times(2)).findById(carrinhoId);
        verify(produtoCarrinhoRepository, times(2)).findByCarrinhoId(carrinhoId);
    }

    // Teste para editarQuantidadeProduto quando o ProdutoCarrinho não existe e
    // adiciona quantidade
    @Test
    public void testEditarQuantidadeProduto_ProdutoCarrinhoNotExist_AddQuantidade() {
        // Arrange
        UUID carrinhoId = UUID.randomUUID();
        UUID produtoId = UUID.randomUUID();
        int quantidadeToAdd = 2;
        ModificaCarrinhoDTO modificaCarrinhoDTO = new ModificaCarrinhoDTO(produtoId, quantidadeToAdd, null);

        Carrinho carrinho = new Carrinho();
        carrinho.setId(carrinhoId);

        Produto produto = new Acessorio();
        produto.setId(produtoId);

        when(carrinhoRepository.findById(carrinhoId)).thenReturn(Optional.of(carrinho));
        when(produtoRepository.findById(produtoId)).thenReturn(Optional.of(produto));

        List<ProdutoCarrinho> produtos = Collections.emptyList();
        when(produtoCarrinhoRepository.findByCarrinhoId(carrinhoId)).thenReturn(produtos);

        // Act

        carrinhoService.editarQuantidadeProduto(carrinhoId, modificaCarrinhoDTO, false);

        // Assert
        ArgumentCaptor<ProdutoCarrinho> captor = ArgumentCaptor.forClass(ProdutoCarrinho.class);
        verify(produtoCarrinhoRepository, times(1)).save(captor.capture());

        ProdutoCarrinho savedProdutoCarrinho = captor.getValue();
        assertEquals(carrinho, savedProdutoCarrinho.getCarrinho());
        assertEquals(produto, savedProdutoCarrinho.getProduto());
        assertEquals(quantidadeToAdd, savedProdutoCarrinho.getQuantidade());

        verify(carrinhoRepository, times(2)).findById(carrinhoId);

    }

    // Teste para deletaProduto quando o produto é encontrado
    @Test
    public void testDeletaProduto_ProdutoFound() {
        // Arrange
        UUID produtoId = UUID.randomUUID();

        Produto produto = new Acessorio();
        produto.setId(produtoId);

        when(produtoRepository.findById(produtoId)).thenReturn(Optional.of(produto));

        // Act
        carrinhoService.deletaProduto(produtoId);

        // Assert
        verify(produtoRepository, times(1)).findById(produtoId);
        verify(produtoCarrinhoRepository, times(1)).deleteByProdutoId(produtoId);
    }

    // Teste para deletaProduto quando o produto não é encontrado
    @Test
    public void testDeletaProduto_ProdutoNotFound() {
        // Arrange
        UUID produtoId = UUID.randomUUID();

        when(produtoRepository.findById(produtoId)).thenReturn(Optional.empty());

        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            carrinhoService.deletaProduto(produtoId);
        });

        assertEquals("Produto não encontrado.", exception.getMessage());
        verify(produtoRepository, times(1)).findById(produtoId);
        verify(produtoCarrinhoRepository, never()).deleteByProdutoId(any());
    }

}
