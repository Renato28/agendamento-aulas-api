package br.com.github.renato28.agendamentoaulaapi.service;

import br.com.github.renato28.agendamentoaulaapi.dto.AvaliacaoRequestDTO;
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
    public void criar(AvaliacaoRequestDTO request) {

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

        Agendamento agendamento = agendamentoRepository
                .findById(request.getAgendamentoId())
                .orElseThrow(() ->
                        new RuntimeException("Agendamento não encontrado"));

        if (!agendamento.getAluno().getId().equals(aluno.getId())) {
            throw new RuntimeException(
                    "O agendamento não pertence ao aluno informado");
        }

        if (!agendamento.getProfessor().getId()
                .equals(professor.getId())) {

            throw new RuntimeException(
                    "O agendamento não pertence ao professor informado");
        }

        if (agendamento.getStatus() != StatusAgendamento.CONCLUIDO) {
            throw new RuntimeException(
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