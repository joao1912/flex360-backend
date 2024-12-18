package com.flex360.api_flex360.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/carrinho")
@Tag(name = "Carrinho")
public class CarrinhoController {

    @Autowired
    private CarrinhoService carrinhoService;

    @Operation(description = "Vai buscar o carrinho do usuário cadastrado.", responses = {
            @ApiResponse(responseCode = "200"),

            @ApiResponse(responseCode = "403", content = @Content()),

            @ApiResponse(responseCode = "404", content = @Content())
    }

    )
    @GetMapping("/buscar")
    public ResponseEntity<ResponseCarrinhoDTO> buscarCarrinho(@AuthenticationPrincipal Usuario usuario) {

        Carrinho carrinho = carrinhoService.buscarCarrinhoPorId(usuario.getCarrinho().getId());
        ProdutosDTO produtos = carrinhoService.buscarProdutosDoCarrinho(carrinho.getId());

        return ResponseEntity.ok(new ResponseCarrinhoDTO(carrinho.getId(), produtos));
    }

    @Operation(description = "Vai aumentar a quantidade de um produto, se ele não estiver no carrinho, será adicionado.", responses = {
            @ApiResponse(responseCode = "200"),

            @ApiResponse(responseCode = "403", content = @Content())
    }

    )
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

    @Operation(description = "Vai diminuir a quantidade de um produto, se for maior que a quantidade presente no carrinho, o produto vai ser removido.", responses = {
            @ApiResponse(responseCode = "200"),

            @ApiResponse(responseCode = "403", content = @Content())
    }

    )
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

    @Operation(description = "Vai remover um produto do carrinho.", responses = {
            @ApiResponse(responseCode = "200"),

            @ApiResponse(responseCode = "403", content = @Content()),

            @ApiResponse(responseCode = "404", content = @Content())
    }

    )
    @DeleteMapping("/deleta/produto/{id}")
    public ResponseEntity<?> deletaProduto(@PathVariable UUID id) {

        carrinhoService.deletaProduto(id);

        return ResponseEntity.ok().build();

    }


    @Operation(description = "Limpa o carrinho do usuário.", responses = {
        @ApiResponse(responseCode = "200"),

        @ApiResponse(responseCode = "403", content = @Content()),

        @ApiResponse(responseCode = "404", content = @Content())
}

)
@DeleteMapping("/limparCarrinho")
public ResponseEntity<?> limparCarrinho() {

    carrinhoService.limparCarrinho();

    return ResponseEntity.ok().build();

}
}