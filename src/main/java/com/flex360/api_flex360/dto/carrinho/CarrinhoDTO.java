package com.flex360.api_flex360.dto.carrinho;

import com.flex360.api_flex360.models.Pedido;
import com.flex360.api_flex360.models.Usuario;

public record CarrinhoDTO(Pedido pedido, Usuario usuario) {
    
}
