package com.flex360.api_flex360.services;

import java.util.Optional;
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
        Optional<Carrinho> carrinho = carrinhoRepository.findById(id);
        return carrinho.orElseThrow(() -> new EntityNotFoundException("Carrinho não encontrado"));
    }

    public boolean  deletarCarrinho(UUID id) {

        Optional<Carrinho> carrinho = carrinhoRepository.findById(id);
        if(carrinho.isPresent()) {
            carrinhoRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Carrinho atualizarCarrinho() {
        throw new EntityNotFoundException();
    }


}
