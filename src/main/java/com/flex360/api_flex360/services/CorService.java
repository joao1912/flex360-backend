package com.flex360.api_flex360.services;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.flex360.api_flex360.models.Cor;
import com.flex360.api_flex360.repository.CorRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import java.util.List;
import jakarta.validation.ValidationException;

@Service
@RequiredArgsConstructor
public class CorService {
    
    private final CorRepository corRepository;

    private void validarCor(Cor cor) {
            if (!StringUtils.hasText(cor.getName()) || cor.getName().length() > 10) {
                throw new ValidationException("O nome da cor é obrigatório e não pode exceder 10 caracteres.");
            }
            if (!StringUtils.hasText(cor.getCodigo())) {
                throw new ValidationException("O código da cor é obrigatório.");
            }
        }
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
        validarCor(novaCor);
        return corRepository.save(novaCor);
    }

    public Cor editarCor(UUID id, Cor corAtualizada) {
        Cor corExistente = buscarCorPorId(id); 
        validarCor(corAtualizada);

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
