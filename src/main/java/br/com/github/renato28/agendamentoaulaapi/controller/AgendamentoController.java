package br.com.github.renato28.agendamentoaulaapi.controller;

import br.com.github.renato28.agendamentoaulaapi.dto.AgendamentoResponseDTO;
import br.com.github.renato28.agendamentoaulaapi.dto.CadastroAtualizacaoDTO;
import br.com.github.renato28.agendamentoaulaapi.service.AgendamentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/agendamento")
@RequiredArgsConstructor

public abstract class AgendamentoController {


    private final AgendamentoService agendamentoService;


    @PostMapping("/cadastrar")

    public ResponseEntity<Void> cadastrar(CadastroAtualizacaoDTO dto) {

        agendamentoService.cadastrar(dto);

        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    @PutMapping("/cancelar/{agendamentoId}")
    public ResponseEntity<Map<String, String>> cancelar(@PathVariable Long agendamentoId) {

        agendamentoService.cancelar(agendamentoId);
        return ResponseEntity.ok(Map.of("message", "Agendamento atualizado com sucesso"));
    }

    @PutMapping("/concluir/{agendamentoId}")
    public ResponseEntity<Map<String, String>> concluir(@PathVariable Long agendamentoId) {

        agendamentoService.concluir(agendamentoId);
        return ResponseEntity.ok(Map.of("message", "Agendamento concluido com sucesso"));
    }


    @PutMapping("/reagendar/{agendamentoid}/{novoHorarioId}")
    public ResponseEntity<Map<String, String>> reagendar(@PathVariable Long agendamentoid, @PathVariable Long novoHorarioId) {

        agendamentoService.reagendar(agendamentoid, novoHorarioId);
        return ResponseEntity.ok(Map.of("message", "Aula reagendada com sucesso"));

    }

    @PutMapping("/atualizar/{agendamentoId}")

    public ResponseEntity<Map<String, String>> atualizar(@PathVariable Long agendamentoId,

                                                         @RequestBody CadastroAtualizacaoDTO dto) {

        agendamentoService.

                atualizar(agendamentoId, dto);

        return ResponseEntity.

                ok(Map.of("message", "agendamento atualizado com sucesso "));

    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<AgendamentoResponseDTO> buscarporId(@PathVariable Long id) {
        AgendamentoResponseDTO response = agendamentoService.buscarPorId(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<AgendamentoResponseDTO>> listarTodos() {

        List<AgendamentoResponseDTO> lista =
        agendamentoService.listarTodos();

        return ResponseEntity.ok(lista);


    }

}
