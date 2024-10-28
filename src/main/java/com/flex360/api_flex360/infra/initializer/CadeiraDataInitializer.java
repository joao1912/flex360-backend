package com.flex360.api_flex360.infra.initializer;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

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
import com.flex360.api_flex360.models.Cadeira;
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
                                List<Cadeira> cadeiras = mapper.readValue(json, new TypeReference<List<Cadeira>>() {
                                });

                                for (Cadeira c : cadeiras) {
                                        RequestCadeiraDTO dto = new RequestCadeiraDTO(
                                                        c.getNome(),
                                                        c.getDescricao(),
                                                        c.getInformacoes(),
                                                        c.getTemp_garantia(),
                                                        c.getPreco(),
                                                        c.getDimensoes(),
                                                        c.getFoto_dimensoes(),
                                                        c.getFoto_banner(),
                                                        c.getDesc_encosto(),
                                                        c.getDesc_apoio(),
                                                        c.getDesc_rodinha(),
                                                        c.getDesc_ajuste_altura(),
                                                        c.getDesc_revestimento(),
                                                        c.getCategorias(),
                                                        c.getCores());

                                        cadeiraService.criarCadeira(dto);
                                }
                        } catch (IOException e) {
                                e.printStackTrace();
                        }

                }

        }
}