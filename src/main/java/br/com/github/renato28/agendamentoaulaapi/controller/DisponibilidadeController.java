package br.com.github.renato28.agendamentoaulaapi.controller;

import br.com.github.renato28.agendamentoaulaapi.dto.DisponibilidadeRequestDTO;
import br.com.github.renato28.agendamentoaulaapi.model.Disponibilidade;
import br.com.github.renato28.agendamentoaulaapi.service.DisponibilidadeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/disponibilidade")
@RequiredArgsConstructor
public class DisponibilidadeController {

    private final DisponibilidadeService disponibilidadeService;

    @PostMapping("/cadastrar")
    public ResponseEntity<Disponibilidade> cadastrar(@Valid @RequestBody DisponibilidadeRequestDTO dto){

        Disponibilidade disponibilidade = disponibilidadeService.cadastrar(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(disponibilidade);
    }
}
