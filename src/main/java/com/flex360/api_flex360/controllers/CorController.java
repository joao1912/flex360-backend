package com.flex360.api_flex360.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flex360.api_flex360.dto.cor.CorDTO;
import com.flex360.api_flex360.models.Cor;
import com.flex360.api_flex360.services.ConverteParaDtoService;
import com.flex360.api_flex360.services.CorService;

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
public class CorController {

    @Autowired
    private CorService corService;

    @Autowired
    private ConverteParaDtoService converteParaDtoService;
    
    @GetMapping("/buscarTodas")
    public ResponseEntity<List<CorDTO>> buscarTodasCores() {

        List<Cor> cores=corService.buscarTodasCores();

        List<CorDTO> coresDTO = converteParaDtoService.converterParaDTO(cores, cor -> new CorDTO(cor.getId(), cor.getName(), cor.getCodigo()));

        return ResponseEntity.status(200).body(coresDTO);

        
    }

    @GetMapping("/buscarPorId/{id}")
    public ResponseEntity<CorDTO> buscarPorId(@PathVariable String id) {

        UUID uuid = UUID.fromString(id);
       
        Cor cor= corService.buscarCorPorId(uuid);

        return ResponseEntity.ok(
            new CorDTO(cor.getId(),cor.getName(), cor.getCodigo())
        );

    }

    @PostMapping("/criar")
    public ResponseEntity<CorDTO> criarCor(@RequestBody CorDTO corDTO) {
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

    @PutMapping("/editar/{id}")
    public ResponseEntity<CorDTO> editarCor(@PathVariable UUID id, @RequestBody CorDTO corDTO) {
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
   
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<String> deletarCor(@PathVariable UUID id) {
            // Chamar o serviço para deletar a cor
            corService.deletarCor(id);



             return ResponseEntity.ok("Cor deletada com sucesso.");
        }
    

    
}
