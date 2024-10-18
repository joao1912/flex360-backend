package com.flex360.api_flex360.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flex360.api_flex360.dto.usuario.ResponseUsuarioDTO;
import com.flex360.api_flex360.models.Usuario;
import com.flex360.api_flex360.services.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import com.flex360.api_flex360.services.ConverteParaDtoService;

@RestController
@RequestMapping("/usuario")
@Tag(name = "Usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ConverteParaDtoService converteParaDtoService;

    @Operation(
        description = "Vai buscar todos os usuários cadastrados."
    )
    @GetMapping("/buscarTodos")
    public ResponseEntity<List<ResponseUsuarioDTO>> buscarTodosUsuarios() {
        List<Usuario> usuarios = usuarioService.buscarTodosUsuarios();

        List<ResponseUsuarioDTO> UsuarioDTOs = converteParaDtoService.converterParaDTO(usuarios,
                usuario -> new ResponseUsuarioDTO( usuario.getId(), usuario.getNome(), usuario.getEmail()) );

        return ResponseEntity.ok(UsuarioDTOs);
    }

    @GetMapping("/buscarPorId/{id}")
    public ResponseEntity<ResponseUsuarioDTO> buscarPorId(@PathVariable UUID id) {

        Usuario usuario = usuarioService.buscarUsuarioPorId(id);

        return ResponseEntity.ok(
            new ResponseUsuarioDTO( usuario.getId(), usuario.getNome(), usuario.getEmail()) );
    }

    @GetMapping("/buscarPerfil")
    public ResponseEntity<ResponseUsuarioDTO> buscarPerfil(@AuthenticationPrincipal Usuario usuario) {

        return ResponseEntity.ok(
            new ResponseUsuarioDTO( usuario.getId(), usuario.getNome(), usuario.getEmail()) );
    }

    @PutMapping("/editar")
    public ResponseEntity<ResponseUsuarioDTO> editarUsuario(@AuthenticationPrincipal Usuario usuario) {

        Usuario usuarioAtualizado = new Usuario();
        usuarioAtualizado.setNome(usuario.getNome());
        usuarioAtualizado.setEmail(usuario.getEmail());

        Usuario usuarioEditado = usuarioService.editarUsuario(usuario.getId(), usuarioAtualizado);

        ResponseUsuarioDTO usuarioEditadoDTO = new ResponseUsuarioDTO(usuarioEditado.getId(), usuarioEditado.getNome(), usuarioEditado.getEmail());

        return ResponseEntity.ok(usuarioEditadoDTO);
    }

    @DeleteMapping("/deletar")
    public ResponseEntity<String> deletarAcessorio(@AuthenticationPrincipal Usuario usuario) {

        usuarioService.deletarUsuario(usuario.getId());

        return ResponseEntity.ok("Usuário deletado com sucesso.");

    }

}
