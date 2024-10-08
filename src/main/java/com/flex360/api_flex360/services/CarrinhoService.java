package com.flex360.api_flex360.services;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flex360.api_flex360.models.Carrinho;
import com.flex360.api_flex360.models.ProdutoCarrinho;
import com.flex360.api_flex360.repository.CarrinhoRepository;
import com.flex360.api_flex360.repository.ProdutoCarrinhoRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CarrinhoService {

    @Autowired
    private CarrinhoRepository carrinhoRepository;

    @Autowired
    private ProdutoCarrinhoRepository produtoCarrinhoRepository;

    public Carrinho buscarCarrinhoPorId(UUID id) {
        throw new EntityNotFoundException();
    }

    public boolean deletarCarrinho(UUID id) {
        throw new EntityNotFoundException();
    }

    public Carrinho atualizarCarrinho() {
        throw new EntityNotFoundException();
    }

    public List<ProdutoCarrinho> buscarProdutosDoCarrinho(UUID id) {
        Carrinho carrinho = carrinhoRepository.findById(id).orElse(null);

        if(carrinho != null) {
            return carrinho.getProdutosCarrinho();
        }

        return Collections.emptyList();
    }

    
}
