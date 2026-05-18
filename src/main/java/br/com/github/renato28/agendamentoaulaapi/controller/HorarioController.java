package br.com.github.renato28.agendamentoaulaapi.controller;

import br.com.github.renato28.agendamentoaulaapi.dto.HorarioRequetDTO;
import br.com.github.renato28.agendamentoaulaapi.model.Horario;
import br.com.github.renato28.agendamentoaulaapi.service.HorarioService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/Horario")
@AllArgsConstructor

public class HorarioController {


    private final HorarioService horarioService;

    @GetMapping("/cadastrar")
    public ResponseEntity<Horario> cadstrar(
            @Valid @RequestBody HorarioRequetDTO dto) {
        Horario horario = horarioService.cadastrar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(horario);
    }

}
