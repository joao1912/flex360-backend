package com.flex360.api_flex360.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flex360.api_flex360.dto.usuario.ResponseUsuarioDTO;
import com.flex360.api_flex360.dto.usuario.UsuarioDTO;
import com.flex360.api_flex360.models.Usuario;
import com.flex360.api_flex360.services.UsuarioService;
import com.flex360.api_flex360.services.ConverteParaDtoService;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ConverteParaDtoService converteParaDtoService;

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

    @PostMapping("/criar")
    public ResponseEntity<ResponseUsuarioDTO> criarAcessorio(@RequestBody UsuarioDTO UsuarioDTO) {

        Usuario novoUsuario = new Usuario();
        novoUsuario.setNome(UsuarioDTO.nome());
        novoUsuario.setEmail(UsuarioDTO.email());
        novoUsuario.setSenha(UsuarioDTO.senha());

        Usuario usuarioCriado = usuarioService.criarUsuario(novoUsuario);

        ResponseUsuarioDTO novoUsuarioDTO = new ResponseUsuarioDTO(usuarioCriado.getId(), usuarioCriado.getNome(), usuarioCriado.getEmail());

        return ResponseEntity.status(201).body(novoUsuarioDTO);

    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<ResponseUsuarioDTO> editarUsuario(@PathVariable UUID id, @RequestBody UsuarioDTO UsuarioDTO) {

        Usuario usuarioAtualizado = new Usuario();
        usuarioAtualizado.setNome(UsuarioDTO.nome());
        usuarioAtualizado.setEmail(UsuarioDTO.email());

        Usuario usuarioEditado = usuarioService.editarUsuario(id, usuarioAtualizado);

        ResponseUsuarioDTO usuarioEditadoDTO = new ResponseUsuarioDTO(usuarioEditado.getId(), usuarioEditado.getNome(), usuarioEditado.getEmail());

        return ResponseEntity.ok(usuarioEditadoDTO);
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<String> deletarAcessorio(@PathVariable UUID id) {

        usuarioService.deletarUsuario(id);

        return ResponseEntity.ok("Usuário deletado com sucesso.");

    }

}
