package br.com.github.renato28.agendamentoaulaapi.service;

import br.com.github.renato28.agendamentoaulaapi.dto.CursoRequestDTO;
import br.com.github.renato28.agendamentoaulaapi.exceptions.ProfessorNaoEncontrado;
import br.com.github.renato28.agendamentoaulaapi.model.Curso;
import br.com.github.renato28.agendamentoaulaapi.model.Usuario;
import br.com.github.renato28.agendamentoaulaapi.repository.CursoRepository;
import br.com.github.renato28.agendamentoaulaapi.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class CursoService {

    private final CursoRepository cursoRepository;
    private final UsuarioRepository usuarioRepository;

    public Curso cadastrar(CursoRequestDTO dto) {

        Usuario professor = usuarioRepository.findById(dto.getProfessorId())
                .orElseThrow(() -> new ProfessorNaoEncontrado("professor não encontrado"));

        Curso curso = Curso.builder().nome(dto.getNome())
                .descricao(dto.getDescricao())
                .duracao(dto.getDuracao())
                .preco(dto.getPreco()).professor(professor).build();
        return cursoRepository.save(curso);
    }


}
