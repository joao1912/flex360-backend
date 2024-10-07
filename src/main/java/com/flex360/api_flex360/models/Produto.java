package com.flex360.api_flex360.models;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.NoArgsConstructor;


@MappedSuperclass
@Data
@NoArgsConstructor
public class Produto {
    @Column(length=25, nullable=false)
    private String nome;
    private float preco;
    private String foto;

}
