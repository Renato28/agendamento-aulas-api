package br.com.github.renato28.agendamentoaulaapi.controller;

import br.com.github.renato28.agendamentoaulaapi.dto.AgendamentoRequestDTO;
import br.com.github.renato28.agendamentoaulaapi.model.Agendamento;
import br.com.github.renato28.agendamentoaulaapi.service.AgendamentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/agendamento")
@RequiredArgsConstructor

public class AgendamentoController {


    private final AgendamentoService agendamentoService;


    @PostMapping("/cadastrar")

    public ResponseEntity<Agendamento>cadastrar(AgendamentoRequestDTO dto) {

        Agendamento agendamento = agendamentoService.cadastrar(dto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(agendamento);
    }


}

