package br.com.github.renato28.agendamentoaulaapi.controller;


import br.com.github.renato28.agendamentoaulaapi.dto.AvaliacaoRequestDTO;
import br.com.github.renato28.agendamentoaulaapi.model.Avaliacoes;
import br.com.github.renato28.agendamentoaulaapi.model.Agendamento;
import br.com.github.renato28.agendamentoaulaapi.model.Usuario;
import br.com.github.renato28.agendamentoaulaapi.repository.AgendamentoRepository;
import br.com.github.renato28.agendamentoaulaapi.repository.AvaliacoesRepository;
import br.com.github.renato28.agendamentoaulaapi.repository.UsuarioRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/avaliacoes")
@RequiredArgsConstructor
public class AvaliacaoController {

    private final AvaliacoesRepository avaliacoesRepository;
    private final UsuarioRepository usuarioRepository;
    private final AgendamentoRepository agendamentoRepository;

    @PostMapping
    public ResponseEntity<Avaliacoes> criar(
            @RequestBody @Valid AvaliacaoRequestDTO dto) {

        Usuario aluno = usuarioRepository.findById(dto.getAlunoId())
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));

        Usuario professor = usuarioRepository.findById(dto.getProfessorId())
                .orElseThrow(() -> new RuntimeException("Professor não encontrado"));

        Agendamento agendamento = agendamentoRepository.findById(dto.getAgendamentoId())
                .orElseThrow(() -> new RuntimeException("Agendamento não encontrado"));

        Avaliacoes avaliacao = new Avaliacoes();

        avaliacao.setAluno(aluno);
        avaliacao.setProfessor(professor);
        avaliacao.setAgendamento(agendamento);
        avaliacao.setNota(dto.getNota());
        avaliacao.setComentario(dto.getComentario());

        Avaliacoes novaAvaliacao = avaliacoesRepository.save(avaliacao);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(novaAvaliacao);
    }
}
