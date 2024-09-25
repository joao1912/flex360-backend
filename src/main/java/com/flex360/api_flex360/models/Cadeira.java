package com.flex360.api_flex360.models;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Data
@AllArgsConstructor
public class Cadeira {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String nome;
    
    private String descricao;

    private String informacoes;

    private int temp_garantia;

    private Float preco;

    private String dimensoes;

    private String foto_cadeira;

    private String foto_dimensoes;

    private String desc_encosto;

    private String desc_apoio;

    private String desc_rodinha;

    private String desc_ajuste_altura;

    private String desc_revestimento;

}
