package com.flex360.api_flex360.models;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity(name = "produto_carrinho")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class ProdutoCarrinho {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne
    @JoinColumn(name = "fk_cor_id", referencedColumnName = "id")
    @Column(nullable = false)
    private Cor cor;

@ManyToOne
@JoinColumn(name = "produto_id", nullable = false)
private Produto produto;
        @ManyToOne
    @JoinColumn(name = "carrinho_id")
    private Carrinho carrinho;

    @Column(nullable = false)
    int quantidade;
}
