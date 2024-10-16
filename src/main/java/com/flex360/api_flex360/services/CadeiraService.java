package com.flex360.api_flex360.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.flex360.api_flex360.models.Cadeira;
import com.flex360.api_flex360.repository.CadeiraRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;

@Service
public class CadeiraService {

    @Autowired
    private CadeiraRepository cadeiraRepository;

    private void validarCadeira(Cadeira cadeira) {
        
        if (!StringUtils.hasText(cadeira.getNome()) || cadeira.getNome().length() > 20) {
            throw new ValidationException("O nome é obrigatório e não pode exceder 20 caracteres.");
        }

        if (!StringUtils.hasText(cadeira.getDescricao()) || cadeira.getDescricao().length() > 100) {
            throw new ValidationException("A descrição é obrigatória e não pode exceder 100 caracteres.");
        }

        if (cadeira.getPreco() <= 0) {
            throw new ValidationException("O preço deve ser maior que zero.");
        }
    }

    public List<Cadeira> buscarTodasCadeiras() {
        List<Cadeira> cadeiras = cadeiraRepository.findAll();
        if (cadeiras.isEmpty()) {
            throw new EntityNotFoundException("Nenhuma cadeira encontrada.");
        }
        return cadeiras;
    }

    public Cadeira buscarCadeiraPorId(UUID id) {
        return cadeiraRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Cadeira com ID " + id + " não encontrada."));
    }

    public Cadeira criarCadeira(Cadeira novaCadeira) {
        validarCadeira(novaCadeira);
        return cadeiraRepository.save(novaCadeira);
    }

    public Cadeira editarCadeira(UUID id, Cadeira cadeiraAtualizada) {
        Cadeira cadeiraExistente = buscarCadeiraPorId(id);
        validarCadeira(cadeiraAtualizada);

        cadeiraExistente.setNome(cadeiraAtualizada.getNome());
        cadeiraExistente.setDescricao(cadeiraAtualizada.getDescricao());
        cadeiraExistente.setPreco(cadeiraAtualizada.getPreco());

        return cadeiraRepository.save(cadeiraExistente);
    }

    public void deletarCadeira(UUID id) {
        Cadeira cadeira = buscarCadeiraPorId(id);
        cadeiraRepository.delete(cadeira);
    }
}

    

