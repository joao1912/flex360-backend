package com.flex360.api_flex360.dto.auth;

import com.flex360.api_flex360.enums.UserRole;

public record RegisterDTO(String nome, String email, String password, UserRole role) {
    
}
