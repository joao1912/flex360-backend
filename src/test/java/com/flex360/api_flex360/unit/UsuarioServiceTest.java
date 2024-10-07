package com.flex360.api_flex360.unit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.flex360.api_flex360.repository.UsuarioRepository;
import com.flex360.api_flex360.services.UsuarioService;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {

    @Mock
    UsuarioRepository usuarioRepository;

    @InjectMocks
    UsuarioService usuarioService;

    @Test
    public void buscarTodosUsuarios() {
        
    }
    
}
