package com.flex360.api_flex360.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flex360.api_flex360.dto.acessorio.AcessorioDTO;
import com.flex360.api_flex360.dto.acessorio.RequestAcessorioDTO;
import com.flex360.api_flex360.models.Acessorio;
import com.flex360.api_flex360.services.AcessorioService;
import com.flex360.api_flex360.services.ConverteParaDtoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/acessorio")
@Tag(name = "Acessorio")
public class AcessorioController {

    @Autowired
    private AcessorioService acessorioService;

    @Autowired
    private ConverteParaDtoService converteParaDtoService;

    @Operation(description = "Vai buscar todas os acessorios cadastrados.", responses = {
            @ApiResponse(responseCode = "200"),

            @ApiResponse(responseCode = "404", content = @Content())
    }

    )
    @GetMapping("/buscarTodos")
    public ResponseEntity<List<AcessorioDTO>> buscarTodosAcessorios() {
        List<Acessorio> acessorios = acessorioService.buscarTodosAcessorios();

        // Converte a lista de Acessorio para AcessorioDTO usando ConverteParaDtoService
        List<AcessorioDTO> acessorioDTOs = converteParaDtoService.converterParaDTO(acessorios,
                acessorio -> new AcessorioDTO(acessorio.getId(), acessorio.getNome(), acessorio.getPreco(),
                        acessorio.getFoto()));

        return ResponseEntity.ok(acessorioDTOs);
    }

    @Operation(description = "Vai buscar um acessorio por id.", responses = {
            @ApiResponse(responseCode = "200"),

            @ApiResponse(responseCode = "404", content = @Content())
    }

    )
    @GetMapping("/buscarPorId/{id}")
    public ResponseEntity<AcessorioDTO> buscarPorId(@PathVariable String id) {

        UUID uuid = UUID.fromString(id);

        Acessorio acessorio = acessorioService.buscarAcessorioPorId(uuid);

        return ResponseEntity.ok(
                new AcessorioDTO(acessorio.getId(), acessorio.getNome(), acessorio.getPreco(), acessorio.getFoto()));
    }

    @Operation(description = "Vai cadastrar um acessorio.", responses = {
            @ApiResponse(responseCode = "201"),

            @ApiResponse(responseCode = "403", content = @Content()),

    }

    )
    @PostMapping("/criar")
    public ResponseEntity<AcessorioDTO> criarAcessorio(@RequestBody RequestAcessorioDTO acessorioDTO) {

        Acessorio novoAcessorio = new Acessorio();
        novoAcessorio.setNome(acessorioDTO.nome());
        novoAcessorio.setPreco(acessorioDTO.preco());
        novoAcessorio.setFoto(acessorioDTO.foto());

        Acessorio acessorioCriado = acessorioService.criarAcessorio(novoAcessorio);

        AcessorioDTO novoAcessorioDTO = new AcessorioDTO(acessorioCriado.getId(), acessorioCriado.getNome(),
                acessorioCriado.getPreco(), acessorioCriado.getFoto());

        return ResponseEntity.status(201).body(novoAcessorioDTO);
    }

    @Operation(description = "Vai editar os dados de um acessorio por id.", responses = {
            @ApiResponse(responseCode = "200"),

            @ApiResponse(responseCode = "403", content = @Content()),

    }

    )
    @PutMapping("/editar/{id}")
    public ResponseEntity<AcessorioDTO> editarAcessorio(@PathVariable UUID id, @RequestBody RequestAcessorioDTO acessorioDTO) {

        Acessorio acessorioAtualizado = new Acessorio();
        acessorioAtualizado.setNome(acessorioDTO.nome());
        acessorioAtualizado.setPreco(acessorioDTO.preco());
        acessorioAtualizado.setFoto(acessorioDTO.foto());

        Acessorio acessorioEditado = acessorioService.editarAcessorio(id, acessorioAtualizado);

        AcessorioDTO acessorioEditadoDTO = new AcessorioDTO(acessorioEditado.getId(), acessorioEditado.getNome(),
                acessorioEditado.getPreco(), acessorioEditado.getFoto());

        return ResponseEntity.ok(acessorioEditadoDTO);
    }

    @Operation(description = "Vai deletar um acessorio por id.", responses = {
            @ApiResponse(responseCode = "200", description = "Acessório deletado com sucesso.", content = @Content()),

            @ApiResponse(responseCode = "403", content = @Content()),

    }

    )
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<String> deletarAcessorio(@PathVariable UUID id) {

        acessorioService.deletarAcessorio(id);

        return ResponseEntity.ok("Acessório deletado com sucesso.");
    }

}
