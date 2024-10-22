package com.flex360.api_flex360.infra.initializer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.flex360.api_flex360.enums.UserRole;
import com.flex360.api_flex360.models.Usuario;
import com.flex360.api_flex360.repository.UsuarioRepository;

@Component
public class AdminDataInitializer implements CommandLineRunner {

    @Autowired
    private UsuarioRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public void run(String... args) throws Exception {
        
        if (userRepository.findByEmail("admin@gmail.com") == null) {
            Usuario adminUser = new Usuario();
            adminUser.setNome("admin");
            adminUser.setEmail("admin@gmail.com");
            adminUser.setSenha(passwordEncoder.encode("admin123"));
            adminUser.setRole(UserRole.ADMIN);
            userRepository.save(adminUser);
        
        }
    }
    
}
