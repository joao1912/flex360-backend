package com.flex360.api_flex360.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flex360.api_flex360.dto.auth.LoginResponseDTO;
import com.flex360.api_flex360.dto.cor.CorDTO;
import com.flex360.api_flex360.models.Cor;
import com.flex360.api_flex360.services.CorService;

import jakarta.el.ELException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/cor")
public class CorController {

    @Autowired
    private CorService corService;
    
    @GetMapping("/buscarTodas")
    public ResponseEntity<List<CorDTO>> buscarTodasCores() {

        List<Cor> cores=corService.buscarTodasCores();

        throw new ELException();

        
    }

    @GetMapping("/buscarPorId/{id}")
    public ResponseEntity<CorDTO> buscarPorId(@RequestParam String id) {

        UUID uuid = UUID.fromString(id);
       
        Cor cor= corService.buscarCorPorId(uuid);

        return ResponseEntity.ok(
            new CorDTO(cor.getId(),cor.getName(), cor.getCodigo())
        );

    }
    
    
    
}
