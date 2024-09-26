package com.flex360.api_flex360.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;

import java.util.UUID;

@Entity
@Data

public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne
    @JoinColumn(name = "fk_cor_id", referencedColumnName = "id")
    private Cor cor;

    @OneToOne
    @JoinColumn(name = "fk_produto_id", referencedColumnName = "id")
    private Produto produto;

    @OneToOne(mappedBy = "pedido", cascade = CascadeType.ALL)
    private Carrinho carrinho;

    private int quantidade;
}

//não estou colocando as FK