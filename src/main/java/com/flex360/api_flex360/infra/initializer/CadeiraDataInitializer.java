package com.flex360.api_flex360.infra.initializer;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flex360.api_flex360.dto.cadeira.RequestCadeiraDTO;
import com.flex360.api_flex360.models.Categoria;
import com.flex360.api_flex360.models.Cor;
import com.flex360.api_flex360.repository.CadeiraRepository;
import com.flex360.api_flex360.services.CadeiraService;

@Component
public class CadeiraDataInitializer implements CommandLineRunner {

    @Autowired
    CadeiraService cadeiraService;

    @Autowired
    CadeiraRepository cadeiraRepository;

    @Autowired
    private ResourceLoader resourceLoader;

    @SuppressWarnings("unchecked")
@Override
    public void run(String... args) throws Exception {

        if (cadeiraRepository.findAll().isEmpty()) {

            String json = "";

            try {
                Resource resource = resourceLoader.getResource("classpath:CadeirasData.json");
                InputStream inputStream = resource.getInputStream();
                json = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
            } catch (IOException e) {
                e.printStackTrace();
            }

            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            try {
                // Deserializando o JSON para uma lista de mapas
                List<Map<String, Object>> cadeiras = mapper.readValue(json, new TypeReference<List<Map<String, Object>>>() {});

                // Iterando sobre as cadeiras e criando DTOs diretamente
                for (Map<String, Object> cadeira : cadeiras) {
                    // Criando a lista de categorias
                    List<Map<String, Object>> categoriasMap = (List<Map<String, Object>>) cadeira.get("categorias");
                    List<Categoria> categorias = new ArrayList<>();
                    for (Map<String, Object> categoria : categoriasMap) {
                
                        Categoria novaCategoria = new Categoria();
                        novaCategoria.setName((String) categoria.get("name"));

                        categorias.add(novaCategoria);
                    }
                    
                    // Criando a lista de cores disponíveis
                    List<Map<String, Object>> coresDisponiveisMap = (List<Map<String, Object>>) cadeira.get("cores_disponiveis");
                    List<Cor> coresDisponiveis = new ArrayList<>();
                    for (Map<String, Object> cor : coresDisponiveisMap) {
                        
                        Cor corNova = new Cor();
                        corNova.setName((String) cor.get("name"));
                        corNova.setCodigo((String) cor.get("codigo"));
                        corNova.setFoto_cadeira((String) cor.get("foto_cadeira"));

                        coresDisponiveis.add(corNova);
                    }

                    // Criando o DTO diretamente com os valores
                    RequestCadeiraDTO dto = new RequestCadeiraDTO(
                        (String) cadeira.get("nome"),
                        (String) cadeira.get("descricao"),
                        (String) cadeira.get("informacoes"),
                        (Integer) cadeira.get("temp_garantia"),
                        ((Number) cadeira.get("preco")).floatValue(),
                        (String) cadeira.get("dimensoes"),
                        (String) cadeira.get("foto_dimensoes"),
                        (String) cadeira.get("foto_banner"),
                        (String) cadeira.get("desc_encosto"),
                        (String) cadeira.get("desc_apoio"),
                        (String) cadeira.get("desc_rodinha"),
                        (String) cadeira.get("desc_ajuste_altura"),
                        (String) cadeira.get("desc_revestimento"),
                        categorias,
                        coresDisponiveis
                    );

                    cadeiraService.criarCadeira(dto);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
