package br.com.github.renato28.agendamentoaulaapi.service;

import br.com.github.renato28.agendamentoaulaapi.dto.AgendamentoResponseDTO;
import br.com.github.renato28.agendamentoaulaapi.dto.CadastroAtualizacaoDTO;
import br.com.github.renato28.agendamentoaulaapi.exceptions.AgendamentoNaoEncontradoException;
import br.com.github.renato28.agendamentoaulaapi.exceptions.HorarioNaoEncontradoException;
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

import java.util.List;

@Service
@RequiredArgsConstructor
public class AgendamentoService {

    private final UsuarioRepository usuarioRepository;
    private final CursoRepository cursoRepository;
    private final HorarioRepository horarioRepository;
    private final AgendamentoRepository agendamentoRepository;

    @Transactional
    public void cadastrar(CadastroAtualizacaoDTO request) {

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
    
    public void concluir(Long agendamentoId) {

        Agendamento agendamento = agendamentoRepository.findById(agendamentoId)
                .orElseThrow(() -> new AgendamentoNaoEncontradoException("Agendamento não encontrado"));

        if (agendamento.getStatus().equals(StatusAgendamento.CANCELADO)) {
            throw new RegraDeNegocioException("Agendamento já foi cancelado");
        }

        if (agendamento.getStatus().equals(StatusAgendamento.CONCLUIDO)) {
            throw new RegraDeNegocioException("Não é possive concluir uma aula concluida");
        }

        agendamento.setStatus(StatusAgendamento.CONCLUIDO);

        agendamentoRepository.save(agendamento);

    }

    public void reagendar(Long agendamentoId, Long novoHorarioId) {

        Agendamento agendamento = agendamentoRepository.findById(agendamentoId)
                .orElseThrow(() -> new AgendamentoNaoEncontradoException("Agendamento não encontrado"));

        if (agendamento.getStatus().equals(StatusAgendamento.CANCELADO)) {
            throw new RegraDeNegocioException("Agendamento já foi cancelado");
        }

        if (agendamento.getStatus().equals(StatusAgendamento.CONCLUIDO)) {
            throw new RegraDeNegocioException("Não é possivel reagendar uma aula concluida");
        }

        Horario horarioAtual = agendamento.getHorario();

        Horario novoHorario = horarioRepository.findById(novoHorarioId)
                .orElseThrow(() -> new HorarioNaoEncontradoException("Horário não encontrado"));

        if (novoHorario.getStatusHorario() != StatusHorario.DISPONIVEL) {
            throw new RegraDeNegocioException("Novo horário indisponivel");
        }

        if (!novoHorario.getProfessor().getId().equals(agendamento.getProfessor().getId())) {
            throw new RegraDeNegocioException("O novo horário não pertence ao professor do agendamento");
        }

        horarioAtual.setStatusHorario(StatusHorario.DISPONIVEL);

        novoHorario.setStatusHorario(StatusHorario.OCUPADO);

        agendamento.setHorario(novoHorario);

        horarioRepository.save(horarioAtual);
        horarioRepository.save(novoHorario);

        agendamentoRepository.save(agendamento);

    }


    @Transactional
    public void atualizar (Long agendamentoId, CadastroAtualizacaoDTO dto) {

        // Busca o agendamento pelo ID
        Agendamento agendamento = agendamentoRepository.findById(agendamentoId)
                .orElseThrow(() ->
                        new AgendamentoNaoEncontradoException("Agendamento não encontrado"));

        // Busca os novos dados
        Usuario aluno = usuarioRepository.findById(dto.getAlunoId())
                .orElseThrow(() ->
                        new UsuarioNaoEncontradoException("Aluno não encontrado"));

        Usuario professor = usuarioRepository.findById(dto.getProfessorId())
                .orElseThrow(() ->
                        new UsuarioNaoEncontradoException("Professor não encontrado"));

        Curso curso = cursoRepository.findById(dto.getCursoId())
                .orElseThrow(() ->
                        new RuntimeException("Curso não encontrado"));

        Horario novoHorario = horarioRepository.findById(dto.getHorarioId())
                .orElseThrow(() ->
                        new RuntimeException("Horário não encontrado"));

        // Libera o horário antigo
        agendamento.getHorario().setStatusHorario(StatusHorario.DISPONIVEL);

        // Ocupa o novo horário
        novoHorario.setStatusHorario(StatusHorario.OCUPADO);

        // Atualiza os dados do agendamento
        agendamento.setAluno(aluno);
        agendamento.setProfessor(professor);
        agendamento.setCurso(curso);
        agendamento.setHorario(novoHorario);

        // Salva os horários
        horarioRepository.save(agendamento.getHorario());
        horarioRepository.save(novoHorario);

        // Salva o agendamento atualizado
        agendamentoRepository.save(agendamento);
    }

    public AgendamentoResponseDTO buscarPorId(Long id){

        Agendamento agendameno=
                agendamentoRepository.buscarCompletoPorId(id)
                        .orElseThrow(()->
                           new AgendamentoNaoEncontradoException("Agendamento nao encontrado"));

        return AgendamentoResponseDTO
                .fromEntity(agendameno);


    }

    public List<AgendamentoResponseDTO> listarTodos(){
        return agendamentoRepository.listarCompleto()
                .stream()
                .map(AgendamentoResponseDTO::fromEntity)
                .toList();
    }

}