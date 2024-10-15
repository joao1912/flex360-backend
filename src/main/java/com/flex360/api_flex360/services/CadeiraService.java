package com.flex360.api_flex360.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flex360.api_flex360.models.Cadeira;
import com.flex360.api_flex360.repository.CadeiraRepository;


import jakarta.persistence.EntityNotFoundException;


@Service
public class CadeiraService {

    @Autowired
    private CadeiraRepository cadeiraRepository;

    public List<Cadeira> buscarTodasCadeiras() {

        List<Cadeira> cadeiras = cadeiraRepository.findAll();
        if (cadeiras.isEmpty()) {
            throw new EntityNotFoundException("Nenhuma cadeira encontrada.");
        }
        return cadeiras;

    }

    public Cadeira buscarCadeiraPorId(UUID id) {

        Optional<Cadeira> cadeira = cadeiraRepository.findById(id);
        return cadeira.orElseThrow(() -> new EntityNotFoundException("Cadeira com ID " + id + " não encontrada."));

    }
    
    public Cadeira criarCadeira(Cadeira novaCadeira) {

        
        return cadeiraRepository.save(novaCadeira);

    }

    public Cadeira editarCadeira(UUID id, Cadeira cadeiraAtualizada) {

        Cadeira cadeiraExistente = buscarCadeiraPorId(id); 


        return cadeiraRepository.save(cadeiraExistente);

    }

    public Cadeira deletarCadeira(UUID id) {

        Cadeira cadeira = buscarCadeiraPorId(id); 
        cadeiraRepository.delete(cadeira);      throw new EntityNotFoundException();

    }
    
}

    

