package com.flex360.api_flex360.services;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.flex360.api_flex360.models.Carrinho;
import com.flex360.api_flex360.repository.CarrinhoRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CarrinhoService {

    private final CarrinhoRepository carrinhoRepository;

    public Carrinho buscarCarrinhoPorId(UUID id) {
        throw new EntityNotFoundException();
    }

    public boolean deletarCarrinho(UUID id) {
        throw new EntityNotFoundException();
    }

    public Carrinho atualizarCarrinho() {
        throw new EntityNotFoundException();
    }


}
