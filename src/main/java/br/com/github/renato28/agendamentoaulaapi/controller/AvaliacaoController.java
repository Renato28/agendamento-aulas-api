package br.com.github.renato28.agendamentoaulaapi.controller;


import br.com.github.renato28.agendamentoaulaapi.dto.AvaliacaoRequestDTO;
import br.com.github.renato28.agendamentoaulaapi.service.AvaliacaoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/avaliacoes")
@RequiredArgsConstructor
public class AvaliacaoController {

    private final AvaliacaoService avaliacaoService;

    @PostMapping
    public ResponseEntity<Void> cadastrar(
            @RequestBody @Valid AvaliacaoRequestDTO dto) {

        avaliacaoService.cadastrar(dto);

        return ResponseEntity
                .status(HttpStatus.CREATED).build();
    }
}
