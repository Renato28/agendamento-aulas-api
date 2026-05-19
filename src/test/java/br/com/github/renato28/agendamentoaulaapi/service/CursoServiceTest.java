package br.com.github.renato28.agendamentoaulaapi.service;

import br.com.github.renato28.agendamentoaulaapi.dto.CursoRequestDTO;
import br.com.github.renato28.agendamentoaulaapi.exceptions.ProfessorNaoEncontradoException;
import br.com.github.renato28.agendamentoaulaapi.model.Curso;
import br.com.github.renato28.agendamentoaulaapi.model.Perfil;
import br.com.github.renato28.agendamentoaulaapi.model.Usuario;
import br.com.github.renato28.agendamentoaulaapi.repository.CursoRepository;
import br.com.github.renato28.agendamentoaulaapi.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CursoServiceTest {

    @Mock
    private CursoRepository cursoRepository;

    @Mock
    private UsuarioRepository  usuarioRepository;

    @InjectMocks
    private CursoService cursoService;

    @Captor
    private ArgumentCaptor<Curso> cursoCaptor;

    @Test
    void deveCadastrarCursoComSucesso() {

        Usuario professor = Usuario.builder()
                .id(1L)
                .nome("Renato")
                .email("renato@teste.com")
                .perfil(Perfil.PROFESSOR)
                .build();

        CursoRequestDTO dto = CursoRequestDTO.builder()
                .nome("Spring Boot")
                .descricao("Curso de Spring Boot")
                .duracao(40)
                .preco(BigDecimal.valueOf(199.90))
                .professorId(1L)
                .build();

        when(usuarioRepository.findById(1L))
                .thenReturn(Optional.of(professor));

        cursoService.cadastrar(dto);

        verify(usuarioRepository, times(1))
                .findById(1L);

        verify(cursoRepository, times(1))
                .save(cursoCaptor.capture());

        Curso cursoCapturado = cursoCaptor.getValue();

        assertEquals("Spring Boot", cursoCapturado.getNome());
        assertEquals("Curso de Spring Boot", cursoCapturado.getDescricao());
        assertEquals(40, cursoCapturado.getDuracao());
        assertEquals(BigDecimal.valueOf(199.90), cursoCapturado.getPreco());

        assertEquals(professor.getId(),
                cursoCapturado.getProfessor().getId());
    }


    @Test
    void deveLancarExcecaoQuandoProfessorNaoEncontrado() {

        CursoRequestDTO dto = new CursoRequestDTO();
        dto.setProfessorId(1L);

        when(usuarioRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(ProfessorNaoEncontradoException.class,
                () -> cursoService.cadastrar(dto));

        verify(cursoRepository, never())
                .save(any(Curso.class));
    }
}
