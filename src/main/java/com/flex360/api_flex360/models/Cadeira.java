package com.flex360.api_flex360.models;

import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cadeira {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
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

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_cor_id", referencedColumnName = "id")
    private Cor cor;

}
