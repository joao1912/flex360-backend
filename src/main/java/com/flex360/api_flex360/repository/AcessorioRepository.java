package com.flex360.api_flex360.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.flex360.api_flex360.models.Acessorio;

@Repository
public interface AcessorioRepository extends JpaRepository<Acessorio, UUID> {
    
}
