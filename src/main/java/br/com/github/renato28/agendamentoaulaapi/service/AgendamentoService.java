package br.com.github.renato28.agendamentoaulaapi.service;

import br.com.github.renato28.agendamentoaulaapi.dto.AgendamentoRequestDTO;
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
    public void realizarAgendamento(AgendamentoRequestDTO request) {

        Usuario aluno = usuarioRepository.findById(request.getAlunoId())
                .orElseThrow(() ->
                        new RuntimeException("Aluno não encontrado"));

        if (aluno.getPerfil() != Perfil.ALUNO) {
            throw new RuntimeException(
                    "Usuário informado não é um aluno");
        }

        Usuario professor = usuarioRepository.findById(request.getProfessorId())
                .orElseThrow(() ->
                        new RuntimeException("Professor não encontrado"));

        if (professor.getPerfil() != Perfil.PROFESSOR) {
            throw new RuntimeException(
                    "Usuário informado não é um professor");
        }

        Curso curso = cursoRepository.findById(request.getCursoId())
                .orElseThrow(() ->
                        new RuntimeException("Curso não encontrado"));

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
}