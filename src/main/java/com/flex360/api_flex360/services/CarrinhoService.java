package com.flex360.api_flex360.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flex360.api_flex360.dto.carrinho.AcessorioDTO;
import com.flex360.api_flex360.dto.carrinho.CadeiraDTO;
import com.flex360.api_flex360.dto.carrinho.ModificaCarrinhoDTO;
import com.flex360.api_flex360.dto.carrinho.ProdutosDTO;
import com.flex360.api_flex360.models.Acessorio;
import com.flex360.api_flex360.models.Cadeira;
import com.flex360.api_flex360.models.Carrinho;
import com.flex360.api_flex360.models.Produto;
import com.flex360.api_flex360.models.ProdutoCarrinho;
import com.flex360.api_flex360.repository.CarrinhoRepository;
import com.flex360.api_flex360.repository.ProdutoCarrinhoRepository;
import com.flex360.api_flex360.repository.ProdutoRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CarrinhoService {

    @Autowired
    private CarrinhoRepository carrinhoRepository;

    @Autowired
    private ProdutoCarrinhoRepository produtoCarrinhoRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    public Carrinho buscarCarrinhoPorId(UUID id) {
        return carrinhoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Erro ao encontrar carrinho com esse id"));
    }

    public void deletarCarrinho(UUID id) {

        Carrinho carrinho = carrinhoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Carrinho não encontrado para deleção."));
        carrinhoRepository.delete(carrinho);
    }

    public Carrinho atualizarCarrinho() {
        throw new EntityNotFoundException();
    }

    public ProdutosDTO buscarProdutosDoCarrinho(UUID id) {
        Carrinho carrinho = carrinhoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Carrinho não encontrado."));

        List<AcessorioDTO> acessorios = new ArrayList<>();
        List<CadeiraDTO> cadeiras = new ArrayList<>();

        for (ProdutoCarrinho produtoCarrinho : carrinho.getProdutosCarrinho()) {

            Produto produto = produtoCarrinho.getProduto();

            switch (produto) {

                case Acessorio acessorio -> {

                    acessorios.add(new AcessorioDTO(acessorio.getId(), acessorio.getNome(), acessorio.getPreco(),
                            produtoCarrinho.getQuantidade(), acessorio.getFoto()));

                }
                case Cadeira cadeira -> {

                    cadeiras.add(new CadeiraDTO(cadeira.getId(), cadeira.getNome(), cadeira.getPreco(),
                            produtoCarrinho.getQuantidade(), cadeira.getFoto(), cadeira.getDescricao(),
                            cadeira.getInformacoes(), cadeira.getTemp_garantia(), cadeira.getDimensoes(),
                            cadeira.getFoto_dimensoes(), cadeira.getDesc_encosto(), cadeira.getDesc_apoio(),
                            cadeira.getDesc_rodinha(), cadeira.getDesc_ajuste_altura(),
                            cadeira.getDesc_revestimento()));
                            
                }
                default -> {
                    throw  new IllegalArgumentException("Tipo de produto desconhecido");
                }
            }
        }

        return new ProdutosDTO(acessorios, cadeiras);
    }

    public ProdutosDTO adicionarProduto(UUID id, ModificaCarrinhoDTO modificaCarrinhoDTO) {
        Carrinho carrinho = carrinhoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Carrinho não encontrado."));

        Produto produto = produtoRepository.findById(modificaCarrinhoDTO.id())
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado."));

        Optional<ProdutoCarrinho> existente = carrinho.getProdutosCarrinho().stream()
                .filter(pc -> pc.getProduto().getId().equals(produto.getId()))
                .findFirst();

        if (existente.isPresent()) {
            ProdutoCarrinho produtoCarrinho = existente.get();
            produtoCarrinho.setQuantidade(produtoCarrinho.getQuantidade() + modificaCarrinhoDTO.quantidade());
            if (produtoCarrinho.getQuantidade() <= 0) {
                produtoCarrinhoRepository.delete(produtoCarrinho);
            }
        } else {
            ProdutoCarrinho produtoCarrinho = new ProdutoCarrinho();
            produtoCarrinho.setProduto(produto);
            produtoCarrinho.setQuantidade(modificaCarrinhoDTO.quantidade());
            produtoCarrinho.setCarrinho(carrinho);
            carrinho.getProdutosCarrinho().add(produtoCarrinho);
        }

        carrinhoRepository.save(carrinho);

        return buscarProdutosDoCarrinho(carrinho.getId());
    }

}