package br.com.github.renato28.agendamentoaulaapi.service;

import br.com.github.renato28.agendamentoaulaapi.dto.AvaliacaoRequestDTO;
import br.com.github.renato28.agendamentoaulaapi.exceptions.RegraDeNegocioException;
import br.com.github.renato28.agendamentoaulaapi.model.*;
import br.com.github.renato28.agendamentoaulaapi.repository.AgendamentoRepository;
import br.com.github.renato28.agendamentoaulaapi.repository.AvaliacoesRepository;
import br.com.github.renato28.agendamentoaulaapi.repository.UsuarioRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AvaliacaoServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private AgendamentoRepository agendamentoRepository;

    @Mock
    private AvaliacoesRepository avaliacaoRepository;

    @InjectMocks
    private AvaliacaoService avaliacaoService;

    private Usuario aluno;
    private Usuario professor;
    private Agendamento agendamento;
    private AvaliacaoRequestDTO dto;

    @BeforeEach
    void setup() {

        aluno = new Usuario();
        aluno.setId(1L);
        aluno.setPerfil(Perfil.ALUNO);

        professor = new Usuario();
        professor.setId(2L);
        professor.setPerfil(Perfil.PROFESSOR);

        agendamento = new Agendamento();
        agendamento.setId(1L);
        agendamento.setAluno(aluno);
        agendamento.setProfessor(professor);
        agendamento.setStatus(StatusAgendamento.CONCLUIDO);

        dto = new AvaliacaoRequestDTO();
        dto.setAlunoId(1L);
        dto.setProfessorId(2L);
        dto.setAgendamentoId(1L);
        dto.setNota(5);
        dto.setComentario("Ótima aula");
    }

    @Test
    void deveCadastrarAvaliacaoComSucesso() {

        when(usuarioRepository.findById(1L))
                .thenReturn(Optional.of(aluno));

        when(usuarioRepository.findById(2L))
                .thenReturn(Optional.of(professor));

        when(agendamentoRepository.findById(1L))
                .thenReturn(Optional.of(agendamento));

        avaliacaoService.cadastrar(dto);

        verify(avaliacaoRepository, times(1))
                .save(any(Avaliacoes.class));
    }

    @Test
    void deveLancarExcecaoQuandoUsuarioNaoForAluno() {

        aluno.setPerfil(Perfil.PROFESSOR);

        when(usuarioRepository.findById(1L))
                .thenReturn(Optional.of(aluno));

        RegraDeNegocioException exception = assertThrows(
                RegraDeNegocioException.class,
                () -> avaliacaoService.cadastrar(dto)
        );

        assertEquals(
                "Usuário informado não é um aluno",
                exception.getMessage()
        );
    }

    @Test
    void deveLancarExcecaoQuandoUsuarioNaoForProfessor() {

        professor.setPerfil(Perfil.ALUNO);

        when(usuarioRepository.findById(1L))
                .thenReturn(Optional.of(aluno));

        when(usuarioRepository.findById(2L))
                .thenReturn(Optional.of(professor));

        RegraDeNegocioException exception = assertThrows(
                RegraDeNegocioException.class,
                () -> avaliacaoService.cadastrar(dto)
        );

        assertEquals(
                "Usuário informado não é um professor",
                exception.getMessage()
        );
    }

    @Test
    void deveLancarExcecaoQuandoAgendamentoNaoPertencerAoAluno() {

        Usuario outroAluno = new Usuario();
        outroAluno.setId(99L);

        agendamento.setAluno(outroAluno);

        when(usuarioRepository.findById(1L))
                .thenReturn(Optional.of(aluno));

        when(usuarioRepository.findById(2L))
                .thenReturn(Optional.of(professor));

        when(agendamentoRepository.findById(1L))
                .thenReturn(Optional.of(agendamento));

        RegraDeNegocioException exception = assertThrows(
                RegraDeNegocioException.class,
                () -> avaliacaoService.cadastrar(dto)
        );

        assertEquals(
                "O agendamento não pertence ao aluno informado",
                exception.getMessage()
        );
    }

    @Test
    void deveLancarExcecaoQuandoAgendamentoNaoPertencerAoProfessor() {

        Usuario outroProfessor = new Usuario();
        outroProfessor.setId(99L);

        agendamento.setProfessor(outroProfessor);

        when(usuarioRepository.findById(1L))
                .thenReturn(Optional.of(aluno));

        when(usuarioRepository.findById(2L))
                .thenReturn(Optional.of(professor));

        when(agendamentoRepository.findById(1L))
                .thenReturn(Optional.of(agendamento));

        RegraDeNegocioException exception = assertThrows(
                RegraDeNegocioException.class,
                () -> avaliacaoService.cadastrar(dto)
        );

        assertEquals(
                "O agendamento não pertence ao professor informado",
                exception.getMessage()
        );
    }

    @Test
    void deveLancarExcecaoQuandoAgendamentoNaoEstiverConcluido() {

        agendamento.setStatus(StatusAgendamento.AGENDADO);

        when(usuarioRepository.findById(1L))
                .thenReturn(Optional.of(aluno));

        when(usuarioRepository.findById(2L))
                .thenReturn(Optional.of(professor));

        when(agendamentoRepository.findById(1L))
                .thenReturn(Optional.of(agendamento));

        RegraDeNegocioException exception = assertThrows(
                RegraDeNegocioException.class,
                () -> avaliacaoService.cadastrar(dto)
        );

        assertEquals(
                "Só é possível avaliar aulas concluídas",
                exception.getMessage()
        );
    }
}