package com.flex360.api_flex360.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.flex360.api_flex360.dto.cadeira.RequestCadeiraDTO;
import com.flex360.api_flex360.dto.cadeira.SugestaoErgonomicaDTO;
import com.flex360.api_flex360.exceptions.ErroAoSalvarException;
import com.flex360.api_flex360.models.Cadeira;
import com.flex360.api_flex360.models.Categoria;
import com.flex360.api_flex360.models.Cor;
import com.flex360.api_flex360.repository.CadeiraRepository;
import com.flex360.api_flex360.repository.CategoriaRepository;
import com.flex360.api_flex360.repository.CorRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;

@Service
public class CadeiraService {

    @Autowired
    private CadeiraRepository cadeiraRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private CorRepository corRepository;

    private void validarCadeira(RequestCadeiraDTO cadeira) {
        
        if (!StringUtils.hasText(cadeira.nome()) || cadeira.nome().length() > 20) {
            throw new ValidationException("O nome é obrigatório e não pode exceder 20 caracteres.");
        }

        if (!StringUtils.hasText(cadeira.descricao()) || cadeira.descricao().length() > 200) {
            throw new ValidationException("A descrição é obrigatória e não pode exceder 200 caracteres.");
        }

        if (cadeira.preco() <= 0) {
            throw new ValidationException("O preço deve ser maior que zero.");
        }

        // validar as outras props aqui
        
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

    @Transactional
    public Cadeira criarCadeira(RequestCadeiraDTO cadeiraDTO) {

        validarCadeira(cadeiraDTO);

        List<Categoria> categoriasModels = new ArrayList<>();
        List<Cor> corModels = new ArrayList<>();

        for (Categoria categoria : cadeiraDTO.categorias()) {

            Optional<Categoria> categoriaExiste = categoriaRepository.findByName(categoria.getName());

            if (categoriaExiste.isPresent()) {

                categoriasModels.add(categoriaExiste.get());

            } else {

                Categoria novaCategoria = new Categoria();
                novaCategoria.setName(categoria.getName());
                Categoria categoriaCriada = categoriaRepository.save(novaCategoria);
                categoriasModels.add(categoriaCriada);

            }

        }

        for (Cor cor : cadeiraDTO.cores_disponiveis()) {

            Optional<Cor> corExiste =  corRepository.findByName(cor.getName());

            if (corExiste.isPresent()){

                corModels.add(corExiste.get());

            }else{

                Cor novaCor = new Cor();
                novaCor.setName(cor.getName());
                novaCor.setCodigo(cor.getCodigo());
                novaCor.setFoto_cadeira(cor.getFoto_cadeira());

                Cor corCriada = corRepository.save(novaCor);
                corModels.add(corCriada);

            }

        }

        Cadeira novaCadeira = new Cadeira();
        novaCadeira.setNome(cadeiraDTO.nome());
        novaCadeira.setDescricao(cadeiraDTO.descricao());
        novaCadeira.setInformacoes(cadeiraDTO.informacoes());
        novaCadeira.setTemp_garantia(cadeiraDTO.temp_garantia());
        novaCadeira.setPreco(cadeiraDTO.preco());
        novaCadeira.setDimensoes(cadeiraDTO.dimencoes());
        novaCadeira.setFoto_dimensoes(cadeiraDTO.foto_dimencoes());
        novaCadeira.setFoto_banner(cadeiraDTO.Foto_banner());
        novaCadeira.setDesc_encosto(cadeiraDTO.desc_encosto());
        novaCadeira.setDesc_apoio(cadeiraDTO.desc_apoio());
        novaCadeira.setDesc_rodinha(cadeiraDTO.desc_rodinha());
        novaCadeira.setDesc_ajuste_altura(cadeiraDTO.desc_ajuste_altura());
        novaCadeira.setDesc_revestimento(cadeiraDTO.desc_revestimento());
        novaCadeira.setCores(corModels);
        novaCadeira.setCategorias(categoriasModels);

        try {
            return cadeiraRepository.save(novaCadeira);
        } catch (Exception e) {
            throw new ErroAoSalvarException("Erro ao salvar cadeira.", e);
        }
    }

    public Cadeira editarCadeira(UUID id, RequestCadeiraDTO cadeiraAtualizada) {
        Cadeira cadeiraExistente = buscarCadeiraPorId(id);
        validarCadeira(cadeiraAtualizada);

        cadeiraExistente.setNome(cadeiraAtualizada.nome());
        cadeiraExistente.setDescricao(cadeiraAtualizada.descricao());
        cadeiraExistente.setInformacoes(cadeiraAtualizada.informacoes());
        cadeiraExistente.setTemp_garantia(cadeiraAtualizada.temp_garantia());
        cadeiraExistente.setPreco(cadeiraAtualizada.preco());
        cadeiraExistente.setDimensoes(cadeiraAtualizada.dimencoes());
        cadeiraExistente.setFoto_dimensoes(cadeiraAtualizada.foto_dimencoes());
        cadeiraExistente.setDesc_encosto(cadeiraAtualizada.desc_encosto());
        cadeiraExistente.setDesc_apoio(cadeiraAtualizada.desc_apoio());
        cadeiraExistente.setDesc_rodinha(cadeiraAtualizada.desc_rodinha());
        cadeiraExistente.setDesc_ajuste_altura(cadeiraAtualizada.desc_ajuste_altura());
        cadeiraExistente.setDesc_revestimento(cadeiraAtualizada.desc_revestimento());
        cadeiraExistente.setCores(cadeiraAtualizada.cores_disponiveis());
        cadeiraExistente.setCategorias(cadeiraAtualizada.categorias());

        try {
            return cadeiraRepository.save(cadeiraExistente);
        } catch (Exception e) {
            throw new ErroAoSalvarException("Erro ao salvar cadeira", e);
        }
    }

    public Cadeira sugestaoErgonomica(SugestaoErgonomicaDTO dados) {

        List<Cadeira> cadeiras = cadeiraRepository.findAll();
        List<Cadeira> cadeirasCategoria = new ArrayList<>();

        float peso = dados.peso();
        float altura = dados.altura();
        String[] categorias = dados.categoria();

        for (Cadeira cadeira : cadeiras) {

            for (String nomeCategoria : categorias) {

                List<Categoria> categoriasDaCadeira = cadeira.getCategorias();

                boolean existeCategoria = categoriasDaCadeira.stream()
                        .anyMatch(categoria -> categoria.getName().equals(nomeCategoria));

                if (existeCategoria) {
                    cadeirasCategoria.add(cadeira);
                    break;
                }

            }

        }
        return selecionarCadeiraPorPesoEAltura(cadeirasCategoria, peso, altura);

    }

    public void deletarCadeira(UUID id) {

        try {
            cadeiraRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException("Cadeira com ID " + id + " já foi removida ou não existe.");
        } catch (Exception e) {
            throw new RuntimeException("Erro ao deletar cadeira: " + e.getMessage(), e);
        }

    }

    private Cadeira selecionarCadeiraPorPesoEAltura(List<Cadeira> cadeiras, float peso, float altura) {

        // Regra especial para cadeira "Obeso BIG ONE"
        if (peso >= 121 && peso <= 150) {
            return cadeiras.stream()
                    .filter(c -> c.getNome().equals("Obeso BIG ONE"))
                    .findFirst()
                    .orElse(null); // Retorna null se não encontrar
        }

        // Regra para peso até 120 kg e altura entre 1,40 e 1,90
        return cadeiras.stream()
                .filter(c -> peso <= 120 && altura >= 1.40 && altura <= 1.90)
                .findFirst()
                .orElse(null); // Retorna null se não encontrar
    }

}
