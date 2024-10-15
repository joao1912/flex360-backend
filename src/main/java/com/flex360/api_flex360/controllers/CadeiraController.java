package com.flex360.api_flex360.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.flex360.api_flex360.dto.cadeira.CadeiraDTO;
import com.flex360.api_flex360.models.Cadeira;
import com.flex360.api_flex360.services.CadeiraService;
import com.flex360.api_flex360.services.ConverteParaDtoService;


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
@RequestMapping("/Cadeira")
public class CadeiraController {

    @Autowired
    private CadeiraService cadeiraService;

    @Autowired
    private ConverteParaDtoService converteParaDtoService;

    @GetMapping("/buscarTodas")
    public ResponseEntity<List<CadeiraDTO>> buscarTodasCadeiras() {

        List<Cadeira> cadeiras=cadeiraService.buscarTodasCadeiras();


        List<CadeiraDTO> cadeiraDTOs=converteParaDtoService.converterParaDTO
        (cadeiras,
                cadeira-> new CadeiraDTO
                (cadeira.getId(),
                    cadeira.getNome(),
                    cadeira.getDescricao(),
                    cadeira.getInformacoes(),
                    cadeira.getTemp_garantia(),
                    cadeira.getPreco(),
                    cadeira.getDimensoes(),
                    cadeira.getFoto(),
                    cadeira.getFoto_dimensoes(),
                    cadeira.getDesc_encosto(),
                    cadeira.getDesc_apoio(),
                    cadeira.getDesc_rodinha(),
                    cadeira.getDesc_ajuste_altura(),
                    cadeira.getDesc_revestimento(),
                    cadeira.getCategorias()));

                    return ResponseEntity.ok(cadeiraDTOs);
        
    }

    @GetMapping("/buscarPorId/{id}")
    public ResponseEntity<CadeiraDTO> buscarPorId(@PathVariable UUID id){
      

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
            cadeira.getFoto(),
            cadeira.getFoto_dimensoes(),
            cadeira.getDesc_encosto(),
            cadeira.getDesc_apoio(),
            cadeira.getDesc_rodinha(),
            cadeira.getDesc_ajuste_altura(),
            cadeira.getDesc_revestimento(),
            cadeira.getCategorias()
            ));
    }

    @PostMapping("/criar")
    public ResponseEntity<CadeiraDTO> criarCadeira(@RequestBody CadeiraDTO cadeiraDTO){

        Cadeira novaCadeira = new Cadeira();
        novaCadeira.setNome(cadeiraDTO.nome());
        novaCadeira.setDescricao(cadeiraDTO.descricao());
        novaCadeira.setInformacoes(cadeiraDTO.informacoes());
        novaCadeira.setTemp_garantia(cadeiraDTO.temp_garantia());
        novaCadeira.setPreco(cadeiraDTO.preco());
        novaCadeira.setDimensoes(cadeiraDTO.dimencoes());
        novaCadeira.setFoto(cadeiraDTO.foto_cadeira());
        novaCadeira.setFoto_dimensoes(cadeiraDTO.foto_dimencoes());
        novaCadeira.setDesc_encosto(cadeiraDTO.desc_encosto());
        novaCadeira.setDesc_apoio(cadeiraDTO.desc_apoio());
        novaCadeira.setDesc_rodinha(cadeiraDTO.desc_rodinha());
        novaCadeira.setDesc_ajuste_altura(cadeiraDTO.desc_ajuste_altura());
        novaCadeira.setDesc_revestimento(cadeiraDTO.desc_revestimento());
        novaCadeira.setCategorias(cadeiraDTO.categorias());

        Cadeira cadeiraCriada= cadeiraService.criarCadeira(novaCadeira);

        CadeiraDTO novaCadeiraDTO=new CadeiraDTO(
                    cadeiraCriada.getId(),
                    cadeiraCriada.getNome(),
                    cadeiraCriada.getDescricao(),
                    cadeiraCriada.getInformacoes(),
                    cadeiraCriada.getTemp_garantia(),
                    cadeiraCriada.getPreco(),
                    cadeiraCriada.getDimensoes(),
                    cadeiraCriada.getFoto(),
                    cadeiraCriada.getFoto_dimensoes(),
                    cadeiraCriada.getDesc_encosto(),
                    cadeiraCriada.getDesc_apoio(),
                    cadeiraCriada.getDesc_rodinha(),
                    cadeiraCriada.getDesc_ajuste_altura(),
                    cadeiraCriada.getDesc_revestimento(),
                    cadeiraCriada.getCategorias()
                    );
                 
                    

        return ResponseEntity.status(201).body(novaCadeiraDTO);
    }
     @PutMapping("/editar/{id}")
    public ResponseEntity<CadeiraDTO> editarCadeira(@PathVariable UUID id, @RequestBody CadeiraDTO cadeiraDTO) {

        Cadeira cadeiraAtualizada = new Cadeira();
        cadeiraAtualizada.setNome(cadeiraDTO.nome());
        cadeiraAtualizada.setDescricao(cadeiraDTO.descricao());
        cadeiraAtualizada.setInformacoes(cadeiraDTO.informacoes());
        cadeiraAtualizada.setTemp_garantia(cadeiraDTO.temp_garantia());
        cadeiraAtualizada.setPreco(cadeiraDTO.preco());
        cadeiraAtualizada.setDimensoes(cadeiraDTO.dimencoes());
        cadeiraAtualizada.setFoto(cadeiraDTO.foto_cadeira());
        cadeiraAtualizada.setFoto_dimensoes(cadeiraDTO.foto_dimencoes());
        cadeiraAtualizada.setDesc_encosto(cadeiraDTO.desc_encosto());
        cadeiraAtualizada.setDesc_apoio(cadeiraDTO.desc_apoio());
        cadeiraAtualizada.setDesc_rodinha(cadeiraDTO.desc_rodinha());
        cadeiraAtualizada.setDesc_ajuste_altura(cadeiraDTO.desc_ajuste_altura());
        cadeiraAtualizada.setDesc_revestimento(cadeiraDTO.desc_revestimento());
        cadeiraAtualizada.setCategorias(cadeiraDTO.categorias());
        
        Cadeira cadeiraEditada = cadeiraService.editarCadeira(id, cadeiraAtualizada);

        CadeiraDTO cadeiraEditadaDTO = new CadeiraDTO(
                    cadeiraEditada.getId(),
                    cadeiraEditada.getNome(),
                    cadeiraEditada.getDescricao(),
                    cadeiraEditada.getInformacoes(),
                    cadeiraEditada.getTemp_garantia(),
                    cadeiraEditada.getPreco(),
                    cadeiraEditada.getDimensoes(),
                    cadeiraEditada.getFoto(),
                    cadeiraEditada.getFoto_dimensoes(),
                    cadeiraEditada.getDesc_encosto(),
                    cadeiraEditada.getDesc_apoio(),
                    cadeiraEditada.getDesc_rodinha(),
                    cadeiraEditada.getDesc_ajuste_altura(),
                    cadeiraEditada.getDesc_revestimento(),
                    cadeiraEditada.getCategorias()
                    );

        return ResponseEntity.ok(cadeiraEditadaDTO);
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<String> deletarCadeira(@PathVariable UUID id) {

        cadeiraService.deletarCadeira(id);

        return ResponseEntity.ok("Cadeira deletada com sucesso.");
    }

    
    
}