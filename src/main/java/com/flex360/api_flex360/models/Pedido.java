package com.flex360.api_flex360.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.UUID;

@Entity
@Data

public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
     private UUID id;

     private int quantidade;
}

//não estou colocando as FK