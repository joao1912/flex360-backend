package com.flex360.api_flex360.services;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.flex360.api_flex360.models.Cor;
import com.flex360.api_flex360.repository.CorRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CorService {
    
    private final CorRepository corRepository;

     public List<Cor> buscarTodasCores() {

        List<Cor> cores = corRepository.findAll();
        if (cores.isEmpty()) {
            throw new EntityNotFoundException("Nenhuma cor encontrada.");
        }
        return cores;

    }

    public Cor buscarCorPorId(UUID id) {

        Optional<Cor> cor = corRepository.findById(id);
        return cor.orElseThrow(() -> new EntityNotFoundException("Cor com ID " + id + " não encontrada."));

    }
    
    public Cor criarCor(Cor novaCor) {

        if (novaCor.getName() == null || novaCor.getCodigo() == null) {
            throw new IllegalArgumentException("Nome ou código hexadecimal da cor não podem ser nulos.");
        }
        return corRepository.save(novaCor);

    }

    public Cor editarCor(UUID id, Cor corAtualizada) {

        Cor corExistente = buscarCorPorId(id); 

       
        corExistente.setName(corAtualizada.getName());
        corExistente.setCodigo(corAtualizada.getCodigo());

        return corRepository.save(corExistente);

    }

    public void deletarCor(UUID id) {

        Cor cor = corRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Cor não encontrada com o id: " + id));
        corRepository.delete(cor);

    }
    
}
