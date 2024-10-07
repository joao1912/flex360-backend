package com.flex360.api_flex360.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.flex360.api_flex360.dto.carrinho.ProdutosDTO;
import com.flex360.api_flex360.dto.carrinho.ResponseCarrinhoDTO;
import com.flex360.api_flex360.models.Carrinho;
import com.flex360.api_flex360.services.CarrinhoService;


@RestController
@RequestMapping("/carrinho")
public class CarrinhoController {
    
    @Autowired
    private CarrinhoService carrinhoService;

    @GetMapping("/buscar")
    public ResponseEntity<ResponseCarrinhoDTO> buscarCarrinho() {
        UUID uuid = UUID.fromString("id-fake-por-enquanto");
        Carrinho carrinho = carrinhoService.buscarCarrinhoPorId(uuid);
        ProdutosDTO produtosDTO = new ProdutosDTO(carrinho.getProdutoCarrinho().)
        return ResponseEntity.ok(new ResponseCarrinhoDTO(carrinho.getId(), ))
    }
}