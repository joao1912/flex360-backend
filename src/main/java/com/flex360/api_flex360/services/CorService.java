package com.flex360.api_flex360.services;

import org.springframework.stereotype.Service;

import com.flex360.api_flex360.models.Cor;
import com.flex360.api_flex360.repository.CorRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CorService {
    
    private final CorRepository corRepository;

     public Cor buscarTodasCores() {

        throw new EntityNotFoundException();

    }

    public Cor buscarCorPorId() {

        throw new EntityNotFoundException();

    }
    
    public Cor criarCor() {

        throw new EntityNotFoundException();

    }

    public Cor editarCor() {

        throw new EntityNotFoundException();

    }

    public Cor deletarCor() {

        throw new EntityNotFoundException();

    }
    
}
