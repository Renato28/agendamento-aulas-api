package br.com.github.renato28.agendamentoaulaapi.controller;

import br.com.github.renato28.agendamentoaulaapi.dto.CursoRequestDTO;
import br.com.github.renato28.agendamentoaulaapi.service.CursoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cursos")
@RequiredArgsConstructor

public class CursoController {

    private final CursoService cursoService;

    @PostMapping("/cadastrar")
    public ResponseEntity<Void> cadastrar(@Valid @RequestBody CursoRequestDTO dto) {
       cursoService.cadastrar(dto);
       return ResponseEntity.status(HttpStatus.CREATED).build();

    }
}
