package com.flex360.api_flex360.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flex360.api_flex360.models.Categoria;
import java.util.List;
import java.util.Optional;


@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, UUID> {

    Optional<Categoria> findByName(String name);
    
} 
    
