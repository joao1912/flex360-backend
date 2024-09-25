package com.flex360.api_flex360.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
public class Cor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
     private UUID id;

     private String name;
     private String codigo;
}
