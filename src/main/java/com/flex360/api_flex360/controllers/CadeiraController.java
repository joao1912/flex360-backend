package com.flex360.api_flex360.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.flex360.api_flex360.dto.cadeira.CadeiraDTO;
import com.flex360.api_flex360.dto.cadeira.RequestCadeiraDTO;
import com.flex360.api_flex360.dto.cadeira.SugestaoErgonomicaDTO;
import com.flex360.api_flex360.models.Cadeira;
import com.flex360.api_flex360.services.CadeiraService;
import com.flex360.api_flex360.services.ConverteParaDtoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/cadeira")
@Tag(name = "Cadeira")
public class CadeiraController {

    @Autowired
    private CadeiraService cadeiraService;

    @Autowired
    private ConverteParaDtoService converteParaDtoService;

    @Operation(description = "Vai buscar todas as cadeiras cadastradas.", responses = {
            @ApiResponse(responseCode = "200"),

            @ApiResponse(responseCode = "404", content = @Content())
    }

    )
    @GetMapping("/buscarTodas")
    public ResponseEntity<List<CadeiraDTO>> buscarTodasCadeiras() {

        List<Cadeira> cadeiras = cadeiraService.buscarTodasCadeiras();

        List<CadeiraDTO> cadeiraDTOs = converteParaDtoService.converterParaDTO(cadeiras,
                cadeira -> new CadeiraDTO(cadeira.getId(),
                        cadeira.getNome(),
                        cadeira.getDescricao(),
                        cadeira.getInformacoes(),
                        cadeira.getTemp_garantia(),
                        cadeira.getPreco(),
                        cadeira.getDimensoes(),
                        cadeira.getFoto_dimensoes(),
                        cadeira.getFoto_banner(),
                        cadeira.getDesc_encosto(),
                        cadeira.getDesc_apoio(),
                        cadeira.getDesc_rodinha(),
                        cadeira.getDesc_ajuste_altura(),
                        cadeira.getDesc_revestimento(),
                        cadeira.getCategorias(),
                        cadeira.getCores()
                ));

        return ResponseEntity.ok(cadeiraDTOs);

    }

    @Operation(description = "Vai buscar uma cadeira por id.", responses = {
            @ApiResponse(responseCode = "200"),

            @ApiResponse(responseCode = "404", content = @Content())
    }

    )
    @GetMapping("/buscarPorId/{id}")
    public ResponseEntity<CadeiraDTO> buscarPorId(@PathVariable UUID id) {

        Cadeira cadeira = cadeiraService.buscarCadeiraPorId(id);

        return ResponseEntity.ok(
                new CadeiraDTO(
                        cadeira.getId(),
                        cadeira.getNome(),
                        cadeira.getDescricao(),
                        cadeira.getInformacoes(),
                        cadeira.getTemp_garantia(),
                        cadeira.getPreco(),
                        cadeira.getDimensoes(),
                        cadeira.getFoto_dimensoes(),
                        cadeira.getFoto_banner(),
                        cadeira.getDesc_encosto(),
                        cadeira.getDesc_apoio(),
                        cadeira.getDesc_rodinha(),
                        cadeira.getDesc_ajuste_altura(),
                        cadeira.getDesc_revestimento(),
                        cadeira.getCategorias(),
                        cadeira.getCores()));
    }

    @Operation(description = "Vai cadastrar uma cadeira.", responses = {
            @ApiResponse(responseCode = "201"),

            @ApiResponse(responseCode = "403", content = @Content()),

    }

    )
    @PostMapping("/criar")
    public ResponseEntity<CadeiraDTO> criarCadeira(@RequestBody RequestCadeiraDTO cadeiraDTO) {

        Cadeira cadeiraCriada = cadeiraService.criarCadeira(cadeiraDTO);

        CadeiraDTO novaCadeiraDTO = new CadeiraDTO(
                cadeiraCriada.getId(),
                cadeiraCriada.getNome(),
                cadeiraCriada.getDescricao(),
                cadeiraCriada.getInformacoes(),
                cadeiraCriada.getTemp_garantia(),
                cadeiraCriada.getPreco(),
                cadeiraCriada.getDimensoes(),
                cadeiraCriada.getFoto_dimensoes(),
                cadeiraCriada.getFoto_banner(),
                cadeiraCriada.getDesc_encosto(),
                cadeiraCriada.getDesc_apoio(),
                cadeiraCriada.getDesc_rodinha(),
                cadeiraCriada.getDesc_ajuste_altura(),
                cadeiraCriada.getDesc_revestimento(),
                cadeiraCriada.getCategorias(),
                cadeiraCriada.getCores());

        return ResponseEntity.status(201).body(novaCadeiraDTO);
    }

