package com.flex360.api_flex360.dto.carrinho;

import java.util.UUID;

public record ModificaCarrinhoDTO(UUID id, int quantidade, UUID idCorSelecionada) {
    
}