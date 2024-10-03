package com.flex360.api_flex360.dto.carrinho;

import java.util.UUID;

public record ResponseCarrinhoDTO(UUID id, String nomeItem, int quantidade, float preco, UUID idCor) {
    
}