    @Operation(description = "Vai editar os dados de uma cadeira por id.", responses = {
            @ApiResponse(responseCode = "200"),

            @ApiResponse(responseCode = "403", content = @Content()),

    }

    )
    @PutMapping("/editar/{id}")
    public ResponseEntity<CadeiraDTO> editarCadeira(@PathVariable UUID id, @RequestBody RequestCadeiraDTO cadeiraDTO) {

        Cadeira cadeiraEditada = cadeiraService.editarCadeira(id, cadeiraDTO);

        CadeiraDTO cadeiraEditadaDTO = new CadeiraDTO(
                cadeiraEditada.getId(),
                cadeiraEditada.getNome(),
                cadeiraEditada.getDescricao(),
                cadeiraEditada.getInformacoes(),
                cadeiraEditada.getTemp_garantia(),
                cadeiraEditada.getPreco(),
                cadeiraEditada.getDimensoes(),
                cadeiraEditada.getFoto_dimensoes(),
                cadeiraEditada.getFoto_banner(),
                cadeiraEditada.getDesc_encosto(),
                cadeiraEditada.getDesc_apoio(),
                cadeiraEditada.getDesc_rodinha(),
                cadeiraEditada.getDesc_ajuste_altura(),
                cadeiraEditada.getDesc_revestimento(),
                cadeiraEditada.getCategorias(),
                cadeiraEditada.getCores());

        return ResponseEntity.ok(cadeiraEditadaDTO);
    }

    @Operation(description = "Vai deletar uma cadeira por id.", responses = {
            @ApiResponse(responseCode = "200", description = "Cadeira deletada com sucesso.", content = @Content()),

            @ApiResponse(responseCode = "403", content = @Content()),

    }

    )
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<String> deletarCadeira(@PathVariable UUID id) {

        cadeiraService.deletarCadeira(id);

        return ResponseEntity.ok("Cadeira deletada com sucesso.");
    }

    @Operation(description = "Vai retornar uma cadeira para o usuário filtrando por: peso, altura e categoria escolhida pelo mesmo.", responses = {
            @ApiResponse(responseCode = "200"),

            @ApiResponse(responseCode = "403", content = @Content()),

    }

    )
    @PostMapping("/sugestaoErgonomica")
    public ResponseEntity<CadeiraDTO> buscarSugestaoErgonomica(@RequestBody SugestaoErgonomicaDTO dados) {

        Cadeira cadeiraEncontrada = cadeiraService.sugestaoErgonomica(dados);

        return ResponseEntity.ok(new CadeiraDTO(
                cadeiraEncontrada.getId(),
                cadeiraEncontrada.getNome(),
                cadeiraEncontrada.getDescricao(),
                cadeiraEncontrada.getInformacoes(),
                cadeiraEncontrada.getTemp_garantia(),
                cadeiraEncontrada.getPreco(),
                cadeiraEncontrada.getDimensoes(),
                cadeiraEncontrada.getFoto_dimensoes(),
                cadeiraEncontrada.getFoto_banner(),
                cadeiraEncontrada.getDesc_encosto(),
                cadeiraEncontrada.getDesc_apoio(),
                cadeiraEncontrada.getDesc_rodinha(),
                cadeiraEncontrada.getDesc_ajuste_altura(),
                cadeiraEncontrada.getDesc_revestimento(),
                cadeiraEncontrada.getCategorias(),
                cadeiraEncontrada.getCores()

        ));

    }

}