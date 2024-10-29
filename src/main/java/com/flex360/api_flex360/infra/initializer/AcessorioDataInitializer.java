package com.flex360.api_flex360.infra.initializer;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
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
import com.flex360.api_flex360.models.Acessorio;
import com.flex360.api_flex360.repository.AcessorioRepository;
import com.flex360.api_flex360.services.AcessorioService;

@Component
public class AcessorioDataInitializer implements CommandLineRunner {

    @Autowired
    AcessorioService acessorioService;

    @Autowired
    AcessorioRepository acessorioRepository;

    @Autowired
    private ResourceLoader resourceLoader;

    @SuppressWarnings("unchecked")
@Override
    public void run(String... args) throws Exception {

        if (acessorioRepository.findAll().isEmpty()) {

            String json = "";

            try {
                Resource resource = resourceLoader.getResource("classpath:AcessoriosData.json");
                InputStream inputStream = resource.getInputStream();
                json = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
            } catch (IOException e) {
                e.printStackTrace();
            }

            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            try {
                // Deserializando o JSON para uma lista de mapas
                List<Map<String, Object>> acessorios = mapper.readValue(json, new TypeReference<List<Map<String, Object>>>() {});

                for (Map<String, Object> acessorio : acessorios) {

                    // Criando o acessório diretamente com os valores
                    Acessorio novoAcessorio = new Acessorio();
                        novoAcessorio.setNome((String) acessorio.get("nome"));
                        novoAcessorio.setPreco(((Number) acessorio.get("preco")).floatValue());
                        novoAcessorio.setFoto((String) acessorio.get("foto"));

                    acessorioService.criarAcessorio(novoAcessorio);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
