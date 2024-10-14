package com.flex360.api_flex360.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flex360.api_flex360.dto.carrinho.ModificaCarrinhoDTO;
import com.flex360.api_flex360.dto.carrinho.ProdutosDTO;
import com.flex360.api_flex360.dto.carrinho.ResponseCarrinhoDTO;
import com.flex360.api_flex360.models.Carrinho;
import com.flex360.api_flex360.models.Usuario;
import com.flex360.api_flex360.services.CarrinhoService;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/carrinho")
public class CarrinhoController {

    @Autowired
    private CarrinhoService carrinhoService;

    @GetMapping("/buscar")
    public ResponseEntity<ResponseCarrinhoDTO> buscarCarrinho(@AuthenticationPrincipal Usuario usuario) {

        Carrinho carrinho = carrinhoService.buscarCarrinhoPorId(usuario.getCarrinho().getId());
        ProdutosDTO produtos = carrinhoService.buscarProdutosDoCarrinho(carrinho.getId());

        return ResponseEntity.ok(new ResponseCarrinhoDTO(carrinho.getId(), produtos));
    }

    @PostMapping("/adiciona")
    public ResponseEntity<ResponseCarrinhoDTO> adicionaProduto(@AuthenticationPrincipal Usuario usuario,
    @RequestBody ModificaCarrinhoDTO modificaCarrinhoDTO) {

        if (modificaCarrinhoDTO.quantidade() < 1) {
            throw new IllegalArgumentException("A quantidade deve ser maior que 0.");
        }

        Carrinho carrinho = carrinhoService.buscarCarrinhoPorId(usuario.getCarrinho().getId());

        ProdutosDTO produtos = carrinhoService.editarQuantidadeProduto(carrinho.getId(), modificaCarrinhoDTO, false);
        return ResponseEntity.ok(new ResponseCarrinhoDTO(carrinho.getId(), produtos));
    }

    @PutMapping("/remove")
    public ResponseEntity<ResponseCarrinhoDTO> removeProduto(@AuthenticationPrincipal Usuario usuario,
    @RequestBody ModificaCarrinhoDTO modificaCarrinhoDTO) {

        if (modificaCarrinhoDTO.quantidade() < 1) {
            throw new IllegalArgumentException("A quantidade deve ser maior que 0.");
        }

        Carrinho carrinho = carrinhoService.buscarCarrinhoPorId(usuario.getCarrinho().getId());

        ProdutosDTO produtos = carrinhoService.editarQuantidadeProduto(carrinho.getId(), modificaCarrinhoDTO, true);
        return ResponseEntity.ok(new ResponseCarrinhoDTO(carrinho.getId(), produtos));
    }

}