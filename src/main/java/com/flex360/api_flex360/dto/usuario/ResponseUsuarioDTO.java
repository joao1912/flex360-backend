package com.flex360.api_flex360.dto.usuario;

import java.util.UUID;

public record ResponseUsuarioDTO(UUID id, String nome, String email) {
    
}
