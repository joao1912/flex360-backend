package com.flex360.api_flex360.dto.carrinho;

import java.util.UUID;

public record AcessorioDTO(UUID id, String nome, float preco, int quantidade, String foto) implements ItemDTO{

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public String getNome() {
        return nome;
    }

    @Override
    public float getPreco() {
        return preco;
    }

    @Override
    public int getQuantidade() {
        return quantidade;
    }

    @Override
    public String getFoto() {
        return foto;
    }

    
    
}
