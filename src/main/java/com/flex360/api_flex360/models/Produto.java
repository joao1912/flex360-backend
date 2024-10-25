package com.flex360.api_flex360.models;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Inheritance(strategy=InheritanceType.JOINED)
@Data
@NoArgsConstructor
public class Produto {
    @Id
    @GeneratedValue(strategy=GenerationType.UUID)
    private UUID id;
    @Column(length=25, nullable=false)
    private String nome;
    private float preco;

}
