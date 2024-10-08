package com.flex360.api_flex360.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flex360.api_flex360.dto.carrinho.AcessorioDTO;
import com.flex360.api_flex360.dto.carrinho.CadeiraDTO;
import com.flex360.api_flex360.dto.carrinho.ProdutosDTO;
import com.flex360.api_flex360.dto.carrinho.ResponseCarrinhoDTO;
import com.flex360.api_flex360.models.Acessorio;
import com.flex360.api_flex360.models.Cadeira;
import com.flex360.api_flex360.models.Carrinho;
import com.flex360.api_flex360.models.Produto;
import com.flex360.api_flex360.models.ProdutoCarrinho;
import com.flex360.api_flex360.models.Usuario;
import com.flex360.api_flex360.services.CarrinhoService;
import com.flex360.api_flex360.services.ConverteParaDtoService;


@RestController
@RequestMapping("/carrinho")
public class CarrinhoController {
    
    @Autowired
    private CarrinhoService carrinhoService;

    @Autowired
    private ConverteParaDtoService converteParaDTOService;

    @GetMapping("/buscar")
    public ResponseEntity<ResponseCarrinhoDTO> buscarCarrinho(@AuthenticationPrincipal Usuario usuario) {
        Carrinho carrinho = usuario.getCarrinho();
        
        List<ProdutoCarrinho> produtosCarrinho = carrinhoService.buscarProdutosDoCarrinho(carrinho.getId());

        List<AcessorioDTO> acessoriosDTOs;
        List<CadeiraDTO> cadeirasDTOs;

        for(ProdutoCarrinho produto: produtosCarrinho) {
            if(produto.getAcessorio() != null) {
                Acessorio acessorio = produto.getAcessorio();
                AcessorioDTO acessorioDTO = new AcessorioDTO(acessorio.getId(), acessorio.getNome(), acessorio.getPreco(), acessorio.getFoto());
            }
            }
        return ResponseEntity.ok(new ResponseCarrinhoDTO(carrinho.getId(), produtos));
    }
}