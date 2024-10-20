package com.flex360.api_flex360.dto.cadeira;

import java.util.List;

import com.flex360.api_flex360.models.Categoria;


public record RequestCadeiraDTO(String nome, String descricao, String informacoes, int temp_garantia, float preco, String dimencoes,
String foto_cadeira, String foto_dimencoes, String desc_encosto, String desc_apoio, String desc_rodinha, String desc_ajuste_altura, 
String desc_revestimento, List<Categoria> categorias) {
    
}
