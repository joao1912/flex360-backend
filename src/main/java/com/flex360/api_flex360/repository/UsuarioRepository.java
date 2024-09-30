package com.flex360.api_flex360.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.UUID;

import com.flex360.api_flex360.models.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {

    UserDetails findByEmail(String email);

}