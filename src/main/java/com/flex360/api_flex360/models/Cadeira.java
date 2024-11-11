package com.flex360.api_flex360.models;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cadeira extends Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String descricao;

    private String informacoes;

    private int temp_garantia;

    private String dimensoes;

    private String foto_dimensoes;
    private String foto_banner;

    private String desc_encosto;

    private String desc_apoio;

    private String desc_rodinha;

    private String desc_ajuste_altura;

    private String desc_revestimento;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "cadeira_cor", joinColumns = @JoinColumn(name = "cadeira_id"), inverseJoinColumns = @JoinColumn(name = "cor_id"))
    private List<Cor> cores;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "cadeira_categoria", joinColumns = @JoinColumn(name = "cadeira_id"), inverseJoinColumns = @JoinColumn(name = "categoria_id"))
    private List<Categoria> categorias;

}