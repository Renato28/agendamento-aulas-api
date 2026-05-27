package br.com.github.renato28.agendamentoaulaapi.service;

import br.com.github.renato28.agendamentoaulaapi.dto.CursoRequestDTO;
import br.com.github.renato28.agendamentoaulaapi.exceptions.CursoNaoEncontradoException;
import br.com.github.renato28.agendamentoaulaapi.exceptions.ProfessorNaoEncontradoException;
import br.com.github.renato28.agendamentoaulaapi.model.Curso;
import br.com.github.renato28.agendamentoaulaapi.model.Usuario;
import br.com.github.renato28.agendamentoaulaapi.repository.CursoRepository;
import br.com.github.renato28.agendamentoaulaapi.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@Service
@RequiredArgsConstructor

public class CursoService {

    private final CursoRepository cursoRepository;
    private final UsuarioRepository usuarioRepository;

    public void cadastrar(CursoRequestDTO dto) {

        Usuario professor = usuarioRepository.findById(dto.getProfessorId())
                .orElseThrow(() -> new ProfessorNaoEncontradoException("professor não encontrado"));

        Curso curso = Curso.builder().nome(dto.getNome())
                .descricao(dto.getDescricao())
                .duracao(dto.getDuracao())
                .preco(dto.getPreco()).professor(professor).build();
        cursoRepository.save(curso);
    }

    @Transactional
    public void atualizar(Long agendamentoId, CursoRequestDTO dto) {

        // Busca o agendamento pelo ID
        Curso curso = cursoRepository.findById(agendamentoId)
                .orElseThrow(() ->
                        new CursoNaoEncontradoException("curso não encontrado"));

        Usuario professor = usuarioRepository
                .findById(dto.getProfessorId())
                .orElseThrow(() ->
                        new ProfessorNaoEncontradoException("professor não encontrado"));


        curso.setNome(dto.getNome());
        curso.setDescricao(dto.getDescricao());
        curso.setDuracao(dto.getDuracao());
        curso.setPreco(dto.getPreco());
        curso.setProfessor(professor);

        cursoRepository.save(curso);


    }

    public Curso buscarPorId(Long id) {

        return cursoRepository.findById(id)
                .orElseThrow(() ->
                        new CursoNaoEncontradoException("curso não encontrado"));
    }


}