package br.com.github.renato28.agendamentoaulaapi.service;

import br.com.github.renato28.agendamentoaulaapi.dto.CadastroAtualizacaoDTO;
import br.com.github.renato28.agendamentoaulaapi.exceptions.RegraDeNegocioException;
import br.com.github.renato28.agendamentoaulaapi.model.*;
import br.com.github.renato28.agendamentoaulaapi.repository.AgendamentoRepository;
import br.com.github.renato28.agendamentoaulaapi.repository.CursoRepository;
import br.com.github.renato28.agendamentoaulaapi.repository.HorarioRepository;
import br.com.github.renato28.agendamentoaulaapi.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AgendamentoServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private CursoRepository cursoRepository;

    @Mock
    private HorarioRepository horarioRepository;

    @Mock
    private AgendamentoRepository agendamentoRepository;

    @InjectMocks
    private AgendamentoService agendamentoService;

    private Usuario aluno;
    private Usuario professor;
    private Curso curso;
    private Horario horario;
    private CadastroAtualizacaoDTO dto;

    @BeforeEach
    void setup() {

        aluno = new Usuario();
        aluno.setId(1L);
        aluno.setPerfil(Perfil.ALUNO);

        professor = new Usuario();
        professor.setId(2L);
        professor.setPerfil(Perfil.PROFESSOR);

        curso = new Curso();
        curso.setId(1L);
        curso.setProfessor(professor);

        horario = new Horario();
        horario.setId(1L);
        horario.setProfessor(professor);
        horario.setStatusHorario(StatusHorario.DISPONIVEL);

        dto = new CadastroAtualizacaoDTO();
        dto.setAlunoId(1L);
        dto.setProfessorId(2L);
        dto.setCursoId(1L);
        dto.setHorarioId(1L);
    }

    @Test
    void deveCadastrarComSucesso() {

        when(usuarioRepository.findById(1L))
                .thenReturn(Optional.of(aluno));

        when(usuarioRepository.findById(2L))
                .thenReturn(Optional.of(professor));

        when(cursoRepository.findById(1L))
                .thenReturn(Optional.of(curso));

        when(horarioRepository.findById(1L))
                .thenReturn(Optional.of(horario));

        agendamentoService.cadastrar(dto);

        verify(agendamentoRepository, times(1))
                .save(any(Agendamento.class));

        verify(horarioRepository, times(1))
                .save(horario);

        assertEquals(StatusHorario.OCUPADO,
                horario.getStatusHorario());
    }

    @Test
    void deveLancarExcecaoQuandoUsuarioNaoForAluno() {

        aluno.setPerfil(Perfil.PROFESSOR);

        when(usuarioRepository.findById(1L))
                .thenReturn(Optional.of(aluno));

        RegraDeNegocioException exception = assertThrows(
                RegraDeNegocioException.class,
                () -> agendamentoService.cadastrar(dto)
        );

        assertEquals(
                "Usuário informado não é um aluno",
                exception.getMessage()
        );
    }

    @Test
    void deveLancarExcecaoQuandoProfessorNaoForProfessor() {

        professor.setPerfil(Perfil.ALUNO);

        when(usuarioRepository.findById(1L))
                .thenReturn(Optional.of(aluno));

        when(usuarioRepository.findById(2L))
                .thenReturn(Optional.of(professor));

        RegraDeNegocioException exception = assertThrows(
                RegraDeNegocioException.class,
                () -> agendamentoService.cadastrar(dto)
        );

        assertEquals(
                "Usuário informado não é um professor",
                exception.getMessage()
        );
    }

    @Test
    void deveLancarExcecaoQuandoHorarioEstiverIndisponivel() {

        horario.setStatusHorario(StatusHorario.OCUPADO);

        when(usuarioRepository.findById(1L))
                .thenReturn(Optional.of(aluno));

        when(usuarioRepository.findById(2L))
                .thenReturn(Optional.of(professor));

        when(cursoRepository.findById(1L))
                .thenReturn(Optional.of(curso));

        when(horarioRepository.findById(1L))
                .thenReturn(Optional.of(horario));

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> agendamentoService.cadastrar(dto)
        );

        assertEquals(
                "Horário indisponível",
                exception.getMessage()
        );
    }

    @Test
    void deveCancelarAgendamentoComSucesso() {

        Agendamento agendamento = new Agendamento();

        agendamento.setId(1L);
        agendamento.setStatus(StatusAgendamento.AGENDADO);
        agendamento.setHorario(horario);

        when(agendamentoRepository.findById(1L))
                .thenReturn(Optional.of(agendamento));

        agendamentoService.cancelar(1L);

        assertEquals(
                StatusAgendamento.CANCELADO,
                agendamento.getStatus()
        );

        assertEquals(
                StatusHorario.DISPONIVEL,
                horario.getStatusHorario()
        );

        verify(agendamentoRepository).save(agendamento);
    }

    @Test
    void deveReagendarComSucesso() {

        Horario novoHorario = new Horario();

        novoHorario.setId(2L);
        novoHorario.setProfessor(professor);
        novoHorario.setStatusHorario(StatusHorario.DISPONIVEL);

        Agendamento agendamento = new Agendamento();

        agendamento.setId(1L);
        agendamento.setProfessor(professor);
        agendamento.setHorario(horario);
        agendamento.setStatus(StatusAgendamento.AGENDADO);

        horario.setStatusHorario(StatusHorario.OCUPADO);

        when(agendamentoRepository.findById(1L))
                .thenReturn(Optional.of(agendamento));

        when(horarioRepository.findById(2L))
                .thenReturn(Optional.of(novoHorario));

        agendamentoService.reagendar(1L, 2L);

        assertEquals(
                StatusHorario.DISPONIVEL,
                horario.getStatusHorario()
        );

        assertEquals(
                StatusHorario.OCUPADO,
                novoHorario.getStatusHorario()
        );

        assertEquals(
                novoHorario,
                agendamento.getHorario()
        );

        verify(agendamentoRepository).save(agendamento);
    }

    @Test
    void deveAtualizarAgendamentoComSucesso() {

        Agendamento agendamento = new Agendamento();

        agendamento.setId(1L);
        agendamento.setHorario(horario);

        Horario novoHorario = new Horario();

        novoHorario.setId(2L);
        novoHorario.setProfessor(professor);
        novoHorario.setStatusHorario(StatusHorario.DISPONIVEL);

        dto.setHorarioId(2L);

        when(agendamentoRepository.findById(1L))
                .thenReturn(Optional.of(agendamento));

        when(usuarioRepository.findById(1L))
                .thenReturn(Optional.of(aluno));

        when(usuarioRepository.findById(2L))
                .thenReturn(Optional.of(professor));

        when(cursoRepository.findById(1L))
                .thenReturn(Optional.of(curso));

        when(horarioRepository.findById(2L))
                .thenReturn(Optional.of(novoHorario));

        agendamentoService.atualizar(1L, dto);

        assertEquals(aluno, agendamento.getAluno());

        assertEquals(professor, agendamento.getProfessor());

        assertEquals(curso, agendamento.getCurso());

        assertEquals(novoHorario, agendamento.getHorario());

        verify(agendamentoRepository).save(agendamento);
    }
}