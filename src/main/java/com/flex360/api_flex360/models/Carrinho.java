package com.flex360.api_flex360.models;


import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Carrinho {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
   private UUID id;
  
}
