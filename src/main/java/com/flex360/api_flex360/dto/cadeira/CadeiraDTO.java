package com.flex360.api_flex360.dto.cadeira;

import java.util.UUID;

import com.flex360.api_flex360.enums.Categorias;


public record CadeiraDTO(UUID id, String nome, String descricao, String informacoes, int temp_garantia, float preco, String dimencoes,
String foto_cadeira, String foto_dimencoes, String desc_encosto, String desc_apoio, String desc_rodinha, String desc_ajuste_altura, 
String desc_revestimento, Categorias categorias) {
    
}
