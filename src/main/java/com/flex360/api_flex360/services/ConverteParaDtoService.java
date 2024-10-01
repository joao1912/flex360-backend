package com.flex360.api_flex360.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service
public class ConverteParaDtoService {

    public <E, D> List<D> converterParaDTO(List<E> entidades, DTOMapper<E, D> mapper) {
        return entidades.stream()
            .map(mapper::toDTO)
            .collect(Collectors.toList());
    }
    
    public interface DTOMapper<E, D> {
        D toDTO(E entidade);
    }
}

/*
Exemplo de uso

public List<EntidadeDTO> obterEntidadesComoDTO(List<Entidade> entidades) {
    return converteParaDtoService.converterParaDTO(entidades, entidade -> new EntidadeDTO(entidade.getId(), entidade.getNome()));
}

*/
