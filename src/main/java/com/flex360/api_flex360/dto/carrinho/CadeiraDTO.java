package com.flex360.api_flex360.dto.carrinho;

import java.util.List;
import java.util.UUID;

import com.flex360.api_flex360.models.Cor;

public record CadeiraDTO(UUID id, String nome, float preco, int quantidade, String descricao, String informacoes, int temp_garantia, String dimenssoes, String foto_dimenssoes, String foto_banner, String desc_encosto, String desc_apoio, String desc_rodinha, String desc_ajuste_altura, String desc_revestimento, Cor corSelecionada, List<Cor> coresDisponiveis) { }