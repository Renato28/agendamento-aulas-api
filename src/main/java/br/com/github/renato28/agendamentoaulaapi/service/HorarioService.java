package br.com.github.renato28.agendamentoaulaapi.service;

import br.com.github.renato28.agendamentoaulaapi.dto.HorarioRequetDTO;
import br.com.github.renato28.agendamentoaulaapi.exceptions.ProfessorNaoEncontradoException;
import br.com.github.renato28.agendamentoaulaapi.model.Horario;
import br.com.github.renato28.agendamentoaulaapi.model.StatusHorario;
import br.com.github.renato28.agendamentoaulaapi.model.Usuario;
import br.com.github.renato28.agendamentoaulaapi.repository.HorarioRepository;
import br.com.github.renato28.agendamentoaulaapi.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HorarioService {

    private final HorarioRepository horarioRepository;
    private final UsuarioRepository usuarioRepository;

    public Horario cadastrar(HorarioRequetDTO dto) {

        Usuario professor = usuarioRepository.findById(dto.getProfessorId())
                .orElseThrow(() -> new ProfessorNaoEncontradoException("Professor não encontrado"));

        Horario horario = Horario.builder()
                .professor(professor)
                .inicio(dto.getInicio())
                .fim(dto.getFim())
                .statusHorario(StatusHorario.DISPONIVEL)
                .build();

        return horarioRepository.save(horario);
    }
}
