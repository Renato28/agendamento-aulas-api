package br.com.github.renato28.agendamentoaulaapi.service;

import br.com.github.renato28.agendamentoaulaapi.dto.DisponibilidadeRequestDTO;
import br.com.github.renato28.agendamentoaulaapi.exceptions.DisponibilidadeNaoEncontradoException;
import br.com.github.renato28.agendamentoaulaapi.exceptions.RegraDeNegocioException;
import br.com.github.renato28.agendamentoaulaapi.exceptions.UsuarioNaoEncontradoException;
import br.com.github.renato28.agendamentoaulaapi.model.Disponibilidade;
import br.com.github.renato28.agendamentoaulaapi.model.Usuario;
import br.com.github.renato28.agendamentoaulaapi.repository.DisponibilidadeRepository;
import br.com.github.renato28.agendamentoaulaapi.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DisponibilidadeService {

    private final DisponibilidadeRepository disponibilidadeRepository;
    private final UsuarioRepository usuarioRepository;

    public void cadastrar(DisponibilidadeRequestDTO dto) {

        Usuario professor = usuarioRepository.findById(dto.getProfessorId()).orElseThrow(()
                -> new DisponibilidadeNaoEncontradoException("Disponibilidade não encontrada"));

        Disponibilidade disponibilidade = Disponibilidade.builder()
                .professor(professor)
                .diaSemana(dto.getDiaSemana())
                .horaInicio(dto.getHoraInicio())
                .horaFim(dto.getHoraFim())
                .duracaoAula(dto.getDuracaoAula())
                .build();

        disponibilidadeRepository.save(disponibilidade);
    }

    @Transactional
    public void atualizar(
            Long disponibilidadeId,
            DisponibilidadeRequestDTO dto
    ) {

        Disponibilidade disponibilidadeExistente =
                disponibilidadeRepository.findById(disponibilidadeId)
                        .orElseThrow(() ->
                                new RegraDeNegocioException(
                                        "Disponibilidade não encontrada"
                                ));

        Usuario professor = usuarioRepository
                .findById(dto.getProfessorId())
                .orElseThrow(() ->
                        new UsuarioNaoEncontradoException(
                                "Professor não encontrado"
                        ));

        if (dto.getHoraInicio().isAfter(dto.getHoraFim())
                || dto.getHoraInicio().equals(dto.getHoraFim())) {

            throw new RegraDeNegocioException(
                    "Hora início deve ser menor que hora fim"
            );
        }

        if (dto.getDuracaoAula() <= 0) {
            throw new RegraDeNegocioException(
                    "Duração da aula inválida"
            );
        }

        Disponibilidade disponibilidadeAtualizada =
                Disponibilidade.builder()
                        .id(disponibilidadeExistente.getId())
                        .professor(professor)
                        .diaSemana(dto.getDiaSemana())
                        .horaInicio(dto.getHoraInicio())
                        .horaFim(dto.getHoraFim())
                        .duracaoAula(dto.getDuracaoAula())
                        .build();

        disponibilidadeRepository.save(
                disponibilidadeAtualizada
        );
    }
}

