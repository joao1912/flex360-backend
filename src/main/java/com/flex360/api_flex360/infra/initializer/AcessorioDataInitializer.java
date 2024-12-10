package com.flex360.api_flex360.infra.initializer;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
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

    private static final String LOCK_KEY = "inicializacaoAcessorios";
    private static final long LOCK_DURATION = 15000;

    @Autowired
    private AcessorioService acessorioService;

    @Autowired
    private AcessorioRepository acessorioRepository;

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired(required = false)
    private RedissonClient redissonClient;

    @Override
    public void run(String... args) throws Exception {
        if (redissonClient != null) {
            RLock lock = redissonClient.getLock(LOCK_KEY);
            boolean locked = false;
            try {
                locked = lock.tryLock(0, LOCK_DURATION, TimeUnit.MILLISECONDS);
                if (locked) {
                    System.out.println("Bloqueio obtido para Acessorios. Iniciando processamento...");
                    processarAcessorios();
                    System.out.println("Processamento de Acessorios concluído. Bloqueio permanecerá ativo por " + LOCK_DURATION + "ms.");
                } else {
                    System.out.println("Não foi possível obter o bloqueio para Acessorios. Outra instância pode estar processando ou ter processado recentemente.");
                }
            } catch (Exception e) {
                System.err.println("Erro ao processar acessorios: " + e.getMessage());
            } finally {
                if (locked) {
                    System.out.println("Bloqueio para Acessorios será liberado automaticamente após o tempo de expiração.");
                }
            }
        } else {
            System.out.println("Redis não está configurado. Executando processamento de Acessorios sem bloqueio...");
            processarAcessorios();
        }
    }

    private void processarAcessorios() {
        if (acessorioRepository.findAll().isEmpty()) {
            String json = "";

            try {
                Resource resource = resourceLoader.getResource("classpath:AcessoriosData.json");
                InputStream inputStream = resource.getInputStream();
                json = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
            } catch (IOException e) {
                System.err.println("Erro ao ler o arquivo AcessoriosData.json: " + e.getMessage());
                return;
            }

            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            try {
                List<Map<String, Object>> acessorios = mapper.readValue(json,
                        new TypeReference<List<Map<String, Object>>>() {});

                for (Map<String, Object> acessorio : acessorios) {
                    Acessorio novoAcessorio = new Acessorio();
                    novoAcessorio.setNome((String) acessorio.get("nome"));
                    novoAcessorio.setPreco(((Number) acessorio.get("preco")).floatValue());
                    novoAcessorio.setFoto((String) acessorio.get("foto"));

                    acessorioService.criarAcessorio(novoAcessorio);
                }
                System.out.println("Acessorios processados e salvos no banco de dados.");
            } catch (IOException e) {
                System.err.println("Erro ao processar o JSON de Acessorios: " + e.getMessage());
            }
        } else {
            System.out.println("Acessorios já existem. Nenhum processamento necessário.");
        }
    }
}