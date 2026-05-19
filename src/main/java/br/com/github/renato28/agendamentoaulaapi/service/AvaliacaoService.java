package br.com.github.renato28.agendamentoaulaapi.service;

import br.com.github.renato28.agendamentoaulaapi.dto.AvaliacaoRequestDTO;
import br.com.github.renato28.agendamentoaulaapi.exceptions.AgendamentoNaoEncontradoException;
import br.com.github.renato28.agendamentoaulaapi.exceptions.RegraDeNegocioException;
import br.com.github.renato28.agendamentoaulaapi.exceptions.UsuarioNaoEncontradoException;
import br.com.github.renato28.agendamentoaulaapi.model.*;
import br.com.github.renato28.agendamentoaulaapi.repository.AgendamentoRepository;
import br.com.github.renato28.agendamentoaulaapi.repository.AvaliacoesRepository;
import br.com.github.renato28.agendamentoaulaapi.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AvaliacaoService{

    private final UsuarioRepository usuarioRepository;
    private final AgendamentoRepository agendamentoRepository;
    private final AvaliacoesRepository avaliacaoRepository;

    @Transactional
    public void cadastrar(AvaliacaoRequestDTO request) {

        Usuario aluno = usuarioRepository.findById(request.getAlunoId())
                .orElseThrow(() ->
                        new UsuarioNaoEncontradoException("Aluno não encontrado"));

        if (aluno.getPerfil() != Perfil.ALUNO) {
            throw new RegraDeNegocioException(
                    "Usuário informado não é um aluno");
        }

        Usuario professor = usuarioRepository.findById(request.getProfessorId())
                .orElseThrow(() ->
                        new UsuarioNaoEncontradoException("Professor não encontrado"));

        if (professor.getPerfil() != Perfil.PROFESSOR) {
            throw new RegraDeNegocioException(
                    "Usuário informado não é um professor");
        }

        Agendamento agendamento = agendamentoRepository
                .findById(request.getAgendamentoId())
                .orElseThrow(() ->
                        new AgendamentoNaoEncontradoException("Agendamento não encontrado"));

        if (!agendamento.getAluno().getId().equals(aluno.getId())) {
            throw new RegraDeNegocioException(
                    "O agendamento não pertence ao aluno informado");
        }

        if (!agendamento.getProfessor().getId()
                .equals(professor.getId())) {

            throw new RegraDeNegocioException(
                    "O agendamento não pertence ao professor informado");
        }

        if (agendamento.getStatus() != StatusAgendamento.CONCLUIDO) {
            throw new RegraDeNegocioException(
                    "Só é possível avaliar aulas concluídas");
        }

        Avaliacoes avaliacao = Avaliacoes.builder()
                .aluno(aluno)
                .professor(professor)
                .nota(request.getNota())
                .comentario(request.getComentario())
                .build();

        avaliacaoRepository.save(avaliacao);
    }
}