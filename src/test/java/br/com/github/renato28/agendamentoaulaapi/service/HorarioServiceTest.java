package br.com.github.renato28.agendamentoaulaapi.service;

import br.com.github.renato28.agendamentoaulaapi.dto.HorarioRequetDTO;
import br.com.github.renato28.agendamentoaulaapi.exceptions.ProfessorNaoEncontradoException;
import br.com.github.renato28.agendamentoaulaapi.model.Horario;
import br.com.github.renato28.agendamentoaulaapi.model.Perfil;
import br.com.github.renato28.agendamentoaulaapi.model.StatusHorario;
import br.com.github.renato28.agendamentoaulaapi.model.Usuario;
import br.com.github.renato28.agendamentoaulaapi.repository.HorarioRepository;
import br.com.github.renato28.agendamentoaulaapi.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class HorarioServiceTest {

    @Mock
    private HorarioRepository horarioRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private HorarioService horarioService;

    @Test
    void deveCadastrarHorarioComSucesso() {

        HorarioRequetDTO dto = new HorarioRequetDTO();
        dto.setProfessorId(1L);
        dto.setInicio(LocalDateTime.of(2026, 5, 18, 14, 0));
        dto.setFim(LocalDateTime.of(2026, 5, 18, 15, 0));

        Usuario professor = Usuario.builder()
                .id(1L)
                .nome("Professor Renato")
                .email("professor@teste.com")
                .perfil(Perfil.PROFESSOR)
                .ativo(true)
                .build();

        when(usuarioRepository.findById(1L))
                .thenReturn(Optional.of(professor));

        Horario horarioSalvo = Horario.builder()
                .professor(professor)
                .inicio(dto.getInicio())
                .fim(dto.getFim())
                .statusHorario(StatusHorario.DISPONIVEL)
                .build();

        when(horarioRepository.save(any(Horario.class)))
                .thenReturn(horarioSalvo);

        Horario resultado = horarioService.cadastrar(dto);

        assertNotNull(resultado);
        assertEquals(StatusHorario.DISPONIVEL, resultado.getStatusHorario());
        assertEquals(professor, resultado.getProfessor());
        assertEquals(dto.getInicio(), resultado.getInicio());
        assertEquals(dto.getFim(), resultado.getFim());

        verify(usuarioRepository, times(1))
                .findById(1L);

        verify(horarioRepository, times(1))
                .save(any(Horario.class));
    }

    @Test
    void deveLancarExcecaoQuandoProfessorNaoEncontrado() {

        HorarioRequetDTO dto = new HorarioRequetDTO();
        dto.setProfessorId(1L);

        when(usuarioRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(ProfessorNaoEncontradoException.class,
                () -> horarioService.cadastrar(dto));

        verify(horarioRepository, never())
                .save(any(Horario.class));
    }
}


