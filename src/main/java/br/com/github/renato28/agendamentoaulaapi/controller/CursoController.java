package br.com.github.renato28.agendamentoaulaapi.controller;

import br.com.github.renato28.agendamentoaulaapi.dto.CursoRequestDTO;
import br.com.github.renato28.agendamentoaulaapi.model.Curso;
import br.com.github.renato28.agendamentoaulaapi.service.CursoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, String>> atualizar
            (@PathVariable Long id, @RequestBody CursoRequestDTO dto) {
        cursoService.atualizar(id, dto);
        return ResponseEntity.ok(Map.of("message", "Curso atualizado com sucesso"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Curso> buscarPorId(@PathVariable Long id) {

        Curso curso = cursoService.buscarPorId(id);

        return ResponseEntity.ok(curso);
    }

    @GetMapping
    public ResponseEntity<List<Curso>> ListarTodos() {

        List<Curso> cursos = cursoService.listarTodos();

        return ResponseEntity.ok(cursos);
    }
}
