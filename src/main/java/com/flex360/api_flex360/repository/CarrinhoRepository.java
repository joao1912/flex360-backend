package com.flex360.api_flex360.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flex360.api_flex360.models.Carrinho;

@Repository
public interface CarrinhoRepository extends JpaRepository<Carrinho, UUID> {
    
    Optional<Carrinho> findByUsuarioId(UUID usuarioId);
}
