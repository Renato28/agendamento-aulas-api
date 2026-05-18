package br.com.github.renato28.agendamentoaulaapi.controller;

import br.com.github.renato28.agendamentoaulaapi.dto.UsuarioRequestDto;
import br.com.github.renato28.agendamentoaulaapi.model.Usuario;
import br.com.github.renato28.agendamentoaulaapi.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping("/cadastrar")
    public ResponseEntity<Usuario> cadastrar(@Valid @RequestBody UsuarioRequestDto dto) {

        Usuario usuario = usuarioService.cadastrar(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(usuario);

    }
}
