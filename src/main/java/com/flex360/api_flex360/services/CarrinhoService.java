package com.flex360.api_flex360.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional(readOnly = true)
    public Carrinho buscarCarrinhoPorId(UUID id) {
        return carrinhoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Erro ao encontrar carrinho com esse id"));
    }

    public void deletarCarrinho(UUID id) {

        Carrinho carrinho = carrinhoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Carrinho não encontrado para deleção."));
        carrinhoRepository.delete(carrinho);
    }

    @Transactional(readOnly = true)
    public ProdutosDTO buscarProdutosDoCarrinho(UUID id) {
        carrinhoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Carrinho não encontrado."));

        List<AcessorioDTO> acessoriosDTO = new ArrayList<>();
        List<CadeiraDTO> cadeirasDTO = new ArrayList<>();

        List<ProdutoCarrinho> produtos = produtoCarrinhoRepository.findByCarrinhoId(id);

        for (ProdutoCarrinho produtoCarrinho : produtos) {

            Produto produto = produtoCarrinho.getProduto();

            switch (produto) {

                case Acessorio acessorio -> {

                    acessoriosDTO.add(new AcessorioDTO(acessorio.getId(), acessorio.getNome(), acessorio.getPreco(),
                            produtoCarrinho.getQuantidade(), acessorio.getFoto()));

                }
                case Cadeira cadeira -> {

                    cadeirasDTO.add(new CadeiraDTO(cadeira.getId(), cadeira.getNome(), cadeira.getPreco(),
                            produtoCarrinho.getQuantidade(), cadeira.getFoto(), cadeira.getDescricao(),
                            cadeira.getInformacoes(), cadeira.getTemp_garantia(), cadeira.getDimensoes(),
                            cadeira.getFoto_dimensoes(), cadeira.getDesc_encosto(), cadeira.getDesc_apoio(),
                            cadeira.getDesc_rodinha(), cadeira.getDesc_ajuste_altura(),
                            cadeira.getDesc_revestimento(),
                            produtoCarrinho.getCor(), cadeira.getCores()));

                }
                default -> {
                    throw new IllegalArgumentException("Tipo de produto desconhecido");
                }
            }
        }

        return new ProdutosDTO(acessoriosDTO, cadeirasDTO);
    }

    public ProdutosDTO editarQuantidadeProduto(UUID id, ModificaCarrinhoDTO modificaCarrinhoDTO,
            boolean removerQuantidade) {

        Carrinho carrinho = carrinhoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Carrinho não encontrado."));

        Produto produto = produtoRepository.findById(modificaCarrinhoDTO.id())
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado."));

        List<ProdutoCarrinho> produtos = produtoCarrinhoRepository.findByCarrinhoId(id);

        Optional<ProdutoCarrinho> existente = produtos.stream()
                .filter(pc -> pc.getProduto().getId().equals(produto.getId()))
                .findFirst();

        if (existente.isPresent()) {

            ProdutoCarrinho produtoCarrinho = existente.get();

            if (removerQuantidade) {

                if (produtoCarrinho.getQuantidade() <= modificaCarrinhoDTO.quantidade()) {

                    produtoCarrinhoRepository.delete(produtoCarrinho);

                } else {

                    produtoCarrinho.setQuantidade(produtoCarrinho.getQuantidade() - modificaCarrinhoDTO.quantidade());

                }

            } else {

                produtoCarrinho.setQuantidade(produtoCarrinho.getQuantidade() + modificaCarrinhoDTO.quantidade());

            }

            try {
                produtoCarrinhoRepository.save(produtoCarrinho);
            } catch (Exception e) {
                throw new ErroAoSalvarException("Erro ao salvar informações na tabela do carrinho", e);
            }
            // tratar erro aqui

        } else {

            if (removerQuantidade) {

                throw new IllegalArgumentException("Produto não está no carrinho");
                // tratar erro aqui

            }

            ProdutoCarrinho produtoCarrinho = new ProdutoCarrinho();
            produtoCarrinho.setProduto(produto);
            produtoCarrinho.setQuantidade(modificaCarrinhoDTO.quantidade());
            produtoCarrinho.setCarrinho(carrinho);

            // tratar erro aqui
            try {
                produtoCarrinhoRepository.save(produtoCarrinho);
            } catch (Exception e) {
                throw new ErroAoSalvarException("Erro ao salvar informações na tabela do carrinho", e);
            }
        }

        return buscarProdutosDoCarrinho(carrinho.getId());
    }

    public void deletaProduto(UUID id) {

        produtoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado."));

        produtoCarrinhoRepository.deleteByProdutoId(id);

    }

}