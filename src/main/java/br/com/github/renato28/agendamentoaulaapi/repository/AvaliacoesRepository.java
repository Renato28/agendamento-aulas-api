package br.com.github.renato28.agendamentoaulaapi.repository;


import br.com.github.renato28.agendamentoaulaapi.model.Avaliacoes;
import br.com.github.renato28.agendamentoaulaapi.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AvaliacoesRepository extends JpaRepository<Avaliacoes, Long> {

    List<Avaliacoes> findByAluno(Usuario aluno);

    List<Avaliacoes> findByProfessor(Usuario professor);

    List<Avaliacoes> findByNota(Integer nota);

    List<Avaliacoes> findByAlunoAndProfessor(Usuario aluno, Usuario professor);
}
