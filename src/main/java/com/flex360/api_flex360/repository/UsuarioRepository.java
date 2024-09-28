package com.flex360.api_flex360.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

import com.flex360.api_flex360.models.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {}