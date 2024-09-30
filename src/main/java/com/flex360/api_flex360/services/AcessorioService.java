package com.flex360.api_flex360.services;

import org.springframework.stereotype.Service;
import com.flex360.api_flex360.models.Acessorio;
import com.flex360.api_flex360.repository.AcessorioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AcessorioService {

    private final AcessorioRepository acessorioRepository;
    
    public Acessorio buscarTodosAcessorios(){

        throw new EntityNotFoundException();

    }

     public Acessorio buscarAcessorioPorId() {

        throw new EntityNotFoundException();

    }

    public Acessorio criarAcessorio() {

        throw new EntityNotFoundException();

    }

    public Acessorio editarAcessorio() {

        throw new EntityNotFoundException();

    }

    public Acessorio deletarAcessorio() {

        throw new EntityNotFoundException();

    }
 
    
}
