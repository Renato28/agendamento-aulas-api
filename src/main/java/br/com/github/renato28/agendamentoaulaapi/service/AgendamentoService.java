package br.com.github.renato28.agendamentoaulaapi.service;

import br.com.github.renato28.agendamentoaulaapi.dto.AgendamentoRequestDTO;
import br.com.github.renato28.agendamentoaulaapi.exceptions.AgendamentoNaoEncontradoException;
import br.com.github.renato28.agendamentoaulaapi.exceptions.RegraDeNegocioException;
import br.com.github.renato28.agendamentoaulaapi.exceptions.UsuarioNaoEncontradoException;
import br.com.github.renato28.agendamentoaulaapi.model.*;
import br.com.github.renato28.agendamentoaulaapi.repository.AgendamentoRepository;
import br.com.github.renato28.agendamentoaulaapi.repository.CursoRepository;
import br.com.github.renato28.agendamentoaulaapi.repository.HorarioRepository;
import br.com.github.renato28.agendamentoaulaapi.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AgendamentoService {

    private final UsuarioRepository usuarioRepository;
    private final CursoRepository cursoRepository;
    private final HorarioRepository horarioRepository;
    private final AgendamentoRepository agendamentoRepository;

    @Transactional
    public Agendamento cadastrar(AgendamentoRequestDTO request) {

        Usuario aluno = usuarioRepository.findById(request.getAlunoId())
                .orElseThrow(() ->
                        new UsuarioNaoEncontradoException("Aluno não encontrado"));

        if (aluno.getPerfil() != Perfil.ALUNO) {
            throw new RegraDeNegocioException(
                    "Usuário informado não é um aluno");
        }

        Usuario professor = usuarioRepository.findById(request.getProfessorId())
                .orElseThrow(() ->
                        new RegraDeNegocioException("Professor não encontrado"));

        if (professor.getPerfil() != Perfil.PROFESSOR) {
            throw new RegraDeNegocioException(
                    "Usuário informado não é um professor");
        }

        Curso curso = cursoRepository.findById(request.getCursoId())
                .orElseThrow(() ->
                        new RegraDeNegocioException("Curso não encontrado"));

        if (!curso.getProfessor().getId().equals(professor.getId())) {
            throw new RuntimeException(
                    "O curso não pertence ao professor informado");
        }

        Horario horario = horarioRepository.findById(request.getHorarioId())
                .orElseThrow(() ->
                        new RuntimeException("Horário não encontrado"));

        if (horario.getStatusHorario() != StatusHorario.DISPONIVEL) {
            throw new RuntimeException(
                    "Horário indisponível");
        }

        Agendamento agendamento = Agendamento.builder()
                .aluno(aluno)
                .professor(professor)
                .curso(curso)
                .horario(horario)
                .status(StatusAgendamento.AGENDADO)
                .build();

        agendamentoRepository.save(agendamento);

        horario.setStatusHorario(StatusHorario.OCUPADO);

        horarioRepository.save(horario);
    }

    public void cancelar(Long agendamentoId) {

        Agendamento agendamento = agendamentoRepository
                .findById(agendamentoId)
                .orElseThrow(()
                        -> new AgendamentoNaoEncontradoException("Agendamento não encontrado"));

        if (agendamento.getStatus().equals(StatusAgendamento.CANCELADO)) {
            throw new RegraDeNegocioException("Agendamento já foi cancelado");
        }

        if (agendamento.getStatus().equals(StatusAgendamento.CONCLUIDO)) {
            throw new RegraDeNegocioException("Não é possivel cancelar uma aula concluida");
        }

        agendamento.setStatus(StatusAgendamento.CANCELADO);

        agendamento.getHorario().setStatusHorario(StatusHorario.DISPONIVEL);

        horarioRepository.save(agendamento.getHorario());

        agendamentoRepository.save(agendamento);
    }
}