package com.flex360.api_flex360.dto.carrinho;

import java.util.List;
import java.util.UUID;

import com.flex360.api_flex360.models.Cor;

public record CadeiraDTO(UUID id, String nome, float preco, int quantidade, String foto, String descricao, String informacoes, int temp_garantia, String dimenssoes, String foto_dimenssoes, String desc_encosto, String desc_apoio, String desc_rodinha, String desc_ajuste_altura, String desc_revestimento, Cor corSelecionada, List<Cor> coresDisponiveis) implements ItemDTO {

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
    
    public String getDescricao() {
        return descricao;
    }
    
    public String getInformacoes() {
        return informacoes;
    }
    
    public int getTempGarantia() {
        return temp_garantia;
    }
    
    public String getDimenssoes() {
        return dimenssoes;
    }
    
    public String getFotoDimenssoes() {
        return foto_dimenssoes;
    }
    
    public String getDescEncosto() {
        return desc_encosto;
    }
    
    public String getDescApoio() {
        return desc_apoio;
    }
    
    public String getDescRodinha() {
        return desc_rodinha;
    }
    
    public String getDescAjusteAltura() {
        return desc_ajuste_altura;
    }
    
    public String getDescRevestimento() {
        return desc_revestimento;
    }

    public Cor getCorSelecionada() {
        return corSelecionada;
    }

    public List<Cor> getCores_disponiveis() {
        return cores_disponiveis;
    }
    
}