package com.flex360.api_flex360.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flex360.api_flex360.dto.cor.CorDTO;
import com.flex360.api_flex360.dto.cor.RequestCorDTO;
import com.flex360.api_flex360.models.Cor;
import com.flex360.api_flex360.services.ConverteParaDtoService;
import com.flex360.api_flex360.services.CorService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;
import java.util.UUID;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/cor")
@Tag(name = "Cor")
public class CorController {

    @Autowired
    private CorService corService;

    @Autowired
    private ConverteParaDtoService converteParaDtoService;

    @Operation(description = "Vai buscar todas as cores cadastrados.", responses = {
            @ApiResponse(responseCode = "200"),

            @ApiResponse(responseCode = "403", content = @Content()),

            @ApiResponse(responseCode = "404", content = @Content())
    }

    )
    @GetMapping("/buscarTodas")
    public ResponseEntity<List<CorDTO>> buscarTodasCores() {

        List<Cor> cores = corService.buscarTodasCores();

        List<CorDTO> coresDTO = converteParaDtoService.converterParaDTO(cores,
                cor -> new CorDTO(cor.getId(), cor.getName(), cor.getCodigo()));

        return ResponseEntity.status(200).body(coresDTO);

    }

    @Operation(description = "Vai buscar uma cor por id.", responses = {
            @ApiResponse(responseCode = "200"),

            @ApiResponse(responseCode = "403", content = @Content()),

            @ApiResponse(responseCode = "404", content = @Content())
    }

    )
    @GetMapping("/buscarPorId/{id}")
    public ResponseEntity<CorDTO> buscarPorId(@PathVariable String id) {

        UUID uuid = UUID.fromString(id);

        Cor cor = corService.buscarCorPorId(uuid);

        return ResponseEntity.ok(
                new CorDTO(cor.getId(), cor.getName(), cor.getCodigo()));

    }

    @Operation(description = "Vai cadastrar uma cor.", responses = {
            @ApiResponse(responseCode = "201"),

            @ApiResponse(responseCode = "403", content = @Content())
    }

    )
    @PostMapping("/criar")
    public ResponseEntity<CorDTO> criarCor(@RequestBody RequestCorDTO corDTO) {
        // Converter o CorDTO para a entidade Cor
        Cor novaCor = new Cor();
        novaCor.setName(corDTO.nome());
        novaCor.setCodigo(corDTO.codigo());

        // Chamar o método do serviço para criar a cor
        Cor corCriada = corService.criarCor(novaCor);

        // Converter a entidade Cor criada para CorDTO para retornar na resposta
        CorDTO novaCorDTO = new CorDTO(corCriada.getId(), corCriada.getName(), corCriada.getCodigo());

        // Retornar o DTO da nova cor criada com status 201 (Created)
        return ResponseEntity.status(201).body(novaCorDTO);
    }

    @Operation(description = "Vai editar os dados de uma cor.", responses = {
            @ApiResponse(responseCode = "200"),

            @ApiResponse(responseCode = "403", content = @Content()),

    }

    )
    @PutMapping("/editar/{id}")
    public ResponseEntity<CorDTO> editarCor(@PathVariable UUID id, @RequestBody RequestCorDTO corDTO) {
        // Converter o CorDTO para a entidade Cor
        Cor corAtualizada = new Cor();
        corAtualizada.setName(corDTO.nome());
        corAtualizada.setCodigo(corDTO.codigo());

        // Chamar o serviço para editar a cor existente
        Cor corEditada = corService.editarCor(id, corAtualizada);

        // Converter a entidade Cor editada para CorDTO para retornar na resposta
        CorDTO corEditadaDTO = new CorDTO(corEditada.getId(), corEditada.getName(), corEditada.getCodigo());

        // Retornar o DTO da cor editada
        return ResponseEntity.ok(corEditadaDTO);
    }

    @Operation(description = "Vai deletar uma cor.", responses = {
            @ApiResponse(responseCode = "200", description = "Cor deletada com sucesso.", content = @Content()),

            @ApiResponse(responseCode = "403", content = @Content()),

            @ApiResponse(responseCode = "404", content = @Content(), description = "Cor não encontrada com o id: ")

    }

    )
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<String> deletarCor(@PathVariable UUID id) {
      
        corService.deletarCor(id);

        return ResponseEntity.ok("Cor deletada com sucesso.");
    }

}
