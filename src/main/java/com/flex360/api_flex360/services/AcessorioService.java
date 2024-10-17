package com.flex360.api_flex360.services;

import java.util.List;
import java.util.UUID;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.flex360.api_flex360.models.Acessorio;
import com.flex360.api_flex360.repository.AcessorioRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException; 
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AcessorioService {

    private final AcessorioRepository acessorioRepository;

    private void validarAcessorio(Acessorio acessorio) {
        
        if (!StringUtils.hasText(acessorio.getNome()) || acessorio.getNome().length() > 20) {
            throw new ValidationException("O nome do acessório é obrigatório e não pode exceder 20 caracteres.");
        }
        if (acessorio.getPreco() <= 0) {
            throw new ValidationException("O preço do acessório deve ser maior que zero.");
        }
        if (acessorio.getFoto() == null || 
        !acessorio.getFoto().matches("^(https?|ftp)://.*$")) {
            throw new ValidationException("A foto do acessório deve ser uma URL válida");
        }
    }

    public List<Acessorio> buscarTodosAcessorios() {
        List<Acessorio> acessorios = acessorioRepository.findAll();
        if (acessorios.isEmpty()) {
            throw new EntityNotFoundException("Nenhum acessório encontrado");
        }
        return acessorios;
    }

    public Acessorio buscarAcessorioPorId(UUID id) {

        return acessorioRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Acessório não encontrado com ID" ));

    }
    
    public Acessorio criarAcessorio(Acessorio acessorio) {

        try {
            return acessorioRepository.save(acessorio);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao criar o acessório: " + e.getMessage());
        }

    }

    public Acessorio editarAcessorio(UUID id, Acessorio acessorioAtualizado) {

        Acessorio acessorioExistente = buscarAcessorioPorId(id);
        acessorioExistente.setNome(acessorioAtualizado.getNome());
        acessorioExistente.setPreco(acessorioAtualizado.getPreco());
        acessorioExistente.setFoto(acessorioAtualizado.getFoto());
        try {
            return acessorioRepository.save(acessorioExistente);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao editar o acessório: " + e.getMessage());
        }

    }

    public void deletarAcessorio(UUID id) {
         try {
            Acessorio acessorio = buscarAcessorioPorId(id);
            acessorioRepository.delete(acessorio);
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("Não foi possível deletar. Acessório não encontrado com ID: " + id);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException("Acessório com ID " + id + " já foi removido ou não existe.");
        } catch (Exception e) {
            throw new RuntimeException("Erro ao deletar o acessório: " + e.getMessage());
        }

    }
    

}
