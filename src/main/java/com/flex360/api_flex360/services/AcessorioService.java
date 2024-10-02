package com.flex360.api_flex360.services;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import com.flex360.api_flex360.models.Acessorio;
import com.flex360.api_flex360.repository.AcessorioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AcessorioService {

    private final AcessorioRepository acessorioRepository;

    public List<Acessorio> buscarTodosAcessorios() {
        return acessorioRepository.findAll();

    }

    public Acessorio buscarAcessorioPorId(UUID id) {

        return acessorioRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Acessório não encontrado com ID"));

    }
    
    public Acessorio criarAcessorio(Acessorio acessorio) {

        return acessorioRepository.save(acessorio);

    }

    public Acessorio editarAcessorio(UUID id, Acessorio acessorioAtualizado) {

        Acessorio acessorioExistente = buscarAcessorioPorId(id);
        acessorioExistente.setNome(acessorioAtualizado.getNome());
        acessorioExistente.setPreco(acessorioAtualizado.getPreco());
        acessorioExistente.setFoto(acessorioAtualizado.getFoto());
        return acessorioRepository.save(acessorioExistente);

    }

    public void deletarAcessorio(UUID id) {

        Acessorio acessorio = buscarAcessorioPorId(id);
        acessorioRepository.delete(acessorio);

    }

}
