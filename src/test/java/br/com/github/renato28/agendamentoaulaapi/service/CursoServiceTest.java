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
    private CursoService service;

    @InjectMocks
    private CursoService cursoService;

    @Test
    void deveCadastrarCursoComSucesso() {

        Usuario professor = Usuario.builder()
                .id(1L)
                .nome("Renato")
                .email("renato@teste.com")
                .perfil(Perfil.PROFESSOR)
                .build();

        CursoRequestDTO dto = new CursoRequestDTO();
        dto.setNome("Spring Boot");
        dto.setDescricao("Curso de Spring Boot");
        dto.setDuracao(40);
        dto.setPreco(BigDecimal.valueOf(199.90));
        dto.setProfessorId(1L);

        Curso cursoSalvo = Curso.builder()
                .id(1L)
                .nome(dto.getNome())
                .descricao(dto.getDescricao())
                .duracao(dto.getDuracao())
                .preco(dto.getPreco())
                .professor(professor)
                .build();

        when(usuarioRepository.findById(1L))
                .thenReturn(Optional.of(professor));

        when(cursoRepository.save(any(Curso.class)))
                .thenReturn(cursoSalvo);

        Curso curso = cursoService.cadastrar(dto);

        assertNotNull(curso);

        assertEquals("Spring Boot", curso.getNome());
        assertEquals("Curso de Spring Boot", curso.getDescricao());
        assertEquals(BigDecimal.valueOf(199.9), curso.getPreco());

        assertEquals(professor.getId(), curso.getProfessor().getId());

        verify(usuarioRepository, times(1))
                .findById(1L);

        verify(cursoRepository, times(1))
                .save(any(Curso.class));
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
