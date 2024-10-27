package com.flex360.api_flex360.dto.carrinho;

import java.util.UUID;

public interface ItemDTO {
    UUID getId();

    String getNome();

    float getPreco();

    int getQuantidade();

    String getFoto();

}