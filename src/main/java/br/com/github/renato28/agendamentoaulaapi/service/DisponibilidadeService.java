package br.com.github.renato28.agendamentoaulaapi.service;

import br.com.github.renato28.agendamentoaulaapi.dto.DisponibilidadeRequestDTO;
import br.com.github.renato28.agendamentoaulaapi.exceptions.DisponibilidadeNaoEncontradoException;
import br.com.github.renato28.agendamentoaulaapi.model.Disponibilidade;
import br.com.github.renato28.agendamentoaulaapi.model.Usuario;
import br.com.github.renato28.agendamentoaulaapi.repository.DisponibilidadeRepository;
import br.com.github.renato28.agendamentoaulaapi.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DisponibilidadeService {

    private final DisponibilidadeRepository disponibilidadeRepository;
    private final UsuarioRepository usuarioRepository;

    public Disponibilidade cadastrar(DisponibilidadeRequestDTO dto) {

        Usuario professor = usuarioRepository.findById(dto.getProfessorId()).orElseThrow(()
                -> new DisponibilidadeNaoEncontradoException("Disponibilidade não encontrada"));

        Disponibilidade disponibilidade = Disponibilidade.builder()
                .professor(professor)
                .diaSemana(dto.getDiaSemana())
                .horaInicio(dto.getHoraInicio())
                .horaFim(dto.getHoraFim())
                .duracaoAula(dto.getDuracaoAula())
                .build();

        return disponibilidadeRepository.save(disponibilidade);
    }
}
